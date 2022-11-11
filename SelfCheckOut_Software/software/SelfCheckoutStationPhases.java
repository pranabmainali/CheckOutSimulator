package SelfCheckOut_Software.software;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

/**
 * For the phase to be in a given state, it implies everything else has been validated with respect to that state.
 * 
 * For example, to be in the minorPhase.ADD state, it means that the baggingArea scale is showing
 *     the expected result with respect to what has been scanned.
 */
public final class SelfCheckoutStationPhases {
	private final SelfCheckoutStation scs;
	private final SelfCheckoutStationLogic scsl;
	
	public static enum majorPhase {
		OFF, MAINTENANCE, WAIT, ADD, PAY;
	}
	
	public static enum minorPhase {
		OFF(majorPhase.OFF),
		MAINTENANCE(majorPhase.MAINTENANCE),
		WAIT(majorPhase.WAIT),
		SCAN_MEMBER(majorPhase.ADD),
		OWN_BAGS(majorPhase.ADD),
		BLOCK(majorPhase.WAIT),
		SCAN_ITEM(majorPhase.ADD),
		ENTER_PLU(majorPhase.ADD),
		PLU_VISUAL(majorPhase.ADD),
		PLU_VISUALA(majorPhase.ADD),
		PLU_VISUALB(majorPhase.ADD),
		PLU_VISUALNONE(majorPhase.ADD),
		PLACE_ITEM(majorPhase.ADD),
		USE_BAGS(majorPhase.PAY),
		PAY_SELECT(majorPhase.PAY),
		PAY_CASH(majorPhase.PAY),
		PAY_DEBIT(majorPhase.PAY),
		PAY_CREDIT(majorPhase.PAY),
		PAY_GIFT(majorPhase.PAY),
		RETURN_CHANGE(majorPhase.PAY);
		
		public final majorPhase major;
		private minorPhase(majorPhase major) {
			this.major = major;
		}
	}
	
	private majorPhase major;
	private minorPhase minor;
	private minorPhase prev;
	
	/**
	 * Basic constructor which initializes both the major and minor phases to WAIT.
	 * 
	 * @param selfCheckoutStation SelfCheckoutStation to attach to
	 * @param selfCheckoutStationLogic SelfCheckoutStationLogic to attach to
	 */
	public SelfCheckoutStationPhases(SelfCheckoutStation selfCheckoutStation, SelfCheckoutStationLogic selfCheckoutStationLogic) {
		scs = selfCheckoutStation;
		scsl = selfCheckoutStationLogic;
		
		this.changePhase(minorPhase.WAIT);
	}
	
	/**
	 * Method which changes the major and minor phases of the machine.
	 * All verification that the phase can be changed to minor should be done in the calling function.
	 * 
	 * @param minorNew new minorPhase to change to
	 */
	public void changePhase(minorPhase minorNew) {
		minor = minorNew;
		major = minor.major;

		switch (major) {
			// fall through
			// fall through
			case MAINTENANCE, WAIT -> {
				scs.mainScanner.disable();
				scs.handheldScanner.disable();
				scs.cardReader.disable();
				scs.banknoteInput.disable();
				scs.coinSlot.disable();
			}
			case ADD -> {
				scs.mainScanner.enable();
				scs.handheldScanner.enable();
				scs.cardReader.disable();
				scs.banknoteInput.disable();
				scs.coinSlot.disable();
			}
			case PAY -> {
				scs.mainScanner.disable();
				scs.handheldScanner.disable();
				scs.cardReader.disable();
				scs.banknoteInput.disable();
				scs.coinSlot.disable();
			}
			default -> {
				scs.mainScanner.disable();
				scs.handheldScanner.disable();
				scs.cardReader.disable();
				scs.banknoteInput.disable();
				scs.coinSlot.disable();
			}
		}
		
		switch (minor) {
			case MAINTENANCE, WAIT, BLOCK, RETURN_CHANGE:
				break;
			case SCAN_MEMBER:
				scs.mainScanner.disable();
				scs.handheldScanner.disable();
				scs.cardReader.enable();
				break;
			case OWN_BAGS:
				scs.mainScanner.disable();
				scs.handheldScanner.disable();
				break;
			case SCAN_ITEM:
				break;
			case ENTER_PLU, PLU_VISUAL, PLU_VISUALA, PLU_VISUALB, PLU_VISUALNONE:
				scs.mainScanner.disable();
				scs.handheldScanner.disable();
				break;
			case PLACE_ITEM:
				scs.mainScanner.disable();
				scs.handheldScanner.disable();
				break;
			case USE_BAGS:
				break;
			case PAY_SELECT:
				break;
			case PAY_CASH:
				if (scsl.pf.getBanknotesFull() == false) {
					scs.banknoteInput.enable();
				} else {
					scsl.scsi.printInfoToCustomer("Currently cannot accept banknotes at this station");
				}
				
				if (scsl.pf.getCoinsFull() == false) {
					scs.coinSlot.enable();
				} else {
					scsl.scsi.printInfoToCustomer("Currently cannot accept coins at this station");
				}
				
				break;
			case PAY_DEBIT, PAY_CREDIT, PAY_GIFT:
				scs.cardReader.enable();
				break;
			default:
				break;
		}

		scsl.gf.changePhase(minorNew);
	}
	
	public minorPhase getPhase() {
		return minor;
	}
	
	/**
	 * Method which forces the station into the BLOCK_ATTENDANT minor phase while storing the previous minor phase.
	 * In the case where the station is not supervised, the station will not block.
	 */
	public void blockStation() {
		if (scsl.asl != null) {
			prev = minor;
			scsl.asl.notifyBlockedStatus(scsl, true);
			this.changePhase(minorPhase.BLOCK);
		} else if (minor == minorPhase.OWN_BAGS || minor == minorPhase.PLACE_ITEM) {
			scsl.aif.overrideExpectedWeight();
			scsl.scsp.changePhase(minorPhase.SCAN_ITEM);
		}
	}
	
	/**
	 * Method which restores the station to an unblocked state by moving to the previous minor phase.
	 * This method ensures that the station is not in an overloaded state before unblocking.
	 */
	public void unblockStation() {
		if (scsl.aif.getScaleOverloaded() == false) {
			switch (prev) {
				case OWN_BAGS, PLACE_ITEM, SCAN_ITEM:
					scsl.aif.overrideExpectedWeight();
					scsl.scsp.changePhase(minorPhase.SCAN_ITEM);
					break;
				default:
					this.changePhase(prev);
					break;
			}
			
			scsl.asl.notifyBlockedStatus(scsl, false);
			prev = null;
		}
	}
}
