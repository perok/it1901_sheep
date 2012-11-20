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
<<<<<<< HEAD
	private ArrayList content;
	private String initialRequest;
=======
	private ArrayList<Object> content;
>>>>>>> aa793f5809c63b634f94738fab578961afcd2eb4
	
	public Response(int type, boolean success, String req) {
		this.type = type;
		this.success = success;
		this.initialRequest = req;
	}
	
<<<<<<< HEAD
	public Response(int type, ArrayList content, String req) {
		this.content = new ArrayList<>();
=======
	public Response(int type, ArrayList<Object> content) {
		this.content = new ArrayList<Object>();
>>>>>>> aa793f5809c63b634f94738fab578961afcd2eb4
		this.type = type;
		for (int i = 0; i < content.size(); i++) {
			this.content.add(content.get(i));
		}
		this.initialRequest = req;
	}
	
<<<<<<< HEAD
	public Response(int type, boolean success, ArrayList content) {
		this.content = new ArrayList<>();
=======
	public Response(int type, boolean success, ArrayList<Object> content) {
		this.content = new ArrayList<Object>();
>>>>>>> aa793f5809c63b634f94738fab578961afcd2eb4
		this.type = type;
		this.success = success;
		for (int i = 0; i < content.size(); i++) {
			this.content.add(content.get(i));
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

<<<<<<< HEAD
	public ArrayList getContent() {
		return content;
	}

	public void setContent(ArrayList content) {
=======
	public ArrayList<Object> getContent() {
		return content;
	}

	public void setContent(ArrayList<Object> content) {
>>>>>>> aa793f5809c63b634f94738fab578961afcd2eb4
		for (int i = 0; i < content.size(); i++) {
			this.content.add(content.get(i));
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