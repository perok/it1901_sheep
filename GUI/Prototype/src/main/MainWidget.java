package main;

import mapWidget.MapWidget;
import sheepList.SheepListWidget;
import statisticsWidget.StatisticsWidget;

import com.trolltech.qt.gui.*;

public class MainWidget extends QWidget
{
	private QHBoxLayout qhbMainLayout;
	private QVBoxLayout qvbSideLayout; 
	
    private MapWidget mwWidget;
    private StatisticsWidget swStatistics;
    
    public static void main(String[] args)
    {
    	QApplication.initialize(args);
    	MainWidget poop = new MainWidget();
    	
    	poop.show();
    	QApplication.exec(); 
    }
    
    public MainWidget()
    {
        /* Widgets - */
        createMapWidget();
        createStatisticsWidget();

        /* Init layout */
        initLayout();
    }
    
    private void initLayout2()
    {
    	
    }
    
    private void initLayout()
    {
    	this.qhbMainLayout = new QHBoxLayout();
    	
    	this.qhbMainLayout.addWidget(this.swStatistics);
    	this.qhbMainLayout.addWidget(this.mwWidget);	
    	
    	this.qhbMainLayout.setSizeConstraint(QLayout.SizeConstraint.SetNoConstraint);
    	
    	super.setLayout(this.qhbMainLayout);
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
