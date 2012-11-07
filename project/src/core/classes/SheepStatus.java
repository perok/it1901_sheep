package core.classes;

import java.io.Serializable;

public class SheepStatus extends Message  implements Serializable {

//	public SheepStatus(int id, int sheepId, int timestamp, float temperature, GpsPosition gpsPosition, int farmId) {
//		super(farmId, sheepId, farmId, temperature, gpsPosition, farmId);
//	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SheepStatus(int parseInt, int parseInt2, int parseInt3,
			float parseFloat, GPSPosition gpsPosition, int parseInt4) {
		super(parseInt4, parseInt4, parseInt4, parseFloat, gpsPosition, parseInt4);
	}

}
