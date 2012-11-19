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

/** Class to hold all graphical components (and itself).
 * 
 * @author Gruppe 10
 *
 */
public class MainWindow extends QMainWindow 
{
	private UiLoginWindow uiLoginWindow;   
    private UiMainWindow uiMainWindow;
    /* DB */
    
    /* Handlers */
    private ServerLogic serverLogic;
    private SheepListWidgetLogic slwHandler;
    private TableWidgetLogic twhandler;
    private UiMainWindowLogic mwLogic;
    
    /*
    static {
        try {
        	System.out.println("loading");
        	System.loadLibrary("libeay32.dll");
        	System.loadLibrary("libs/libssl32.dll");
        	System.loadLibrary("libs/ssleay32.dll");

        	
        } catch (UnsatisfiedLinkError e) {
          System.err.println("Native code library failed to load.\n" + e);
          System.exit(1);
        }
      }*/
    
    
    /** Main.
     * 
     * @param args parameters for program. Not used.
     */
    public static void main(String[] args) 
    {
    	
        com.trolltech.qt.Utilities.loadQtLibrary("QtXml"); 
        com.trolltech.qt.Utilities.loadQtLibrary("QtSql"); 
        com.trolltech.qt.Utilities.loadQtLibrary("QtSvg");
    	com.trolltech.qt.Utilities.loadQtLibrary("QtNetwork");
        com.trolltech.qt.Utilities.loadQtLibrary("QtXmlPatterns");

        //com.trolltech.qt.Utilities.loadQtLibrary("QtWebKit");

    	/* Initialize qt-framework, set default style */
        QApplication.initialize(args);        
        QApplication.setStyle("Plastique");
        
        MainWindow mainWindow = new MainWindow(null);
        mainWindow.show();
    	
        QApplication.exec();
        
        /*
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
        
        this.setWindowIcon(new QIcon(new QPixmap("res/sheep.png")));
        serverLogic = new ServerLogic();
        uiLoginWindow = new UiLoginWindow();
        
        uiLoginWindow.setupUi(this, Constants.INIT_SCREEN_WIDTH, Constants.INIT_SCREEN_WIDTH);
        
        this.setWindowTitle(Constants.title);
        
        new UiLoginWindowLogic(uiLoginWindow, serverLogic);
        serverLogic.loggedIn.connect(this, "setupUi_MainWindow()");
    }
    
	/**
	 * Event fired when user has made a successful login.
	 * Changes the view to application mode
	 */
	public void setupUi_MainWindow(){
		System.out.println("Setting up Main Window UI");
		uiLoginWindow = null;
		
		uiMainWindow = new UiMainWindow();
		uiMainWindow.setupUi(this, this.size().width(), this.size().height());//INIT_SCREEN_WIDTH, INIT_SCREEN_HEIGHT);
		
		twhandler = new TableWidgetLogic(uiMainWindow.tableWidget);
		slwHandler = new SheepListWidgetLogic(uiMainWindow.listWidget);		
        mwLogic = new UiMainWindowLogic(uiMainWindow, slwHandler, twhandler, serverLogic);
                
        mwLogic.signalShowAbout.connect(this, "about()");
        mwLogic.signalShowAboutQt.connect(this, "aboutQt()");
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
	 * Close event
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
	protected void keyPressEvent(QKeyEvent event){
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

/* EOF */