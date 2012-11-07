package com.storage;

import core.classes.User;

public class UserStorage {
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
}
