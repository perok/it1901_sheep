package com.gui.logic;

import com.gui.MainWindow;
import com.gui.UiLoginWindow;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;

public class UiLoginWindowLogic {
	ServerLogic sLogic;
	UiLoginWindow lWindow;
	
	public UiLoginWindowLogic(UiLoginWindow lWindow, ServerLogic sLogic){
		this.sLogic = sLogic;
		this.lWindow = lWindow;
		
    	lWindow.tryLogin.connect(sLogic, "tryLogIn(String, String)");
	}
}
