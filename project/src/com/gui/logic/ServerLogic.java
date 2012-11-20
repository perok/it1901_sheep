package com.gui.logic;

import com.net.Response;
import com.net.ClientSocket;
import com.storage.UserStorage;
import com.trolltech.qt.QSignalEmitter;

import java.util.ArrayList;
import core.classes.Sheep;
import core.classes.User;


public class ServerLogic extends QSignalEmitter{
	
    private static ClientSocket clientSocket;
    private Object objectAskingForResponse = null;

    public Signal0 loggedIn;
    public Signal1<ArrayList<User>> signalUserDataRecieved ;

    /**
     * Constructs the object
     */
	public ServerLogic()
	{
		loggedIn = new Signal0();
		this.signalUserDataRecieved = new Signal1<ArrayList<User>>();
	}
	
	/**
	 * Get clientsocket
	 * @return ClientSocket
	 */
	public static ClientSocket getClientsocket()
	{
		return clientSocket;
	}
	
	/**
	 * Signaled method for setting up network connection and log in
	 * 
	 * @param usrName
	 * @param usrPW
	 */
	public void tryLogIn(String usrName, String usrPW){
		//loggedIn.emit();
		
		System.out.println("Trying to log in with user: " + usrName);
		//For testing only
		//loggedIn.emit();
		if(clientSocket == null )
			clientSocket = new ClientSocket("kord.dyndns.org", 1500, usrName, this);
		
		try{
			if(!clientSocket.start())
				System.err.println("Problem with connecting");
			else{
				clientSocket.login(usrName, usrPW);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 * Method called by clientsocket when it recieved a response from the server
	 * 
	 * @param response
	 */
	public void handleResponse(Response response)
	{
		System.out.println("Recieved response: "+ response.getType());
		
		/*
		 * Mï¿½ ha ett system der de ulike viewsa som kaller etter informasjon
		 * fra serveren blir registrert slikt at de kan sendes dit
		 */
		
		int responseType = response.getType();
		
		/* List */
		if(responseType == 1) 
		{			
			if(response.getContent() != null 
			&& response.getContent().isEmpty() == false 
			&& response.getContent().get(0) instanceof User)
			{
				this.signalUserDataRecieved.emit(response.getContent());
			}
			
			//A object has called for a list
			if (objectAskingForResponse != null){
				//Sheeplist asking for information
				//if( objectAskingForResponse instanceof SheepListWidget){
					//objectAskingForResponse.giveResponse(response);
					
					//objectAskingForResponse = null;
				//}
			}
			System.out.println("response 1");
		}
		/* Boolean */
		else if(responseType == 2)
		{	
		}
		
		/* User */
		else if(response.getType() == 3)
			if(response.getUser() == null)
				/*Koble seg til loggininterface og gi beskjed der */
				System.out.println("Loggin failed, try again");
			else{
				System.out.println("Logged in with user: " + response.getUser().getName());
				
				new UserStorage(response.getUser());
				UserStorage.setCurrentFarm(0);
				UserStorage.setCurrentMessageType(0);
				
				loggedIn.emit();
			}
		
		System.out.println("Response handled");				
	}
	
	/**
	 * Close the connection to the server
	 */
	public void closeConnection(){
		if(clientSocket != null)
			clientSocket.disconnect();
	}
	
	/**
	 * Method called from clientsocket when it has had a connection failure
	 */
	public void connectionFailed(){
		System.err.println("Connection error");
	}
	
	/**
	 * Method called from clientsocket when it needs to give a message to the program
	 * @param message
	 */
	public void handleMessage(String message){
		System.out.println("Message from server: " + message);
	}
	
	/**
	 * TODO: IMPLEMENT
	 */
	public void addSheep(Sheep sheep){
		//clientSocket.
	}
	
	/**
	 * Method for sending a sheep that has been edited to the server
	 * @param sheep Reference to the sheep that has been edited.
	 */
	public void editSheep(Sheep sheep){
		clientSocket.editSheep(sheep);
		
		//TODO: skal ikke håndteres slik. SKal bli håndtert av serverlogic
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		clientSocket.getSheep(sheep.getFarmId());
	}
}
