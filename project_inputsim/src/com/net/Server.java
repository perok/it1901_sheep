package com.net;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import core.AlertNotifier;
import core.classes.Message;
import core.settings.Settings;
import core.sim.SheepStatusSim;

/**Server that accpects sockets and creates ClientHandlers for connecting clients.
 * 
 * @author Lars Erik
 *
 */
public class Server {
	private ArrayList<ClientHandler> al;
	private SimpleDateFormat sdf;
	private int port;
	private boolean keepGoing;
	private Settings settings;
	public ServerGUI sg;
	public AlertNotifier notifier;
	public SheepStatusSim simulator;
	private ArrayList<Thread> threads;

	/**Constructor without GUI.
	 * 
	 * @param port
	 */
	public Server(int port) {
		this(port, null);
	}

	/**Constructor with GUI.
	 * 
	 * @param port
	 * @param sg
	 */
	public Server(int port, ServerGUI sg) {
		settings = new Settings();
		this.sg = sg;
		this.port = port;
		sdf = new SimpleDateFormat("HH:mm:ss");
		al = new ArrayList<ClientHandler>();
		threads = new ArrayList<Thread>();
		simulator = new SheepStatusSim(10, this);
	}

	/** Starts the server
	 * 
	 */
	public void start() {
		keepGoing = true;
		try 
		{
			ServerSocket serverSocket = new ServerSocket(port);
			notifier = new AlertNotifier(settings,this);
			new Thread(notifier).start();
			display("Server waiting for Clients on port " + port + ".");

			while(keepGoing) 
			{

				Socket socket = serverSocket.accept();				
				if(!keepGoing)
					break;
				ClientHandler t = new ClientHandler(socket,this,settings);
				al.add(t); 
				Thread thread = new Thread(t);
				thread.start();
				thread.setName(t.getUsername());
				threads.add(thread);
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
		//		 Socket socket = serverSocket.accept();
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
			// nothing I can really do
		}
	}

	/**
	 * Display an event to the GUI
	 */
	public void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		sg.appendEvent(time + "\n");
	}

	/** Notifies client of usernames and messages
	 * 
	 * @param usernames
	 * @param messages
	 */
	public void notifyClient(String[] usernames,ArrayList<Message> messages) {
		if(usernames != null) {
			for (int i = 0; i < al.size(); i++) {
				for (int j = 0; j < usernames.length; j++) {
					if(al.get(i).getUsername().equals(usernames[j])) {
						al.get(i).sendUpdate(messages);
					}
				}
			}
		}
	}

	public void shutdown(ClientHandler clientHandler) {
		for (int i = 0; i < threads.size(); i++) {
			if(threads.get(i).getName().equals(clientHandler.getUsername()))
				threads.get(i).stop();
		}
	}

}
