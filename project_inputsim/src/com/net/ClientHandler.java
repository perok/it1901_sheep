package com.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.db.DatabaseConnector;
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
					server.sg.appendEvent(username + " disconnected with a LOGOUT message.");
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
			System.err.println(e);
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
			Response lol = new Response(Response.USER,db.loginQuery(request.getparameter("username"), request.getparameter("password")));
		return lol;
		case("editSheep"):
			return new Response(Response.BOOLEAN,db.editSheep(request.getSheep().getId(), request.getSheep()));

		case("editUser"):
			return new Response(Response.BOOLEAN,db.editSheep(request.getSheep().getId(), request.getSheep()));

		case("invokeAlert"):
			return new Response(Response.BOOLEAN,server.simulator.addAlert(Integer.parseInt(request.getparameter("farmId")), 1));
		
		case("listUsersArrayList"):
			return new Response(Response.BOOLEAN,db.listUsersArrayList());
		}
		
		


		return null;
	}

}
