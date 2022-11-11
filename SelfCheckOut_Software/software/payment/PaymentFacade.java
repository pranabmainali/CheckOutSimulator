package ca.ucalgary.seng300.selfcheckout.software.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;

public class PaymentFacade {
	private SelfCheckoutStation scs;
	private SelfCheckoutStationLogic scsl;
	public FinishCheckoutLogic fcl;
	
	private BanknoteOutputObserverLogic bool;
	public CashDispenserObserverLogic cdol;
	public CardReaderObserverLogic crol;
	private CashStorageUnitObserverLogic csuol;
	private CashValidatorObserverLogic cvol;
	public ReceiptPrinterObserverLogic rpol;
	
	/**
	 * Total cost of the cart.
	 */
	protected BigDecimal totalCost = new BigDecimal("0.00");
	
	/**
	 * Total funds already paid.
	 */
	protected BigDecimal totalFunds = new BigDecimal("0.00");
	
	/**
	 * Amount to be paid using current payment method.
	 * This should be set when paying with debit, credit, or gift, but should be reset to null all other times.
	 */
	protected BigDecimal paymentAmount = null;
	
	/**
	 * Total funds paid using cash.
	 */
	protected BigDecimal cashPayments = new BigDecimal("0.00");
	
	/**
	 * Map representing the debit / credit / gift cards used to pay during the current transaction.
	 * Data stored as <account number, amount paid>.
	 */
	protected Map<String, BigDecimal> debitPayments = new LinkedHashMap<>(),
			creditPayments = new LinkedHashMap<>(),
			giftPayments = new LinkedHashMap<>();
	
	/**
	 * Member number of current transaction. Null means that no membership number has been entered.
	 */
	protected String memberNumber = null;

	/**
	 * Number of bags bought.
	 */
	protected int numBags = 0;
	
	/**
	 * Boolean flags used to indicate if the corresponding storage units are full.
	 */
	protected boolean banknotesFull, coinsFull;
	
	public PaymentFacade(SelfCheckoutStation selfCheckoutStation, SelfCheckoutStationLogic selfCheckoutStationLogic) {
		scs = selfCheckoutStation;
		scsl = selfCheckoutStationLogic;
		
		rpol = new ReceiptPrinterObserverLogic(scs, scsl, this);
		fcl = new FinishCheckoutLogic(scs, scsl, this, rpol);
		bool = new BanknoteOutputObserverLogic(scs, fcl);
		cdol = new CashDispenserObserverLogic(scs, scsl, this);
		crol = new CardReaderObserverLogic(scs, scsl, this, fcl);
		csuol = new CashStorageUnitObserverLogic(scs, scsl, this);
		cvol = new CashValidatorObserverLogic(scs, this, fcl);
		
		banknotesFull = !scs.banknoteStorage.hasSpace();
		coinsFull = !scs.coinStorage.hasSpace();
	}
	
	public void startUp() {
		bool.startUp();
		cdol.startUp();
		crol.startUp();
		csuol.startUp();
		cvol.startUp();
		rpol.startUp();
	}
	
	public void shutDown() {
		bool.shutDown();
		cdol.shutDown();
		crol.shutDown();
		csuol.shutDown();
		cvol.shutDown();
		rpol.shutDown();
	}
	
	/**
	 * Called to reset the internal state of the PaymentFacade for a new customer.
	 */
	public void resetState() {
		totalCost = new BigDecimal("0.00");
		totalFunds = new BigDecimal("0.00");
		paymentAmount = null;
		
		cashPayments = new BigDecimal("0.00");
		debitPayments = new LinkedHashMap<>();
		creditPayments = new LinkedHashMap<>();
		giftPayments = new LinkedHashMap<>();
		
		memberNumber = null;
		
		numBags = 0;
	}
	
	public void addCost(BigDecimal value) {
		totalCost = totalCost.add(value);
	}
	
	public void subCost(BigDecimal value) {
		totalCost = totalCost.subtract(value);
	}
	
	public BigDecimal getPaymentAmount() {
		if (paymentAmount != null) {
			return paymentAmount.setScale(2, RoundingMode.HALF_UP);
		}
		
		return null;
	}
	
