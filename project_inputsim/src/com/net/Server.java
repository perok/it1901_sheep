package com.net;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.db.DatabaseConnector;
import com.skype.PhoneNotifier;

import core.AlertNotifier;
import core.classes.Farm;
import core.classes.SheepAlert;
import core.settings.Settings;

public class Server {
	private ArrayList<ClientHandler> al;
	public ServerGUI sg;
	private SimpleDateFormat sdf;
	private int port;
	private boolean keepGoing;
	private DatabaseConnector db;
	private AlertNotifier notifier;
	private Settings settings;


	public Server(int port) {
		this(port, null);
	}

	public Server(int port, ServerGUI sg) {
		settings = new Settings();
		this.sg = sg;
		this.port = port;
		sdf = new SimpleDateFormat("HH:mm:ss");
		al = new ArrayList<ClientHandler>();
		db = new DatabaseConnector(settings);
		notifier = new AlertNotifier(settings);
	}

	public void start() {
		keepGoing = true;
		try 
		{
			ServerSocket serverSocket = new ServerSocket(port);

			while(keepGoing) 
			{
				notifier = new AlertNotifier(settings);
				new Thread(notifier).start();
				display("Server waiting for Clients on port " + port + ".");

				Socket socket = serverSocket.accept();				
				if(!keepGoing)
					break;
				ClientHandler t = new ClientHandler(socket,this);
				al.add(t); 
				new Thread(t).start();
			}
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientHandler tc = al.get(i);
					try {
						tc.kill();
					}
					catch(IOException ioE) {
						ioE.printStackTrace();
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		// something went bad
		catch (IOException e) {
			String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}
	}       
	/**Gui-method to stop the server
	 * 
	 */
	protected void stop() {
		keepGoing = false;
		// connect to myself as Client to exit statement 
		// Socket socket = serverSocket.accept();
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
			// nothing I can really do
		}
	}
	/*
	 * Display an event (not a message) to the console or the GUI
	 */
	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		if(sg == null)
			System.out.println(time);
		else
			sg.appendEvent(time + "\n");
	}

	/*
	 *  To run as a console application just open a console window and: 
	 * > java Server
	 * > java Server portNumber
	 * If the port number is not specified 1500 is used
	 */ 
	public static void main(String[] args) {
		int portNumber = 1500;
		switch(args.length) {
		case 1:
			try {
				portNumber = Integer.parseInt(args[0]);
			}
			catch(Exception e) {
				System.out.println("Invalid port number.");
				System.out.println("Usage is: > java Server [portNumber]");
				return;
			}
		case 0:
			break;
		default:
			System.out.println("Usage is: > java Server [portNumber]");
			return;

		}
		Server server = new Server(portNumber);
		server.start();
	}

	public Response HandleRequest(Request request) {
		switch(request.getMessage()){
		case("getSheep"):
			return new Response(Response.LIST,db.getSheep(Integer.parseInt(request.getparameter("farmId"))));
		
		case("getSheepStatus"):
			return new Response(Response.LIST,db.getSheepStatus(Integer.parseInt(request.getparameter("farmId"))));
			
		case("getSheepAlert"):
			return new Response(Response.LIST,db.getSheepAlert(Integer.parseInt(request.getparameter("farmId"))));
			
		case("deleteAccessRights"):
			return new Response(Response.BOOLEAN,db.removeAccessRights(Integer.parseInt(request.getparameter("userId")), Integer.parseInt(request.getparameter("farmId"))));
			
		case("addAccessRights"):
			return new Response(Response.BOOLEAN,db.addAccessRights(Integer.parseInt(request.getparameter("userId")), Integer.parseInt(request.getparameter("farmId"))));
			
		case("removeSheep"):
			return new Response(Response.BOOLEAN,db.removeSheep(Integer.parseInt(request.getparameter("sheepId"))));

		case("login"):
			return new Response(Response.USER,db.loginQuery(request.getparameter("username"), request.getparameter("password")));
		
		}

		return null;
	}
	
}
