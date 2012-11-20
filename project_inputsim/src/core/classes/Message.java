package core.classes;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int sheepId;
	private int timestamp;
	private float temperature;
	private int heartRate;
	private GPSPosition gpsPosition;
	private int farmId;
	
	public Message(int id, int sheepId, int timestamp, float temperature,int heartRate ,GPSPosition gpsPosition, int farmId) {
		this.setId(id);
		this.setSheep(sheepId);
		this.setTimestamp(timestamp);
		this.setTemperature(temperature);
		this.heartRate = heartRate;
		this.setGpsPosition(gpsPosition);
		this.setFarmId(farmId);
	}
	
	public Message(int sheepId, int timestamp, float temperature,int heartRate ,GPSPosition gpsPosition, int farmId) {
		this.setSheep(sheepId);
		this.setTimestamp(timestamp);
		this.setTemperature(temperature);
		this.heartRate = heartRate;
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
	
	public int getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}

	public GPSPosition getGpsPosition() {
		return gpsPosition;
	}

	public void setGpsPosition(GPSPosition gpsPosition) {
		this.gpsPosition = gpsPosition;
	}

	public int getFarmId() {
		return farmId;
	}

	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}
}
