package SelfCheckOut_Software.software.additems;

import SelfCheckOut_Hardware.Barcode;
import SelfCheckOut_Hardware.devices.BarcodeScanner;
import SelfCheckOut_Hardware.devices.SelfCheckoutStation;
import SelfCheckOut_Hardware.devices.observers.BarcodeScannerObserver;
import SelfCheckOut_Hardware.external.ProductDatabases;
import SelfCheckOut_Hardware.products.BarcodedProduct;

import SelfCheckOut_Software.software.SelfCheckoutStationLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationObserverLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationPhases.minorPhase;

public class BarcodeScannerObserverLogic extends SelfCheckoutStationObserverLogic
	implements BarcodeScannerObserver {
	private SelfCheckoutStation scs;
	private SelfCheckoutStationLogic scsl;
	private AddItemsFacade aif;
	
	public BarcodeScannerObserverLogic(SelfCheckoutStation selfCheckoutStation,
			SelfCheckoutStationLogic selfCheckoutStationLogic, AddItemsFacade addItemsFacade) {
		scs = selfCheckoutStation;
		scsl = selfCheckoutStationLogic;
		aif = addItemsFacade;
		
		startUp();
	}
	
	public void startUp() {
		scs.mainScanner.attach(this);
		scs.handheldScanner.attach(this);
	}
	
	public void shutDown() {
		scs.mainScanner.detach(this);
		scs.handheldScanner.detach(this);
	}
	
	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		if (aif.scaleCorrect == false) {
			scsl.scsi.printInfoToCustomer("Please ensure everything is correctly placed in bagging area before adding more items");
			return;
		}
		
		boolean isMain = (barcodeScanner == scs.mainScanner);
		BarcodedProduct curProduct = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		
		if (curProduct != null) {
			scsl.pf.addCost(curProduct.getPrice());
			
			// handheldScanner items and items that would cause the baggingArea scale to go into overload should not be placed
			//   onto the baggingArea scale and should be left in the cart for the attendant to verify
			if (isMain == true && (aif.totalWeight + curProduct.getExpectedWeight()) <= scs.baggingArea.getWeightLimit()) {
				aif.addToCart(curProduct, true, curProduct.getExpectedWeight());
				aif.totalWeight += curProduct.getExpectedWeight();
				
				if ((aif.totalWeight - aif.lastWeight) > (scs.baggingArea.getSensitivity() + SelfCheckoutStationLogic.SCALE_PRECISION)) {
					aif.scaleCorrect = false;
					scsl.scsp.changePhase(minorPhase.PLACE_ITEM);
					
					// runTimer should theoretically never be true at this point, but because of synchronization delay between threads
					//   and the unrealistic speed of adding previous item to bagging area and scanning next item the timer would not
					//   be correctly started otherwise
					while (aif.bat.runTimer == true);
					synchronized (aif.bat.lock) {
						aif.bat.runTimer = true;
					}
				}
			} else {
				aif.addToCart(curProduct, false, curProduct.getExpectedWeight());
				scsl.scsi.printInfoToCustomer("Scanned item should be left in cart; please wait for attendant verification");
				scsl.scsp.blockStation();
			}
		}
		// else, silently ignore it (this is the same behavior BarcodeScanner exhibits)
	}
}
