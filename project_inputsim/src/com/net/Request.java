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
	private HashMap parameters;
	
	public Request(int type, String message) {
		this.type = type;
		this.message = message;
		parameters = new HashMap();
	}
	
	public Request(int type, String message, User user) {
		this.type = type;
		this.user = user;
	}
	
	public Request(int type, String message, Sheep sheep) {
		this.type = type;
		this.sheep = sheep;
	}
	
	public Request(int type, String message, HashMap parameters) {
		this.type = type;
		this.message = message;
		parameters = new HashMap();
		this.parameters = parameters;
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
