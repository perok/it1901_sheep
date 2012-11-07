package core.classes;

import java.io.Serializable;

public class SheepAlert extends Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SheepAlert(int id, int sheepId, int timestamp, float temperature, GPSPosition gpsPosition, int farmId) {
		super(farmId, sheepId, farmId, temperature, gpsPosition, farmId);
	}

}
