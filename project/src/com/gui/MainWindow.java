package com.gui;

import java.util.ArrayList;

import com.net.ClientSocket;
import com.net.Response;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QCloseEvent;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QWidget;

import core.classes.Sheep;


/** Class to hold all graphical components (and itself).
 * 
 * @author Gruppe 10 <3
 *
 */
public class MainWindow extends QMainWindow 
{
	private static final double SHEEP_WINDOW_COVERAGE = 0.2;
	
	public  static final int INIT_SCREEN_WIDTH      = 800, 
							 INIT_SCREEN_HEIGHT 	= 600;
	public  static final int INIT_SHEEP_WIDGET_SIZE = (int) (INIT_SCREEN_WIDTH * SHEEP_WINDOW_COVERAGE);
    
   
    private QAction aboutAct;
    private QAction aboutQtJambiAct;
    private QAction exitAct;
    private QAction undoAct;
        
    private UiMainWindow uiMainWindow;
    private UiLoginWindow Ui_LoginWindow;
    
    private Object objectAskingForResponse = null;
    
    private static ClientSocket clientSocket;
    
    /** Main.
     * 
     * @param args parameters for program. Not used.
     */
    public static void main(String[] args) 
    {
        QApplication.initialize(args);
        
        MainWindow testMainWindow = new MainWindow(null);
        testMainWindow.show();
    	
        
        QApplication.exec();
        
        /*
         * http://doc.qt.digia.com/qt/gallery.html
         * http://www.slideshare.net/qtbynokia/how-to-make-your-qt-app-look-native
         */
        
        //testMainWindow.setStyleSheet(styleSheet)
    }
    

    /** Constructor. Initialize..
     * 
     * @param parent potential parent for this window. Should be set to null for now.
     */
    public MainWindow(QWidget parent)
    {
        super(parent);
        
        Ui_LoginWindow = new UiLoginWindow();
        Ui_LoginWindow.setupUi(this);
        setupLoginWindow();
    }
    
    /*
     *LOGINWINDOW 
     */
    
    /**
     * 
     */
    public void setupLoginWindow(){        
    	Ui_LoginWindow.tryLogin.connect(this, "tryLogIn(String, String)");

    }
    
    /*
     * APPLICATION 
     */
    
    /** Handle "about" trigger
	*/
	protected void about() {
	    QMessageBox.information(this, "Info", "baa! baa! baa! baa! baa! baa! baa! baa! baa! ");
	}

    
    @SuppressWarnings("unused")
    /** Handle undo-trigger 
     */
	private void undo()
    {
    	System.out.println("What has been done cannot be undone :o");
    }
    
    /** Set the initial actions
     */
	private void initActions()
	{
		this.aboutAct = new QAction(tr("&About the Sheep Protoype"), this);
		this.aboutAct.setStatusTip(tr("Show the application's About box"));
	
		this.aboutQtJambiAct = new QAction(tr("About &Qt Jambi"), this);
		this.aboutQtJambiAct.setStatusTip(tr("Show the Qt Jambi's About box"));
		
		this.exitAct = new QAction(tr("E&xit"), this);
		this.exitAct.setShortcut(tr("Ctrl+Q"));
		this.exitAct.setStatusTip(tr("Exit the application"));
	    
		this.undoAct = new QAction(tr("&Undo"), this);
		this.undoAct.setShortcut(tr("Ctrl+Z"));
		this.undoAct.setStatusTip(tr("Undo your last action"));
		
	}

	/** Set the initial event-handlers
	 */
	private void init_connectEvents()
	{
		this.aboutAct			.triggered			.connect(this, "about()");
		this.aboutQtJambiAct	.triggered			.connect(QApplication.instance(), "aboutQtJambi()");
		this.exitAct			.triggered			.connect(this, "close()");
		this.undoAct			.triggered			.connect(this, "undo()");
		
		
	}
	
