package SelfCheckOut_Software.software.additems;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationPhases.minorPhase;

public class AddItemsFacade {
	private SelfCheckoutStation scs;
	private SelfCheckoutStationLogic scsl;
	protected BaggingAreaTimer bat;
	
	private BaggingAreaObserverLogic baol;
	public BarcodeScannerObserverLogic bsol;
	private ScanningAreaObserverLogic saol;
	
	private final int REPEAT_WEIGH = 200;
	
	/**
	 * List representing the items scanned into the cart.
	 * Data stored as <product, <onScale, weight>>.
	 */
	protected List<SimpleEntry<Product, SimpleEntry<Boolean, Double>>> shoppingCart = new ArrayList<>();
	
	/**
	 * Total weight expected on the baggingArea scale.
	 */
	protected double totalWeight = 0;
	
	/**
	 * Weight at last call to ElectronicScaleObserverLogic.weightChanged().
	 * Used to predict when the sensitivity of the scale would cause another event.
	 */
	protected double lastWeight = 0;
	
	/**
	 * Flag to track state of the bagging area scale
	 */
	protected boolean scaleCorrect = true, scaleOverloaded = false;
	
	/**
	 * Flag to track state of the scanning area scale
	 */
	public boolean scanOverloaded = false;
	
	public AddItemsFacade(SelfCheckoutStation selfCheckoutStation, SelfCheckoutStationLogic selfCheckoutStationLogic) {
		scs = selfCheckoutStation;
		scsl = selfCheckoutStationLogic;
		
		bat = new BaggingAreaTimer(scsl);
		bat.start();
		bsol = new BarcodeScannerObserverLogic(scs, scsl, this);
		baol = new BaggingAreaObserverLogic(scs, scsl, this);
		saol = new ScanningAreaObserverLogic(scs, this);
	}
	
	public void startUp() {
		baol.startUp();
		bsol.startUp();
		saol.startUp();
	}
	
	public void shutDown() {
		baol.shutDown();
		bsol.shutDown();
		saol.shutDown();
	}
	
	/**
	 * Close function, which should rarely be used, ensures that the bagging area timer is properly shut down.
	 */
	public void close() {
		synchronized (bat.lock) {
			bat.shutDown = true;
			bat.interrupt();
		}
		
		try {
			bat.join();
		} catch (InterruptedException e) {
			// Should never happen
		}
	}
	
	/**
	 * Called to reset the internal state of the AddItemsFacade for a new customer.
	 */
	public void resetState() {
		shoppingCart = new ArrayList<>();
		totalWeight = 0;
		// Other fields should not be reset.
		// They will automatically become the correct values based on the new expected weight.
	}
	
	/**
	 * Weighs and adds a plu item to the cart.
	 * 
	 * @param plu item to add to cart
	 * @return true if plu item was successfully added, false otherwise
	 */
	public boolean addPLUItem(PriceLookupCode plu) {		
		if (scaleCorrect == false) {
			scsl.scsi.printInfoToCustomer("Please ensure everything is correctly placed in bagging area before adding more items");
			return false;
		}

		if (scanOverloaded == true) {
			scsl.scsi.printInfoToCustomer("Please weigh less items at once");
			return false;
		}

		PLUCodedProduct curProduct = ProductDatabases.PLU_PRODUCT_DATABASE.get(plu);
		if (curProduct == null) {
			scsl.scsi.printInfoToCustomer("Entered PLU not found");
			return false;
		}
		
		double minWeight = Double.MAX_VALUE;
		
		// read current weight multiple times to try and minimize the error
		for (int i = 0; i < REPEAT_WEIGH; ++i) {
			try {
				double tempWeight = scs.scanningArea.getCurrentWeight();
				if (tempWeight < minWeight) {
					minWeight = tempWeight;
				}
			} catch (OverloadException e) {
				// Should never happen
			}
		}
		
		scsl.pf.addCost(curProduct.getPrice().multiply(new BigDecimal(minWeight)));
			
		// items that would cause the baggingArea scale to go into overload should not be placed
		//   onto the baggingArea scale and should be left in the cart for the attendant to verify
		if ((totalWeight + minWeight) <= scs.baggingArea.getWeightLimit()) {
			addToCart(curProduct, true, minWeight);
			totalWeight += minWeight;
				
			if ((totalWeight - lastWeight) > (scs.baggingArea.getSensitivity() + SelfCheckoutStationLogic.SCALE_PRECISION)) {
				scaleCorrect = false;
				scsl.scsp.changePhase(minorPhase.PLACE_ITEM);
				
				synchronized (bat.lock) {
					bat.runTimer = true;
				}
			} else {
				scsl.scsp.changePhase(minorPhase.SCAN_ITEM);
			}
		} else {
			addToCart(curProduct, false, minWeight);
			scsl.scsi.printInfoToCustomer("Scanned item should be left in cart; please wait for attendant verification");
			scsl.scsp.changePhase(minorPhase.SCAN_ITEM);
			scsl.scsp.blockStation();
		}
		scsl.gf.buildGUIList();
		return true;
	}
	
	/**
	 * Add Product to cart.
	 * The cart at this stage is a list so multiplicity will not be taken into account
	 * 
	 * @param product to add to cart
	 * @param onScale boolean indicating if item should be expected on scale
	 * @param weight weight of item
	 */
	public void addToCart(Product product, boolean onScale, double weight) {
		shoppingCart.add(buildCartListing(product, onScale, weight));
		scsl.gf.buildGUIList();
	}
	
