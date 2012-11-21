package com.gui.logic;

import org.json.simple.JSONArray;

import java.util.ArrayList;

import com.gui.UiMainWindow;
import com.gui.widgets.SettingsMenu;
import com.storage.UserStorage;
import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.core.QDate;
import com.trolltech.qt.core.QDateTime;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QLabel;

import core.classes.Farm;
import core.classes.Message;
import core.classes.Sheep;
import core.classes.SheepAlert;

import core.classes.SheepJS;

import core.classes.User;

public class UiMainWindowLogic extends QSignalEmitter
{
	private UiMainWindow mw;
	
	private SheepListWidgetLogic slwHandler;
	private TableWidgetLogic twHandler;
	private ServerLogic sLogic;
	
	private QLabel statusbarMessage;
	
	public Signal0 signalShowAbout;
	public Signal0 signalShowAboutQt;
	public Signal0 signalUpdateSheepList;
	public Signal1<ArrayList<User>> signalUserListRecieved;
	
	
	private Sheep currentSheep;
	
	@SuppressWarnings("unused")
	private void sendUserData(ArrayList<User> lUsers)
	{
		this.signalUserListRecieved.emit(lUsers);
	}

	/**
	 * Initialize the logic for the UiMainWindow.
	 * @param mw MainWindow. The main class.
	 * @param slwHandler SheepListWidgetHandler.
	 * @param twHandler TableWidgetLogic.
	 * @param sLogic ServerLogic.
	 */
	public UiMainWindowLogic(UiMainWindow mw, SheepListWidgetLogic slwHandler, TableWidgetLogic twHandler, ServerLogic sLogic){
		/* Storing referances */
		this.mw = mw;
		this.slwHandler = slwHandler;
		this.twHandler = twHandler;
		this.sLogic = sLogic;
		
		/* ServerLogic signals*/
		this.signalUserListRecieved = new Signal1<ArrayList<User>>();		
		sLogic.signalUserDataRecieved.connect(this, "sendUserData(ArrayList)");
		sLogic.signalNewSheeps.connect(this, "sLogic_signalNewSheeps(ArrayList, int)");
		
		/* Setting up user information*/
		if(UserStorage.getUser() != null)
			for(int i = 0; i < UserStorage.getUser().getFarmlist().size(); i++)
				mw.cmbDockFarmId.addItem(UserStorage.getUser().getFarmlist().get(i).getName());
		
		/* Setting up extra widgets*/
		statusbarMessage = new QLabel("Ready");
		mw.statusbar.addWidget(statusbarMessage);
		//Fiks mapWidget her..
		
		/* Adding values to ui */
		//Information tab defaults at open.
		mw.tabWidget.setCurrentIndex(0);
		mw.tabWidget.setContentsMargins(0, 0, 0, 0);
		mw.MAPWIDGET.setUrl(new QUrl("web/index.html"));
		
		/* Setting up signals */
		signalShowAbout = new Signal0();
		signalShowAboutQt = new Signal0();
		signalUpdateSheepList = new Signal0();
		
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
		this.slwHandler.sheepsShowOnMap.connect(this,"sheepsShowOnMap(ArrayList)");
		this.slwHandler.sheepsDelete.connect(this, "sheepsDelete(ArrayList)");

		
		slwHandler.refreshSheepList();
	}
	
	/* SERVERLOGIC*/
	/**
	 * Updates the UserStorage with the new sheeps and refreshes the list.
	 * @param sheeps
	 * @param farmID
	 */
	private void sLogic_signalNewSheeps(ArrayList<Sheep> sheeps, int farmID){
		for(Farm farm : UserStorage.getUser().getFarmlist()){
			if(farm.getId() == farmID){
				farm.setSheepList(sheeps);
				
				//TODO: DOES NOT WORK. CurrentFarm needs to be fixed..
				//Only refresh sheeplist if the changed sheeps are in the row
				//if(UserStorage.getCurrentFarm() == farmID)
				QApplication.invokeLater(new Runnable() {
				    @Override
				    public void run() {
				    	slwHandler.refreshSheepList();
				    }
				});
				
				break;
			}
		}
		
		
		
	}
	
	
	/* ACTIONS */
	
	/**
	 * Shows a popupmessage about the program
	 * @param trigg
	 */
	@SuppressWarnings("unused")
	private void actionAbout_toggled(boolean trigg){
		signalShowAbout.emit();
	}
	
	/**
	 * Shows a popupmessage about Qt
	 * @param trigg
	 */
	@SuppressWarnings("unused")
	private void actionAbout_Qt_Jambi_triggerd(boolean trigg)
	{
		signalShowAboutQt.emit();
	}
	    
	/**
	 * Pops up the settings widget
	 * @param triggered
	 */
    public void actionSettings_triggered(boolean triggered)
    {
    	SettingsMenu spawn = new SettingsMenu(this.mw.getMother());
    	
    	spawn.show();
    	spawn.signalFarmChanged.connect(this, "updateSheepList()");
    	this.signalUserListRecieved.connect(spawn, "sendData(ArrayList)");
    	sLogic.signalFarmListCreated.connect(spawn, "notifyChildren()");
    }
    
