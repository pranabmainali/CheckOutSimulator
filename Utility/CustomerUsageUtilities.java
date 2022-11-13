package Utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SelfCheckOut_Hardware.Banknote;
import SelfCheckOut_Hardware.BarcodedItem;
import SelfCheckOut_Hardware.Card;
import SelfCheckOut_Hardware.ChipFailureException;
import SelfCheckOut_Hardware.Coin;
import SelfCheckOut_Hardware.Item;
import SelfCheckOut_Hardware.MagneticStripeFailureException;
import SelfCheckOut_Hardware.TapFailureException;
import SelfCheckOut_Hardware.PLUCodedItem;
import SelfCheckOut_Hardware.devices.BanknoteSlot;
import SelfCheckOut_Hardware.devices.BarcodeScanner;
import SelfCheckOut_Hardware.devices.CardReader;
import SelfCheckOut_Hardware.devices.CoinSlot;
import SelfCheckOut_Hardware.devices.CoinTray;
import SelfCheckOut_Hardware.external.ProductDatabases;
import SelfCheckOut_Hardware.products.BarcodedProduct;
import SelfCheckOut_Hardware.products.PLUCodedProduct;
import SelfCheckOut_Hardware.products.Product;

import SelfCheckOut_Software.software.SelfCheckoutStationLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationPhases.minorPhase;

public class CustomerUsageUtilities {
	public static void scanItem(BarcodedItem item, BarcodeScanner scanner, SelfCheckoutStationLogic scsl) {
		int curSize = scsl.aif.getCart().size();
		
		while (scsl.aif.getCart().size() == curSize) {
			scanner.scan(item);
		}
	}
	
	public static void scanItem(BarcodedItem item, BarcodeScanner scanner, SelfCheckoutStationLogic scsl, int rep) {
		int curSize = scsl.aif.getCart().size();
		
		for (int i = 0; i < rep; ++i) {
			if (scsl.aif.getCart().size() == curSize) {
				scanner.scan(item);
			}
		}
	}
	
	public static void insertBanknote(Banknote banknote, BanknoteSlot slot, SelfCheckoutStationLogic scsl) throws Exception {
		BigDecimal curFunds = scsl.pf.getTotalFunds();
		
		slot.accept(banknote);
		while (scsl.pf.getTotalFunds().compareTo(curFunds) == 0
				&& !(scsl.scsp.getPhase() == minorPhase.RETURN_CHANGE || scsl.scsp.getPhase() == minorPhase.WAIT)) {
			slot.removeDanglingBanknotes();
			slot.accept(banknote);
		}
	}
	
	public static void insertBanknote(Banknote banknote, BanknoteSlot slot, SelfCheckoutStationLogic scsl, int rep) throws Exception {
		BigDecimal curFunds = scsl.pf.getTotalFunds();
		
		slot.accept(banknote);
		for (int i = 0; i < rep; ++i) {
			if (scsl.pf.getTotalFunds().compareTo(curFunds) == 0
					&& !(scsl.scsp.getPhase() == minorPhase.RETURN_CHANGE || scsl.scsp.getPhase() == minorPhase.WAIT)) {
				slot.removeDanglingBanknotes();
				slot.accept(banknote);
			}
		}
	}
	
	public static void insertCoin(Coin coin, CoinSlot slot, CoinTray tray, SelfCheckoutStationLogic scsl) throws Exception {
		BigDecimal curFunds = scsl.pf.getTotalFunds();
		
		slot.accept(coin);
		while (scsl.pf.getTotalFunds().compareTo(curFunds) == 0
				&& !(scsl.scsp.getPhase() == minorPhase.RETURN_CHANGE || scsl.scsp.getPhase() == minorPhase.WAIT)) {
			tray.collectCoins();
			slot.accept(coin);
		}
	}
	
	public static void insertCoin(Coin coin, CoinSlot slot, CoinTray tray, SelfCheckoutStationLogic scsl, int rep) throws Exception {
		BigDecimal curFunds = scsl.pf.getTotalFunds();
		
		slot.accept(coin);
		for (int i = 0; i < rep; ++i) {
			if (scsl.pf.getTotalFunds().compareTo(curFunds) == 0
					&& !(scsl.scsp.getPhase() == minorPhase.RETURN_CHANGE || scsl.scsp.getPhase() == minorPhase.WAIT)) {
				tray.collectCoins();
				slot.accept(coin);
			}
		}
	}
	
