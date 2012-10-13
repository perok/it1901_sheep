package com.net;

import java.io.*;
import java.util.ArrayList;


public class Response implements Serializable { 
	protected static final long serialVersionUID = 2;
	static final int LIST = 1, BOOLEAN = 2;
	private int type;
	private String message;
	private ArrayList content;
	
	// constructor
	public Response(int type, String message) {
		this.type = type;
		this.message = message;
	}
	
	public Response(int type, ArrayList content) {
		this.type = type;
		this.content = content;
	}
	
	public Response(int type, String message, ArrayList<String> content) {
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
		if(this.type == Response.LIST) {
			String out = new String();
			for (int i = 0; i < content.size(); i++) {
				out += content.get(i).toString();
			}
			return out;
		}
		else
			return message;
	}
}


