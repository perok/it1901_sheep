package com.net;

import java.net.*;
import java.io.*;
import java.util.*;

import com.gui.logic.ServerLogic;

import core.classes.Farm;
import core.classes.Sheep;
import core.classes.User;
import core.settings.Settings;

public class ClientSocket  {

	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private Socket socket;
	private ServerLogic caller;
	private String server, username;
	private int port;

	/**Console constructor
	 * 
	 * @param server
	 * @param port
	 * @param username
	 */
	public ClientSocket(String server, int port, String username) {
		this(server, port, username, null);
	}
	
	public ClientSocket(Settings settings, String username) {
		//this.settings = settings
		this.username = username;
	}

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
	 * This method shows status and connections to the server
	 * 
	 * @param req
	 */
	
	private void display(String req) {
		if(caller == null)
			System.out.println(req);
		else
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
		catch(Exception e) {}
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} 
		try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} 

		if(caller != null)
			caller.connectionFailed();

	}


	public static void main(String[] args) {
		int portNumber = 1500;
		String serverAddress = "localhost";
		String userName = "Anonymous";

		ClientSocket ClientSocket = new ClientSocket(serverAddress, portNumber, userName);
		if(!ClientSocket.start())
			return;

		ClientSocket.disconnect();	
	}

	/**Makes a request with the given paramters and sends it to the server.
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
	
	/**Makes a request with the given paramter and sends it to the server.
	 * 
	 * @param user
	 */
	public void editUser(User user) {
		sendRequest(new Request(Request.EDITUSER, "editUser", user));
	}

	/**Makes a request with the given paramter and sends it to the server.
	 * 
	 * @param user
	 */
	public void editSheep(Sheep sheep) {
		System.out.println("EditSheep called");
		System.out.println(sheep.getId() + sheep.getName());
		sendRequest(new Request(Request.EDITSHEEP, "editSheep", sheep));
		System.out.println("Request sent");
	}
	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param farm
	 */
	public void getSheep(Farm farm) {
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("farmId", farm.getId());
		sendRequest(new Request(Request.REQUEST, "getSheep", params));
	}
	
	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param sheepId
	 */
	public void removeSheep(int sheepId) {
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("sheepId", sheepId);
		sendRequest(new Request(Request.REQUEST, "removeSheep", params));
	}
	
	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param user
	 * @param farm
	 */
	public void addAccessRights(User user, Farm farm) {
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("userId", user.getId());
		params.put("farmId", farm.getId());
		sendRequest(new Request(Request.REQUEST, "removeSheep", params));
	}
	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param user
	 * @param farm
	 */
	public void removeAccessRights(User user, Farm farm) {
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("userId", user.getId());
		params.put("farmId", farm.getId());
		sendRequest(new Request(Request.REQUEST, "removeSheep", params));
	}
	
	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param farm
	 */
	public void getSheepStatus(Farm farm) {
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("farmId", farm.getId());
		sendRequest(new Request(Request.REQUEST, "getSheepStatus", params));
	}
	
	/**Makes a request with the given paramters and sends it to the server.
	 * 
	 * @param farm
	 */
	public void getSheepAlert(Farm farm) {
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("farmId", farm.getId());
		sendRequest(new Request(Request.REQUEST, "getSheepAlert", params));
	}

	/**Internal class(thread) that listens for input from the server. The object "caller"
	 * must have a method to handle the response it is given. When started the thread constantly
	 * listens for input and calls the "handleResponse" method in the "caller" object.
	 * 
	 * @author Lars Erik
	 */
	class ListenFromServer extends Thread {
		public void run() {
			while(true) {
				try {
					Response res = (Response) sInput.readObject();			
					

					
					if(caller == null) {
						System.out.println(res.toString());
						System.out.print("> ");
					}
					else {
						caller.handleResponse(res);
					}
				}
				catch(IOException e) {
					display("Server has close the connection: " + e);
					if(caller != null) 
						caller.connectionFailed();
					break;
				}
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}


