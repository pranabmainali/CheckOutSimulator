package SelfCheckOut_Software.attendant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendantStationDatabase {
public static AttendantStationDatabase asd = new AttendantStationDatabase();
	
	private AttendantStationDatabase() {
	}
	
	public static AttendantStationDatabase getInstance() {
		return asd;
	}
	
	public void resetState() {
		employeeDatabase = new HashMap<>();
	}
	
	private Map<String, String> employeeDatabase = new HashMap<>();
	
	//******************************
	// Employee database utilities
	//******************************
	
	public boolean addEmployee(String id, String pass) {
		if (id != null && !id.equals("") && pass != null && !pass.equals("") && employeeDatabase.containsKey(id) == false) {
			employeeDatabase.put(id, pass);
			return true;
		}
		
		return false;
	}
	
	public boolean changePassword(String id, String pass) {
		if (id != null && !id.equals("") && pass != null && !pass.equals("") && employeeDatabase.containsKey(id) == true) {
			employeeDatabase.put(id, pass);
			return true;
		}
		
		return false;
	}
	
	public boolean isValidEmployee(String id, String pass) {
		if (Objects.equals(employeeDatabase.get(id), pass)) {
			return true;
		}
		
		return false;
	}
}