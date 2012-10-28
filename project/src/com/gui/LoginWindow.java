package com.gui;


public class LoginWindow {
	
	String username;
	String password;
	
	DatabaseConnector connector = new DatabaseConnector();
	
	loginUser User (String username, String password){
		return connector.loginQuery(username, password);
	}
	
}
