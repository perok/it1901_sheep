package com.storage;

import core.classes.User;

public class UserStorage {
	private static int currentFarm;
	private static int currentMessageType;
	private static User user;
	
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
	
}
