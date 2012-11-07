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
		System.out.println("Applying logic");
		/* Storing referances */
		this.mw = mw;
		this.slwHandler = slwHandler;
		this.twHandler = twHandler;
		
		/* Setting up extra widgets*/
		statusbarMessage = new QLabel("Ready");
		mw.statusbar.addWidget(statusbarMessage);
		//Fiks mapWidget her..
		
		/* Adding values to ui */
		
		/* Setting up signals */
			//MainWinow
		//MENU
		//DOCKWIDGET
		//TABWIDGET
		mw.pbTabInformationUpdate.clicked.connect(this, "pbTabInformationUpdate_clicked(boolean)");
		mw.pbTabInformationReset.clicked.connect(this, "pbTabInformationReset_clicked(boolean)");
		
		mw.cmbTabMessages.currentIndexChanged.connect(this, "cmbTabMessages_currentIndexChanged(int)");
		mw.pBSubmit_Add.clicked.connect(this, "pBSubmit_Add_clicked(boolean)");
		
			//tableWidgetHandler
			//SheepListWidget
		this.slwHandler.statusBarMessage.connect(this, "newStatusBarMessage(String)");
		this.slwHandler.sheepSelected.connect(this, "populateTableWidget(Sheep)");
		
		
		/*
		mw.pbTabInformationUpdate.clicked.connect(signalOut)
		mw.pbTabInformationReset.clicked
		mw.pBSubmit_Add.clicked
		
		mw.tableWidget.cellClicked
		
		mw.listWidget.itemClicked.connect(receiver, method)
		
		mw.dockWidget.*/
		
		
		System.out.println("Logic applied");
		
	}
	
	private void pbTabInformationUpdate_clicked(boolean click){
		System.out.println("CLICK");
	}
	
	private void pbTabInformationReset_clicked(boolean click){
		System.out.println("CLICK");

	}
	
	private void cmbTabMessages_currentIndexChanged(int index){
		System.out.println("INDEX CHAGNED " + index);
	}
	
	private void pBSubmit_Add_clicked(boolean click){
		System.out.println("CLICK");

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