	/**
	 * Removes the most recently added item from being considered in the expected weight
	 */
	public void skipBaggingCurProd() {
		SimpleEntry<Product, SimpleEntry<Boolean, Double>> lastScan = shoppingCart.get(shoppingCart.size() - 1);
		if (get2nd(lastScan) == true) {
			shoppingCart.set(shoppingCart.size() - 1, buildCartListing(get1st(lastScan), false, get3rd(lastScan)));
			totalWeight -= get3rd(lastScan);
			scsl.scsp.blockStation();
		}
	}
	
	/**
	 * Removes the product from the cart, and does the corresponding calculations for expected weight and cost.
	 * If the product to remove is of type PLUCodedProduct, then it will remove all instances of said product from the cart,
	 *   but if the product is of type BarcodedProduct, then it will remove the first occurrence and print information
	 *   regarding whether or not the removed item should come off the bagging area scale.
	 * 
	 * @param product to remove
	 * @return true if product was removed, false otherwise
	 */
	public boolean removeFromCart(Product product) {
		boolean ret = false;
		
		if (product instanceof PLUCodedProduct) {
			ListIterator<SimpleEntry<Product, SimpleEntry<Boolean, Double>>> it = shoppingCart.listIterator();
			while (it.hasNext()) {
				SimpleEntry<Product, SimpleEntry<Boolean, Double>> curProd = it.next();
				
				if (get1st(curProd) == product) {
					if (get2nd(curProd) == true) {
						totalWeight -= get3rd(curProd);
					}
					
					scsl.pf.subCost(product.getPrice().multiply(new BigDecimal(get3rd(curProd))));
					
					it.remove();
					ret = true;
				}
			}
		} else {
			ListIterator<SimpleEntry<Product, SimpleEntry<Boolean, Double>>> it = shoppingCart.listIterator();
			while (it.hasNext()) {
				SimpleEntry<Product, SimpleEntry<Boolean, Double>> curProd = it.next();
				
				if (get1st(curProd) == product) {
					if (get2nd(curProd) == true) {
						totalWeight -= get3rd(curProd);
						scsl.scsi.printInfoToCustomer("Item should be removed from the bagging area");
					} else {
						scsl.scsi.printInfoToCustomer("Item should not be removed from the bagging area");
					}
					
					scsl.pf.subCost(product.getPrice());
					
					it.remove();
					ret = true;
					break;
				}
			}
		}
		scsl.gf.buildGUIList();
		return ret;
	}
	
	/**
	 * Update the expected totalWeight based on the new weight on the baggingArea scale.
	 * Note that this should only be called after the attendant has verified that everything is correct.
	 */
	public void overrideExpectedWeight() {
		double minWeight = Double.MAX_VALUE;
	
		// read current weight multiple times to try and minimize the error
		for (int i = 0; i < REPEAT_WEIGH; ++i) {
			try {
				double tempWeight = scs.baggingArea.getCurrentWeight();
				if (tempWeight < minWeight) {
					minWeight = tempWeight;
				}
			} catch (OverloadException e) {
				// Should never happen
			}
		}
		
		if (bat.runTimer == true) {
			bat.interrupt();
		}
		
		totalWeight = minWeight;
		scaleCorrect = true;
		scsl.gf.buildGUIList();
	}
	
	public boolean getScaleCorrect() {
		return scaleCorrect;
	}
	
	public boolean getScaleOverloaded() {
		return scaleOverloaded;
	}
	
	public boolean getScanOverloaded() {
		return scanOverloaded;
	}
	
	public double getTotalWeight() {
		return totalWeight;
	}
	
	public List<SimpleEntry<Product, SimpleEntry<Boolean, Double>>> getCart() {
		return shoppingCart;
	}
	
	public Map<Product, Double> getCartCollapse() {
		Map<Product, Double> ret = new LinkedHashMap<>();
		
		for (SimpleEntry<Product, SimpleEntry<Boolean, Double>> curListing : shoppingCart) {
			Product curProd = get1st(curListing);
			Double curWeight = get3rd(curListing);
			
			if (curProd instanceof BarcodedProduct) {
				if (ret.get(curProd) != null) {
					double cnt = ret.get(curProd);
					ret.put(curProd, cnt + 1);
				} else {
					ret.put(curProd, 1.0);
				}
			} else {
				if (ret.get(curProd) != null) {
					double sumWeight = ret.get(curProd);
					ret.put(curProd, sumWeight + curWeight);
				} else {
					ret.put(curProd, curWeight);
				}
			}
		}
		
		return ret;
	}
	
	private SimpleEntry<Product, SimpleEntry<Boolean, Double>> buildCartListing(Product product, boolean onScale, double weight) {
		return new SimpleEntry<Product, SimpleEntry<Boolean, Double>>(product, new SimpleEntry<>(onScale, weight));
	}
	
	public Product get1st(SimpleEntry<Product, SimpleEntry<Boolean, Double>> listing) {
		return listing.getKey();
	}
	
	public Boolean get2nd(SimpleEntry<Product, SimpleEntry<Boolean, Double>> listing) {
		return listing.getValue().getKey();
	}
	
	public Double get3rd(SimpleEntry<Product, SimpleEntry<Boolean, Double>> listing) {
		return listing.getValue().getValue();
	}

	public Product getLastItemAdded() {

		if (this.shoppingCart.isEmpty()) return null;

		return this.shoppingCart.get(this.shoppingCart.size() - 1).getKey();
	}
}