package com.gui.logic;

import com.net.Response;
import com.net.ClientSocket;
import com.storage.UserStorage;
import com.trolltech.qt.QSignalEmitter;

import java.util.ArrayList;

import core.classes.Farm;
import core.classes.Sheep;
import core.classes.User;


public class ServerLogic extends QSignalEmitter{
	
    private static ClientSocket clientSocket;
    private Object objectAskingForResponse = null;

    public Signal0 loggedIn;
    public Signal0 signalFarmListCreated;
    public Signal1<ArrayList<User>> signalUserDataRecieved;
    public Signal2<ArrayList<Sheep>, Integer> signalNewSheeps;

    /**
     * Constructs the object
     */
	public ServerLogic()
	{
		loggedIn = new Signal0();
		signalFarmListCreated = new Signal0();
		this.signalUserDataRecieved = new Signal1<ArrayList<User>>();
		this.signalNewSheeps = new Signal2<ArrayList<Sheep>, Integer>();

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
		System.out.println("Trying to log in with user: " + usrName);
		
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
		 * M� ha ett system der de ulike viewsa som kaller etter informasjon
		 * fra serveren blir registrert slikt at de kan sendes dit
		 */
		
		int responseType = response.getType();
		
		/* List */
		if(responseType == 1) 
		{			
			//There is content
			if(response.getContent() != null 
					&& response.getContent().isEmpty() == false) 
			{
				//Content is of a User response
				if(response.getContent().get(0) instanceof User){
					ArrayList<User> newUsers = new ArrayList<User>();
					for(Object o : response.getContent()){
						newUsers.add((User)o);
					}
					this.signalUserDataRecieved.emit(newUsers);
				}
				if(response.getContent().get(0) instanceof Sheep){
					System.out.println("recieved new SHEEPS");
					ArrayList<Sheep> newSheeps = new ArrayList<Sheep>();
					for(Object o : response.getContent()){
						newSheeps.add((Sheep)o);
					}
					this.signalNewSheeps.emit(newSheeps, newSheeps.get(0).getFarmId());
				}
				if(response.getContent().get(0) instanceof Farm)
				{
					ArrayList<Farm> lFarms = new ArrayList<Farm>();
					for(Object o : response.getContent())
					{
						lFarms.add((Farm)o);
					}
					
					UserStorage.setFarmList(lFarms);
					this.signalFarmListCreated.emit();
				}
			}
			System.out.println("response 1");
		}
		/* Boolean */
		else if(responseType == 2)
		{
			if(response.getInitialRequest().equals("editSheep") || response.getInitialRequest().equals("addSheep")){ 
				clientSocket.getSheep(response.getInitialRequestId());
				System.out.println("An action on the server was accepted");
			}
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
		//TODO: Alarm response!
		
		System.out.println("||Response handled||");				
	}
	
	/**
	 * Close the connection to the server
	 */
	public static void closeConnection(){
		if(clientSocket != null)
			clientSocket.disconnect();
	}
	
	/**
	 * Method called from clientsocket when it has had a connection failure
	 */
	public static void connectionFailed(){
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
	 * Add the sheep to the server
	 * @param sheep Sheep without ID.
	 */
	public void addSheep(Sheep sheep){
		clientSocket.addSheep(sheep);
	}
	
	/**
	 * Method for sending a sheep that has been edited to the server
	 * @param sheep Reference to the sheep that has been edited.
	 */
	public void editSheep(Sheep sheep){
		clientSocket.editSheep(sheep);
	}
	
	/**
	 * TODO: implement
	 * @param sheep
	 */
	public void deleteSheep(int id){
		clientSocket.removeSheep(id);
	}
}
