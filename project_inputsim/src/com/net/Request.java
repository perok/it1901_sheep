package com.net;

import java.io.Serializable;
import java.util.HashMap;

import core.classes.Sheep;
import core.classes.User;

public class Request implements Serializable {
	private static final long serialVersionUID = 2L;
	static final int REQUEST = 1, LOGOUT = 2, EDITUSER = 3, EDITSHEEP = 4;
	private int type;
	private String message;
	private User user;
	private Sheep sheep;
	private HashMap<String,String> parameters;

	public Request(int type, String message) {
		this.type = type;
		this.message = message;
		parameters = new HashMap<String,String>();
	}

	public Request(int type, String message, User user) {
		this.type = type;
		this.message = message;
		this.user = new User(user.getId(),user.getUsername(),user.getName(),user.getPassword(),
				user.getMobileNumber(),user.getEmail(),user.getFarmlist());
	}

	public Request(int type, String message, Sheep sheep) {
		System.out.println("Constructor called");
		this.type = type;
		this.message = message;
		this.sheep = new Sheep(sheep.getId(), sheep.getName(), sheep.getFarmId(), sheep.getDateOfBirth(),
				sheep.isAlive(), sheep.getWeight());
		System.out.println(sheep.getName());
	}

	public Request(int type, String message, HashMap<String, String> _parameters) {
		this.type = type;
		this.message = message;
		parameters = new HashMap<String, String>();
		for( String key : _parameters.keySet()){
			parameters.put(key, _parameters.get(key));
		}
	}

	public Request(int type) {
		this.type = type;
		parameters = new HashMap();
	}

	int getType() {
		return type;
	}
	String getMessage() {
		return message;
	} 

	public HashMap getparameters() {
		return parameters;
	}

	public void setparameters(HashMap parameters) {
		this.parameters = parameters;
	}

	public void addparameter(String name, String value) {
		parameters.put(name, value);
	}

	public String getparameter(String name) {
		return (String) parameters.get(name);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Sheep getSheep() {
		return sheep;
	}

	public void setSheep(Sheep sheep) {
		this.sheep = sheep;
	}

}
