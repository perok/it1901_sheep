package com.net;

import java.io.*;
import java.util.ArrayList;


public class Response implements Serializable { 
	protected static final long serialVersionUID = 2;
	// The different types of message sent by the Client
	// WHOISIN to receive the list of the users connected
	// MESSAGE an ordinary message
	// LOGOUT to disconnect from the Server
	static final int MESSAGE = 1, LOGOUT = 2;
	private int type;
	private String message;
	private ArrayList<String> content;
	
	// constructor
	Response(int type, String message) {
		this.type = type;
		this.message = message;
	}
	
	Response(int type, String message, ArrayList<String> content) {
		this.type = type;
		this.message = message;
		this.content = content;
	}
	
	// getters
	public int getType() {
		return type;
	}
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<String> getContent() {
		return content;
	}

	public void setContent(ArrayList<String> content) {
		this.content = content;
	}
	
	public String toString() {
		return message;
	}
}


