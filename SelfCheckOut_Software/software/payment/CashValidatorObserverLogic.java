package SelfCheckOut_Software.software.payment;

import java.util.Currency;
import java.math.BigDecimal;

import SelfCheckOut_Hardware.devices.BanknoteValidator;
import SelfCheckOut_Hardware.devices.CoinValidator;
import SelfCheckOut_Hardware.devices.SelfCheckoutStation;
import SelfCheckOut_Hardware.devices.observers.BanknoteValidatorObserver;
import SelfCheckOut_Hardware.devices.observers.CoinValidatorObserver;

import SelfCheckOut_Software.software.SelfCheckoutStationObserverLogic;

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
