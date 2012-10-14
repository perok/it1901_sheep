package core.classes;

public class SheepAlert extends Message {

	public SheepAlert(int id, int sheepId, int timestamp, float temperature, GpsPosition gpsPosition, int farmId) {
		super(farmId, sheepId, farmId, temperature, gpsPosition, farmId);
	}

}
