package com.gui;

import com.net.ClientSocket;
import core.settings.Settings;


public class LoginWindow {
	
	String username;
	String password;
	
	ClientSocket connector = new ClientSocket(new Settings(),username);
	
	/**Sends login request to the server with username and password.
	 * 
	 * @param username
	 * @param password
	 */
	public void loginUser(String username, String password){
		connector.login(username, password);
	}
	
}
