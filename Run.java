import SelfCheckOut_Hardware.devices.SelfCheckoutStation;
import SelfCheckOut_Hardware.devices.SupervisionStation;
import SelfCheckOut_Software.attendant.AttendantStationDatabase;
import SelfCheckOut_Software.attendant.AttendantStationLogic;
import SelfCheckOut_Software.software.SelfCheckoutStationDatabase;
import SelfCheckOut_Software.software.SelfCheckoutStationLogic;
import Utility.*;

import java.math.BigDecimal;

public class Run {
    private SelfCheckoutStation scs;
	private SupervisionStation ss;
	private SelfCheckoutStationLogic scsl;
	private AttendantStationLogic asl;

    public Run () throws Exception{
        setup();
        
    }

    public void setup() throws Exception {
		scs = DeviceSetupUtilities.createSCS(100, 1);
		scsl = new SelfCheckoutStationLogic(scs);
		ss = new SupervisionStation();
		asl = new AttendantStationLogic(ss);
		asl.add(scs, scsl);
		AttendantStationDatabase.getInstance().addEmployee("Robert Walker", "12345");
		SelfCheckoutStationDatabase.getInstance().resetState();
		
		DatabaseUtilities.createBarcodedItem("1234", "Normal product", new BigDecimal("4.00"), 10);
		DatabaseUtilities.createBarcodedItem("4321", "Overload product", new BigDecimal("50.00"), 150);
		DatabaseUtilities.createBarcodedItem("1111", "Light product", new BigDecimal("0.75"), 0.5);
		DatabaseUtilities.createBarcodedItem("1111", "Light product", new BigDecimal("0.75"), 0.5);
		
		DatabaseUtilities.createPLUCodedItem("3000", "Apple", new BigDecimal("1.50"), 2d);
		DatabaseUtilities.createPLUCodedItem("4011", "Bananas", new BigDecimal("3.00"), 5.0d);

		scs.printer.addInk(1000);
		scs.printer.addPaper(100);
		DeviceSetupUtilities.loadChange(scs);
		// DeviceSetupUtilities.loadStorage(scs, 1, 1);  // fills storage except for a single banknote and a single coin
		
		DatabaseUtilities.addCard("1234567890123456", "Alice", null, "123", new BigDecimal("99999.99"));
		DatabaseUtilities.addCard("7890123456789012", "Bob", null, "123", new BigDecimal("20.05"));
		DatabaseUtilities.addCard("3456789012345678", "Eve", null, "123", new BigDecimal("99999.99"));
		DatabaseUtilities.addCard("9012345678901234", "Mark", null, "123", new BigDecimal("20.05"));
		
		DatabaseUtilities.addCard("9911222233334444", "Dave", null, "123", new BigDecimal("99999.99"));
		
		scsl.scsd.addMembership("123456");

		DeviceSetupUtilities.createDevFrame(scs, scsl, asl.aso, asl.asi);
	}
}
