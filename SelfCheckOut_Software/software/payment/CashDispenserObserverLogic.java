package SelfCheckOut_Software.software.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.BanknoteDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.CoinDispenserObserver;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationObserverLogic;

public class CashDispenserObserverLogic extends SelfCheckoutStationObserverLogic
	implements BanknoteDispenserObserver, CoinDispenserObserver {
	private SelfCheckoutStation scs;
	private SelfCheckoutStationLogic scsl;
	
	public CashDispenserObserverLogic(SelfCheckoutStation selfCheckoutStation,
			SelfCheckoutStationLogic selfCheckoutStationLogic, PaymentFacade paymentFacade) {
		scs = selfCheckoutStation;
		scsl = selfCheckoutStationLogic;
		
		startUp();
	}

	public void startUp() {
		for (BanknoteDispenser dispenser : scs.banknoteDispensers.values()) {
			dispenser.attach(this);
		}
		
		for (CoinDispenser dispenser : scs.coinDispensers.values()) {
			dispenser.attach(this);
		}
	}
	
	public void shutDown() {
		for (BanknoteDispenser dispenser : scs.banknoteDispensers.values()) {
			dispenser.detach(this);
		}
		
		for (CoinDispenser dispenser : scs.coinDispensers.values()) {
			dispenser.detach(this);
		}
	}

	@Override
	public void moneyFull(BanknoteDispenser dispenser) {}  // this is required but never used by the hardware

	@Override
	public void banknotesEmpty(BanknoteDispenser dispenser) {
		scsl.scsi.printInfoToAttendant("Dispenser for " + getDispenser(dispenser) + " is empty");
	}

	@Override
	public void billAdded(BanknoteDispenser dispenser, Banknote banknote) {}  // this is required but never used by the hardware

	@Override
	public void banknoteRemoved(BanknoteDispenser dispenser, Banknote banknote) {}

	@Override
	public void banknotesLoaded(BanknoteDispenser dispenser, Banknote... banknotes) {}

	@Override
	public void banknotesUnloaded(BanknoteDispenser dispenser, Banknote... banknotes) {}
	
	@Override
	public void coinsFull(CoinDispenser dispenser) {}

	@Override
	public void coinsEmpty(CoinDispenser dispenser) {
		scsl.scsi.printInfoToAttendant("Dispenser for " + getDispenser(dispenser) + " is empty");
	}

	@Override
	public void coinAdded(CoinDispenser dispenser, Coin coin) {}

	@Override
	public void coinRemoved(CoinDispenser dispenser, Coin coin) {}

	@Override
	public void coinsLoaded(CoinDispenser dispenser, Coin... coins) {}

	@Override
	public void coinsUnloaded(CoinDispenser dispenser, Coin... coins) {}
	
	public String getDispenser(BanknoteDispenser dispenser) {
		for (Map.Entry<Integer, BanknoteDispenser> entry : scs.banknoteDispensers.entrySet()) {
			if (entry.getValue() == dispenser) {
				return Integer.toString(entry.getKey());
			}
		}
		
		return null;
	}
	
	public String getDispenser(CoinDispenser dispenser) {
		for (Map.Entry<BigDecimal, CoinDispenser> entry : scs.coinDispensers.entrySet()) {
			if (entry.getValue() == dispenser) {
				return entry.getKey().setScale(2, RoundingMode.HALF_UP).toString();
			}
		}
		
		return null;
	}
}