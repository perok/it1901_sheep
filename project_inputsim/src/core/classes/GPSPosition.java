package core.classes;

import java.io.Serializable;

public class GPSPosition implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double latitute;
	double longditude;
	
	/**Constructor
	 * 
	 * @param latitute
	 * @param longditude
	 */
	public GPSPosition(double latitute, double longditude){
		this.latitute = latitute;
		this.longditude = longditude;
	}

	/**Returns latitude
	 * 
	 * @return
	 */
	public double getLatitute() {
		return latitute;
	}

	/**Sets latitude
	 * 
	 * @param latitute
	 */
	public void setLatitute(double latitute) {
		this.latitute = latitute;
	}

	/**Returns longditude
	 * 
	 * @return
	 */
	public double getLongditude() {
		return longditude;
	}

	/**Sets Longditude
	 * 
	 * @param longditude
	 */
	public void setLongditude(double longditude) {
		this.longditude = longditude;
	}
}