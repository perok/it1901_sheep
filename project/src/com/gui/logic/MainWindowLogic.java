package com.gui.logic;


import com.gui.SheepListWidget;
import com.gui.UiMainWindow;
import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;

public class MainWindowLogic extends QSignalEmitter{
	
	UiMainWindow mw;
	sheepListWidgetHandler slwHandler;
	
	QLabel statusbarMessage;
	
	public MainWindowLogic(UiMainWindow mw, sheepListWidgetHandler slwHandler){
		this.mw = mw;
		this.slwHandler = slwHandler;
		statusbarMessage = new QLabel("Ready");
		mw.statusbar.addWidget(statusbarMessage);
		
		/*
		mw.pbTabInformationUpdate.clicked.connect(signalOut)
		mw.pbTabInformationReset.clicked
		mw.pBSubmit_Add.clicked
		
		mw.tableWidget.cellClicked
		
		mw.listWidget.itemClicked.connect(receiver, method)
		
		mw.dockWidget.*/
		
		this.slwHandler.statusBarMessage.connect(this, "statusBarMessage(String)");
		
		
	}
	
	public void statusBarMessage(String text){
		System.out.println("hello there");
		statusbarMessage.setText(text);
	}
	

}
