package com.net;

import java.io.*;
import java.util.ArrayList;

import core.classes.User;
	
public class Response implements Serializable { 
	static final int LIST = 1, BOOLEAN = 2, USER = 3;
	private int type;
	private boolean success;
	private User user;
	private ArrayList content;
	
	public Response(int type, boolean success) {
		this.type = type;
		this.success = success;
	}
	
	public Response(int type, ArrayList content) {
		this.type = type;
		this.content = content;
	}
	
	public Response(int type, boolean success, ArrayList content) {
		this.type = type;
		this.success = success;
		this.content = content;
	}
	
	public Response(int type, User user) {
		this.type = type;
		this.user = user;
	}
	
	public int getType() {
		return type;
	}
	public boolean getSuccess() {
		return success;
	}
	
	public void setMessage(boolean success) {
		this.success = success;
	}

	public ArrayList getContent() {
		return content;
	}

	public void setContent(ArrayList content) {
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
			if(success)
				return "Success";
			else
				return "Fail";
	}
}

