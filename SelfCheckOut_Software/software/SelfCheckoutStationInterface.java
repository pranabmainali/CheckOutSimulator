package SelfCheckOut_Software.software;

import java.math.*;
import SelfCheckOut_Hardware.*;
import SelfCheckOut_Software.software.SelfCheckoutStationPhases.minorPhase;
import SelfCheckOut_Hardware.products.Product;
import javax.swing.*;

public class SelfCheckoutStationInterface {
	private final SelfCheckoutStationLogic scsl;
	
	/**
	 * Stores the most recently printed message. Used for debugging.
	 */
	private String customerStr = null, attendantStr = null;
	
	public SelfCheckoutStationInterface(SelfCheckoutStationLogic selfCheckoutStationLogic) {
		scsl = selfCheckoutStationLogic;
	}
	
	//=============================================================================================
	// Methods the user can call via the GUI
	//=============================================================================================
	
	//******************************
	// Waiting for customer phase
	//******************************
	
	/**
	 * Moves major phase from WAIT to ADD.
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean startAddPhase() {
		if (scsl.scsp.getPhase() == minorPhase.WAIT) {
			if (scsl.aif.getScaleCorrect() == true) {
				scsl.scsp.changePhase(minorPhase.SCAN_MEMBER);
				return true;
			} else {
				printInfoToCustomer("Please remove unscanned items from bagging area before starting transaction");
			}
		}

		return false;
	}
	
	//******************************
	// Adding items phase
	//******************************
	
	/**
	 * Manually enters membership card details.
	 * Automatically moves minor phase from SCAN_MEMBER to OWN_BAGS if entered information is valid.
	 * 
	 * @param str membership number
	 * @return true if membership number was successful, false otherwise
	 */
	public boolean enterMembershipManually(String str) {
		if (scsl.scsp.getPhase() == minorPhase.SCAN_MEMBER) {
			if (scsl.aif.getScaleCorrect() == true) {
				if (scsl.pf.setMemberNumber(str) == true) {
					scsl.scsp.changePhase(minorPhase.OWN_BAGS);
					return true;
				}
			} else {
				printInfoToCustomer("Please remove unscanned items from bagging area before starting transaction");
			}
		}
		
		return false;
	}
	
	/**
	 * Moves minor phase from SCAN_MEMBER to OWN_BAGS without setting a member number.
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean cancelScanMembershipCard() {
		if (scsl.scsp.getPhase() == minorPhase.SCAN_MEMBER) {
			scsl.scsp.changePhase(minorPhase.OWN_BAGS);
			return true;
		}
		
		return false;
	}
	
	/**
	* Moves minor phase from OWN_BAGS to SCAN_ITEM.
	* 
	* @return true if phase change was successful, false otherwise
	*/
	public boolean finishAddOwnBags() {
		if (scsl.scsp.getPhase() == minorPhase.OWN_BAGS) {
			if (scsl.aif.getScaleOverloaded() == false) {
				scsl.scsp.blockStation();
				return true;
			} else {
				printInfoToCustomer("Added bags are too heavy");
			}
		}
		
		return false;
	}
	
	/**
	 * Moves minor phase from OWN_BAGS to SCAN_ITEMS without altering the expected weight.
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean cancelAddOwnBags() {
		if (scsl.scsp.getPhase() == minorPhase.OWN_BAGS) {
			if (scsl.aif.getScaleCorrect() == true) {
				scsl.scsp.changePhase(minorPhase.SCAN_ITEM);
				return true;
			} else {
				printInfoToCustomer("Please remove unscanned items from bagging area before starting transaction");
			}
		}
		
		return false;
	}
	
	/**
	 * Moves minor phase from SCAN_ITEM to ENTER_PLU.
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean addPLUItem() {
		if (scsl.scsp.getPhase() == minorPhase.SCAN_ITEM) {
			if (scsl.aif.getScaleCorrect() == true) {
				scsl.scsp.changePhase(minorPhase.ENTER_PLU);
				return true;
			} else {
				printInfoToCustomer("Please ensure weight on bagging area is correct");
			}
		}
		
		return false;
	}
	
	/**
	 * Allows user to enter the PLU of the item to be weighed and added to cart.
	 * 
	 * @return true if payment amount has been set, false otherwise
	 */
	public boolean enterPLU(PriceLookupCode plu) {
		switch (scsl.scsp.getPhase()) {
			case ENTER_PLU, PLU_VISUAL, PLU_VISUALA, PLU_VISUALB, PLU_VISUALNONE:
				if (scsl.aif.getScanOverloaded() == false) {
					return scsl.aif.addPLUItem(plu);
				} else {
					printInfoToCustomer("Please weigh less items at once");
				}
				// fall through
			default:
				// fall through
		}
		
		return false;
	}
	
