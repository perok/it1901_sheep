package core.classes;

public class SheepAlert extends Message {

	public SheepAlert(int id, Sheep sheep, int timestamp, float temperature, GpsPosition gpsPosition, int farmId) {
		super(farmId, sheep, farmId, temperature, gpsPosition, farmId);
	}

}
