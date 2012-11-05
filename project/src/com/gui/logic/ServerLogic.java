package com.gui.logic;

import com.net.Response;
import com.net.ClientSocket;
import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.QSignalEmitter.Signal0;


public class ServerLogic extends QSignalEmitter{
	
    private static ClientSocket clientSocket;
    private Object objectAskingForResponse = null;

    public Signal0 loggedIn;

	public ServerLogic(){
		loggedIn = new Signal0();
	}
	
	/**
	 * Signaled method for setting up network connection and log in
	 * 
	 * @param usrName
	 * @param usrPW
	 */
	public void tryLogIn(String usrName, String usrPW){
		//For testing only
		loggedIn.emit();

		
		if(this.clientSocket == null )
			this.clientSocket = new ClientSocket("kord.dyndns.org", 1500, usrName, this);
		
		try{
			clientSocket.start();
			clientSocket.login(usrName, usrPW);
			/*if(!clientSocket.start())
				System.out.println("Problem with connecting");
			else
				clientSocket.login(usrName, usrPW);*/
				
		}
		catch (Exception e){
			System.out.println(e);
		}

	}
	
	public void handleResponse(Response response){
		System.out.println("Response: "+ response.getType());
		
		/*
		 * Må ha ett system der de ulike viewsa som kaller etter informasjon
		 * fra serveren blir registrert slikt at de kan sendes dit
		 */
		
		int responseType = response.getType();
		
		/* List */
		if(responseType == 1) {
			//A object has called for a list
			if (objectAskingForResponse != null){
				//Sheeplist asking for information
				//if( objectAskingForResponse instanceof SheepListWidget){
					//objectAskingForResponse.giveResponse(response);
					
					//objectAskingForResponse = null;
				//}
			}
			System.out.println("respinse 1");
		}
		/* Boolean */
		else if(responseType == 2)
			System.out.println("response 2");
		/* User */
		else if(response.getType() == 3)
			if(response.getUser() == null)
				/*Koble seg til loggininterface og gi beskjed der */
				System.out.println("Loggin failed, try again");
			else{
				System.out.println("Logged in with user: " + response.getUser().getName());
				loggedIn.emit();
			}
				
	}
	
	public void closeConnection(){
		if(clientSocket != null)
			clientSocket.disconnect();
	}
	
	public void connectionFailed(){
		System.out.println("Connection error");
	}
	
	public void handleMessage(String message){
		System.out.println("Message from server: " + message);
	}
	
	protected void requestSheeps(Object o){
		if(objectAskingForResponse == null)
			objectAskingForResponse = o;
		//SendRequest
	}
}