	public boolean enterVisualPLU() {
		if (scsl.scsp.getPhase() == minorPhase.ENTER_PLU) {
			if (scsl.aif.getScaleCorrect() == true) {
				scsl.scsp.changePhase(minorPhase.PLU_VISUAL);
				return true;
			} else {
				printInfoToCustomer("Please ensure weight on bagging area is correct");
			}
		}
		
		return false;
	}
	
	public boolean enterVisualPLUA() {
		if ((scsl.scsp.getPhase() == minorPhase.PLU_VISUAL) || (scsl.scsp.getPhase() == minorPhase.PLU_VISUALB) || (scsl.scsp.getPhase() == minorPhase.PLU_VISUALNONE)) {
			if (scsl.aif.getScaleCorrect() == true) {
				scsl.scsp.changePhase(minorPhase.PLU_VISUALA);
				return true;
			} else {
				printInfoToCustomer("Please ensure weight on bagging area is correct");
			}
		}
		
		return false;
	}
	
	public boolean enterVisualPLUB() {
		if ((scsl.scsp.getPhase() == minorPhase.PLU_VISUAL) || (scsl.scsp.getPhase() == minorPhase.PLU_VISUALA) || (scsl.scsp.getPhase() == minorPhase.PLU_VISUALNONE)) {
			if (scsl.aif.getScaleCorrect() == true) {
				scsl.scsp.changePhase(minorPhase.PLU_VISUALB);
				return true;
			} else {
				printInfoToCustomer("Please ensure weight on bagging area is correct");
			}
		}
		
		return false;
	}
	
	public boolean enterVisualPLUNone() {
		if ((scsl.scsp.getPhase() == minorPhase.PLU_VISUAL) || (scsl.scsp.getPhase() == minorPhase.PLU_VISUALB) || (scsl.scsp.getPhase() == minorPhase.PLU_VISUALA)) {
			if (scsl.aif.getScaleCorrect() == true) {
				scsl.scsp.changePhase(minorPhase.PLU_VISUALNONE);
				return true;
			} else {
				printInfoToCustomer("Please ensure weight on bagging area is correct");
			}
		}
		
		return false;
	}
	
	public boolean exitVisualPLU() {
		if ((scsl.scsp.getPhase() == minorPhase.PLU_VISUAL) || (scsl.scsp.getPhase() == minorPhase.PLU_VISUALA) || (scsl.scsp.getPhase() == minorPhase.PLU_VISUALB) || (scsl.scsp.getPhase() == minorPhase.PLU_VISUALNONE)) {
			if (scsl.aif.getScaleCorrect() == true) {
				scsl.scsp.changePhase(minorPhase.ENTER_PLU);
				return true;
			} else {
				printInfoToCustomer("Please ensure weight on bagging area is correct");
			}
		}
		
		return false;
	}
	