	/**
	 * Set the paymentAmount field which is used when charging debit / credit / gift cards.
	 * This method verifies a valid amount has been set (at least one cent),
	 *   but amount passed in should already be verified to not be null and rounded
	 *   to the nearest cent.
	 * 
	 * @param amount the value to set paymentAmount to
	 * @return true if paymentAmount could be set, false otherwise
	 */
	public boolean setPaymentAmount(BigDecimal amount) {
		// ensure amount is at least one cent and no paymentAmount has been set yet
		if (amount.compareTo(new BigDecimal("0.01")) >= 0 && paymentAmount == null) {
				if (amount.compareTo(getTotalCost().subtract(totalFunds)) <= 0) {
					paymentAmount = amount;
					return true;
				}
		}
		
		return false;
	}
	
	/**
	 * Set the paymentAmount field to the total outstanding cost of the cart.
	 * 
	 * @return true if paymentAmount could be set, false otherwise
	 */
	public boolean setFullPaymentAmount() {
		if (paymentAmount == null) {
			paymentAmount = getTotalCost().subtract(totalFunds);
			return true;
		}
		
		return false;
	}
	
	public void resetPaymentAmount() {
		paymentAmount = null;
	}
	
	/**
	 * Adds a debit card payment, both to the totalFunds paid and the list of debit payments.
	 * This method verifies if the same debit card has already been used, in which case the amount paid is simply increased.
	 * 
	 * This method must be called AFTER the validity of the card (including sufficient funds) has been verified.
	 * 
	 * @param number card number to charge
	 */
	public void addDebitPayment(String number) {
		totalFunds = totalFunds.add(paymentAmount);
		
		if (debitPayments.get(number) != null) {
			debitPayments.put(number, debitPayments.get(number).add(paymentAmount));
		} else {
			debitPayments.put(number, paymentAmount);
		}
		
		paymentAmount = null;
	}
	
	/**
	 * Adds a credit card payment, both to the totalFunds paid and the list of credit payments.
	 * This method verifies if the same credit card has already been used, in which case the amount paid is simply increased.
	 * 
	 * This method must be called AFTER the validity of the card (including sufficient funds) has been verified.
	 * 
	 * @param number card number to charge
	 */
	public void addCreditPayment(String number) {
		totalFunds = totalFunds.add(paymentAmount);
		
		if (creditPayments.get(number) != null) {
			creditPayments.put(number, creditPayments.get(number).add(paymentAmount));
		} else {
			creditPayments.put(number, paymentAmount);
		}
		
		paymentAmount = null;
	}
	
	/**
	 * Adds a gift card payment, both to the totalFunds paid and the list of gift payments.
	 * This method verifies if the same gift card has already been used, in which case the amount paid is simply increased.
	 * 
	 * This method must be called AFTER the validity of the card (including sufficient funds) has been verified.
	 * 
	 * @param number card number to charge
	 */
	public void addGiftPayment(String number) {
		totalFunds = totalFunds.add(paymentAmount);
		
		if (giftPayments.get(number) != null) {
			giftPayments.put(number, giftPayments.get(number).add(paymentAmount));
		} else {
			giftPayments.put(number, paymentAmount);
		}
		
		paymentAmount = null;
	}
	
	/**
	 * Sets the member number of the current transaction.
	 * This method verifies that the member number is valid and can be set.
	 * 
	 * @param str member number to try and use
	 * @return true if memberNumber could be set, false otherwise
	 */
	public boolean setMemberNumber(String str) {
		if (scsl.scsd.isMember(str)) {
			memberNumber = str;
			return true;
		}
		
		scsl.scsi.printInfoToCustomer("Member number unsuccessfully added");
		
		return false;
	}
	
	/**
	 * Sets the number of bags purchased.
	 * 
	 * @param num number of bags purchased
	 * @return true if numBags could be set, false otherwise
	 */
	public boolean setNumBags(int num) {
		if (num >= 0) {
			if (num > 0) {
				numBags += num;
				addCost(SelfCheckoutStationLogic.BAG_COST.multiply(new BigDecimal(Integer.toString(num))));
			}
			
			return true;
		}
		
		return false;
	}
	
	public BigDecimal getTotalCost() {
		return totalCost.setScale(2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getTotalFunds() {
		return totalFunds;
	}
	
	public String getMemberNumber() {
		return memberNumber;
	}
	
	public boolean getBanknotesFull() {
		return banknotesFull;
	}
	
	public boolean getCoinsFull() {
		return coinsFull;
	}
}
