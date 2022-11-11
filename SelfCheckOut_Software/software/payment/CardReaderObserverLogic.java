package SelfCheckOut_Software.software.payment;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.Card.CardSwipeData;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;
import org.lsmr.selfcheckout.external.CardIssuer;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationDatabase;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationObserverLogic;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationPhases.minorPhase;

public class CardReaderObserverLogic extends SelfCheckoutStationObserverLogic
	implements CardReaderObserver {
	private SelfCheckoutStation scs;
	private SelfCheckoutStationLogic scsl;
	private PaymentFacade pf;
	private FinishCheckoutLogic fcl;
	
	public CardReaderObserverLogic(SelfCheckoutStation selfCheckoutStation,
			SelfCheckoutStationLogic selfCheckoutStationLogic, PaymentFacade paymentFacade,
			FinishCheckoutLogic finishCheckoutLogic) {
		scs = selfCheckoutStation;
		scsl = selfCheckoutStationLogic;
		pf = paymentFacade;
		fcl = finishCheckoutLogic;
		
		startUp();
	}
	
	public void startUp() {
		scs.cardReader.attach(this);
	}
	
	public void shutDown() {
		scs.cardReader.detach(this);
	}
	
	@Override
	public void cardInserted(CardReader reader) {}
	
	@Override
	public void cardRemoved(CardReader reader) {}
	
	@Override
	public void cardTapped(CardReader reader) {}
	
	@Override
	public void cardSwiped(CardReader reader) {}
	
	@Override
	public void cardDataRead(CardReader reader, CardData data) {
		switch(scsl.scsp.getPhase()) {
			case SCAN_MEMBER:
				String type = data.getType();
				if ("member".equals(type) == true) {
					if (data instanceof CardSwipeData) {
						scsl.scsi.enterMembershipManually(data.getNumber());
					} else {
						scsl.scsi.printInfoToCustomer("Membership card must be swiped");
					}
				} else {
					scsl.scsi.printInfoToCustomer("Bad card read, scanned card may be of incorrect type");
				}
				break;
			case PAY_DEBIT:
				// fall through
			case PAY_CREDIT:
				type = data.getType();
				if ("debit".equals(type) == true || "credit".equals(type) == true) {
					String number = data.getNumber();
					if (getCardFunds(number, pf.paymentAmount)) {
						if ("debit".equals(type) == true) {
							pf.addDebitPayment(number);
						} else {
							pf.addCreditPayment(number);
						}
						scsl.scsp.changePhase(minorPhase.PAY_SELECT);
						fcl.completeCheckout();
					} else {
						scsl.scsi.printInfoToCustomer("Card not approved");
					}
				} else {
					scsl.scsi.printInfoToCustomer("Bad card read, scanned card may be of incorrect type");
				}
				break;
			case PAY_GIFT:
				type = data.getType();
				if ("gift card".equals(type) == true) {
					String number = data.getNumber();
					if (getCardFunds(number, pf.paymentAmount)) {
						pf.addGiftPayment(number);
						scsl.scsp.changePhase(minorPhase.PAY_SELECT);
						fcl.completeCheckout();
					} else {
						scsl.scsi.printInfoToCustomer("Card not approved");
					}
				} else {
					scsl.scsi.printInfoToCustomer("Bad card read, scanned card may be of incorrect type");
				}
				break;
			default:
				scsl.scsi.printInfoToCustomer("Card read in wrong phase");
		}
	}
	
	/**
	 * Looks up the relevant card issuer then authorizes a hold and posts a transaction.
	 *  
	 * @param number card number to post a transaction against
	 * @param paymentAmount amount to charge card for
	 * @return true if transaction was successfully posted, false otherwise
	 */
	private boolean getCardFunds(String number, BigDecimal paymentAmount) {
		CardIssuer issuer = scsl.scsd.getCardIssuer(number.substring(0, SelfCheckoutStationDatabase.CARD_PREFIX));
		if (issuer != null) {
			int hold = issuer.authorizeHold(number, paymentAmount);
			if (hold >= 0) {
				issuer.postTransaction(number, hold, paymentAmount);
				return true;
			}
		}
		
		return false;
	}
}
