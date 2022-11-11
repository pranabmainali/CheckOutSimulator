package SelfCheckOut_Software.software;

import SelfCheckOut_Hardware.devices.AbstractDevice;
import SelfCheckOut_Hardware.devices.observers.AbstractDeviceObserver;

public class SelfCheckoutStationObserverLogic implements AbstractDeviceObserver {

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {}
}