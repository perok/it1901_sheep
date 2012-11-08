package com.gui.logic;

import com.gui.UiLoginWindow;

public class UiLoginWindowLogic {
	ServerLogic sLogic;
	UiLoginWindow lWindow;
	
	public UiLoginWindowLogic(UiLoginWindow lWindow, ServerLogic sLogic){
		this.sLogic = sLogic;
		this.lWindow = lWindow;
		
    	lWindow.tryLogin.connect(sLogic, "tryLogIn(String, String)");
	}
}