	/**
	 * Moves minor phase from ENTER_PLU to SCAN_ITEM.
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean cancelPLU() {
		if (scsl.scsp.getPhase() == minorPhase.ENTER_PLU) {
			if (scsl.aif.getScaleCorrect() == true) {
				scsl.scsp.changePhase(minorPhase.SCAN_ITEM);
				return true;
			} else {
				printInfoToCustomer("Please ensure weight on bagging area is correct");
			}
		}
		
		return false;
	}
	
	/**
	 * Removes previously added item from needing to be placed on the scale.
	 * On success, this method will send the station to the BLOCK phase since attendant
	 *   must verify the bagging area and physical cart.
	 * 
	 * @return true if cancel was successful, false otherwise
	 */
	public boolean cancelBagItem() {
		if (scsl.scsp.getPhase() == minorPhase.PLACE_ITEM || scsl.scsp.getPhase() == minorPhase.SCAN_ITEM) {
			scsl.aif.skipBaggingCurProd();
			return true;
		}
		
		return false;
	}
	
	/**
	 * Moves major phase from SCAN_ITEM to USE_BAGS (the start of the checkout phase).
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean startPayPhase() {
		if (scsl.scsp.getPhase() == minorPhase.SCAN_ITEM) {
			if (scsl.aif.getCart().isEmpty() == false) {
				if (scsl.aif.getScaleCorrect() == true) {
					scsl.scsp.changePhase(minorPhase.USE_BAGS);
					return true;
				} else {
					printInfoToCustomer("Please ensure weight on bagging area is correct before checking out");
				}
			} else {
				printInfoToCustomer("Please add at least one item to cart before checking out");
			}
		}
		
		return false;
	}
	
	//******************************
	// Payment phase
	//******************************
	
	/**
	 * Sets the number of plastic bags used.
	 * Automatically moves minor phase from USE_BAGS to PAY_SELECT if entered information is valid.
	 * Note that since there is the ability to go back to a the previous phase then remove an item,
	 *   the call to check if sufficient funds have been entered is made directly in this method as a check.
	 * 
	 * @param num number of bags purchased
	 * @return true if numBags could be set, false otherwise
	 */
	public boolean setNumberBags(int num) {
		if (scsl.scsp.getPhase() == minorPhase.USE_BAGS) {
			if (scsl.pf.setNumBags(num)) {
				scsl.scsp.changePhase(minorPhase.PAY_SELECT);
				scsl.pf.fcl.completeCheckout();
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Moves minor phase from PAY_SELECT to PAY_CASH.
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean selectCashPayment() {
		if (scsl.scsp.getPhase() == minorPhase.PAY_SELECT) {
			if (scsl.pf.getBanknotesFull() == false || scsl.pf.getCoinsFull() == false) {
				scsl.scsp.changePhase(minorPhase.PAY_CASH);
				return true;
			} else {
				printInfoToCustomer("Currently cannot accept change at this station");
			}
		}
		
		return false;
	}
	
	/**
	 * Moves minor phase from PAY_SELECT to PAY_DEBIT.
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean selectDebitPayment() {
		if (scsl.scsp.getPhase() == minorPhase.PAY_SELECT) {
			scsl.scsp.changePhase(minorPhase.PAY_DEBIT);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Moves minor phase from PAY_SELECT to PAY_CREDIT.
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean selectCreditPayment() {
		if (scsl.scsp.getPhase() == minorPhase.PAY_SELECT) {
			scsl.scsp.changePhase(minorPhase.PAY_CREDIT);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Moves minor phase from PAY_SELECT to PAY_GIFT
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean selectGiftPayment() {
		if (scsl.scsp.getPhase() == minorPhase.PAY_SELECT) {
			scsl.scsp.changePhase(minorPhase.PAY_GIFT);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sets amount to be paid using the select payment method.
	 * This method performs rounding to the nearest cent before passing the amount to other methods.
	 * 
	 * @return true if payment amount has been set, false otherwise
	 */
	public boolean setPaymentAmount(BigDecimal amount) {
		if (scsl.scsp.getPhase() == minorPhase.PAY_DEBIT || scsl.scsp.getPhase() == minorPhase.PAY_CREDIT
				|| scsl.scsp.getPhase() == minorPhase.PAY_GIFT) {
			if (amount != null && scsl.pf.setPaymentAmount(amount.setScale(2, RoundingMode.HALF_UP)) == true) {
				return true;
			} else {
				printInfoToCustomer("Enter an amount less than or equal to the total price of the cart");
			}
		}
		
		return false;
	}
	
	/**
	 * Sets amount to be paid to be the rest of the cart price.
	 * 
	 * @return true if payment amount has been set, false otherwise
	 */
	public boolean setFullPaymentAmount() {
		if (scsl.scsp.getPhase() == minorPhase.PAY_DEBIT || scsl.scsp.getPhase() == minorPhase.PAY_CREDIT
				|| scsl.scsp.getPhase() == minorPhase.PAY_GIFT) {
			if (scsl.pf.setFullPaymentAmount() == true) {
				return true;
			} else {
				printInfoToCustomer("Payment amount has already been set");
			}
		}
		
		return false;
	}
	
	/**
	 * Moves minor phase from any PAY_* to PAY_SELCT.
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean exitCurrentPaymentMethod() {
		if (scsl.scsp.getPhase() == minorPhase.PAY_CASH || scsl.scsp.getPhase() == minorPhase.PAY_DEBIT
				|| scsl.scsp.getPhase() == minorPhase.PAY_CREDIT || scsl.scsp.getPhase() == minorPhase.PAY_GIFT) {
			scsl.scsp.changePhase(minorPhase.PAY_SELECT);
			scsl.pf.resetPaymentAmount();
			return true;
		}
		
		return false;
	}
	
	/**
	 * Moves major phase from PAY_SELECT to SCAN_ITEM.
	 * 
	 * @return true if phase change was successful, false otherwise
	 */
	public boolean addMoreItems() {
		if (scsl.scsp.getPhase() == minorPhase.PAY_SELECT) {
			if (scsl.aif.getScaleCorrect() == true) {
				scsl.scsp.changePhase(minorPhase.SCAN_ITEM);
				return true;
			} else {
				printInfoToCustomer("Please ensure everything is correctly placed in bagging area before adding more items");
			}
		}
		
		return false;
	}
	
	//=============================================================================================
	// Methods that print information onto the GUI for the user
	//=============================================================================================
	
	/**
	 * Prints information onto the screen for the user to see.
	 * 
	 * @param message String to print on screen to user
	 */
	public void printInfoToCustomer(String message) {
		customerStr = message;
		System.out.println("Message to customer: " + customerStr);

		SwingUtilities.invokeLater(() -> scsl.gf.informCustomer(message));
	}
	
	//=============================================================================================
	// Methods that send information to the attendant station
	//=============================================================================================
	
	/**
	 * Prints information onto the screen for the attendant to see.
	 * 
	 * @param message String to print on screen to attendant
	 */
	public void printInfoToAttendant(String message) {
		attendantStr = message;
		if (scsl.asl != null) {
			System.out.println("Message to attendant from station " + Integer.toString(scsl.asl.getIdx(scsl)) + ": " + attendantStr);
			SwingUtilities.invokeLater(() -> scsl.asl.asi.informAttendant(message));
		} else {
			System.out.println("Message to attendant: " + attendantStr);
		}
	}
	
	//=============================================================================================
	// Methods for internal verification and testing
	//=============================================================================================
	
	/**
	 * Returns the most recently printed message to the customer.
	 * Stored string is cleared after a call to this method.
	 * 
	 * @return previously printed message to customer
	 */
	public String getLastInfoToCustomer() {
		String ret = customerStr;
		customerStr = null;
		return ret;
	}
	
	/**
	 * Returns the most recently printed message to the attendant.
	 * Stored string is cleared after a call to this method.
	 * 
	 * @return previously printed message to attendant
	 */
	public String getLastInfoToAttendant() {
		String ret = attendantStr;
		attendantStr = null;
		return ret;
	}

	/**
	 * Called by the AttendantStationInterface when removing an item
	 */
	public Product getLastItemAdded() {
		return this.scsl.aif.getLastItemAdded();
	}

	public void killAllThreads() {
		scsl.gf.killGui();

		if (scsl.asl != null) scsl.asl.asi.killGui();
	}
}