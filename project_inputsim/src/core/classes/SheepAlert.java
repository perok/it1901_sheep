package core.classes;

import java.io.Serializable;

/**Class for generating sheep alert messages
 * 
 * @author Svenn
 *
 */
public class SheepAlert extends Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	public SheepAlert(int id, int sheepId, int timestamp, float temperature, int heartRate ,GPSPosition gpsPosition, int farmId) {
		super(id, sheepId, timestamp, temperature, heartRate, gpsPosition, farmId);
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
	public SheepAlert(int sheepId, int timestamp, float temperature, int heartRate ,GPSPosition gpsPosition, int farmId) {
		super(sheepId, timestamp, temperature, heartRate, gpsPosition, farmId);
	}

}
