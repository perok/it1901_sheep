package com.storage;

import core.classes.User;

/** Class to hold userdata and make it easily availible
 * through static methods.
 * 
 * @author Gruppe 10
 *
 */
public class UserStorage {
	private static int currentFarm;
	private static int currentMessageType;
	private static User user;
	
	/** Constructor. Initialize..
	 * 
	 * @param user_ User-object this class should hold.
	 * 				Note that it will be distributed statically.
	 */
	public UserStorage(User user_){
		user = user_;
	}
		
	/** Return the user set to this class
	 * 
	 * @return currently logged in user.
	 */
	public static User getUser(){
		return user;
	}
	
	/** Set the user of this class
	 * 
	 * @param user_ the user to set for this class.
	 */
	public static void setUser(User user_){
		user = user_;
	}
	
	/** Set (modify) the username of the user in this class
	 * 
	 * @param sUsername the username to set for this user.
	 */
	public static void setUserName(String sUsername)
	{
		user.setName(sUsername);
	}
	
	/** Set (modify) the mail for the user in this class
	 * 
	 * @param sMail the mail to set/update for this user.
	 */
	public static void setUserMail(String sMail)
	{
		user.setEmail(sMail);
	}
	
	/** Set (modify) the phone-number for the user in this class
	 * 
	 * @param sPhoneNumber phone-number to use in this class.
	 * 					   will be translated to an integer.
	 */
	public static void setUserPhone(String sPhoneNumber)
	{
		/* Parameter should be safe as the input needs to pass a regex match,
		 * however, do please note that should there be an error here, then the application might crash */ 
		int iPhoneNumber = Integer.parseInt(sPhoneNumber);
		
		user.setMobileNumber(iPhoneNumber);
	}
	
	/** Return the farm currently in use in the GUI
	 * 
	 * @return Integer pointing to which farm in farmlist is set.
	 * @see User.getFarmList();
	 */
	public static int getCurrentFarm() {
		return currentFarm;
	}

	/** Set the current farm in use (for the GUI, primarily
	 * 
	 *  @param currentFarm integer holding what farm that should be set in a list of farms.
	 *  @see User.getFarmList();
	 */
	public static void setCurrentFarm(int currentFarm) {
		UserStorage.currentFarm = currentFarm;
	}

	/** Get the type of message that was sent from the server
	 * 
	 * @return an integer code for what kind of message that was sent from the server.
	 * @see ServerLogic.handleResponse();
	 */
	public static int getCurrentMessageType() {
		return currentMessageType;
	}

	/** Set the type of message that was sent from the server
	 * 
	 * @param currentMessageType an integer code for what kind of message that was sent from the server.
	 * @see ServerLogic.handleResponse();
	 */
	public static void setCurrentMessageType(int currentMessageType) {
		UserStorage.currentMessageType = currentMessageType;
	}
}

/* EOF */