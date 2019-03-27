package database;

import java.sql.Timestamp;

public class DataFromModule {

	private double latitude;
	private double longitude;
	private Timestamp timestamp; 
	private int RSSI;
	
		
	public DataFromModule() {} // end simple constructor
	
	public DataFromModule(double latitude, double longitude,
			Timestamp timestamp, int rSSI) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
		RSSI = rSSI;
	}	// end constructor

	
	/* Getters and setters */

	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public int getRSSI() {
		return RSSI;
	}


	public void setRSSI(int rSSI) {
		RSSI = rSSI;
	}
	
		
	
}	// end class
