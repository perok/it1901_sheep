package com.gui;


public class LoginWindow {
	
	String username;
	String password;
	
	ClientSocket connector = new ClientSocket();
	
	loginUser User (String username, String password){
		return ClientSocket.login(username, password);
	}
	
}
