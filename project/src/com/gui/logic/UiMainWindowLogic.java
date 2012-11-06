package com.gui.logic;


import com.gui.UiMainWindow;
import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.gui.QLabel;

import core.classes.Sheep;

public class UiMainWindowLogic extends QSignalEmitter{
	
	UiMainWindow mw;
	sheepListWidgetHandler slwHandler;
	tableWidgetHandler twHandler;
	ServerLogic sLogic;
	
	
	QLabel statusbarMessage;
	
	public UiMainWindowLogic(UiMainWindow mw, sheepListWidgetHandler slwHandler, tableWidgetHandler twHandler, ServerLogic sLogic){
		this.mw = mw;
		this.slwHandler = slwHandler;
		this.twHandler = twHandler;
		this.sLogic = sLogic;
		
		statusbarMessage = new QLabel("Ready");
		mw.statusbar.addWidget(statusbarMessage);
		
		/*
		mw.pbTabInformationUpdate.clicked.connect(signalOut)
		mw.pbTabInformationReset.clicked
		mw.pBSubmit_Add.clicked
		
		mw.tableWidget.cellClicked
		
		mw.listWidget.itemClicked.connect(receiver, method)
		
		mw.dockWidget.*/
		
		this.slwHandler.statusBarMessage.connect(this, "newStatusBarMessage(String)");
		this.slwHandler.sheepSelected.connect(this, "populateTableWidget(Sheep)");		
		
		//Debug
		slwHandler.addSheep();
		
	}
	
	private void newStatusBarMessage(String text){
		statusbarMessage.setText(text);
	}
	
	private void populateTableWidget(Sheep sheep){
		System.out.println("Sheep selected: " + sheep.getName());
		//Get messages for sheep
		//Send them to twHandler
		
		//this.twHandler.addSheep(Message)
	}
	

}
