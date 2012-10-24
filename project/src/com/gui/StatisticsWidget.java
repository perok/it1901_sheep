package com.gui;

import java.util.Arrays;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import com.trolltech.qt.gui.QWidget;

/** Hold statistics
 * 
 * @author Gruppe 10 <3
 *
 */
public class StatisticsWidget extends QWidget 
{
	private QHBoxLayout qhbMainLayout;
	
	private QTableWidget table;
	private QTableWidgetItem item = null;
	
	/** Constructor. Initialize..
	 */
	public StatisticsWidget()
	{
		this.setWindowTitle("Statistics");
		
		initLayout();
		initWidgets();
		
		addSheeps();
	}
	
	
	/** Initialize the main layout of THIS
	 */
	private void initLayout()
	{
		this.qhbMainLayout = new QHBoxLayout();
		
		super.setLayout(qhbMainLayout);
	}
	
	/** Initialize widgets in the current object context
	 */
	private void initWidgets()
	{
		table = new QTableWidget(this);
		
		/* Horizontal headers */
		String[] list = {"Message #","Message Date", "Sheep #", "Farm #", "Name", "Birthdate","Alive", "Weight", "Location"};
		table.setColumnCount(list.length);
		table.setHorizontalHeaderLabels(Arrays.asList(list));
		
		/* We don't need the vertical header */
		table.verticalHeader().setVisible(false);
		
		/* Rezise rows and columns to needed size */
		table.resizeRowsToContents();
		table.resizeColumnsToContents();
		
		/* Stretch the vertical headers last element */
		//table.verticalHeader().setStretchLastSection(true);
		table.horizontalHeader().setStretchLastSection(true);
		
		/* Define the size */
		table.setMinimumHeight(20);
		
		/* Add it*/
		qhbMainLayout.addWidget(table);
	}
	
	/**
	 * Adds sheep information to the current window
	 * 
	 * TODO: Hook up to SHeeListWidget
	 * TODO: How should this look?
	 * 
	 * @param ArrayList<Sheep> sheep
	 */
	public void addSheeps(){
		
		//this.setWindowTitle("Statistics: " + name);
		
		table.setRowCount(6);
		
		for(int i = 0; i < 6; i++) {
		 	for(int j = 0; j < table.columnCount(); j++) {
		      item = new QTableWidgetItem("Test value");
		      table.setItem(i, j, item);
		   }
		}
		
		table.resizeRowsToContents();
		table.resizeColumnsToContents();
		
	}
}

/* EOF */