package com.gui;

import com.net.ClientSocket;
<<<<<<< HEAD
import core.settings.Settings;
=======
>>>>>>> f7d89ae8aa6017bcef50d4f3058a88b36577b9ca


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
