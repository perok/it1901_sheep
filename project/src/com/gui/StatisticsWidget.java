package com.gui;

import java.util.Arrays;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QPushButton;
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
	}
	
	/** Initialize widgets in the current object context
	 */
	private void initWidgets()
	{
		//this.qwStatWidget = new QWidget();
		String[] list = {"#", "Name", "Farm #", "Birthdate","Alive", "Weight", "Location"};
		
		
		table = new QTableWidget(0, list.length, this);

		table.setMinimumHeight(20);
		
		table.setHorizontalHeaderLabels(Arrays.asList(list));
		
		table.verticalHeader().setVisible(false);
		//table.
		
		
		//table.
		qhbMainLayout.addWidget(table);
		//table.
		
		
		table.resizeRowsToContents();
		table.resizeColumnsToContents();
		
		//table.verticalHeader().setStretchLastSection(true);
		table.horizontalHeader().setStretchLastSection(true);
		
		
		super.setLayout(qhbMainLayout);
	}
	
	public void addSheeps(){
		
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