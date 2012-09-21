<<<<<<< HEAD
=======
/*
 *  Wanted design
    		  __________________________________
    		 |          |		   2			|
    		 |	  1		|_______________________|
    		 |			|		   3			|
    		 |__________|_______________________|
 * 
 */

>>>>>>> 1efcda16656a885ca904e4df3f165cc118d18b04
package main;

import mapWidget.MapWidget;
import sheepList.SheepListWidget;
import statisticsWidget.StatisticsWidget;

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
<<<<<<< HEAD
=======
    private QMdiSubWindow qmswMapWindow;
    private QMdiSubWindow qmswStatWindow;
>>>>>>> 1efcda16656a885ca904e4df3f165cc118d18b04
    
    private MapWidget mwWidget;
    private SheepListWidget slwSheepList;
    private StatisticsWidget swStatistics;
<<<<<<< HEAD
=======
    
    private static final int SHEEP_WIDGET_SIZE = (int) (SCREEN_WIDTH * 0.2);
>>>>>>> 1efcda16656a885ca904e4df3f165cc118d18b04

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

<<<<<<< HEAD
        /* Clever fix to remove (pesky) titlebars. */
=======
        /* Remove (pesky) titlebars. */
>>>>>>> 1efcda16656a885ca904e4df3f165cc118d18b04
        // Possible to consider autohiding the taskbar, but showing it
        // when the user holds the cursor over the dockwidget

        // QWidget test = new QWidget(this);
        // this.slwSheepList.setTitleBarWidget(test);
<<<<<<< HEAD
        
        //this.setCentralWidget(new MainWidget());
    }
    
    // FIXME: Hardcoded version - merely used to show the group a protoype
    // TODO: QDockWidget is the preferred option, however,
    //		  it does not like resizing within the window frame.
    //		  Is there a way to implement the following design
    //		  AND (furthermore) have intelligent resizing of windows?
    //
    //		  __________________________________
    //		 |          |		   2			|
    //		 |	  1		|_______________________|
    //		 |			|		   3			|
    //		 |__________|_______________________|
    private void initMdi()
    {
    	this.qmaSheep = new QMdiArea();
    	QMdiSubWindow mapWindow = new QMdiSubWindow(this.qmaSheep);
    	QMdiSubWindow statWindow = new QMdiSubWindow(this.qmaSheep);
    	
    	mapWindow.setWidget(this.mwWidget);
    	statWindow.setWidget(this.swStatistics);
    	
    	mapWindow.setSizePolicy(QSizePolicy.Policy.Maximum, QSizePolicy.Policy.Preferred);
    	statWindow.setSizePolicy(QSizePolicy.Policy.Maximum, QSizePolicy.Policy.Preferred);
    	
    	mapWindow.setMinimumWidth(600);
    	statWindow.setMinimumWidth(600);
    	
    	mapWindow.setMinimumHeight(350);    	
    	statWindow.move(0, mapWindow.height());
    	statWindow.setMinimumHeight(350);
=======
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
>>>>>>> 1efcda16656a885ca904e4df3f165cc118d18b04
    	
    	super.setCentralWidget(this.qmaSheep);
    }
    
<<<<<<< HEAD
    // QMdiSubwindow...

    private void createSheepList()
    {
    	this.slwSheepList = new SheepListWidget();
    }
    
=======
    private void createSheepList()
    {
    	this.slwSheepList = new SheepListWidget();
    	this.slwSheepList.setFixedWidth(SHEEP_WIDGET_SIZE);
    }
        
>>>>>>> 1efcda16656a885ca904e4df3f165cc118d18b04
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

<<<<<<< HEAD
=======
// Is the following design OK as long as it is intelligent with resizing and adjusting?

>>>>>>> 1efcda16656a885ca904e4df3f165cc118d18b04
/* EOF */