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
		mw.actionInformation_Window.toggled.connect(this, "actionInformation_Window_toggled(boolean)");
		mw.actionMap.toggled.connect(this, "actionMap_toggled(boolean)");
		mw.actionAbout.triggered.connect(this, "actionAbout_toggled(boolean)");
		mw.actionExit.triggered.connect(this, "actionExit_toggled(boolean)");
		mw.actionUndo.triggered.connect(this, "actionUndo_toggled(boolean)");
	
		
		
				//DOCKWIDGET
		mw.rbAscDesc.toggled.connect(this, "rbAscDesc_toggled(boolean)");
		mw.cmbDockFarmId.currentIndexChanged.connect(this, "cmbDockFarmId_currentIndexChanged(int)");
		mw.lineEdit.textChanged.connect(this, "lineEdit_textChanged(String)");
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
	
	/* ACTIONS */
	
	private void actionAbout_toggled(boolean trigg){
		System.out.println("wtf");
	}
	
	private void actionInformation_Window_toggled(boolean toggle){
		System.out.println(toggle);
	}
	private void actionMap_toggled(boolean trigg){
		System.out.println(trigg);
	}
	private void actionExit_toggled(boolean trigg){
		System.out.println(trigg);
	}
	private void actionUndo_toggled(boolean trigg){
		System.out.println(trigg);
	}
	
		//DockWidget
	private void rbAscDesc_toggled(boolean toggled){
		
	}
	
	private void cmbDockFarmId_currentIndexChanged(int index){
		
	}
	
	private void lineEdit_textChanged(String text){
		
	}
	
		//TABWIDGET	
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
