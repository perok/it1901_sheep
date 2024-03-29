package com.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.db.DatabaseConnector;

import core.classes.Message;
import core.settings.Settings;

/** Thread to handle connections from Client Socket.
 * 
 * @author Lars Erik
 *
 */
public class ClientHandler implements Runnable {
	private Socket socket;
	private ObjectOutputStream sOutput;
	private ObjectInputStream sInput;
	private Request req;
	private Server server;
	private String username;
	private boolean keepGoing = true;
	private DatabaseConnector db;
	private Settings settings;

	/** Constructor.
	 * 
	 * @param socket
	 * @param server
	 * @param settings
	 */
	public ClientHandler(Socket socket,Server server, Settings settings) {
		this.settings = settings;
		this.socket = socket;
		this.server = server;
		db = new DatabaseConnector(this.settings);
	}

	/** Returns the username of connected client.
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/** Starts the thread to allow communication with client.
	 * 
	 */
	@Override
	public void run() {
		try{
			sOutput = new ObjectOutputStream(socket.getOutputStream());
			sInput  = new ObjectInputStream(socket.getInputStream());
			username = (String) sInput.readObject();

			while(keepGoing){
				req = (Request) sInput.readObject();

				switch(req.getType()) {
				case Request.LOGOUT:
					server.display(username + " disconnected with a LOGOUT message.");
					keepGoing = false;
					kill();
					break;

				default :
					server.display(username + ": " + req.getMessage() );
					Response res = HandleRequest(req);
					sOutput.writeObject(res);
					sOutput.flush();
					break;
				}
			}
		}
		catch(IOException e) {
			server.sg.appendEvent(username + " Disconnected \n");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/** Kills streams and socket.
	 * 
	 * @throws IOException
	 */
	public void kill() throws IOException {
		sOutput.close();
		sInput.close();
		socket.close(); 
	}

	/**Processes requests from clients. Calls the appropriate db method based on the input given.
	 * 
	 * @param request
	 * @return
	 */
	public Response HandleRequest(Request request) {
		switch(request.getMessage()){
		case("getSheep"):
			return new Response(Response.LIST,db.getSheep(Integer.parseInt(request.getparameter("farmId"))),request.getMessage());

		case("getSheepStatus"):
			return new Response(Response.LIST,db.getSheepStatus(Integer.parseInt(request.getparameter("farmId"))),request.getMessage());

		case("getSheepAlert"):
			return new Response(Response.LIST,db.getSheepAlert(Integer.parseInt(request.getparameter("farmId"))),request.getMessage());

		case("deleteAccessRights"):
			return new Response(Response.BOOLEAN,db.removeAccessRights(Integer.parseInt(request.getparameter("userId")), Integer.parseInt(request.getparameter("farmId"))),request.getMessage());

		case("addAccessRights"):
			return new Response(Response.BOOLEAN,db.addAccessRights(Integer.parseInt(request.getparameter("userId")), Integer.parseInt(request.getparameter("farmId"))),request.getMessage());

		case("removeSheep"):
			return new Response(Response.BOOLEAN,db.removeSheep(Integer.parseInt(request.getparameter("sheepId"))),request.getMessage());
		
		case("login"):
			return new Response(Response.USER,db.loginQuery(request.getparameter("username"), request.getparameter("password")));
		
		case("addSheep"):
			return new Response(Response.BOOLEAN,db.insertSheep(request.getSheep()),request.getMessage(),request.getIntialRequestId());
		
		case("editSheep"):
			return new Response(Response.BOOLEAN,db.editSheep(request.getSheep().getId(), request.getSheep()),request.getMessage(),request.getIntialRequestId());

		case("editUser"):
			return new Response(Response.BOOLEAN,db.editUser(request.getUser().getId(), request.getUser()),request.getMessage());

		case("invokeAlert"):
			return new Response(Response.BOOLEAN,server.simulator.addAlert(Integer.parseInt(request.getparameter("farmId")), 1),request.getMessage());

		case("listUsersArrayList"):
			return new Response(Response.LIST,db.listUsersArrayList(),request.getMessage());

		case("listFarms"):
			return new Response(Response.LIST,db.listFarmsAsArray(),request.getMessage());
		}

		return null;
	}

	/**
	 * 
	 * @param messages
	 */
	public void sendUpdate(ArrayList<Message> messages) {
		try {
			sOutput.writeObject(new Response(Response.LIST,messages,"updatedMessages"));
			sOutput.flush();
		} catch (IOException e) {
			server.display("Error sending updates messages");
			e.printStackTrace();
		}
	}
	

}
