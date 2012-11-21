package core.classes;

import java.io.Serializable;

/**Class for generating status messages from sheeps.
 * 
 * @author Lars-Erik
 *
 */
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
	
	/**Constructor
	 * 
	 * @param id
	 * @param sheepId
	 * @param timestamp
	 * @param temperature
	 * @param heartRate
	 * @param gpsPosition
	 * @param farmId
	 */
	public Message(int id, int sheepId, int timestamp, float temperature,int heartRate ,GPSPosition gpsPosition, int farmId) {
		this.setId(id);
		this.setSheep(sheepId);
		this.setTimestamp(timestamp);
		this.setTemperature(temperature);
		this.heartRate = heartRate;
		this.setGpsPosition(gpsPosition);
		this.setFarmId(farmId);
	}
	
	/**Constructor
	 * 
	 * @param sheepId
	 * @param timestamp
	 * @param temperature
	 * @param heartRate
	 * @param gpsPosition
	 * @param farmId
	 */
	public Message(int sheepId, int timestamp, float temperature,int heartRate ,GPSPosition gpsPosition, int farmId) {
		this.setSheep(sheepId);
		this.setTimestamp(timestamp);
		this.setTemperature(temperature);
		this.heartRate = heartRate;
		this.setGpsPosition(gpsPosition);
		this.setFarmId(farmId);
	}

	/**Returns id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**Sets id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**Return sheepId
	 * 
	 * @return
	 */
	public int getSheep() {
		return sheepId;
	}

	/**set sheepId
	 * 
	 * @param sheepId
	 */
	public void setSheep(int sheepId) {
		this.sheepId = sheepId;
	}
	
	/**Return timestamp
	 * 
	 * @return
	 */
	public int getTimestamp() {
		return timestamp;
	}

	/**Sets timestamp
	 * 
	 * @param timestamp
	 */
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	/**Returns temperature
	 * 
	 * @return
	 */
	public float getTemperature() {
		return temperature;
	}

	/**Sets Temperature
	 * 
	 * @param temperature
	 */
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	
	/**Returns heart rate
	 * 
	 * @return
	 */
	public int getHeartRate() {
		return heartRate;
	}

	/**Sets heart rate
	 * 
	 * @param heartRate
	 */
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}

	/**Returns GPS position
	 * 
	 * @return
	 */
	public GPSPosition getGpsPosition() {
		return gpsPosition;
	}

	/**Set GPS position
	 * 
	 * @param gpsPosition
	 */
	public void setGpsPosition(GPSPosition gpsPosition) {
		this.gpsPosition = gpsPosition;
	}

	/**Returns farm id
	 * 
	 * @return
	 */
	public int getFarmId() {
		return farmId;
	}

	/**Sets farm id
	 * 
	 * @param farmId
	 */
	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}
}
