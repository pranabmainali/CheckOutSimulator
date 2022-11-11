package SelfCheckOut_Software.software.payment;

import java.math.BigDecimal;

import SelfCheckOut_Hardware.Card.CardData;
import SelfCheckOut_Hardware.Card.CardSwipeData;
import SelfCheckOut_Hardware.devices.CardReader;
import SelfCheckOut_Hardware.devices.SelfCheckoutStation;
import SelfCheckOut_Hardware.devices.observers.CardReaderObserver;
import SelfCheckOut_Hardware.external.CardIssuer;

import SelfCheckOut_Software.software.SelfCheckoutStationDatabase;
import SelfCheckOut_Software.software.SelfCheckoutStationLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationObserverLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationPhases.minorPhase;

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