    @SuppressWarnings("unused")
    private void updateSheepList()
    {
    	this.slwHandler.refreshSheepList();
    	this.mw.cmbDockFarmId.setCurrentIndex(UserStorage.getCurrentFarm());
    }
	
	//NOT WORKING FIXME:
	@SuppressWarnings("unused")
	private void actionInformation_Window_toggled(boolean toggle){
		//mw.tableWidget.setVisible(toggle);
		
		if(toggle){
			mw.tableWidget.hide();
			//mw.tableWidget.update();
			//mw.splitter.update();
			//mw.splitter.updateGeometry();
			//mw.splitter.c
		}
		else
			mw.tabWidget.show();
		
		System.out.println(toggle);
	}
	
	@SuppressWarnings({ "unused", "static-method" })
	private void actionMap_toggled(boolean trigg){
		System.out.println(trigg);
	}
	
	/**
	 * Closes the program
	 * @param trigg
	 */
	@SuppressWarnings("unused")
	private void actionExit_toggled(boolean trigg){
		sLogic.closeConnection();
		System.exit(0);
	}

	/**
	 * Method that is called when actionUndo is toggled
	 * @param trigg
	 */
	@SuppressWarnings("unused")
	private void actionUndo_toggled(boolean trigg){
		/** TODO: add undo-action here */
	}

	/**
	 * Method called when the combo box in the SheepListWidget's index is changed.
	 * @param index Index changed too.
	 */
	@SuppressWarnings("unused")
	private void cmbDockFarmId_currentIndexChanged(int index){
		UserStorage.setCurrentFarm(index);
		slwHandler.refreshSheepList();
	}

	/**
	 * Method called when the combo box in the Message tab in the TableWidget is changed.
	 * @param index Index changed too.
	 */
	@SuppressWarnings("unused")
	private void cmbTabMessages_currentIndexChanged(int index){
			UserStorage.setCurrentMessageType(index);
			this.twHandler.updateMessages(currentSheep);
		}

	/**
	 * Method called when the search line edit box's text is changed.
	 * Starts searching through the SheepListWidget's sheeps.
	 * @param text Current text
	 */
	@SuppressWarnings("unused")
	private void lineEdit_textChanged(String text){
		slwHandler.searchSheeps(text);
	}
	
		//STATUSBAR
	/**
	 * Method called when some object wants to update some given text to the status bar.
	 * @param text Text to show.
	 */
	@SuppressWarnings("unused")
	private void newStatusBarMessage(String text){
			statusbarMessage.setText(text);
	}
	
	

	//OTHER EVENTS
	/**
	 * Delete sheeps from server and client.
	 * @param sheeps
	 */
	private void sheepsDelete(ArrayList<Sheep> sheeps){
		int currentFarm = UserStorage.getCurrentFarm();
		for(Sheep sheep : sheeps){
			sLogic.deleteSheep(sheep.getId());
			UserStorage.getUser().getFarmlist().get(currentFarm).getSheepList().remove(sheep);
		}
	}
	
