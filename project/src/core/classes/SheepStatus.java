package core.classes;

public class SheepStatus extends Message {

	public SheepStatus(int id, Sheep sheep, int timestamp, float temperature, GpsPosition gpsPosition, int farmId) {
		super(farmId, sheep, farmId, temperature, gpsPosition, farmId);
	}

}
