package ca.ucalgary.seng300.selfcheckout.attendant;

import javax.swing.JFrame;

import org.lsmr.selfcheckout.devices.SupervisionStation;

import ca.ucalgary.seng300.selfcheckout.software.gui.AttendantLoginPanel;
import ca.ucalgary.seng300.selfcheckout.software.gui.AttendantVisCat;
import ca.ucalgary.seng300.selfcheckout.software.gui.StationManagerPanel;

public class AttendantStationGuiFacade {

	public final AttendantLoginPanel attendantLoginPanel;
	public final StationManagerPanel stationManagerPanel;
	public final AttendantVisCat attendantVisCat;
	
	private final AttendantStationLogic asl;
	private final SupervisionStation ss;
	public final JFrame frame;
	
	
	public AttendantStationGuiFacade(AttendantStationLogic a, SupervisionStation s) {
		this.asl = a;
		this.ss = s;
		
		var ts = ss.screen;
		frame = ts.getFrame();
		
		attendantLoginPanel = new AttendantLoginPanel(asl, this);
		stationManagerPanel = new StationManagerPanel(asl, this);
		attendantVisCat = new AttendantVisCat(this);
		
		frame.add(attendantLoginPanel);
		frame.setSize(900, 600);
		frame.setLocation(900, 0);
		frame.setVisible(true);
	}
	
	public void updateSCSNum() {
		stationManagerPanel.refreshComponents();
	}
	
	public void loginToManager() {
		asl.loggedIn = true;
		frame.getContentPane().setVisible(false);
		frame.setContentPane(stationManagerPanel);
		frame.getContentPane().setVisible(true);
	}
	
	public void logoutOfManager() {
		asl.loggedIn = false;
		frame.getContentPane().setVisible(false);
		frame.setContentPane(attendantLoginPanel);
		frame.getContentPane().setVisible(true);
	}
	
	public void toVisCat() {
		frame.getContentPane().setVisible(false);
		frame.setContentPane(attendantVisCat);
		frame.getContentPane().setVisible(true);
	}
	
	public void exitVisCat() {
		frame.getContentPane().setVisible(false);
		frame.setContentPane(stationManagerPanel);
		frame.getContentPane().setVisible(true);
	}

	public void kill() {
		frame.dispose();
	}
}