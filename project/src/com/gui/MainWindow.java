package com.gui;

import java.util.ArrayList;

import com.gui.logic.UiLoginWindowLogic;
import com.gui.logic.UiMainWindowLogic;
import com.gui.logic.ServerLogic;
import com.gui.logic.sheepListWidgetHandler;
import com.gui.logic.tableWidgetHandler;
import com.net.ClientSocket;
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
	
	public  static final int INIT_SCREEN_WIDTH      = 900, 
							 INIT_SCREEN_HEIGHT 	= 800;
	public  static final int INIT_SHEEP_WIDGET_SIZE = (int) (INIT_SCREEN_WIDTH * SHEEP_WINDOW_COVERAGE);
    
   
    private QAction aboutAct;
    private QAction aboutQtJambiAct;
    private QAction exitAct;
    private QAction undoAct;
        
    private UiMainWindow uiMainWindow;
    private UiLoginWindow uiLoginWindow;
    
    
    /* DB */
    
    /* Handlers */
    private tableWidgetHandler twhandler;
    private sheepListWidgetHandler slwHandler;
    private UiMainWindowLogic mwLogic;
    private UiLoginWindowLogic lwLogic;
    private ServerLogic serverLogic;
    
    /** Main.
     * 
     * @param args parameters for program. Not used.
     */
    public static void main(String[] args) 
    {
        QApplication.initialize(args);
        
        QApplication.setStyle("Plastique");//QStyleFactory.keys().get(0));
        MainWindow testMainWindow = new MainWindow(null);
        //testMainWindow.setStyle(new QStyle().)
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
        
        serverLogic = new ServerLogic();
        uiLoginWindow = new UiLoginWindow();
        
        uiLoginWindow.setupUi(this, INIT_SCREEN_WIDTH, INIT_SCREEN_HEIGHT);
    	
        lwLogic = new UiLoginWindowLogic(uiLoginWindow, serverLogic);
        
        serverLogic.loggedIn.connect(this, "setupUi_MainWindow()");

    }
    
    
	/**
	 * Event fired when user has made a succesfull loggin.
	 * Changes the view to application mode
	 */
	public void setupUi_MainWindow(){		
		uiMainWindow = new UiMainWindow();
		uiMainWindow.setupUi(this, INIT_SCREEN_WIDTH, INIT_SCREEN_HEIGHT);
        
        init_connectEventsForWidgets();
  	}
	
	/** Set the initial event-handlers
	 */
	private void init_connectEventsForWidgets()
	{
		twhandler = new tableWidgetHandler(uiMainWindow.tableWidget);
		slwHandler = new sheepListWidgetHandler(uiMainWindow.listWidget);
		//this.slwSheepList		.topLevelChanged	.connect(this, "dockEvent()");
		
        mwLogic = new UiMainWindowLogic(uiMainWindow, slwHandler, twhandler, serverLogic);
	}
    
    /*
     * APPLICATION 
     */
    
    /** Handle "about" trigger
	*/
	protected void about() {
	    QMessageBox.information(this, "About", "Sheep surveilance application." 
	    		+ "Created by Anders Sildnes, Lars erik Grasdal, Tor Økland Barstad"
	    		+ ", Svenn K and Per Øyvind Kanestrøm");
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
		serverLogic.closeConnection();
		
		super.closeEvent(event);
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
	
}


/* 14.10.2012 - worked about 5 hours to fix everything */

/* EOF */