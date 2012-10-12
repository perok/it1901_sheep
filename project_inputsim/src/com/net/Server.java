package com.net;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.db.*;

/*
 * The server that can be run both as a console application or a GUI
 */
public class Server {
    private static int uniqueId;
    private ArrayList<ClientHandler> al;
    public ServerGUI sg;
    private SimpleDateFormat sdf;
    private int port;
    private boolean keepGoing;
//    private DatabaseConnector db;

    public Server(int port) {
        this(port, null);
    }

    public Server(int port, ServerGUI sg) {
        this.sg = sg;
        this.port = port;
        // to display hh:mm:ss
        sdf = new SimpleDateFormat("HH:mm:ss");
        // ArrayList for the Client list
        al = new ArrayList<ClientHandler>();
    }

    public void start() {
        keepGoing = true;
        try 
        {
            // the socket used by the server
            ServerSocket serverSocket = new ServerSocket(port);

            // infinite loop to wait for connections
            while(keepGoing) 
            {
                // format message saying we are waiting
                display("Server waiting for Clients on port " + port + ".");

                Socket socket = serverSocket.accept();      // accept connection
                // if I was asked to stop
                if(!keepGoing)
                    break;
                ClientHandler t = new ClientHandler(socket,this);  // make a thread of it
                al.add(t);                                  // save it in the ArrayList
                new Thread(t).start();
            }
            // I was asked to stop
            try {
                serverSocket.close();
                for(int i = 0; i < al.size(); ++i) {
                	ClientHandler tc = al.get(i);
                    try {
                    tc.kill();
                    }
                    catch(IOException ioE) {
                        // not much I can do
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
    /*
     * For the GUI to stop the server
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
        // start server on port 1500 unless a PortNumber is specified 
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
        // create a server object and start it
        Server server = new Server(portNumber);
        server.start();
    }

	public Response HandleRequest() {
		return new Response(1, "Svar");
	}

  
}
