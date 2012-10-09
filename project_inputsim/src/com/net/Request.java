package com.net;

import java.io.Serializable;

public class Request implements Serializable {

	protected static final long serialVersionUID = 1;
	
	static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2;
	private int type;
	private String message;
	
	Request(int type, String message) {
		this.type = type;
		this.message = message;
	}
	
	// getters
	int getType() {
		return type;
	}
	String getMessage() {
		return message;
	}

}
