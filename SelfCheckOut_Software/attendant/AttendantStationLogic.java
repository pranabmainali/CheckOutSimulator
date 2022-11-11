package SelfCheckOut_Software.attendant;

import java.util.ArrayList;
import java.util.List;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;

public class AttendantStationLogic {
	protected SupervisionStation ss;
	protected AttendantStationGuiFacade gf;
	protected List<SelfCheckoutStation> scs;
	protected List<SelfCheckoutStationLogic> scsl;
	protected List<Boolean> blocked;
	
	public AttendantStationDatabase asd;
	public AttendantStationInterface asi;
	public AttendantStationOperations aso;
	
	public static final int PAPER_ROLL = 100;
	public static final int INK_CARTRIDGE = 1000;
	
	protected boolean loggedIn = false;
	
	public AttendantStationLogic(SupervisionStation supervisionStation) {
		ss = supervisionStation;
		
		scs = new ArrayList<>();
		scsl = new ArrayList<>();
		blocked = new ArrayList<>();
		
		asd = AttendantStationDatabase.getInstance();
		asi = new AttendantStationInterface(this);
		aso = new AttendantStationOperations(this);
		gf = new AttendantStationGuiFacade(this, ss);
	}
	
	/**
	 * Adds a self checkout station and the corresponding logic to this attendant station.
	 * This method will also add it to the physical supervision station as well.
	 * 
	 * @param station to supervise
	 * @param logic to supervise
	 * @return true if add was successful, false otherwise
	 */
	public boolean add(SelfCheckoutStation station, SelfCheckoutStationLogic logic) {
		if (station != null && logic != null) {
			logic.addSupervisor(this);
			
			scs.add(station);
			scsl.add(logic);
			blocked.add(false);
			ss.add(station);
			gf.updateSCSNum();
			return true;
		}
		
		return false;
	}
	
	/**
	 * Removes a self checkout station and the corresponding logic from this attendant station.
	 * This method will also remove it from the physical supervision station as well.
	 * 
	 * @param station to remove
	 * @return true if remove was successful, false otherwise
	 */
	public boolean remove(SelfCheckoutStation station) {
		int idx = scs.indexOf(station);
		
		if (idx != -1) {
			scsl.get(idx).removeSupervisor();
			
			scs.remove(idx);
			scsl.remove(idx);
			blocked.remove(idx);
			return ss.remove(station);
		}
		
		return false;
	}
	
	/**
	 * Method to be called by the selfCheckoutStationLogic when the blocked status has changed.
	 * 
	 * @param logic station that has a status change
	 * @param isBlocked the new status
	 * @return true if station is found and updated correctly, false otherwise
	 */
	public boolean notifyBlockedStatus(SelfCheckoutStationLogic logic, boolean isBlocked) {
		int idx = scsl.indexOf(logic);
		
		if (idx != -1) {
			blocked.set(idx, isBlocked);
			return true;
		}
		
		return false;
	}
	
	public int getIdx(SelfCheckoutStationLogic logic) {
		return scsl.indexOf(logic);
	}

	public int getSCSLsize() {
		return scsl.size();
	}

	/**
	 * To be called by asi ONLY
	 *
	 * @param idx index of scsl to get
	 * @return
	 */
	public SelfCheckoutStationLogic getSCSL(int idx) {
		return scsl.get(idx);
	}
	
	public AttendantStationGuiFacade getASGF() {
		return this.gf;
	}
}