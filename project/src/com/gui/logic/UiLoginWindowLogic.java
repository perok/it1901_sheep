package com.gui.logic;

import com.gui.UiLoginWindow;

public class UiLoginWindowLogic {
	private ServerLogic sLogic;
	
	/**
	 * Initializes the logic for the given UiLoginWindow
	 * @param lWindow The UiLoginWindow that this class shall handle.
	 * @param sLogic The ServerLogic class that shall be informed on inlogin tries.
	 */
	public UiLoginWindowLogic(UiLoginWindow lWindow, ServerLogic sLogic){
		this.sLogic = sLogic;
		
    	lWindow.signalTryLogin.connect(this.sLogic, "tryLogIn(String, String)");
	}
}
