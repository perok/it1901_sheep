package main;

import mapWidget.MapWidget;
import sheepList.SheepListWidget;
import statisticsWidget.StatisticsWidget;

import com.trolltech.qt.core.QMimeData;
import com.trolltech.qt.gui.*;

public class SideWidget extends QWidget
{
	private QMdiArea qmaMapWidget;
	private QMdiArea qmaStatistics;
	
	private QHBoxLayout qhbMainLayout;
	private QVBoxLayout qvbSideLayout; 
	
    private MapWidget mwWidget;
    private StatisticsWidget swStatistics;
    
    public static void main(String[] args)
    {
    	QApplication.initialize(args);
    	SideWidget poop = new SideWidget();
    	
    	poop.show();
    	QApplication.exec(); 
    }
    
    public SideWidget()
    {
        /* Widgets - */
        createMapWidget();
        createStatisticsWidget();

        /* Init layout */
        initLayout();
    }
    // what if I added mdi-areas into the layout?
    private void initLayout()
    {
    	this.qmaStatistics = new QMdiArea();
    	this.qmaMapWidget = new QMdiArea();
    	this.qvbSideLayout = new QVBoxLayout();
    	this.swStatistics = new StatisticsWidget();
    	this.mwWidget = new MapWidget();
    	
//    	this.qmaMapWidget.addSubWindow(this.mwWidget);
//    	this.qmaStatistics.addSubWindow(swStatistics);
//    	this.qvbSideLayout.addWidget(this.qmaStatistics);
//    	this.qvbSideLayout.addWidget(this.qmaMapWidget);
    	
    	this.qmaMapWidget.addSubWindow(this.mwWidget);
    	this.qmaMapWidget.addSubWindow(this.swStatistics);
    	this.qmaMapWidget.tileSubWindows();
    	this.qvbSideLayout.addWidget(this.qmaMapWidget);
    	
    	this.setLayout(this.qvbSideLayout);
    }
    
    private void createStatisticsWidget()
    {
    	this.swStatistics = new StatisticsWidget();
    }
    
    private void createMapWidget()
    {
    	this.mwWidget = new MapWidget();
    }

}
