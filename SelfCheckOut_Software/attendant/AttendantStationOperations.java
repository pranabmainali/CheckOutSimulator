package ca.ucalgary.seng300.selfcheckout.attendant;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationPhases.minorPhase;

public class AttendantStationOperations {
	private AttendantStationLogic asl;
	
	public AttendantStationOperations (AttendantStationLogic attendantStationLogic) {
		asl = attendantStationLogic;
	}
	
	//=============================================================================================
	// Methods that simulate the attendant performing actions
	//=============================================================================================
	
	public boolean addPaper(int idx) {
		SelfCheckoutStation station = asl.scs.get(idx);
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		
		if (logic.scsp.getPhase() == minorPhase.MAINTENANCE) {
			try {
				station.printer.addPaper(AttendantStationLogic.PAPER_ROLL);
				return true;
			} catch (OverloadException e) {
				// Should never happen
			}
		}
			
		return false;
	}
	
	public boolean addInk(int idx) {
		SelfCheckoutStation station = asl.scs.get(idx);
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		
		if (logic.scsp.getPhase() == minorPhase.MAINTENANCE) {
			try {
				station.printer.addInk(AttendantStationLogic.INK_CARTRIDGE);
				return true;
			} catch (OverloadException e) {
				// Should never happen
			}			
		}
			
		return false;
	}
	
	public boolean emptyCoins(int idx) {
		SelfCheckoutStation station = asl.scs.get(idx);
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		
		if (logic.scsp.getPhase() == minorPhase.MAINTENANCE) {
			station.coinStorage.unload();
			return true;
		}
		
		return false;
	}
	
	public boolean emptyBanknotes(int idx) {
		SelfCheckoutStation station = asl.scs.get(idx);
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		
		if (logic.scsp.getPhase() == minorPhase.MAINTENANCE) {
			station.banknoteStorage.unload();
			return true;
		}
		
		return false;
	}
	
	public boolean refillCoins(int idx) {
		SelfCheckoutStation station = asl.scs.get(idx);
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		
		if (logic.scsp.getPhase() == minorPhase.MAINTENANCE) {
			for (BigDecimal denom : station.coinDenominations) {
				CoinDispenser disp = station.coinDispensers.get(denom);
				while (disp.hasSpace() == true) {
					try {
						disp.load(new Coin(Currency.getInstance("CAD"), denom));
					} catch (SimulationException | OverloadException e) {
						// Should never happen
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public boolean refillBanknotes(int idx) {
		SelfCheckoutStation station = asl.scs.get(idx);
		SelfCheckoutStationLogic logic = asl.scsl.get(idx);
		
		if (logic.scsp.getPhase() == minorPhase.MAINTENANCE) {
			for (int denom : station.banknoteDenominations) {
				BanknoteDispenser disp = station.banknoteDispensers.get(denom); 
				while (disp.size() < disp.getCapacity()) {
					try {
						disp.load(new Banknote(Currency.getInstance("CAD"), denom));
					} catch (OverloadException e) {
						// Should never happen
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
}