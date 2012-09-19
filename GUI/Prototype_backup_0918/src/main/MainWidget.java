package main;

import mapWidget.MapWidget;
import sheepList.SheepListWidget;
import statisticsWidget.StatisticsWidget;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QMdiArea;
import com.trolltech.qt.gui.QWidget;

public class MainWidget extends QWidget 
{
	private QHBoxLayout qhbMainLayout;
	
	private QMdiArea qmaSheep;
	
    private SheepListWidget slwSheepList;
    private SideWidget swDesign;
	
	public MainWidget()
	{
		initMdi();
		initLayout();
	}
	
    private void initMdi()
    {
    	this.qmaSheep = new QMdiArea();
    	this.swDesign = new SideWidget();
    	this.qmaSheep.addSubWindow(this.slwSheepList);
    	this.qmaSheep.tileSubWindows();
   }
	
	private void initLayout()
	{
		this.qhbMainLayout = new QHBoxLayout();
	}

}

/* EOF */