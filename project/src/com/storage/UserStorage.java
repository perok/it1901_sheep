package com.storage;

import core.classes.User;

public class UserStorage {
	private static int currentFarm;
	private static int currentMessageType;
	private static User user;
	
	private static int msgType = 0;
	
	
	public UserStorage(User user_){
		user = user_;
	}
	
	public static User getUser(){
		return user;
	}
	
	public static void setUser(User user_){
		user = user_;
	}
	

	public static int getCurrentFarm() {
		return currentFarm;
	}

	public static void setCurrentFarm(int currentFarm) {
		UserStorage.currentFarm = currentFarm;
	}

	public static int getCurrentMessageType() {
		return currentMessageType;
	}

	public static void setCurrentMessageType(int currentMessageType) {
		UserStorage.currentMessageType = currentMessageType;
	}
	
	public int getCurrentMessageTpe(){
		return msgType;
	}
	
	public void setCurrentMessageType1(int i) throws Exception{
		if(i >= 0 && i <= 1)
			msgType = i;
		else
			throw new Exception("MessageType don't ekist");
	}
	
}
