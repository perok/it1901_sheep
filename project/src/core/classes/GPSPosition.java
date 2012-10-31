package core.classes;

public class GPSPosition {
	
	double latitute;
	double longditude;
	
	public GPSPosition(double latitute, double longditude){
		this.latitute = latitute;
		this.longditude = longditude;
	}

	public double getLatitute() {
		return latitute;
	}

	public void setLatitute(double latitute) {
		this.latitute = latitute;
	}

	public double getLongditude() {
		return longditude;
	}

	public void setLongditude(double longditude) {
		this.longditude = longditude;
	}

}