	public static boolean checkCart(Map<Product, Double> generalCart, Item... itemArray) {
		Map<BarcodedProduct, Integer> barcodecart = new HashMap<>();
		Map<PLUCodedProduct, Double> plucart = new HashMap<>();
		Map<PLUCodedProduct, Integer> plumult = new HashMap<>();
		
		for (Map.Entry<Product, Double> prod : generalCart.entrySet()) {
			if (prod.getKey() instanceof BarcodedProduct) {
				barcodecart.put((BarcodedProduct) prod.getKey(), (int) Math.round(prod.getValue()));
			} else {
				plucart.put((PLUCodedProduct) prod.getKey(), prod.getValue());
			}
		}
		
		for (Item item : itemArray) {
			if (item instanceof BarcodedItem) {
				BarcodedProduct prod = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(((BarcodedItem) item).getBarcode());
				
				if (barcodecart.containsKey(prod) == true) {
					if (barcodecart.get(prod) > 1) {
						barcodecart.put(prod, barcodecart.get(prod) - 1);
					} else {
						barcodecart.remove(prod);
					}
				} else {
					return false;
				}
			} else {
				PLUCodedProduct prod = ProductDatabases.PLU_PRODUCT_DATABASE.get(((PLUCodedItem) item).getPLUCode());
				
				if (plucart.containsKey(prod) == true) {
					plucart.put(prod,  plucart.get(prod) - ((PLUCodedItem) item).getWeight());
					if (plumult.containsKey(prod) == true) {
						plumult.put(prod, plumult.get(prod) + 1);
					} else {
						plumult.put(prod, 1);
					}
				} else {
					return false;
				}
			}
		}
		
		if (barcodecart.size() != 0) {
			return false;
		}
		
		for (PLUCodedProduct prod : plucart.keySet()) {
			if (plucart.get(prod) > (plumult.get(prod) * SelfCheckoutStationLogic.SCALE_PRECISION)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean checkBanknotes(BanknoteSlot slot, int... correctArray) {
		for (int i = 0; i < correctArray.length; ++i) {
			Banknote[] banknote = slot.removeDanglingBanknotes();
			if (banknote[0].getValue() != correctArray[i]) {
				return false;
			}
		}
		
		if (slot.hasSpace() == true) {
			return true;
		}
		
		return false;
	}
	
	public static boolean checkCoins(List<Coin> coins, BigDecimal... correctArray) {
		ArrayList<BigDecimal> correct = new ArrayList<>(Arrays.asList(correctArray));
		
		for (Coin curCoin : coins) {
			if (curCoin != null) {
				int idx = correct.indexOf(curCoin.getValue());
				
				if (idx == -1) {
					return false;
				}
				
				correct.remove(idx);
			}
		}
		
		if (correct.size() == 0) {
			return true;
		}
		
		return false;
	}
	
	public static void swipeCard(Card card, CardReader cardReader, SelfCheckoutStationLogic scsl) throws Exception {
		BigDecimal curFunds = scsl.pf.getTotalFunds();
		
		while (scsl.pf.getTotalFunds().compareTo(curFunds) == 0
				&& !(scsl.scsp.getPhase() == minorPhase.RETURN_CHANGE || scsl.scsp.getPhase() == minorPhase.WAIT)) {
			boolean error = true;
			
			while (error == true) {
				try {
					cardReader.swipe(card);
					error = false;
				} catch (MagneticStripeFailureException e) {
					// automatically loop
				}
			}
		}
	}
	
	public static boolean swipeCardError(Card card, CardReader cardReader, SelfCheckoutStationLogic scsl, int rep, String str) throws Exception {
		BigDecimal curFunds = scsl.pf.getTotalFunds();
		
		for (int i = 0; i < rep; ++i) {
			if (scsl.pf.getTotalFunds().compareTo(curFunds) == 0
					&& !(scsl.scsp.getPhase() == minorPhase.RETURN_CHANGE || scsl.scsp.getPhase() == minorPhase.WAIT)) {
				boolean error = true;
				
				while (error == true) {
					try {
						cardReader.swipe(card);
						error = false;
					} catch (MagneticStripeFailureException e) {
						// automatically loop
					}
				}
				
				if (scsl.scsi.getLastInfoToCustomer() == str) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void swipeMemberCard(Card card, CardReader cardReader, SelfCheckoutStationLogic scsl) throws Exception {
		while (scsl.pf.getMemberNumber() == null) {
			boolean error = true;
			
			while (error == true) {
				try {
					cardReader.swipe(card);
					error = false;
				} catch (MagneticStripeFailureException e) {
					// automatically loop
				}
			}
		}
	}
	
	public static void swipeMemberCard(Card card, CardReader cardReader, SelfCheckoutStationLogic scsl, int rep) throws Exception {
		for (int i = 0; i < rep; ++i) {
			if (scsl.pf.getMemberNumber() == null) {
				boolean error = true;
				
				while (error == true) {
					try {
						cardReader.swipe(card);
						error = false;
					} catch (MagneticStripeFailureException e) {
						// automatically loop
					}
				}
			}
		}
	}
	
	public static boolean swipeMemberCardError(Card card, CardReader cardReader, SelfCheckoutStationLogic scsl, int rep, String str) throws Exception {
		for (int i = 0; i < rep; ++i) {
			if (scsl.pf.getMemberNumber() == null) {
				boolean error = true;
				
				while (error == true) {
					try {
						cardReader.swipe(card);
						error = false;
					} catch (MagneticStripeFailureException e) {
						// automatically loop
					}
				}
				
				if (scsl.scsi.getLastInfoToCustomer() == str) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void tapCard(Card card, CardReader cardReader, SelfCheckoutStationLogic scsl) throws Exception {
		BigDecimal curFunds = scsl.pf.getTotalFunds();
		
		while (scsl.pf.getTotalFunds().compareTo(curFunds) == 0
				&& !(scsl.scsp.getPhase() == minorPhase.RETURN_CHANGE || scsl.scsp.getPhase() == minorPhase.WAIT)) {
			boolean error = true;
			
			while (error == true) {
				try {
					cardReader.tap(card);
					error = false;
				} catch (ChipFailureException | TapFailureException e) {
					// automatically loop
				}
			}
		}
	}
	
	public static void tapCard(Card card, CardReader cardReader, SelfCheckoutStationLogic scsl, int rep) throws Exception {
		BigDecimal curFunds = scsl.pf.getTotalFunds();
		
		for (int i = 0; i < rep; ++i) {
			if (scsl.pf.getTotalFunds().compareTo(curFunds) == 0
					&& !(scsl.scsp.getPhase() == minorPhase.RETURN_CHANGE || scsl.scsp.getPhase() == minorPhase.WAIT)) {
				boolean error = true;
				
				while (error == true) {
					try {
						cardReader.tap(card);
						error = false;
					} catch (ChipFailureException | TapFailureException e) {
						// automatically loop
					}
				}
			}
		}
	}
	
	public static boolean tapCardError(Card card, CardReader cardReader, SelfCheckoutStationLogic scsl, int rep, String str) throws Exception {
		BigDecimal curFunds = scsl.pf.getTotalFunds();
		
		for (int i = 0; i < rep; ++i) {
			if (scsl.pf.getTotalFunds().compareTo(curFunds) == 0
					&& !(scsl.scsp.getPhase() == minorPhase.RETURN_CHANGE || scsl.scsp.getPhase() == minorPhase.WAIT)) {
				boolean error = true;
				
				while (error == true) {
					try {
						cardReader.tap(card);
						error = false;
					} catch (ChipFailureException | TapFailureException e) {
						// automatically loop
					}
				}
				
				if (scsl.scsi.getLastInfoToCustomer() == str) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean tapMemberCardError(Card card, CardReader cardReader, SelfCheckoutStationLogic scsl, int rep, String str) throws Exception {
		for (int i = 0; i < rep; ++i) {
			if (scsl.pf.getMemberNumber() == null) {
				boolean error = true;
				
				while (error == true) {
					try {
						cardReader.tap(card);
						error = false;
					} catch (ChipFailureException | TapFailureException e) {
						// automatically loop
					}
				}
				
				if (scsl.scsi.getLastInfoToCustomer() == str) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void insertCard(Card card, String pin, CardReader cardReader, SelfCheckoutStationLogic scsl) throws Exception {
		BigDecimal curFunds = scsl.pf.getTotalFunds();
		
		while (scsl.pf.getTotalFunds().compareTo(curFunds) == 0
				&& !(scsl.scsp.getPhase() == minorPhase.RETURN_CHANGE || scsl.scsp.getPhase() == minorPhase.WAIT)) {
			boolean error = true;
			
			while (error == true) {
				try {
					cardReader.insert(card, pin);
					error = false;
				} catch (ChipFailureException e) {
					cardReader.remove();
					// automatically loop
				}
			}
			
			cardReader.remove();
		}
	}
}