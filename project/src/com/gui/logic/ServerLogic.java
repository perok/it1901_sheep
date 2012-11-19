package com.gui.logic;

import com.net.Response;
import com.net.ClientSocket;
import com.storage.UserStorage;
import com.trolltech.qt.QSignalEmitter;

import core.classes.Sheep;


public class ServerLogic extends QSignalEmitter{
	
    private static ClientSocket clientSocket;
    private Object objectAskingForResponse = null;

    public Signal0 loggedIn;

    /**
     * Constructs the object
     */
	public ServerLogic(){
		loggedIn = new Signal0();
	}
	
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
		
		System.out.println("ClientSocket ready");
		try{
			System.out.println("ClientSocket starting");
			if(!clientSocket.start())
				System.out.println("Problem with connecting");
			else{
				
			System.out.println("ClientSocket loggin message sending");
				clientSocket.login(usrName, usrPW);
				System.out.println("ClientSocket loggin message sent");
			}
			
				
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
	
	public void handleResponse(Response response){
		System.out.println("Response incoming");
		System.out.println("Recieved response: "+ response.getType());
		
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
				System.out.println("Logged in with user: " + response.getUser().getName()
						+ "\nNumber of farms: " + response.getUser().getFarmlist().size());
				
				for(int i = 0; i < response.getUser().getFarmlist().size(); i++){
					System.out.println("=== Farm: " + response.getUser().getFarmlist().get(i).getId() + " ==");
					System.out.println("\tNumber of sheep: " + response.getUser().getFarmlist().get(i).getSheepList().size());
				}
				
				new UserStorage(response.getUser());
				UserStorage.setCurrentFarm(0);
				UserStorage.setCurrentMessageType(0);
				
				loggedIn.emit();
			}
		
		System.out.println("Response handled");				
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
	
	public void editSheep(Sheep sheep){
		clientSocket.editSheep(sheep);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clientSocket.getSheep(sheep.getFarmId());
	}
	
	protected void requestSheeps(Object o){
		if(objectAskingForResponse == null)
			objectAskingForResponse = o;
		//SendRequest
	}
}
