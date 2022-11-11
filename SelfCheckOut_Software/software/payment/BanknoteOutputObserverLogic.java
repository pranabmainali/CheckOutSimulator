package SelfCheckOut_Software.software.payment;

import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.BanknoteSlotObserver;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationObserverLogic;

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