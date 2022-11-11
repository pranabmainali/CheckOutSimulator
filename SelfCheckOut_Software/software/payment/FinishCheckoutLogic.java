package ca.ucalgary.seng300.selfcheckout.software.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationPhases.minorPhase;

public class FinishCheckoutLogic {
	private SelfCheckoutStation scs;
	private SelfCheckoutStationLogic scsl;
	private PaymentFacade pf;
	private ReceiptPrinterObserverLogic rpol;
	
	/**
	 * The amount of change that must be returned to the customer.
	 * This will be calculated at the beginning of the RETURN_CHANGE phase, and will account
	 *   for rounding to the nearest nickel.
	 * This field should be decremented as change is successfully returned to the customer.   
	 */
	private BigDecimal changeToReturn = null;
	
	public FinishCheckoutLogic(SelfCheckoutStation selfCheckoutStation, SelfCheckoutStationLogic selfCheckoutStationLogic,
			PaymentFacade paymentFacade, ReceiptPrinterObserverLogic receiptPrinterObserverLogic) {
		scs = selfCheckoutStation;
		scsl = selfCheckoutStationLogic;
		pf = paymentFacade;
		rpol = receiptPrinterObserverLogic;
	}
	
	/**
	 * Check to see if the totalFunds is greater than or equal to totalCost.
	 * This method should be called after every successful addition of funds.
	 */
	public void completeCheckout() {
		scsl.gf.updatePaid(scsl.pf.totalFunds);
		scsl.gf.updateDue(scsl.pf.getTotalCost().subtract(scsl.pf.totalFunds));
		if (pf.totalFunds.compareTo(pf.getTotalCost()) >= 0) {
			changeToReturn = getRoundedChange();
			if (changeToReturn.compareTo(new BigDecimal("0.00")) > 0) {
				scsl.scsp.changePhase(minorPhase.RETURN_CHANGE);
				returnBanknote();
			} else {
				finishCheckout();
			}
		}
		// else, silently continue with no changes to the system
	}
	
	/**
	 * This method calculates the amount of change needed to be returned to the customer.
	 * This method works by first taking the raw difference between the totalFunds and then
	 *   multiplies by 2, rounds to one decimal, then divides by 2. This effectively rounds
	 *   to the nearest nickel.
	 * 
	 * @return amount of change to return to the customer, rounded to the nearest nickel
	 */
	protected BigDecimal getRoundedChange() {
		return pf.totalFunds.subtract(pf.getTotalCost()).multiply(new BigDecimal("2")).setScale(1, RoundingMode.HALF_UP).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
	}
	
	/**
	 * This method attempts to return a single banknote.
	 * This is because the output slot can only dispense a single banknote at a time.
	 * The observer will be notified when the banknote is removed, at which point this method will be called again.
	 */
	public void returnBanknote() {
		for (int i = scs.banknoteDenominations.length - 1; i >= 0; --i) {
			int curDenom = scs.banknoteDenominations[i];
			
			if (changeToReturn.compareTo(new BigDecimal(Integer.toString(curDenom))) >= 0
					&& scs.banknoteDispensers.get(curDenom).size() >= 1) {
				try {
					scs.banknoteDispensers.get(curDenom).emit();
					changeToReturn = changeToReturn.subtract(new BigDecimal(Integer.toString(curDenom))).setScale(2, RoundingMode.HALF_UP);
					return;
				} catch (Exception e) {
					// Should never happen
				}
			}
		}
		
		returnCoins();
	}
	
	/**
	 * This method attempts to return up to SelfCheckoutStation.COIN_TRAY_CAPACITY coins in change.
	 */
	private void returnCoins() {
		for (int i = 0; i < SelfCheckoutStation.COIN_TRAY_CAPACITY; ++i) {
			for (int j = scs.coinDenominations.size() - 1; j >= 0; --j) {
				BigDecimal curDenom = scs.coinDenominations.get(j);
				
				if (changeToReturn.compareTo(curDenom.setScale(2, RoundingMode.HALF_UP)) >= 0
						&& scs.coinDispensers.get(curDenom).size() >= 1) {
					try {
						scs.coinDispensers.get(curDenom).emit();
						changeToReturn = changeToReturn.subtract(curDenom).setScale(2, RoundingMode.HALF_UP);
						break;
					} catch (Exception e) {
						// Should never happen
					}
				}
			}
		}
		
		if (changeToReturn.compareTo(new BigDecimal("0.00")) > 0) {
			scsl.scsi.printInfoToCustomer("Unable to return exact change; see attendant remaining change");
			scsl.scsi.printInfoToAttendant("Customer is still owed $" + changeToReturn.setScale(2, RoundingMode.HALF_DOWN).toString() + " in change");
			finishCheckout();
		} else {
			finishCheckout();
		}
	}

	/**
	 * Method called to complete the checkout process, namely printing a receipt,
	 *   resetting variables to a fresh state, and setting phase back to minorPhase.WAIT.
	 */
	private void finishCheckout() {
		rpol.printReceipt(getRoundedChange());
		scsl.aif.resetState();
		scsl.pf.resetState();
		changeToReturn = null;
		scsl.scsp.changePhase(minorPhase.WAIT);
	}
}