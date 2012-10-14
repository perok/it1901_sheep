package com.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
	private Socket socket;
	private ObjectOutputStream sOutput;
	private ObjectInputStream sInput;
	private Request req;
	private Server server;
	private String username;
	private boolean keepGoing = true;

	public ClientHandler(Socket socket,Server server) {
		this.socket = socket;
		this.server = server;
	}


	@Override
	public void run() {
		try{
			sOutput = new ObjectOutputStream(socket.getOutputStream());
			sInput  = new ObjectInputStream(socket.getInputStream());
			username = (String) sInput.readObject();

			while(keepGoing){
				req = (Request) sInput.readObject();
				
				switch(req.getType()) {

                case Request.REQUEST:
                	server.sg.appendRoom(username + ": " + req.getMessage() );
                    Response res = server.HandleRequest(req);
    				sOutput.writeObject(res);

                    break;
                case Request.LOGOUT:
                    server.sg.appendRoom(username + " disconnected with a LOGOUT message.");
                    keepGoing = false;
    				kill();
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
	
	public void kill() throws IOException {
		sOutput.close();
		sInput.close();
		socket.close();
	}

}
