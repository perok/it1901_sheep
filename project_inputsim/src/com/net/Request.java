package com.net;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
	protected static final long serialVersionUID = 1;
	static final int REQUEST = 1, LOGOUT = 2;
	private int type;
	private String message;
	private HashMap parameters;
	
	public Request(int type, String message) {
		this.type = type;
		this.message = message;
		parameters = new HashMap();
	}
	
	public Request(int type, String message, HashMap paramters) {
		this.type = type;
		this.message = message;
		parameters = new HashMap();
		this.parameters = paramters;
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
	
	public void addParamter(String name, String value) {
		parameters.put(name, value);
	}
	
	public String getParamter(String name) {
		return (String) parameters.get(name);
	}

}
