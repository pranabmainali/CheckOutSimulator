package SelfCheckOut_Hardware.devices.observers;

import SelfCheckOut_Hardware.Barcode;
import SelfCheckOut_Hardware.devices.BarcodeScanner;

/**
 * Observes events emanating from a barcode scanner.
 */
public interface BarcodeScannerObserver extends AbstractDeviceObserver {
	/**
	 * An event announcing that the indicated barcode has been successfully scanned.
	 * 
	 * @param barcodeScanner
	 *            The device on which the event occurred.
	 * @param barcode
	 *            The barcode that was read by the scanner.
	 */
	void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode);

}
