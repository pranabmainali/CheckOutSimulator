package Utility;

import java.math.BigDecimal;
import java.util.Currency;

import SelfCheckOut_Software.attendant.AttendantStationInterface;
import SelfCheckOut_Software.attendant.AttendantStationOperations;
import SelfCheckOut_Software.software.SelfCheckoutStationLogic;

import SelfCheckOut_Hardware.Banknote;
import SelfCheckOut_Hardware.Coin;
import SelfCheckOut_Hardware.devices.BanknoteDispenser;
import SelfCheckOut_Hardware.devices.CoinDispenser;
import SelfCheckOut_Hardware.devices.SelfCheckoutStation;

import javax.swing.*;

public class DeviceSetupUtilities {
	public static JFrame devFrame;
	public static DevPanel devPanel;
	public static AttendantDevFrame adp;

	public static SelfCheckoutStation createSCS(int weightLimitInGrams, int sensitivity) {
		Currency currency = Currency.getInstance("CAD");
		int[] banknoteDenominations = {5, 10, 20, 50};
		BigDecimal[] coinDenominations = {new BigDecimal("0.05"), new BigDecimal("0.10"), new BigDecimal("0.25"), new BigDecimal("1.00"), new BigDecimal("2.00")};
		return new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, weightLimitInGrams, sensitivity);
	}
	
	public static void loadChange(SelfCheckoutStation scs) throws Exception {
		// load max banknotes
		for (int denom : scs.banknoteDenominations) {
			BanknoteDispenser disp = scs.banknoteDispensers.get(denom); 
			while (disp.size() < disp.getCapacity()) {
				disp.load(new Banknote(Currency.getInstance("CAD"), denom));
			}
		}
		
		// load max coins
		for (BigDecimal denom : scs.coinDenominations) {
			CoinDispenser disp = scs.coinDispensers.get(denom);
			while (disp.hasSpace() == true) {
				disp.load(new Coin(Currency.getInstance("CAD"), denom));
			}
		}
	}
	
	public static void loadStorage(SelfCheckoutStation scs) throws Exception {
		// load max banknotes
		int denomBanknote = scs.banknoteDenominations[0];
		while (scs.banknoteStorage.hasSpace() == true) {
			scs.banknoteStorage.load(new Banknote(Currency.getInstance("CAD"), denomBanknote));
		}
		
		// load max coins
		BigDecimal denomCoin = scs.coinDenominations.get(0);
		while (scs.coinStorage.hasSpace() == true) {
			scs.coinStorage.load(new Coin(Currency.getInstance("CAD"), denomCoin));
		}
	}
	
	/**
	 * Fully loads both banknote and coin storage except for some number of spaces
	 * 
	 * @param scs SelfCheckoutStation to load
	 * @param numBanknote number of banknote spaces to leave empty
	 * @param numCoin number of coin spaces to leave empty
	 */
	public static void loadStorage(SelfCheckoutStation scs, int numBanknote, int numCoin) throws Exception {
		// load max banknotes
		int denomBanknote = scs.banknoteDenominations[0];
		int numLoad = scs.banknoteStorage.getCapacity() - numBanknote;
		scs.banknoteStorage.unload();
		for (int i = 0; i < numLoad; ++i) {
			scs.banknoteStorage.load(new Banknote(Currency.getInstance("CAD"), denomBanknote));
		}
		
		// load max coins
		BigDecimal denomCoin = scs.coinDenominations.get(0);
		numLoad = scs.coinStorage.getCapacity() - numCoin;
		scs.coinStorage.unload();
		for (int i = 0; i < numLoad; ++i) {
			scs.coinStorage.load(new Coin(Currency.getInstance("CAD"), denomCoin));
		}
	}

	public static void createDevFrame(SelfCheckoutStation scs, SelfCheckoutStationLogic scsl, AttendantStationOperations aso, AttendantStationInterface asi){
		devPanel = new DevPanel(scs, scsl);
		devFrame = new JFrame();

		devFrame.setVisible(true);
		devFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		devFrame.add(devPanel);
		devFrame.setSize(1350, 600);
		devFrame.setLocation(0, 600);
		devFrame.setVisible(true);

		adp = new AttendantDevFrame(aso, asi);

		adp.setVisible(true);
		adp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		adp.setSize(375, 250);
		adp.setLocation(1400, 600);
		devFrame.setVisible(true);
	}

	public static void killDevFrame() {
		devFrame.dispose();
		adp.dispose();
	}
}