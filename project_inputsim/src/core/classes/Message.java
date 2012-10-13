package core.classes;

public class Message {
	private int id;
	private int sheepId;
	private int timestamp;
	private float temperature;
	private GpsPosition gpsPosition;
	private int farmId;
	
	public Message(int id, int sheepId, int timestamp, float temperature, GpsPosition gpsPosition, int farmId) {
		this.setId(id);
		this.setSheep(sheepId);
		this.setTimestamp(timestamp);
		this.setTemperature(temperature);
		this.setGpsPosition(gpsPosition);
		this.setFarmId(farmId);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSheep() {
		return sheepId;
	}

	public void setSheep(int sheepId) {
		this.sheepId = sheepId;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public GpsPosition getGpsPosition() {
		return gpsPosition;
	}

	public void setGpsPosition(GpsPosition gpsPosition) {
		this.gpsPosition = gpsPosition;
	}

	public int getFarmId() {
		return farmId;
	}

	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}
}
