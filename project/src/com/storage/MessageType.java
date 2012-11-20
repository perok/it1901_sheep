package com.storage;

/**
 * 
 * @author perok
 *
 */
public enum MessageType{
	SheepStatus(0),
	SheepAlert(1);
	
	private int currentType;
	
	MessageType(int i){
		currentType = i;
	}
	
	public void setCurrentMessageType(int i ){
		currentType = i;
	}
	
	public int getCurrentMessageType(){
		return this.currentType;
	}
} 
