package SelfCheckOut_Software.software.payment;

import SelfCheckOut_Hardware.devices.BanknoteStorageUnit;
import SelfCheckOut_Hardware.devices.CoinStorageUnit;
import SelfCheckOut_Hardware.devices.SelfCheckoutStation;
import SelfCheckOut_Hardware.devices.observers.BanknoteStorageUnitObserver;
import SelfCheckOut_Hardware.devices.observers.CoinStorageUnitObserver;

import SelfCheckOut_Software.software.SelfCheckoutStationLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationObserverLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationPhases.minorPhase;

public class CashStorageUnitObserverLogic extends SelfCheckoutStationObserverLogic
	implements BanknoteStorageUnitObserver, CoinStorageUnitObserver {
	private SelfCheckoutStation scs;
	private SelfCheckoutStationLogic scsl;
	private PaymentFacade pf;
	
	public CashStorageUnitObserverLogic(SelfCheckoutStation selfCheckoutStation,
			SelfCheckoutStationLogic selfCheckoutStationLogic, PaymentFacade paymentFacade) {
		scs = selfCheckoutStation;
		scsl = selfCheckoutStationLogic;
		pf = paymentFacade;
		
		startUp();
	}

	public void startUp() {
		scs.banknoteStorage.attach(this);
		scs.coinStorage.attach(this);
	}
	
	public void shutDown() {
		scs.banknoteStorage.detach(this);
		scs.coinStorage.detach(this);
	}
	
	@Override
	public void banknotesFull(BanknoteStorageUnit unit) {
		pf.banknotesFull = true;
		scs.banknoteInput.disable();
		scsl.scsi.printInfoToAttendant("Banknote storage is full");
		checkAllCashStorageFull();
	}

	@Override
	public void banknoteAdded(BanknoteStorageUnit unit) {}

	@Override
	public void banknotesLoaded(BanknoteStorageUnit unit) {
		if (unit.hasSpace() == false) {
			pf.banknotesFull = true;
			scs.banknoteInput.disable();
			scsl.scsi.printInfoToAttendant("Banknote storage is full");
		}
	}

	@Override
	public void banknotesUnloaded(BanknoteStorageUnit unit) {
		pf.banknotesFull = false;
	}

	@Override
	public void coinsFull(CoinStorageUnit unit) {
		pf.coinsFull = true;
		scs.coinSlot.disable();
		scsl.scsi.printInfoToAttendant("Coin storage is full");
		checkAllCashStorageFull();
	}

	@Override
	public void coinAdded(CoinStorageUnit unit) {}

	@Override
	public void coinsLoaded(CoinStorageUnit unit) {
		if (unit.hasSpace() == false) {
			pf.coinsFull = true;
			scs.coinSlot.disable();
			scsl.scsi.printInfoToAttendant("Coin storage is full");
		}
	}

	@Override
	public void coinsUnloaded(CoinStorageUnit unit) {
		pf.coinsFull = false;
	}
	
	/**
	 * When observer is notified due to one of the storage units being full, this method is called
	 *   to check if both storage units are now full. If this is the case, do not allow any more cash payments.
	 */
	private void checkAllCashStorageFull() {
		if (pf.banknotesFull == true && pf.coinsFull == true) {
			scsl.scsp.changePhase(minorPhase.PAY_SELECT);
			scsl.scsi.printInfoToCustomer("Currently cannot accept change at this station");
		}
	}
}