	/** Set the initial event-handlers
	 */
	private void init_connectEventsForWidgets()
	{
		
		//this.slwSheepList		.topLevelChanged	.connect(this, "dockEvent()");	
		
	}
	

	/** Set the initial menu
	 */
	/*
	private void initMenus()
	{
		this.fileMenu = menuBar().addMenu(tr("&File"));
	    this.fileMenu.addAction(exitAct);
	    
	    this.editMenu = menuBar().addMenu(tr("&Edit"));
		this.editMenu.addAction(this.undoAct);
	
		this.helpMenu = menuBar().addMenu(tr("&Help"));
	    this.helpMenu.addAction(aboutAct);
	    this.helpMenu.addAction(aboutQtJambiAct);
	    
	    this.viewMenu = menuBar().addMenu(tr("&View"));
	    this.viewMenu.addAction("hey");
	}*/
	

	/** Set the initial screen settings.
	 */
	private void initScreenSettings()
	{
		super.setGeometry(10, 10,  INIT_SCREEN_WIDTH, INIT_SCREEN_HEIGHT);
	}

	
	/*
	 * EVENTS
	 */
	/**
	 * Close event
	 * 
	 * @param event the close event
	 */
	@Override
	protected void closeEvent(QCloseEvent event) {
		
		if(clientSocket != null)
			clientSocket.disconnect();
		
		super.closeEvent(event);
	}
	
	/**
	 * Event fired when user has made a succesfull loggin.
	 * Changes the view to application mode
	 */
	public void setupUi_MainWindow(){		
		uiMainWindow = new UiMainWindow();
		
		uiMainWindow.setupUi(this);
        
        
        //init_connectEventsForWidgets();
	}
	
	/**
	 * Signaled method for setting up network connection and log in
	 * 
	 * @param usrName
	 * @param usrPW
	 */
	private void tryLogIn(String usrName, String usrPW){
		
		if(this.clientSocket == null )
			this.clientSocket = new ClientSocket("kord.dyndns.org", 1500, usrName, this);
		
		try{
			if(!clientSocket.start())
				System.out.println("Problem with connecting");
			else
				clientSocket.login(usrName, usrPW);
				
		}
		catch (Exception e){
			System.out.println(e);
		}

	}
	
	/*
	 * SIGNALS
	 * 
	 */
	public Signal0 loggedIn;
	public Signal1<ArrayList<Sheep>> sheepsRecieved;
	public Signal1<Sheep> showSheepInformation;
	
	
	/*
	 * DATABSE CONNECTION
	 */
	
	public void handleResponse(Response response){
		System.out.println("Response: "+ response.getType());
		
		/*
		 * Må ha ett system der de ulike viewsa som kaller etter informasjon
		 * fra serveren blir registrert slikt at de kan sendes dit
		 */
		
		int responseType = response.getType();
		
		/* List */
		if(responseType == 1) {
			//A object has called for a list
			if (objectAskingForResponse != null){
				//Sheeplist asking for information
				if( objectAskingForResponse instanceof SheepListWidget){
					//objectAskingForResponse.giveResponse(response);
					
					objectAskingForResponse = null;
				}
			}
			System.out.println("respinse 1");
		}
		/* Boolean */
		else if(responseType == 2)
			System.out.println("response 2");
		/* User */
		else if(response.getType() == 3)
			if(response.getUser() == null)
				/*Koble seg til loggininterface og gi beskjed der */
				System.out.println("Loggin failed, try again");
			else{
				System.out.println("Logged in with user: " + response.getUser().getName());
				setupUi_MainWindow();
			}
				
	}
	
	public void connectionFailed(){
		System.out.println("Connection error");
	}
	
	public void handleMessage(String message){
		System.out.println("Message from server: " + message);
	}
	
	protected void requestSheeps(Object o){
		if(objectAskingForResponse == null)
			objectAskingForResponse = o;
		//SendRequest
	}
}

//splitDockWidget

/* 14.10.2012 - worked about 5 hours to fix everything */

/* EOF */