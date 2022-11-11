package SelfCheckOut_Software.software.payment;

import SelfCheckOut_Hardware.devices.BanknoteSlot;
import SelfCheckOut_Hardware.devices.SelfCheckoutStation;
import SelfCheckOut_Hardware.devices.observers.BanknoteSlotObserver;

import SelfCheckOut_Software.software.SelfCheckoutStationObserverLogic;

public class BanknoteOutputObserverLogic extends SelfCheckoutStationObserverLogic
	implements BanknoteSlotObserver {
	private SelfCheckoutStation scs;

	private FinishCheckoutLogic fcl;
	
	public BanknoteOutputObserverLogic(SelfCheckoutStation selfCheckoutStation,
			FinishCheckoutLogic finishCheckoutLogic) {
		scs = selfCheckoutStation;
		fcl = finishCheckoutLogic;
		
		startUp();
	}

	public void startUp() {
		scs.banknoteOutput.attach(this);
	}
	
	public void shutDown() {
		scs.banknoteOutput.detach(this);
	}
	
	@Override
	public void banknoteInserted(BanknoteSlot slot) {}

	@Override
	public void banknotesEjected(BanknoteSlot slot) {}

	@Override
	public void banknoteRemoved(BanknoteSlot slot) {
		fcl.returnBanknote();
	}
}