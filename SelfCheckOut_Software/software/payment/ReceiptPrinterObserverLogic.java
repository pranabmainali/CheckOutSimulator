package SelfCheckOut_Software.software.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import SelfCheckOut_Hardware.devices.EmptyException;
import SelfCheckOut_Hardware.devices.OverloadException;
import SelfCheckOut_Hardware.devices.ReceiptPrinter;
import SelfCheckOut_Hardware.devices.SelfCheckoutStation;
import SelfCheckOut_Hardware.devices.observers.ReceiptPrinterObserver;
import SelfCheckOut_Hardware.products.BarcodedProduct;
import SelfCheckOut_Hardware.products.PLUCodedProduct;
import SelfCheckOut_Hardware.products.Product;

import SelfCheckOut_Software.attendant.AttendantStationLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationObserverLogic;

public class ReceiptPrinterObserverLogic extends SelfCheckoutStationObserverLogic
	implements ReceiptPrinterObserver {
	private SelfCheckoutStation scs;
	private SelfCheckoutStationLogic scsl;
	private PaymentFacade pf;
	
	/**
	 * Variable representing the width of the product description column on the receipt.
	 * Cannot be more than (ReceiptPrinter.CHARACTERS_PER_LINE - 18).
	 */
	private final int MAX_DESCRIPTION_LENGTH = Math.min(ReceiptPrinter.CHARACTERS_PER_LINE / 2,
			ReceiptPrinter.CHARACTERS_PER_LINE - 18);
	
	private int remainingPaper = 0;
	private int remainingInk = 0;
	private double lowWarning = 0.25; 
	
	private String receiptString = "";
	private String lastReceipt = "";
	
	public ReceiptPrinterObserverLogic(SelfCheckoutStation selfCheckoutStation,
			SelfCheckoutStationLogic selfCheckoutStationLogic, PaymentFacade paymentFacade) {
		scs = selfCheckoutStation;
		scsl = selfCheckoutStationLogic;
		pf = paymentFacade;
		
		startUp();
	}

	public void startUp() {
		scs.printer.attach(this);
	}
	
	public void shutDown() {
		scs.printer.detach(this);
	}
	
	@Override
	public void outOfPaper(ReceiptPrinter printer) {
		scsl.scsi.printInfoToAttendant("Out of receipt paper");
	}

	@Override
	public void outOfInk(ReceiptPrinter printer) {
		scsl.scsi.printInfoToAttendant("Out of receipt ink");
	}

	@Override
	public void paperAdded(ReceiptPrinter printer) {
		remainingPaper += AttendantStationLogic.PAPER_ROLL;
	}

	@Override
	public void inkAdded(ReceiptPrinter printer) {
		remainingInk += AttendantStationLogic.INK_CARTRIDGE;
	}
	
	/**
	 * Prints the receipt for the user to take.
	 */
	public void printReceipt(BigDecimal roundedChange) {
		// Print member number
		if (pf.memberNumber != null) {
			printReceiptString("Member number: " + pf.memberNumber + "\n\n");
		}
		
		// Print each product bought
		Map<Product, Double> cart = scsl.aif.getCartCollapse();
		for (Map.Entry<Product, Double> entry : cart.entrySet()) {
			if (entry.getKey() instanceof BarcodedProduct) {
				printReceiptBarcodedProduct(((BarcodedProduct) entry.getKey()).getDescription(),
						Math.round(entry.getValue()), entry.getKey().getPrice());
			} else {
				printReceiptPLUCodedProduct(((PLUCodedProduct) entry.getKey()).getDescription(),
						entry.getValue(), entry.getKey().getPrice());
			}
		}
		
		// Print number of bags
		if (pf.numBags > 0) {
			printReceiptBags(pf.numBags);
		}
		
		// Print total cost
		printReceiptSpacing(MAX_DESCRIPTION_LENGTH + 4);
		printReceiptString("Total: ");
		String str = roundBigDec(pf.getTotalCost());
		printReceiptSpacing(7 - str.length());
		printReceiptString(str + "\n\n");
		
		// Print total paid on each debit card
		if (pf.debitPayments.isEmpty() == false) {
			printReceiptString("Debit payments:\n");
			for (Map.Entry<String, BigDecimal> entry : pf.debitPayments.entrySet()) {
				printReceiptCard(entry.getKey(), entry.getValue());
			}
		}
		
		// Print total paid on each credit card
		if (pf.creditPayments.isEmpty() == false) {
			printReceiptString("Credit payments:\n");
			for (Map.Entry<String, BigDecimal> entry : pf.creditPayments.entrySet()) {
				printReceiptCard(entry.getKey(), entry.getValue());
			}
		}
		
		// Print total paid on each gift card
		if (pf.giftPayments.isEmpty() == false) {
			printReceiptString("Gift payments:\n");
			for (Map.Entry<String, BigDecimal> entry : pf.giftPayments.entrySet()) {
				printReceiptCard(entry.getKey(), entry.getValue());
			}
		}
		
		// Print total paid in cash
		if (pf.cashPayments.compareTo(new BigDecimal("0.00")) > 0) {
			printReceiptString("Cash payment:\n");
			printReceiptSpacing(MAX_DESCRIPTION_LENGTH + 11);
			str = roundBigDec(pf.cashPayments);
			printReceiptSpacing(7 - str.length());
			printReceiptString(str + "\n");
		}
		
		// Print total paid
		printReceiptSpacing(MAX_DESCRIPTION_LENGTH - 1);
		printReceiptString("Total paid: ");
		str = roundBigDec(pf.totalFunds);
		printReceiptSpacing(7 - str.length());
		printReceiptString(str + "\n");
		
		// Print change due
		if (pf.totalFunds.compareTo(pf.getTotalCost()) > 0) {
			printReceiptSpacing(MAX_DESCRIPTION_LENGTH + 3);
			printReceiptString("Change: ");
			str = roundBigDec(pf.totalFunds.subtract(pf.getTotalCost()));
			printReceiptSpacing(7 - str.length());
			printReceiptString(str + "\n");
			
			printReceiptSpacing(MAX_DESCRIPTION_LENGTH - 5);
			printReceiptString("Rounded Change: ");
			str = roundBigDec(roundedChange);
			printReceiptSpacing(7 - str.length());
			printReceiptString(str + "\n");
		}

		// Print thank you message
		printReceiptString("\nThank you for shopping with us!\n");
		
		sendToPrinter();
		scs.printer.cutPaper();

		lastReceipt = receiptString;
		receiptString = "";
	}
	
	/**
	 * Prints structured product description onto receipt.
	 * Includes multiplicity (if more than 1), product description (truncated based on maxDescLen),
	 *   price per item, and total price (including multiplicity).
	 * 
	 * @param description the product description string
	 * @param mult the multiplicity of the product being purchased
	 * @param price the price of a single copy of the product
	 */
	private void printReceiptBarcodedProduct(String description, long mult, BigDecimal price) {
		int curChar = 0;
		
		if (mult != 1) {
			String str = String.valueOf(mult);
			curChar += str.length() + 3;
			printReceiptString(str + " X ");
		}
		
		if (curChar + description.length() <= MAX_DESCRIPTION_LENGTH) {
			curChar += description.length();
			printReceiptString(description);
			printReceiptSpacing(MAX_DESCRIPTION_LENGTH - curChar);
		} else {
			printReceiptString(description.substring(0, MAX_DESCRIPTION_LENGTH - curChar - 3) + "...");
		}
		
		printReceiptSpacing(2);
		
		String str = roundBigDec(price);
		printReceiptSpacing(7 - str.length());
		printReceiptString(str);
		printReceiptSpacing(2);
		
		str = roundBigDec(price.multiply(new BigDecimal(Long.toString(mult))));
		printReceiptSpacing(7 - str.length());
		printReceiptString(str + "\n");
	}
	
	/**
	 * Prints structured product description onto receipt.
	 * Includes weight (rounded to two decimal places), product description (truncated based on maxDescLen),
	 *   price per item, and total price (including multiplicity).
	 * 
	 * @param description the product description string
	 * @param weight the total weight of the product being purchased
	 * @param price the price of a single copy of the product
	 */
	private void printReceiptPLUCodedProduct(String description, double weight, BigDecimal price) {
		int curChar = 0;
		
		String str = BigDecimal.valueOf(weight).setScale(2, RoundingMode.HALF_UP).toString();
		curChar += str.length() + 4;
		printReceiptString(str + " kg ");
		
		if (curChar + description.length() <= MAX_DESCRIPTION_LENGTH) {
			curChar += description.length();
			printReceiptString(description);
			printReceiptSpacing(MAX_DESCRIPTION_LENGTH - curChar);
		} else {
			printReceiptString(description.substring(0, MAX_DESCRIPTION_LENGTH - curChar - 3) + "...");
		}
		
		printReceiptSpacing(2);
		
		str = roundBigDec(price);
		printReceiptSpacing(7 - str.length());
		printReceiptString(str);
		printReceiptSpacing(2);
		
		str = roundBigDec(price.multiply(new BigDecimal(weight)));
		printReceiptSpacing(7 - str.length());
		printReceiptString(str + "\n");
	}
	
	/**
	 * Prints structured number of bags purchased onto receipt.
	 * Includes multiplicity (even if 1), product description (truncated based on maxDescLen),
	 *   price per item, and total price (including multiplicity).
	 * 
	 * @param num number of bags purchased
	 */
	private void printReceiptBags(int num) {
		String description = "Plastic Bags";
		int curChar = 0;
		
		String str = String.valueOf(num);
		curChar += str.length() + 3;
		printReceiptString(str + " X ");
		
		curChar += description.length();
		printReceiptString(description);
		printReceiptSpacing(MAX_DESCRIPTION_LENGTH - curChar);
		
		printReceiptSpacing(2);
		
		str = roundBigDec(SelfCheckoutStationLogic.BAG_COST);
		printReceiptSpacing(7 - str.length());
		printReceiptString(str);
		printReceiptSpacing(2);
		
		str = roundBigDec(SelfCheckoutStationLogic.BAG_COST.multiply(new BigDecimal(Long.toString(num))));
		printReceiptSpacing(7 - str.length());
		printReceiptString(str + "\n");
	}
	
	/**
	 * Prints structured debit / credit card information onto receipt.
	 * Includes the card number (with all but the last four digits censored) and the amount paid
	 * 
	 * @param number card number to be censored and printed
	 * @param amount amount paid using this card
	 */
	private void printReceiptCard(String number, BigDecimal amount) {
		printReceiptSpacing(4);
		for (int i = 0; i < number.length() - 4; ++i) {
			printReceiptString("*");
		}
		printReceiptString(number.substring(number.length() - 4));
		
		printReceiptSpacing(MAX_DESCRIPTION_LENGTH - number.length() + 7);
		String str = roundBigDec(amount);
		printReceiptSpacing(7 - str.length());
		printReceiptString(str + "\n");
	}
	
	/**
	 * Simple method to add a string to receiptString.
	 * 
	 * @param str string to print
	 */
	private void printReceiptString(String str) {
		receiptString += str;
	}
	
	/**
	 * Simple method to add spaces to receiptString.
	 * 
	 * @param num number of spaces to print
	 */
	private void printReceiptSpacing(int num) {
		for (int i = 0; i < num; ++i) {
			receiptString += ' ';
		}
	}
	
	/**
	 * Sends the formed string to the actual receipt printer.
	 */
	private void sendToPrinter() {
		for (int i = 0; i < receiptString.length(); ++i) {
			if (remainingPaper > 0 && remainingInk > 0) {
				char curChar = receiptString.charAt(i);
				
				try {
					scs.printer.print(curChar);
				} catch (EmptyException | OverloadException e) {
					// Should never happen
				}
				
				if (curChar == '\n') {
					--remainingPaper;
				} else if (curChar != ' ') {
					--remainingInk;
				}
			}
		}
		
		if (remainingPaper <= 0) {
			scsl.scsi.printInfoToAttendant("Out of receipt paper");
		} else if (remainingPaper < AttendantStationLogic.PAPER_ROLL * lowWarning) {
			scsl.scsi.printInfoToAttendant("Low on receipt paper");
		}
		
		if (remainingInk <= 0) {
			scsl.scsi.printInfoToAttendant("Out of receipt ink");
		} else if (remainingInk < AttendantStationLogic.INK_CARTRIDGE * lowWarning) {
			scsl.scsi.printInfoToAttendant("Low on receipt ink");
		}
	}
	
	/**
	 * Rounds a BigDecimal type to 2 digits (dollars and cents) and prepends a dollar sign.
	 * 
	 * @param price to be converted
	 * @return rounded price as string
	 */
	private String roundBigDec(BigDecimal price) {
		return "$" + price.setScale(2, RoundingMode.HALF_UP).toPlainString();
	}

	/**
	 * Only called by tests
	 */
	public String getLastReceipt() {
		return lastReceipt;
	}
}