	/**
	 * Event triggered when multiple sheeps are selected in the sheep list.
	 * Used for showing the selected sheeps in the map
	 * @param sheeps
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private void sheepsShowOnMap(ArrayList<Sheep> sheeps){
		
		JSONArray arr = new JSONArray();
		//Go through all the sheeps
		for (Sheep sheep : sheeps){
			if(sheep.getRecentStatuses() != null && sheep.getRecentStatuses().size() > 0){
				double lat = sheep.getRecentStatuses().get(0).getGpsPosition().getLatitute();
				double lon = sheep.getRecentStatuses().get(0).getGpsPosition().getLongditude();
				boolean isAlert = false;
				
				//Set right alert prefix
				if((Message)sheep.getRecentStatuses().get(0) instanceof SheepAlert)
					isAlert = true;
				
				QDateTime d = new QDateTime();
				d.setTime_t(((Message)sheep.getRecentStatuses().get(0)).getTimestamp());
				
				arr.add(new SheepJS(sheep.getId(), sheep.getName(),sheep.isAlive(), isAlert, lat, lon, d.toString()));
				
			}
		}
		
		if (arr.size() > 0){
			mw.MAPWIDGET.page().mainFrame().evaluateJavaScript("receiveJSONMany("+ arr +")");
		}
	}
		
		/**
		 * Populates the tabWidget
		 * @param sheep
		 */
	@SuppressWarnings({ "unused", "unchecked" })
	private void populateTableWidget(Sheep sheep){
		mw.tabWidget.setCurrentIndex(0);
		
		//MAP
		JSONArray arr = new JSONArray();
		
		for (Message msg : sheep.getRecentStatuses()){
			boolean isAlert = false;
			
			if(msg instanceof SheepAlert)
				isAlert = true;
			
			QDateTime d = new QDateTime();
			d.setTime_t(msg.getTimestamp());
			
			
			arr.add(new SheepJS(sheep.getId(), sheep.getName(),sheep.isAlive(), isAlert, msg.getGpsPosition().getLatitute(), msg.getGpsPosition().getLongditude(), d.toString() ));
		}
		
		if (arr.size() > 0){
			mw.MAPWIDGET.page().mainFrame().evaluateJavaScript("receiveJSONOne("+ arr +")");
		}
		else
			mw.MAPWIDGET.page().mainFrame().evaluateJavaScript("receiveJSONRemove()");
		
		//TABLEWIDGET	
		currentSheep = sheep;
		
		//Sheep id, Sheep name, farmId
		mw.lblTabMessages.setText("Sheep#: " + sheep.getId() + "\tFarm#: " + sheep.getFarmId() + "\tName: " + sheep.getName());
		
		mw.lEName.setText(sheep.getName());
		
		QDateTime d = new QDateTime();
		d.setTime_t(sheep.getDateOfBirth());
		mw.dEBirthdaye.setDateTime(d);

		mw.dSBWeight.setValue((double)sheep.getWeight());
		mw.lEFarmId.setText(String.valueOf(sheep.getFarmId()));
		if(sheep.isAlive())
			mw.chbAlive.setChecked(true);
		else
			mw.chbAlive.setChecked(false);
		
		//Get messages for sheep
		//Send them to twHandler
			
		this.twHandler.updateMessages(sheep);
	}
	/**
	 * Method for sending a new sheep to the server
	 * @param click
	 */
	@SuppressWarnings({ "unused", "boxing" })
	private void pBSubmit_Add_clicked(boolean click){
		
		Sheep sheepAdd;
		
		if(!mw.lEName_Add_2.text().equals("") && !mw.lEFar_Add.text().equals("") && Integer.parseInt(mw.lEFar_Add.text()) != 0){
			
			sheepAdd = new Sheep(mw.lEName_Add_2.text(), Integer.parseInt(mw.lEFar_Add.text()), 
					mw.dEBirthdate_Add.dateTime().toTime_t(),
					mw.cBAlive_Add.isChecked(), 
					(int)mw.dSBWeight_Add_2.value()); //TODO:M� FIKSES, skal ikke v�re int
			try{
				sLogic.addSheep(sheepAdd);
			}
			catch(Exception e){
				System.err.println(e.getStackTrace());
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
	@SuppressWarnings("unused")
	private void pbTabInformationReset_clicked(boolean click){
		if(currentSheep != null){			
			mw.lEName.setText(currentSheep.getName());
			QDateTime d = new QDateTime();
			d.setTime_t(currentSheep.getDateOfBirth());
			mw.dEBirthdaye.setDateTime(d);
			mw.dSBWeight.setValue((double)currentSheep.getWeight());
			mw.lEFarmId.setText(String.valueOf(currentSheep.getFarmId()));
			if(currentSheep.isAlive())
				mw.chbAlive.setChecked(true);
			else
				mw.chbAlive.setChecked(false);
			
			statusbarMessage.setText("Information reset done.");
		}
			
	
	}
	

	//TABWIDGET	
	/**
	 * Update button for the informaton tab
	 * 
	 * Handles the call to DB and update of locale sheep
	 * 
	 * @param click
	 */
	@SuppressWarnings({"unused", "boxing"})
	private void pbTabInformationUpdate_clicked(boolean click){
		if(currentSheep == null){
			statusbarMessage.setText("Select a sheep to update.");
			return;
		}
		
		Sheep sheepUpdate;
		//Not empty
		if(!mw.lEName.text().equals("") && !mw.lEFarmId.text().equals("") && Integer.parseInt(mw.lEFarmId.text()) != 0){
			sheepUpdate = new Sheep(currentSheep.getId(), mw.lEName.text(), Integer.parseInt(mw.lEFarmId.text()), 
					mw.dEBirthdaye.dateTime().toTime_t(),
					mw.chbAlive.isChecked(), 
					(int)mw.dSBWeight.value()); //M� FIKSES, skal ikke v�re int
			
			try{
				sLogic.editSheep(sheepUpdate);
			}
			catch(Exception e){
				System.err.println("Sheep updating went in the toilet");
				System.err.println(e.getStackTrace());
			}
		}
		else
			statusbarMessage.setText("Some fields are blank, or not valid input..");
	}

	
	
	//OTHER EVENTS
	
	//DockWidget
	/**
	 * Method called when the ascend descend chechbox has been checked/unchecked.
	 * @param toggled The new toggle status.
	 */
	@SuppressWarnings("unused")
	private void rbAscDesc_toggled(boolean toggled){
		slwHandler.changeSortOrder();
		if(toggled)
			mw.rbAscDesc.setText("Ascending");
		else
			mw.rbAscDesc.setText("Descending");
	
	}

	
}

/* EOF */