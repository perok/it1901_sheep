package com.gui;

import com.gui.logic.UiLoginWindowLogic;
import com.gui.logic.UiMainWindowLogic;
import com.gui.logic.ServerLogic;
import com.gui.logic.sheepListWidgetHandler;
import com.gui.logic.tableWidgetHandler;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QCloseEvent;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QStyleFactory;
import com.trolltech.qt.gui.QWidget;

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
        
        System.out.println("Available themes: " + QStyleFactory.keys());
        
        QApplication.setStyle("Plastique");
        MainWindow testMainWindow = new MainWindow(null);
        testMainWindow.show();
    	
        
        QApplication.exec();
        
        /*
         * http://doc.qt.digia.com/qt/gallery.html
         * http://www.slideshare.net/qtbynokia/how-to-make-your-qt-app-look-native
         */
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
		System.out.println("Setting up Main Window UI");
		uiLoginWindow = null;
		lwLogic = null;
		
		uiMainWindow = new UiMainWindow();
		uiMainWindow.setupUi(this, INIT_SCREEN_WIDTH, INIT_SCREEN_HEIGHT);
		
		twhandler = new tableWidgetHandler(uiMainWindow.tableWidget);
		slwHandler = new sheepListWidgetHandler(uiMainWindow.listWidget);		
        mwLogic = new UiMainWindowLogic(uiMainWindow, slwHandler, twhandler, serverLogic);
        
        mwLogic.setupUserInformation();
        
        mwLogic.signalShowAbout.connect(this, "about()");
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
	
	@Override
	public void keyPressEvent(QKeyEvent event){
		if(uiLoginWindow != null){
			if (event.key() == Qt.Key.Key_Return.value()){
				uiLoginWindow.loginCheck();
	    	}
			else
				super.keyPressEvent(event);
		}
    	else
    		super.keyPressEvent(event);
   	}
}
/* 14.10.2012 - worked about 5 hours to fix everything */

/* EOF */