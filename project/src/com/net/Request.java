package com.net;

import java.io.Serializable;
import java.util.HashMap;

import core.classes.Sheep;
import core.classes.User;

/**Wrapper class for information sent to server from client
 * 
 * @author Svenn
 *
 */

public class Request implements Serializable {
	private static final long serialVersionUID = 2L;
	static final int REQUEST = 1, LOGOUT = 2, EDITUSER = 3, EDITSHEEP = 4;
	private int type;
	private String message;
	private User user;
	private Sheep sheep;
	private HashMap<String,String> parameters;
	private int intialRequestId;

	/**Cnostructor
	 * 
	 * @param type
	 * @param message
	 */
	public Request(int type, String message) {
		this.type = type;
		this.message = message;
		parameters = new HashMap<String,String>();
	}

	/**Constructor
	 * 
	 * @param type
	 * @param message
	 * @param user
	 */
	public Request(int type, String message, User user) {
		this.type = type;
		this.message = message;
		this.user = new User(user.getId(),user.getUsername(),user.getName(),user.getPassword(),
				user.getMobileNumber(),user.getEmail(),user.getFarmlist());
	}

	/**Constructor
	 * 
	 * @param type
	 * @param message
	 * @param sheep
	 */
	public Request(int type, String message, Sheep sheep) {
		this.type = type;
		this.message = message;
		this.sheep = new Sheep(sheep.getId(), sheep.getName(), sheep.getFarmId(), sheep.getDateOfBirth(),
				sheep.isAlive(), sheep.getWeight());
	}
	
	/**Constructor
	 * 
	 * @param type
	 * @param message
	 * @param sheep
	 * @param id
	 */
	public Request(int type, String message, Sheep sheep,int id) {
		this.type = type;
		this.message = message;
		this.intialRequestId = id;
		this.sheep = new Sheep(sheep.getId(), sheep.getName(), sheep.getFarmId(), sheep.getDateOfBirth(),
				sheep.isAlive(), sheep.getWeight());
	}

	/**Constructor
	 * 
	 * @param type
	 * @param message
	 * @param _parameters
	 */
	public Request(int type, String message, HashMap<String, String> _parameters) {
		this.type = type;
		this.message = message;
		parameters = new HashMap<String, String>();
		for( String key : _parameters.keySet()){
			parameters.put(key, _parameters.get(key));
		}
	}
	
	/**Constructor
	 * 
	 * @param type
	 */
	public Request(int type) {
		this.type = type;
		parameters = new HashMap<String, String>();
	}

	/**Return type
	 * 
	 * @return
	 */
	int getType() {
		return type;
	}
	
	/**Returns message
	 * 
	 * @return
	 */
	String getMessage() {
		return message;
	} 

	/**Returns parameters
	 * 
	 * @return
	 */
	public HashMap getparameters() {
		return parameters;
	}

	/**Sets parameters
	 * 
	 * @param _parameters
	 */
	public void setparameters(HashMap<String, String> _parameters) {
		parameters = new HashMap<String, String>();
		for( String key : _parameters.keySet()){
			parameters.put(key, _parameters.get(key));
		}
	}

	/**Adds a parameter
	 * 
	 * @param name
	 * @param value
	 */
	public void addparameter(String name, String value) {
		parameters.put(name, value);
	}

	/**Returns parameter by name
	 * 
	 * @param name
	 * @return
	 */
	public String getparameter(String name) {
		return (String) parameters.get(name);
	}

	/**Returns user
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**Sets user
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**Returns sheep
	 * 
	 * @return
	 */
	public Sheep getSheep() {
		return sheep;
	}

	/**Sets sheep
	 * 
	 * @param sheep
	 */
	public void setSheep(Sheep sheep) {
		this.sheep = sheep;
	}
	
	/**Returns initial request id
	 * 
	 * @return
	 */
	public int getIntialRequestId() {
		return intialRequestId;
	}

}
