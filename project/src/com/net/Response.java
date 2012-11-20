package com.net;

import java.io.*;
import java.util.ArrayList;

import core.classes.User;
	
public class Response implements Serializable { 
	private static final long serialVersionUID = 1L;
	static final int LIST = 1, BOOLEAN = 2, USER = 3;
	private int type;
	private boolean success;
	private User user;
	private ArrayList<User> content;
	
	public Response(int type, boolean success) {
		this.type = type;
		this.success = success;
	}
	
	public Response(int type, ArrayList<Object> content) {
		this.content = new ArrayList<>();
		this.type = type;
		for (int i = 0; i < content.size(); i++) {
			this.content.add((User)content.get(i));
		}
	}
	
	public Response(int type, boolean success, ArrayList<Object> content) {
		this.content = new ArrayList<>();
		this.type = type;
		this.success = success;
		for (int i = 0; i < content.size(); i++) {
			this.content.add((User)content.get(i));
		}
	}
	
	public Response(int type, User user) {
		this.type = type;
		this.user = new User(user.getId(),user.getUsername(),user.getName(),user.getPassword(),
				user.getMobileNumber(),user.getEmail(),user.getFarmlist());
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

	public ArrayList<User> getContent() {
		return content;
	}

	public void setContent(ArrayList<User> content) {
		for (int i = 0; i < content.size(); i++) {
			this.content.add((User)content.get(i));
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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