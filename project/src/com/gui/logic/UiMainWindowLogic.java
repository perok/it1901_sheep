package com.gui.logic;

import com.gui.SettingsMenu;
import com.gui.UiMainWindow;
import com.storage.UserStorage;
import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.core.QDate;
import com.trolltech.qt.gui.QLabel;

import core.classes.Sheep;

public class UiMainWindowLogic extends QSignalEmitter{
	
	UiMainWindow mw;
	sheepListWidgetHandler slwHandler;
	tableWidgetHandler twHandler;
	ServerLogic sLogic;
	
	QLabel statusbarMessage;
	
	public Signal0 signalShowAbout;
	public Signal0 signalShowAboutQt;
	
	private Sheep currentSheep;

	public UiMainWindowLogic(UiMainWindow mw, sheepListWidgetHandler slwHandler, tableWidgetHandler twHandler, ServerLogic sLogic){
		System.out.println("Applying logic");
		/* Storing referances */
		this.mw = mw;
		this.slwHandler = slwHandler;
		this.twHandler = twHandler;
		this.sLogic = sLogic;
		
		/* Setting up user information*/
		for(int i = 0; i < UserStorage.getUser().getFarmlist().size(); i++)
			mw.cmbDockFarmId.addItem(UserStorage.getUser().getFarmlist().get(i).getName());
		
		/* Setting up extra widgets*/
		statusbarMessage = new QLabel("Ready");
		mw.statusbar.addWidget(statusbarMessage);
		//Fiks mapWidget her..
		
		/* Adding values to ui */
		
		
		/* Setting up signals */
		signalShowAbout = new Signal0();
		signalShowAboutQt = new Signal0();
			//MainWinow
				//MENU
		mw.actionInformation_Window.toggled.connect(this, "actionInformation_Window_toggled(boolean)");
		mw.actionMap.toggled.connect(this, "actionMap_toggled(boolean)");
		mw.actionAbout.triggered.connect(this, "actionAbout_toggled(boolean)");
		mw.actionAbout_Qt_Jambi.triggered.connect(this, "actionAbout_Qt_Jambi_triggerd(boolean)");
		mw.actionExit.triggered.connect(this, "actionExit_toggled(boolean)");
		mw.actionUndo.triggered.connect(this, "actionUndo_toggled(boolean)");
		mw.actionSettings.triggered.connect(this, "actionSettings_triggered(boolean)");
		mw.actionSettings.setStatusTip("Show the settings for this application");

		
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
		//mw.ta
		
			//SheepListWidget
		this.slwHandler.statusBarMessage.connect(this, "newStatusBarMessage(String)");
		this.slwHandler.sheepSelected.connect(this, "populateTableWidget(Sheep)");		
		
		System.out.println("Logic applied");
		
		slwHandler.addSheep();
	}
	
	/* ACTIONS */
	
	private void actionAbout_toggled(boolean trigg){
		signalShowAbout.emit();
	}
	
	private void actionAbout_Qt_Jambi_triggerd(boolean trigg){
		signalShowAboutQt.emit();
	}
	    
    public void actionSettings_triggered(boolean triggered)
    {
    	new SettingsMenu(null).show();
    }
	
	
	//NOT WORKING 
	private void actionInformation_Window_toggled(boolean toggle){
		mw.tableWidget.setVisible(toggle);
		
		if(toggle){
			mw.tableWidget.show();
			//mw.tableWidget.update();
			//mw.splitter.update();
			//mw.splitter.updateGeometry();
			//mw.splitter.c
		}
		else
			mw.tabWidget.close();
		
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
		slwHandler.changeSortOrder();
		if(toggled)
			mw.rbAscDesc.setText("Ascending");
		else
			mw.rbAscDesc.setText("Descending");

	}
	
	private void cmbDockFarmId_currentIndexChanged(int index){
		System.out.println("INDEX CHAGNED " + index);

		UserStorage.setCurrentFarm(index);
		slwHandler.addSheep();
	}
	
	private void lineEdit_textChanged(String text){
		slwHandler.searchSheeps(text);
	}
	
		//TABWIDGET	
	/**
	 * Update button for the informaton tab
	 * 
	 * Handles the call to DB and update of locale sheep
	 * 
	 * @param click
	 */
	private void pbTabInformationUpdate_clicked(boolean click){
		System.out.println("Sheep updated clicked");
		Sheep sheepUpdate;
		//Not empty
		if(!mw.lEName.text().equals("") && !mw.lEFarmId.text().equals("") && Integer.parseInt(mw.lEFarmId.text()) != 0){
			sheepUpdate = new Sheep(currentSheep.getId(), mw.lEName.text(), Integer.parseInt(mw.lEFarmId.text()), 
					Integer.valueOf(String.valueOf(mw.dEBirthdaye.date().year()) + String.valueOf(mw.dEBirthdaye.date().month()) + String.valueOf(mw.dEBirthdaye.date().day())),
					mw.chbAlive.isChecked(), (int)mw.dSBWeight.value()); //MÅ FIKSES, skal ikke være int
			
			try{
				System.out.println("Sending edited sheep");
				sLogic.editSheep(sheepUpdate);
			}
			catch(Exception e){
				System.out.println("Sheep updating went in the toilet");
				e.printStackTrace();
			}
		}
		else
			statusbarMessage.setText("Some fields are blank, or not valid input..");
	}
	
	/**
	 * Resets the sheep information tab
	 * 
	 * @param click
	 */
	private void pbTabInformationReset_clicked(boolean click){
		if(currentSheep != null){			
			mw.lEName.setText(currentSheep.getName());
			mw.dEBirthdaye.setDate(new QDate(1991, 02, 25));//sheep.getDateOfBirth(), m, d))
			mw.dSBWeight.setValue((double)currentSheep.getWeight());
			mw.lEFarmId.setText(String.valueOf(currentSheep.getFarmId()));
			if(currentSheep.isAlive())
				mw.chbAlive.setChecked(true);
			else
				mw.chbAlive.setChecked(false);
			
			statusbarMessage.setText("Information reset done");
		}
			

	}
	
	private void cmbTabMessages_currentIndexChanged(int index){
		System.out.println("INDEX CHAGNED " + index);
	}
	
	private void pBSubmit_Add_clicked(boolean click){
		System.out.println("CLICK");

	}
	
	//STATUSBAR
	private void newStatusBarMessage(String text){
		statusbarMessage.setText(text);
	}
	
	//OTHER EVENTS
	
	private void populateTableWidget(Sheep sheep){
		currentSheep = sheep;
		
		//Sheep id, Sheep name, farmId
		mw.lblTabMessages.setText("Sheep#: " + sheep.getId() + "\tFarm#: " + sheep.getFarmId() + "\tName: " + sheep.getName());
		
		mw.lEName.setText(sheep.getName());
		mw.dEBirthdaye.setDate(new QDate(1991, 02, 25));//sheep.getDateOfBirth(), m, d))
		mw.dSBWeight.setValue((double)sheep.getWeight());
		mw.lEFarmId.setText(String.valueOf(sheep.getFarmId()));
		if(sheep.isAlive())
			mw.chbAlive.setChecked(true);
		else
			mw.chbAlive.setChecked(false);
		
		//Get messages for sheep
		//Send them to twHandler
		
		//this.twHandler.addSheep(Message)
	}
	

}

/* EOF */