package SelfCheckOut_Software.software;

import java.math.BigDecimal;

import SelfCheckOut_Software.software.gui.GuiFacade;
import SelfCheckOut_Hardware.devices.SelfCheckoutStation;

import SelfCheckOut_Software.attendant.AttendantStationLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationPhases.minorPhase;
import SelfCheckOut_Software.software.additems.AddItemsFacade;
import SelfCheckOut_Software.software.payment.PaymentFacade;

public class SelfCheckoutStationLogic {
	private SelfCheckoutStation scs;
	public AttendantStationLogic asl;
	
	public SelfCheckoutStationDatabase scsd;
	public SelfCheckoutStationInterface scsi;
	public SelfCheckoutStationPhases scsp;
	
	public AddItemsFacade aif;
	public PaymentFacade pf;
	public GuiFacade gf;

	public static final double SCALE_PRECISION = 0.1;
	public static final BigDecimal BAG_COST = new BigDecimal("0.05");
	
	public SelfCheckoutStationLogic(SelfCheckoutStation selfCheckoutStation) {
		scs = selfCheckoutStation;

		aif = new AddItemsFacade(scs, this);
		pf = new PaymentFacade(scs, this);
		gf = new GuiFacade(scs, this);

		scsd = SelfCheckoutStationDatabase.getInstance();
		scsi = new SelfCheckoutStationInterface(this);
		scsp = new SelfCheckoutStationPhases(scs, this);
	}
	
	public void startUp() {
		scsp.changePhase(minorPhase.MAINTENANCE);
		aif.startUp();
		pf.startUp();
	}
	
	public void shutDown() {
		scsp.changePhase(minorPhase.OFF);
		aif.shutDown();
		pf.shutDown();
	}
	
	public void addSupervisor(AttendantStationLogic attendantStationLogic) {
		asl = attendantStationLogic;
	}
	
	public void removeSupervisor() {
		asl = null;
	}
}