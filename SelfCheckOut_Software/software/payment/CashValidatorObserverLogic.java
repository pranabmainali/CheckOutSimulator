package SelfCheckOut_Software.software.payment;

import java.util.Currency;
import java.math.BigDecimal;

import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationObserverLogic;

public class CashValidatorObserverLogic extends SelfCheckoutStationObserverLogic
	implements BanknoteValidatorObserver, CoinValidatorObserver {
	private SelfCheckoutStation scs;
	private PaymentFacade pf;
	private FinishCheckoutLogic fcl;
	
	public CashValidatorObserverLogic(SelfCheckoutStation selfCheckoutStation,
			PaymentFacade paymentFacade, FinishCheckoutLogic finishCheckoutLogic) {
		scs = selfCheckoutStation;
		pf = paymentFacade;
		fcl = finishCheckoutLogic;
		
		startUp();
	}

	public void startUp() {
		scs.banknoteValidator.attach(this);
		scs.coinValidator.attach(this);
	}
	
	public void shutDown() {
		scs.banknoteValidator.detach(this);
		scs.coinValidator.detach(this);
	}
	
	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		pf.totalFunds = pf.totalFunds.add(new BigDecimal(Integer.toString(value)));
		pf.cashPayments = pf.cashPayments.add(new BigDecimal(Integer.toString(value)));
		fcl.completeCheckout();

	}

	@Override
	public void invalidBanknoteDetected(BanknoteValidator validator) {}
	
	@Override
	public void validCoinDetected(CoinValidator validatorSimultor, BigDecimal valueSimulator) {
		pf.totalFunds = pf.totalFunds.add(valueSimulator);
		pf.cashPayments = pf.cashPayments.add(valueSimulator);
		fcl.completeCheckout();
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {}
}
