package ca.ucalgary.seng300.selfcheckout.software.additems;

import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationObserverLogic;
import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationPhases.minorPhase;

public class BaggingAreaObserverLogic extends SelfCheckoutStationObserverLogic
	implements ElectronicScaleObserver {
	private SelfCheckoutStation scs;
	private SelfCheckoutStationLogic scsl;
	private AddItemsFacade aif;
	
	public BaggingAreaObserverLogic(SelfCheckoutStation selfCheckoutStation,
			SelfCheckoutStationLogic selfCheckoutStationLogic, AddItemsFacade addItemsFacade) {
		scs = selfCheckoutStation;
		scsl = selfCheckoutStationLogic;
		aif = addItemsFacade;
		
		startUp();
	}
	
	public void startUp() {
		scs.baggingArea.attach(this);
	}
	
	public void shutDown() {
		scs.baggingArea.detach(this);
	}
	
	@Override
	public void weightChanged(ElectronicScale scale, double weightInGrams) {
		if (aif.scaleOverloaded == false) {
			aif.lastWeight = weightInGrams;
			if (Math.abs(weightInGrams - aif.totalWeight) <= (scs.baggingArea.getSensitivity() + SelfCheckoutStationLogic.SCALE_PRECISION)) {
				aif.scaleCorrect = true;
				if (scsl.scsp.getPhase() == minorPhase.PLACE_ITEM) {
					aif.bat.interrupt();
					scsl.scsp.changePhase(minorPhase.SCAN_ITEM);
				}
			} else {
				aif.scaleCorrect = false;
			}
		}
		scsl.gf.buildGUIList();
	}

	@Override
	public void overload(ElectronicScale scale) {
		aif.scaleOverloaded = true;
		aif.scaleCorrect = false;
	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		try {
			double weightInGrams = scs.baggingArea.getCurrentWeight();
			aif.lastWeight = weightInGrams;
			aif.scaleOverloaded = false;
			
			if (Math.abs(weightInGrams - aif.totalWeight) <= scs.baggingArea.getSensitivity()) {
				aif.scaleCorrect = true;
				if (scsl.scsp.getPhase() == minorPhase.PLACE_ITEM) {
					aif.bat.interrupt();
					scsl.scsp.changePhase(minorPhase.SCAN_ITEM);
				}
			}
		} catch (OverloadException e) {
			// Should never happen
		}
	}
}
