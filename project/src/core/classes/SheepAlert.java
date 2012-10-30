package core.classes;

import java.io.Serializable;

public class SheepAlert extends Message implements Serializable {

	public SheepAlert(int id, int sheepId, int timestamp, float temperature, GpsPosition gpsPosition, int farmId) {
		super(farmId, sheepId, farmId, temperature, gpsPosition, farmId);
	}

}
