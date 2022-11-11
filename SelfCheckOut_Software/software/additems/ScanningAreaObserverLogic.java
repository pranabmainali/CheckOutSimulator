package SelfCheckOut_Software.software.additems;

import SelfCheckOut_Hardware.devices.ElectronicScale;
import SelfCheckOut_Hardware.devices.SelfCheckoutStation;
import SelfCheckOut_Hardware.devices.observers.ElectronicScaleObserver;

import SelfCheckOut_Software.software.SelfCheckoutStationObserverLogic;

public class ScanningAreaObserverLogic extends SelfCheckoutStationObserverLogic
	implements ElectronicScaleObserver {
	private SelfCheckoutStation scs;
	private AddItemsFacade aif;
	
	public ScanningAreaObserverLogic(SelfCheckoutStation selfCheckoutStation, AddItemsFacade addItemsFacade) {
		scs = selfCheckoutStation;
		aif = addItemsFacade;
		
		startUp();
	}
	
	public void startUp() {
		scs.scanningArea.attach(this);
	}
	
	public void shutDown() {
		scs.scanningArea.detach(this);
	}
	
	@Override
	public void weightChanged(ElectronicScale scale, double weightInGrams) {}

	@Override
	public void overload(ElectronicScale scale) {
		aif.scanOverloaded = true;
	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		aif.scanOverloaded = false;
	}
}