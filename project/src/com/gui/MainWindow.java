package com.gui;

import java.util.ArrayList;

import com.net.ClientSocket;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QCloseEvent;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QResizeEvent;
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
        
	private QMenu editMenu;
    private QMenu fileMenu;
    private QMenu helpMenu;
    private QMenu viewMenu;
    
    private MDIArea maSheep;
    private SubWindow qmswMapWindow;
    private SubWindow swStatWindow;
    
    private QTimer qtWindowTimer;
        
    private MapWidget mwWidget;
    private SheepListWidget slwSheepList;
    private StatisticsWidget swStatistics;
    
    private SubWindow qmsLoginWindow;
    private LoginWindowWidget lWindowWidget;
    
    
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
        
        setupLoginWindow();
    }
    
    /*
     *LOGINWINDOW 
     */
    
    /**
     * 
     */
    public void setupLoginWindow(){
		/* Main window properties - */
        initActions();
        initMenus();
        initScreenSettings();
        initTimerResize();
        
        /* Widgets - */
        initLoginWindowWidget();
        initLoginWindowSubWindows();
        
        /* Mdi-areas - */
        initMdiLoginWindow();
        
        /* Triggers and actions */
        init_connectEvents();
        
        if (lWindowWidget == null)
        	System.out.println("wtf");
        		
    	lWindowWidget.tryLogin.connect(this, "tryLogIn(String, String)");
    }
    
    /*
     * APPLICATION 
     */
    
    /** Handle "about" trigger
	*/
	protected void about() 
	{
	    QMessageBox.information(this, "Info", "baa! baa! baa! baa! baa! baa! baa! baa! baa! ");
	}

	@SuppressWarnings("unused")
    /** Handle QDockwidget docking and un-docking.
     */
    private void dockEvent()
    {
    	/* If QDockwidget is un-docked */
    	if(this.slwSheepList.isFloating() == true)
    	{
    		/* Set QMdiArea to full-screen */
    		this.maSheep.resizeWidget(new QSize(super.width(), this.maSheep.height()));
    	}
    	else /* QDockwidget docked back into the main window. */
    	{
    		/* If a subwindow is maximized, we Qt does everything for us */
    		if(this.maSheep.hasMaximized()) { return; }
    		/* Decrement the MDIArea */
    		this.maSheep.resizeWidget(new QSize(getMdiWidth(), this.maSheep.height()));
    	}
    }
      
    /** Return the desired width of the central widget of THIS
     * 
     *  @return the desired width for the central widget of THIS.
     */
    //TODO: is this function necessary?
    public int getMdiWidth() { return (super.width() - INIT_SHEEP_WIDGET_SIZE); }
    
    @Override
    /** Handle resize-event of this
     * 
     * @param qreSize the former size of this
     */
    //TODO: Qt-docs says that no repainting etc should or need to be done inside here -
    //		where else? Is there another way to handle user re-sizing?
	protected void resizeEvent(QResizeEvent qreSize)
    {
		this.maSheep.resizeWidget(qreSize, getMdiWidth());
	}
    
    
    @SuppressWarnings("unused")
    /** Resize the window appropriately after the window is initialized.
     */
    //FIXME: This function is not desired, it would be better if resizeEvent
    //		 wasn't called until this had initialized itself
    private void timedResize()
    {
    	this.qtWindowTimer.stop();
    	this.qtWindowTimer.disconnect();
    	this.maSheep.cascadeWindows();
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
		this.qtWindowTimer		.timeout		 	.connect(this, "timedResize()");
		this.undoAct			.triggered			.connect(this, "undo()");
		
		
	}
	
	/** Set the initial event-handlers
	 */
	private void init_connectEventsForWidgets()
	{
		
		this.slwSheepList		.topLevelChanged	.connect(this, "dockEvent()");	
		
	}
	

	/** Set the initial menu
	 */
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
	}

	/** Set the initial MDIArea (centralwidget)
	 */
	private void initMdi()
	{
		this.maSheep = new MDIArea();
		
		this.maSheep.addSubWindow(this.qmswMapWindow);
		this.maSheep.addSubWindow(this.swStatWindow);
		
		/* Make sure it looks OK to begin with */
		this.maSheep.cascadeWindows();
		
		super.setCentralWidget(this.maSheep);
	}
	
	/**
	 * Set the initial MDIArea (centralwidget) for LoginWindow
	 */
	private void initMdiLoginWindow(){
		this.maSheep = new MDIArea();
		
		this.maSheep.addSubWindow(this.qmsLoginWindow);
		
		/* Make sure it looks OK to begin with */
		this.maSheep.cascadeWindows();
		
		super.setCentralWidget(this.maSheep);
	}
	

	/** Set the initial screen settings.
	 */
	private void initScreenSettings()
	{
		super.setGeometry(10, 10,  INIT_SCREEN_WIDTH, INIT_SCREEN_HEIGHT);
	}

	
	/** Initialize the LoginWindow subwindow to be placed in the central MDIArea
	 */
	private void initLoginWindowSubWindows()
	{
		this.qmsLoginWindow = new SubWindow(this.lWindowWidget);
	}
	
	/** Initialize the initial subwindows to be placed in the central MDIArea
	 */
	private void initSubWindows()
	{
		this.qmswMapWindow  = new SubWindow(this.mwWidget);
		this.swStatWindow   = new SubWindow(this.swStatistics);
	}

	/** Initialize the first timed resize
	 */
	private void initTimerResize()
	{
		this.qtWindowTimer = new QTimer();
	    this.qtWindowTimer.setInterval(400);
	    this.qtWindowTimer.start();
	}
	
	/**
	 * Initializes the login window widget
	 */
	private void initLoginWindowWidget(){
		this.lWindowWidget	= new LoginWindowWidget();
	}
	
	/** Initialize the first widgets
	 */
	private void initWidgets()
	{
		this.mwWidget     = new MapWidget();
		this.slwSheepList = new SheepListWidget();
		this.swStatistics = new StatisticsWidget();
		
		this.slwSheepList.setFixedWidth(INIT_SHEEP_WIDGET_SIZE);
		super.addDockWidget(Qt.DockWidgetArea.LeftDockWidgetArea, this.slwSheepList);
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
	public void loggedIn(){
		/* Widgets - */
        initWidgets();
        initSubWindows();
        
        qmsLoginWindow = null;
        lWindowWidget = null;
        
        /* Mdi-areas - */
        initMdi();
        
        init_connectEventsForWidgets();
	}
	
	/**
	 * Signaled method for setting up network connection and log in
	 * 
	 * @param usrName
	 * @param usrPW
	 */
	private void tryLogIn(String usrName, String usrPW){
		loggedIn();
		/*
		this.clientSocket = new ClientSocket("kord.dyndns.org", 1500, usrName, this);

		try{
			if(!clientSocket.start())
				System.out.println("Problem with connecting");
			else
				clientSocket.login(usrName, usrPW);
				
		}
		catch (Exception e){
			System.out.println(e);
		}*/

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
	
	public void handleResponse(String response){
		System.out.println("Response: "+ response);
		
		//Run loggIn() here
		
	}
	
	public void connectionFailed(){
		System.out.println("Something got seriously fucked");
	}
}

//splitDockWidget

/* 14.10.2012 - worked about 5 hours to fix everything */

/* EOF */