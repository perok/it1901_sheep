package com.gui.main;

import com.gui.mapWidget.MapWidget;
import com.gui.sheepList.SheepListWidget;
import com.gui.statisticsWidget.StatisticsWidget;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QMdiArea;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class MainWidget extends QWidget 
{
	private QHBoxLayout qhbMainLayout;
	private QVBoxLayout qvbBoxLayout;
	
	private QMdiArea qmaSheep;
	
    private SheepListWidget slwSheepList;
    private StatisticsWidget swWidget;
    private MapWidget mwWidget;
	
	public MainWidget()
	{
		//initMdi();
		initWidget();
		initLayout();
	
		super.setLayout(qhbMainLayout);
	}
	
	private void initWidget()
	{
		this.slwSheepList = new SheepListWidget();
		this.swWidget = new StatisticsWidget();
		this.mwWidget = new MapWidget();
	}
	
    private void initMdi()
    {
    	this.qmaSheep = new QMdiArea();
    	this.qmaSheep.addSubWindow(this.slwSheepList);
    	this.qmaSheep.tileSubWindows();
   }
	
	private void initLayout()
	{
		this.qhbMainLayout = new QHBoxLayout();
		this.qvbBoxLayout = new QVBoxLayout();
		
		//this.slwSheepList.setMaximumWidth(200);
		qhbMainLayout.addWidget(this.slwSheepList);
		
		qvbBoxLayout.addWidget(swWidget);
		qvbBoxLayout.addWidget(mwWidget);
		
		qhbMainLayout.addLayout(qvbBoxLayout);		
	}

}

/* EOF */