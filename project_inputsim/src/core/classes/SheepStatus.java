package core.classes;

import java.io.Serializable;

public class SheepStatus extends Message  implements Serializable {

//	public SheepStatus(int id, int sheepId, int timestamp, float temperature, GpsPosition gpsPosition, int farmId) {
//		super(farmId, sheepId, farmId, temperature, gpsPosition, farmId);
//	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SheepStatus(int id, int sheepId, int timestamp, float temperature, int heartRate ,GPSPosition gpsPosition, int farmId) {
		super(id, sheepId, timestamp, temperature, heartRate, gpsPosition, farmId);
	}
	
	public SheepStatus(int sheepId, int timestamp, float temperature, int heartRate ,GPSPosition gpsPosition, int farmId) {
		super(sheepId, timestamp, temperature, heartRate, gpsPosition, farmId);
	}

}
