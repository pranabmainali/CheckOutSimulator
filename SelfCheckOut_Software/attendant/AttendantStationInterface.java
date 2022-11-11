package SelfCheckOut_Software.attendant;

import SelfCheckOut_Hardware.*;
import SelfCheckOut_Hardware.devices.SelfCheckoutStation;
import SelfCheckOut_Hardware.products.Product;

import SelfCheckOut_Software.software.SelfCheckoutStationLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationPhases.minorPhase;
import SelfCheckOut_Software.software.gui.Dialog;

public class AttendantStationInterface {
	private final AttendantStationLogic asl;
	private Dialog dialog;
	
	public AttendantStationInterface(AttendantStationLogic attendantStationLogic) {
		asl = attendantStationLogic;
	}
	
	//=============================================================================================
	// Methods the attendant can call via the GUI
	//=============================================================================================
	
	public boolean blockStation(int idx) {
		if (asl.loggedIn == false) {
			return false;
		}
		
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		if (logic.scsp.getPhase() != minorPhase.BLOCK) {
			logic.scsp.blockStation();
			return true;
		}
		
		return false;
	}
	
	public boolean unblockStation(int idx) {
		if (asl.loggedIn == false) {
			return false;
		}
		
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		if (logic.scsp.getPhase() == minorPhase.BLOCK) {
			logic.scsp.unblockStation();
			return true;
		}
		
		return false;
	}
	
	public boolean enterMaintenance(int idx) {
		if (asl.loggedIn == false) {
			return false;
		}
		
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		if (logic.scsp.getPhase() == minorPhase.WAIT) {
			logic.scsp.changePhase(minorPhase.MAINTENANCE);
			return true;
		}
		
		return false;
	}
	
	public boolean exitMaintenance(int idx) {
		if (asl.loggedIn == false) {
			return false;
		}
		
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		if (logic.scsp.getPhase() == minorPhase.MAINTENANCE) {
			logic.scsp.changePhase(minorPhase.WAIT);
			return true;
		}
		
		return false;
	}
	
	public boolean addProduct(int idx, PriceLookupCode plu) {
		if (asl.loggedIn == false) {
			return false;
		}
		
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		if (logic.scsp.getPhase() == minorPhase.SCAN_ITEM || logic.scsp.getPhase() == minorPhase.ENTER_PLU) {
			return logic.aif.addPLUItem(plu);
		}
		
		return false;
	}
	
	public boolean addProduct(int idx, Barcode barcode) {
		if (asl.loggedIn == false) {
			return false;
		}
		
		SelfCheckoutStation station = asl.scs.get(idx);
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		if (logic.scsp.getPhase() == minorPhase.SCAN_ITEM) {
			logic.aif.bsol.barcodeScanned(station.mainScanner, barcode);
			return true;
		}
		
		return false;
	}
	
	public boolean removeProduct(int idx, Product product) {
		if (asl.loggedIn == false) {
			return false;
		}
		
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		if (logic.scsp.getPhase() == minorPhase.SCAN_ITEM) {
			return logic.aif.removeFromCart(product);
		}
		
		return false;
	}
	
	public boolean startUp(int idx) {
		if (asl.loggedIn == false) {
			return false;
		}
		
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		if (logic.scsp.getPhase() == minorPhase.OFF) {
			logic.startUp();
			return true;
		}
		
		return false;
	}
	
	public boolean shutDown(int idx) {
		if (asl.loggedIn == false) {
			return false;
		}
		
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		if (logic.scsp.getPhase() == minorPhase.MAINTENANCE) {
			logic.shutDown();
			return true;
		}
		
		return false;
	}
	
	public boolean logIn(String id, String pass) {
		if (asl.loggedIn == false && asl.asd.isValidEmployee(id, pass)) {
			asl.loggedIn = true;
			return true;
		}
		
		return false;
	}
	
	public boolean logOut() {
		if (asl.loggedIn == true) {
			asl.loggedIn = false;
			return true;
		}
		
		return false;
	}

	public boolean approveWeight(int idx) {
		return unblockStation(idx);
	}

	public Product getLatestItem(int id) {
		return this.asl.getSCSL(id).scsi.getLastItemAdded();
	}

	public void informAttendant(String message) {
		if (this.dialog != null) dialog.dispose();
		dialog = new Dialog();
		dialog.setTitle("Attention, attendant!");
		dialog.setMessage(message);
		dialog.setSize(400, 300);
		dialog.setVisible(true);
	}

	public void killGui() {
		this.asl.gf.kill();
		if (this.dialog != null) dialog.dispose();
	}
}