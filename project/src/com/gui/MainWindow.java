package com.gui;

import com.gui.logic.UiLoginWindowLogic;
import com.gui.logic.UiMainWindowLogic;
import com.gui.logic.ServerLogic;
import com.gui.logic.SheepListWidgetLogic;
import com.gui.logic.TableWidgetLogic;
import com.storage.Constants;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QCloseEvent;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

/** Class to hold all graphical components.
 * 
 * @author Gruppe 10
 *
 */
public class MainWindow extends QMainWindow 
{
	private final String WINDOW_ICON = "res/sheep.png";
	
	/** User-interface for the login-window */
	private UiLoginWindow uiLoginWindow;   
	/** User-interface for the main window */
    private UiMainWindow uiMainWindow;
    /* DB */
    
    /* Handlers */
    private ServerLogic serverLogic;
    private SheepListWidgetLogic slwHandler;
    private TableWidgetLogic twhandler;
    private UiMainWindowLogic mwLogic;
    
    /** Main.
     * 
     * @param args parameters for program. Not used internally.
     */
    public static void main(String[] args) 
    {
//    	
//        com.trolltech.qt.Utilities.loadQtLibrary("QtXml"); 
//        com.trolltech.qt.Utilities.loadQtLibrary("QtSql"); 
//        com.trolltech.qt.Utilities.loadQtLibrary("QtSvg");
//    	com.trolltech.qt.Utilities.loadQtLibrary("QtNetwork");
//        com.trolltech.qt.Utilities.loadQtLibrary("QtXmlPatterns");
//
        //com.trolltech.qt.Utilities.loadQtLibrary("QtWebKit");

    	/* Initialize qt-framework, set default style */
        QApplication.initialize(args);        
        QApplication.setStyle("Plastique"); /* Default preferred style */
        
        MainWindow mainWindow = new MainWindow(null);
        mainWindow.show();
    	
        QApplication.exec();
     }
        
    /** Constructor. Initialize..
     * 
     * @param parent potential parent for this window.
     */
    public MainWindow(QWidget parent)
    {
        super(parent);
        
        this.serverLogic = new ServerLogic();
        this.uiLoginWindow = new UiLoginWindow();
        
        this.setWindowIcon(new QIcon(new QPixmap(WINDOW_ICON)));
        this.setWindowTitle(Constants.title);
        
        /* Spawn the graphical login-window */
        new UiLoginWindowLogic(this.uiLoginWindow, this.serverLogic);
        /* Application starts with a login-window. The GUI gets initialized with this line */
        this.uiLoginWindow.setupUi(this, Constants.INIT_SCREEN_WIDTH, Constants.INIT_SCREEN_WIDTH);
    }
    
	/**
	 * Event fired when user has made a successful login.
	 * Changes the view to application mode
	 */
	public void setupUi_MainWindow()
	{
		this.uiLoginWindow = null; /* Make sure the login-widget doesn't occupy window space */
		this.uiMainWindow = new UiMainWindow();
		this.twhandler = new TableWidgetLogic(uiMainWindow.tableWidget);
		this.slwHandler = new SheepListWidgetLogic(uiMainWindow.listWidget);		
		this.mwLogic = new UiMainWindowLogic(uiMainWindow, slwHandler, twhandler, serverLogic);
		
		this.uiMainWindow.setupUi(this, super.size().width(), super.size().height());
        
		/* Setup user-triggered events */
		this.mwLogic.signalShowAbout.connect(this, "about()");
		this.mwLogic.signalShowAboutQt.connect(this, "aboutQt()");
        this.serverLogic.loggedIn.connect(this, "setupUi_MainWindow()");
  	}
    
    /*
     * APPLICATION 
     */
    
    /** 
     * Shows the about window
     */
	protected void about() {
	    QMessageBox.information(this, "About", Constants.about);
	}
	
	/**
	 * Shows the about Qt window
	 */
	protected void aboutQt() {
	    QMessageBox.aboutQt(this);
	}
	
	/*
	 * EVENTS
	 */
	
	/**
	 * Gracefully close the application.
	 * 
	 * @param event the close event
	 */
	@Override
	protected void closeEvent(QCloseEvent event) {
		serverLogic.closeConnection();
		
		super.closeEvent(event);
	}
	
	/**
	 * Global keypress event.
	 */
	@Override
	protected void keyPressEvent(QKeyEvent event)
	{
		/* If the login window is the active widget */
		if(uiLoginWindow != null){
			/* If the user presses enter */
			if (event.key() == Qt.Key.Key_Return.value()){
				/* Try to login */
				uiLoginWindow.loginCheck();
	    	}
			else /* Another keypress-event in login-window. Let Qt deal with that. */
				super.keyPressEvent(event);
		}
    	else /* If the login-window is NOT the active widget */
    		/* Let Qt deal with keypress-event */
    		super.keyPressEvent(event);
   	}
}

/* EOF */