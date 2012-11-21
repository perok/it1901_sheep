package core.classes;

import java.io.Serializable;

/**Class generating sheep statuses
 * 
 * @author Svenn
 *
 */
public class SheepStatus extends Message  implements Serializable {

//	public SheepStatus(int id, int sheepId, int timestamp, float temperature, GpsPosition gpsPosition, int farmId) {
//		super(farmId, sheepId, farmId, temperature, gpsPosition, farmId);
//	}

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
	public SheepStatus(int id, int sheepId, int timestamp, float temperature, int heartRate ,GPSPosition gpsPosition, int farmId) {
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
	public SheepStatus(int sheepId, int timestamp, float temperature, int heartRate ,GPSPosition gpsPosition, int farmId) {
		super(sheepId, timestamp, temperature, heartRate, gpsPosition, farmId);
	}

}
