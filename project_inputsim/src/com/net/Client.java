package com.net;


import java.net.*;
import java.io.*;
import java.util.*;

import core.classes.Farm;
import core.classes.User;


public class Client  {

	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private Socket socket;
	private ClientGUI cg;
	private String server, username;
	private int port;

	/*
	 *  Constructor called by console mode
	 *  server: the server address
	 *  port: the port number
	 *  username: the username
	 */
	public Client(String server, int port, String username) {
		// which calls the common constructor with the GUI set to null
		this(server, port, username, null);
	}

	/*
	 * Constructor call when used from a GUI
	 * in console mode the ClienGUI parameter is null
	 */
	public Client(String server, int port, String username, ClientGUI cg) {
		this.server = server;
		this.port = port;
		this.username = username;
		// save if we are in GUI mode or not
		this.cg = cg;
	}

	/**
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

		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);

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


	private void display(String msg) {
		if(cg == null)
			System.out.println(msg);
		else
			cg.append(msg + "\n");
	}


	void sendMessage(Request msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}


	private void disconnect() {
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

		if(cg != null)
			cg.connectionFailed();

	}


	public static void main(String[] args) {
		// default values
		int portNumber = 1500;
		String serverAddress = "localhost";
		String userName = "Anonymous";

		// depending of the number of arguments provided we fall through
		switch(args.length) {
		// > javac Client username portNumber serverAddr
		case 3:
			serverAddress = args[2];
			// > javac Client username portNumber
		case 2:
			try {
				portNumber = Integer.parseInt(args[1]);
			}
			catch(Exception e) {
				System.out.println("Invalid port number.");
				System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
				return;
			}
			// > javac Client username
		case 1: 
			userName = args[0];
			// > java Client
		case 0:
			break;
			// invalid number of arguments
		default:
			System.out.println("Usage is: > java Client [username] [portNumber] {serverAddress]");
			return;
		}
		// create the Client object
		Client client = new Client(serverAddress, portNumber, userName);
		if(!client.start())
			return;

		// wait for messages from user
		Scanner scan = new Scanner(System.in);
		// loop forever for message from the user
		while(true) {
			System.out.print("> ");
			String msg = scan.nextLine();
			if(msg.equalsIgnoreCase("LOGOUT")) {
				client.sendMessage(new Request(Request.LOGOUT, ""));
				break;
			}
			else {
				Request req = new Request(Request.REQUEST, msg);
				client.sendMessage(req);
			}
		}
		client.disconnect();	
	}

	public void login(String username, String password) {
		HashMap params = new HashMap();
		params.put("username", username);
		params.put("password", password);
		sendMessage((new Request(Request.REQUEST, "login" ,params)));
	}

	public void getSheep(Farm farm) {
		HashMap params = new HashMap();
		params.put("farmId", farm.getId());
		sendMessage(new Request(Request.REQUEST, "getSheep", params));
	}
	
	public void removeSheep(int sheepId) {
		HashMap params = new HashMap();
		params.put("sheepId", sheepId);
		sendMessage(new Request(Request.REQUEST, "removeSheep", params));
	}
	
	public void addAccessRights(User user, Farm farm) {
		HashMap params = new HashMap();
		params.put("userId", user.getId());
		params.put("farmId", farm.getId());
		sendMessage(new Request(Request.REQUEST, "removeSheep", params));
	}
	
	public void removeAccessRights(User user, Farm farm) {
		HashMap params = new HashMap();
		params.put("userId", user.getId());
		params.put("farmId", farm.getId());
		sendMessage(new Request(Request.REQUEST, "removeSheep", params));
	}
	
	public void getSheepStatus(Farm farm) {
		HashMap params = new HashMap();
		params.put("farmId", farm.getId());
		sendMessage(new Request(Request.REQUEST, "getSheepStatus", params));
	}
	
	public void getSheepAlert(Farm farm) {
		HashMap params = new HashMap();
		params.put("farmId", farm.getId());
		sendMessage(new Request(Request.REQUEST, "getSheepAlert", params));
	}

	class ListenFromServer extends Thread {
		public void run() {
			while(true) {
				try {
					Response msg = (Response) sInput.readObject();
					if(cg == null) {
						System.out.println(msg);
						System.out.print("> ");
					}
					else {
						cg.append(msg.toString());
					}
				}
				catch(IOException e) {
					display("Server has close the connection: " + e);
					if(cg != null) 
						cg.connectionFailed();
					break;
				}
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}


