package Utility;

import java.math.BigDecimal;
import java.util.Calendar;

import SelfCheckOut_Hardware.*;
import SelfCheckOut_Hardware.external.CardIssuer;
import SelfCheckOut_Hardware.external.ProductDatabases;
import SelfCheckOut_Hardware.products.BarcodedProduct;
import SelfCheckOut_Hardware.products.PLUCodedProduct;

import SelfCheckOut_Software.software.SelfCheckoutStationDatabase;

public class DatabaseUtilities {
	public static BarcodedItem createBarcodedItem(String barcodeStr, String description, BigDecimal price, double weight) {
		Barcode barcode = createBarcode(barcodeStr);
		BarcodedItem item = new BarcodedItem(barcode, weight);
		BarcodedProduct product = new BarcodedProduct(barcode, description, price, weight);
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);
		
		return item;
	}
	
	private static Barcode createBarcode(String barcode) {
		Numeral[] barcodeRaw = new Numeral[barcode.length()];
		
		for (int i = 0; i < barcode.length(); ++i) {
			if (Character.isDigit(barcode.charAt(i)) == true) {
				barcodeRaw[i] = Numeral.valueOf((byte) Character.digit(barcode.charAt(i), 10));
			} else {
				return null;
			}
		}
		
		return new Barcode(barcodeRaw);
	}
	
	public static PLUCodedItem createPLUCodedItem(String pluStr, String description, BigDecimal price, double weight) {
		PriceLookupCode plu = new PriceLookupCode(pluStr);
		PLUCodedItem item = new PLUCodedItem(plu, weight);
		PLUCodedProduct product = new PLUCodedProduct(plu, description, price);

		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu, product);

		return item;
	}

	public static void addCard(String number, String cardholder, Calendar expiry, String ccv, BigDecimal amount) {
		SelfCheckoutStationDatabase scsd = SelfCheckoutStationDatabase.getInstance();
		CardIssuer curIssuer = scsd.getCardIssuer(number.substring(0, SelfCheckoutStationDatabase.CARD_PREFIX));
		
		if (expiry == null) {
			expiry = Calendar.getInstance();
			expiry.add(Calendar.YEAR, 1);
		}
		
		if (curIssuer == null) {
			scsd.addCardIssuer(number.substring(0, SelfCheckoutStationDatabase.CARD_PREFIX), "Random issuer");
			curIssuer = scsd.getCardIssuer(number.substring(0, SelfCheckoutStationDatabase.CARD_PREFIX));
		}
		
		curIssuer.addCardData(number, cardholder, expiry, ccv, amount);
	}
}