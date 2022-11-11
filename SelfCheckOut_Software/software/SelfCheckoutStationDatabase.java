package SelfCheckOut_Software.software;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import SelfCheckOut_Hardware.external.CardIssuer;
import SelfCheckOut_Hardware.external.ProductDatabases;

/**
 * Singleton so that all stations can access the same database easily.
 */
public class SelfCheckoutStationDatabase{
	public static SelfCheckoutStationDatabase scsd = new SelfCheckoutStationDatabase();
	
	private SelfCheckoutStationDatabase() {
		this.addCardIssuer("99", "GiftCard");
	}
	
	public static SelfCheckoutStationDatabase getInstance() {
		return scsd;
	}
	
	public void resetState() {
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
		ProductDatabases.PLU_PRODUCT_DATABASE.clear();
		membershipDatabase = new HashSet<>();
		cardIssuerDatabase = new HashMap<>();
		
		this.addCardIssuer("99", "GiftCard");
	}
	
	/**
	 * Set to store valid membership numbers.
	 */
	private Set<String> membershipDatabase = new HashSet<>();
	
	/**
	 * Number of starting digits to use to uniquely define card issuers.
	 */
	public static final int CARD_PREFIX = 2;
	
	/**
	 * Mapping from first CARD_PREFIX digits of card number to the cardIssuer.
	 * This mimics real world situation where the starting numbers of a card identify the issuer. 
	 */
	private Map<String, CardIssuer> cardIssuerDatabase = new HashMap<>();
	
	//******************************
	// Membership database utilities
	//******************************
	
	/**
	 * Add membership number to database.
	 * 
	 * @param str member number to add
	 * @return true if addition was successful, false otherwise
	 */
	public boolean addMembership(String str) {
		if (str != null && str != "" && isNumeric(str)
				&& membershipDatabase.contains(str) == false) {
			membershipDatabase.add(str);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check if membership number is valid
	 * 
	 * @param str member number to verify
	 * @return true if member number exists, false otherwise
	 */
	public boolean isMember(String str) {
		return membershipDatabase.contains(str);
	}
	
	//******************************
	// CardIssuer database utilities
	//******************************
	
	/**
	 * Generates a new CardIssuer with the provided name and starting prefix.
	 * 
	 * @param prefix initial digits of card numbers to uniquely define CardIssuer
	 * @param name name to use for the card issuer
	 * @return true if generation was successful, false otherwise
	 */
	public boolean addCardIssuer(String prefix, String name) {
		if (prefix != null && prefix.length() == CARD_PREFIX && isNumeric(prefix)
				&& name != null && name != "" && cardIssuerDatabase.containsKey(prefix) == false) { 
			cardIssuerDatabase.put(prefix, new CardIssuer(name));
			return true;
		}
		
		return false;
	}
	
	/**
	 * Get card issuer corresponding to the first CARD_PREFIX digits of the full card number.
	 * 
	 * @param number the first CARD_PREFIX digits of the full card number
	 * @return the CardIssuer corresponding to the input number
	 */
	public CardIssuer getCardIssuer(String prefix) {
		return cardIssuerDatabase.get(prefix);
	}
	
	//******************************
	// General utilities
	//******************************
	
	/**
	 * Verifies that the input string only contains "valid digits".
	 * Note that the Character.isDigit() function technically returns true on more than just 0-9,
	 *   but the other characters are all extended Unicode.
	 * 
	 * @param str string to verify
	 * @return true if input is numeric, false otherwise
	 */
	private boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); ++i) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		
		return true;
	}
}
