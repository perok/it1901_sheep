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
	private GPSPosition gpsPosition;
	private int farmId;
	private int weight;
	
	public Message(int id, int sheepId, int timestamp, float temperature, GPSPosition gpsPosition, int farmId, int weight) {
		this.setId(id);
		this.setSheep(sheepId);
		this.setTimestamp(timestamp);
		this.setTemperature(temperature);
		this.setGpsPosition(gpsPosition);
		this.setFarmId(farmId);
		this.setWeight(weight);
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
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public int getWeight(){
		return weight;
	}
}
