package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@XmlRootElement
public class Route {
	
	private String userID;
	private String startLat;
	private String startLong;
	private String endLat;
	private String endLong;
	private Timestamp timeOfStart;
	private String nameOfRoute;
	
	public Route(){
		
	}
	
	public Route(String userID, String startLat, String startLong, String endLat, String endLong, String timeOfStart, String nameOfRoute){
		
		this.userID = userID;
		this.startLat = startLat;
		this.startLong = startLong;
		this.endLat = endLat;
		this.endLong = endLong;
		this.setNameOfRoute(nameOfRoute);
		
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss"); // plus " a" for AM/PM
		//String timeOfStart = "Friday, Jun 7, 2013 12:10:56 PM";
		
		try {
			 
			Date date = formatter.parse(timeOfStart);
			//System.out.println(date);
			//System.out.println(formatter.format(date));
			long time = date.getTime();
			this.timeOfStart = new Timestamp(time);
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the startLat
	 */
	public String getStartLat() {
		return startLat;
	}

	/**
	 * @param startLat the startLat to set
	 */
	public void setStartLat(String startLat) {
		this.startLat = startLat;
	}

	/**
	 * @return the startLong
	 */
	public String getStartLong() {
		return startLong;
	}

	/**
	 * @param startLong the startLong to set
	 */
	public void setStartLong(String startLong) {
		this.startLong = startLong;
	}

	/**
	 * @return the endLat
	 */
	public String getEndLat() {
		return endLat;
	}

	/**
	 * @param endLat the endLat to set
	 */
	public void setEndLat(String endLat) {
		this.endLat = endLat;
	}

	/**
	 * @return the endLong
	 */
	public String getEndLong() {
		return endLong;
	}

	/**
	 * @param endLong the endLong to set
	 */
	public void setEndLong(String endLong) {
		this.endLong = endLong;
	}

	/**
	 * @return the timeOfStart
	 */
	public Timestamp getTimeOfStart() {
		return timeOfStart;
	}

	/**
	 * @param timeOfStart the timeOfStart to set
	 */
	public void setTimeOfStart(Timestamp timeOfStart) {
		this.timeOfStart = timeOfStart;
	}

	/**
	 * @return the nameOfRoute
	 */
	public String getNameOfRoute() {
		return nameOfRoute;
	}

	/**
	 * @param nameOfRoute the nameOfRoute to set
	 */
	public void setNameOfRoute(String nameOfRoute) {
		this.nameOfRoute = nameOfRoute;
	}

}
