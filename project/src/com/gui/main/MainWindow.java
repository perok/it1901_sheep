/*
 *  Wanted design
    		  __________________________________
    		 |          |		   2			|
    		 |	  1		|_______________________|
    		 |			|		   3			|
    		 |__________|_______________________|
 * 
 */

package com.gui.main;
   
import com.gui.mapWidget.MapWidget;
import com.gui.sheepList.SheepListWidget;
import com.gui.statisticsWidget.StatisticsWidget;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.DockWidgetArea;
import com.trolltech.qt.core.Qt.WindowFlags;
import com.trolltech.qt.gui.*;

public class MainWindow extends QMainWindow
{
	private static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
	
    private QAction undoAct;
    private QAction exitAct;
    private QAction aboutAct;
    private QAction aboutQtJambiAct;
        
	private QMenu editMenu;
    private QMenu fileMenu;
    private QMenu helpMenu;
    
    private QMdiArea qmaSheep;
    private QMdiSubWindow qmswMapWindow;
    private QMdiSubWindow qmswStatWindow;
    
    private MapWidget mwWidget;
    private SheepListWidget slwSheepList;
    private StatisticsWidget swStatistics;
    
    private static final int SHEEP_WIDGET_SIZE = (int) (SCREEN_WIDTH * 0.2);

    public static void main(String[] args) 
    {
        QApplication.initialize(args);

        MainWindow testMainWindow = new MainWindow(null);
        testMainWindow.show();
        QApplication.exec();
    }

    public MainWindow(QWidget parent)
    {
        super(parent);
        
        /* Main window properties - */
        createActions();
        createMenus();
        initScreenSettings();
        
        /* Widgets - */
        createSheepList();
        createMapWidget();
        createStatisticsWidget();	
        
        /* Mdi-areas - */
        initMdi();
        super.addDockWidget(Qt.DockWidgetArea.LeftDockWidgetArea, this.slwSheepList);

        /* Remove (pesky) titlebars. */
        // Possible to consider autohiding the taskbar, but showing it
        // when the user holds the cursor over the dockwidget

        // QWidget test = new QWidget(this);
        // this.slwSheepList.setTitleBarWidget(test);
    }

    private void initMdi()
    {
    	this.qmaSheep = new QMdiArea();
    	this.qmswMapWindow = new QMdiSubWindow(this.qmaSheep);
    	this.qmswStatWindow = new QMdiSubWindow(this.qmaSheep);
    	
    	this.qmaSheep.setFixedSize(SCREEN_WIDTH - SHEEP_WIDGET_SIZE, SCREEN_HEIGHT);    	
    	
    	this.qmswMapWindow.setWidget(this.mwWidget);
    	this.qmswMapWindow.setSizePolicy(QSizePolicy.Policy.Maximum, QSizePolicy.Policy.Preferred);
    	this.qmswMapWindow.setMinimumWidth(((QMdiArea) this.qmswMapWindow.parent()).width());
    	this.qmswMapWindow.setMinimumHeight(((QMdiArea) this.qmswMapWindow.parent()).height() / 2);    	
    	
    	this.qmswStatWindow.setWidget(this.swStatistics);    	
    	this.qmswStatWindow.setSizePolicy(QSizePolicy.Policy.Maximum, QSizePolicy.Policy.Preferred);    	
    	this.qmswStatWindow.setMinimumWidth(((QMdiArea) this.qmswMapWindow.parent()).width());
    	this.qmswStatWindow.move(0, qmswMapWindow.height());
    	this.qmswStatWindow.setMinimumHeight(((QMdiArea) this.qmswMapWindow.parent()).height() / 2);
    	
    	super.setCentralWidget(this.qmaSheep);
    }
    
    private void createSheepList()
    {
    	this.slwSheepList = new SheepListWidget();
    	this.slwSheepList.setFixedWidth(SHEEP_WIDGET_SIZE);
    }
        
    private void createStatisticsWidget()
    {
    	this.swStatistics = new StatisticsWidget();
    }
    
    private void createMapWidget()
    {
    	this.mwWidget = new MapWidget();
    }

   
    private void initScreenSettings()
    {
    	super.setMinimumWidth(SCREEN_WIDTH);
    	super.setMinimumHeight(SCREEN_HEIGHT);
    }
    
    @SuppressWarnings("unused")
	private void undo()
    {
    	System.out.println("What has been done cannot be undone :o");
    }

    private void createActions()
    {
    	this.exitAct = new QAction(tr("E&xit"), this);
    	this.exitAct.setShortcut(tr("Ctrl+Q"));
    	this.exitAct.setStatusTip(tr("Exit the application"));
    	this.exitAct.triggered.connect(this, "close()");

    	this.aboutAct = new QAction(tr("&About the Sheep Protoype"), this);
    	this.aboutAct.setStatusTip(tr("Show the application's About box"));
    	this.aboutAct.triggered.connect(this, "about()");

    	this.aboutQtJambiAct = new QAction(tr("About &Qt Jambi"), this);
    	this.aboutQtJambiAct.setStatusTip(tr("Show the Qt Jambi's About box"));
    	this.aboutQtJambiAct.triggered.connect(QApplication.instance(), "aboutQtJambi()");
        
    	this.undoAct = new QAction(tr("&Undo"), this);
    	this.undoAct.setShortcut(tr("Ctrl+Z"));
    	this.undoAct.setStatusTip(tr("Undo your last action"));
    	this.undoAct.triggered.connect(this, "undo()");
    }

    private void createMenus()
    {
    	this.fileMenu = menuBar().addMenu(tr("&File"));
        this.fileMenu.addAction(exitAct);
        
        this.editMenu = menuBar().addMenu(tr("&Edit"));
    	this.editMenu.addAction(this.undoAct);

    	this.helpMenu = menuBar().addMenu(tr("&Help"));
        this.helpMenu.addAction(aboutAct);
        this.helpMenu.addAction(aboutQtJambiAct);
    }

    protected void about() 
    {
        QMessageBox.information(this, "Info", "baa! baa! baa! baa! baa! baa! baa! baa! baa! ");
    }
}

// Is the following design OK as long as it is intelligent with resizing and adjusting?

/* EOF */