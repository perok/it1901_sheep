package com.net;

import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {

	protected static final long serialVersionUID = 1;

	
	static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2;
	private int type;
	private String message;
	private ArrayList<String> content;
	private int callerId;
	
	Request(int type, String message) {
		this.type = type;
		this.message = message;
		this.callerId = callerId;
	}
	
	// getters
	int getType() {
		return type;
	}
	String getMessage() {
		return message;
	}
	ArrayList<String> getContent() {
		return content;
	}
}
