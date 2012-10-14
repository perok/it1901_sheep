package com.net;

import java.io.Serializable;

public class Request implements Serializable {

	protected static final long serialVersionUID = 1;
	
	static final int REQUEST = 1, LOGOUT = 2;
	private int type;
	private String message;
	
	public Request(int type, String message) {
		this.type = type;
		this.message = message;
	}
	
	public Request(int type) {
		this.type = type;
	}
	
	// getters
	int getType() {
		return type;
	}
	String getMessage() {
		return message;
	}

}
