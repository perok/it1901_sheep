package com.net;

import java.net.*;
import java.io.*;
import java.util.*;

import com.gui.logic.ServerLogic;

import core.classes.Farm;
import core.classes.Sheep;
import core.classes.User;


/** Connects the system to a server via sockets. Also handles sending
 * of requests to the server.
 */
public class ClientSocket  {

	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private Socket socket;
	private ServerLogic caller;
	private String server, username;
	private int port;

	/**Gui constructor
	 * 
	 * @param server
	 * @param port
	 * @param username
	 * @param caller
	 */
	public ClientSocket(String server, int port, String username, ServerLogic caller) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.caller = caller;
	}

	/**Connects to the server and opens streams for objects to be sent.
	 * 
	 * @return
	 */
	public boolean start() {
		try {
			socket = new Socket(server, port);
		} 
		catch(Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}

		String req = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(req);

		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		new ListenFromServer().start();
		try
		{
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		return true;
	}


	/** Writes parameter to command line or GUI, depending on mode. 
	 * This primarily send error messages to the server.
	 * 
	 * @param req
	 */
	private void display(String req) {
		caller.handleMessage(req + "\n");
	}

	/**Transmits the request via outputstream to the server.
	 * 
	 * @param req
	 */
	void sendRequest(Request req) {
		try {
			sOutput.writeObject(req);
			sOutput.flush();
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	/**Closes all streams and shuts down the socket.
	 * 
	 */
	public void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) { /** Ignore errors */}
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {/** Ignore errors */} 
		try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {/** Ignore errors */} 

		if(caller != null)
			caller.connectionFailed();

	}

	/**Makes a request with the given parameters and sends it to the server.
	 * 
	 * @param username
	 * @param password
	 */
	public void login(String username, String password) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		sendRequest(new Request(Request.REQUEST, "login" ,params));
	}

	/** Makes a request with the given parameter and sends it to the server.
	 * 
	 * @param user
	 */
	public void editUser(User user) 
	{
		sendRequest(new Request(Request.EDITUSER, "editUser", user));
	}
	
	/** Makes a request with the given parameter and sends it to the server.
	 * 
	 * @param sheep
	 */
	public void addSheep(Sheep sheep) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("farmId", Integer.toString(sheep.getFarmId()));
		sendRequest(new Request(Request.REQUEST, "addSheep", sheep));
	}

	/**Makes a request with the given paramter and sends it to the server.
	 * 
	 * @param user
	 */
	public void editSheep(Sheep sheep) {
		sendRequest(new Request(Request.EDITSHEEP, "editSheep", sheep));
	}

	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param farm
	 */
	public void getSheep(Farm farm) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("farmId", Integer.toString(farm.getId()));
		sendRequest(new Request(Request.REQUEST, "getSheep", params));
	}

	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param farm
	 */
	public void getSheep(int farmId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("farmId", Integer.toString(farmId));
		sendRequest(new Request(Request.REQUEST, "getSheep", params));
	}

	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param sheepId
	 */
	public void removeSheep(int sheepId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sheepId", Integer.toString(sheepId));
		sendRequest(new Request(Request.REQUEST, "removeSheep", params));
	}

	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param user
	 * @param farm
	 */
	public void addAccessRights(User user, Farm farm) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", Integer.toString(user.getId()));
		params.put("farmId", Integer.toString(farm.getId()));
		sendRequest(new Request(Request.REQUEST, "removeSheep", params));
	}
	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param user
	 * @param farm
	 */
	public void removeAccessRights(User user, Farm farm) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", Integer.toString(user.getId()));
		params.put("farmId", Integer.toString(farm.getId()));
		sendRequest(new Request(Request.REQUEST, "removeSheep", params));
	}

	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param farm
	 */
	public void getSheepStatus(Farm farm) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("farmId", Integer.toString(farm.getId()));
		sendRequest(new Request(Request.REQUEST, "getSheepStatus", params));
	}

	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param farm
	 */
	public void getSheepAlert(Farm farm) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("farmId", Integer.toString(farm.getId()));
		sendRequest(new Request(Request.REQUEST, "getSheepAlert", params));
	}

	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param farm
	 */
	public void invokeAlert(Farm farm) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("farmId", Integer.toString(farm.getId()));
		sendRequest(new Request(Request.REQUEST, "invokeAlert", params));
	}
	
	/** Makes a request for all the users in an ArrayList and sends it to the server.
	 * 
	 */
	public void listUsers() {
		sendRequest(new Request(Request.REQUEST, "listUsersArrayList"));
	}
	

	/**Internal class(thread) that listens for input from the server. The object "caller"
	 * must have a method to handle the response it is given. When started the thread constantly
	 * listens for input and calls the "handleResponse" method in the "caller" object.
	 * 
	 * @author Lars Erik
	 */
	private class ListenFromServer extends Thread {
		protected ListenFromServer() {}
		public void run() {
			while(true) {
				try {
					Response res = (Response) sInput.readObject();			
					caller.handleResponse(res);
				}
				catch(IOException e) {
					display("Server has close the connection: " + e);
					if(caller != null) 
						caller.connectionFailed();
					break;
				}
				catch(ClassNotFoundException e2) {/** Ignore errors */
				}
			}
		}
	}
}


