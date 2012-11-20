package core.classes;

import java.io.Serializable;

public class SheepAlert extends Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SheepAlert(int id, int sheepId, int timestamp, float temperature, int heartRate ,GPSPosition gpsPosition, int farmId) {
		super(id, sheepId, timestamp, temperature, heartRate, gpsPosition, farmId);
	}
	
	public SheepAlert(int sheepId, int timestamp, float temperature, int heartRate ,GPSPosition gpsPosition, int farmId) {
		super(sheepId, timestamp, temperature, heartRate, gpsPosition, farmId);
	}

}
