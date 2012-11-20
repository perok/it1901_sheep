package com.gui.logic;

import com.gui.UiLoginWindow;

public class UiLoginWindowLogic {
	private ServerLogic sLogic;
	//private UiLoginWindow lWindow;
	
	public UiLoginWindowLogic(UiLoginWindow lWindow, ServerLogic sLogic){
		this.sLogic = sLogic;
		//this.lWindow = lWindow;
		
    	lWindow.signalTryLogin.connect(this.sLogic, "tryLogIn(String, String)");
	}
}
