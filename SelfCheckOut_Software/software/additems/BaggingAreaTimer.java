package ca.ucalgary.seng300.selfcheckout.software.additems;

import java.util.concurrent.TimeUnit;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;

public class BaggingAreaTimer extends Thread {
	private SelfCheckoutStationLogic scsl;
	
	public Object lock = new Object();
	public volatile boolean runTimer = false;
	public boolean shutDown = false;
	
	public BaggingAreaTimer(SelfCheckoutStationLogic selfCheckoutStationLogic) {
		scsl = selfCheckoutStationLogic;
	}
	
	public void run() {
		while (shutDown == false) {
			if (runTimer == true) {
				try {
					TimeUnit.SECONDS.sleep(5);
					scsl.scsi.printInfoToCustomer("Please place item in the bagging area");
					scsl.scsp.blockStation();
				} catch (InterruptedException e) {
					// Scale is now at correct weight
				}
				
				synchronized (lock) {
					runTimer = false;
				}
			}
		}
	}
}
