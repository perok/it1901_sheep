package com.gui.logic;

import java.util.ArrayList;
import java.util.Arrays;

import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;

import core.classes.Message;
import core.classes.Sheep;

public class tableWidgetHandler {
	
	private QTableWidget widget;
	
	public tableWidgetHandler(QTableWidget widget){
		this.widget = widget;
		
        /* Horizontal headers */
		String[] list = {"Message #","Message Date", "Sheep #", "Farm #", "Name", "Birthdate","Alive", "Weight", "Location"};
		widget.setColumnCount(list.length);
		widget.setHorizontalHeaderLabels(Arrays.asList(list));
		
		/* We don't need the vertical header */
		widget.verticalHeader().setVisible(false);
		
		/* Rezise rows and columns to needed size */
		widget.resizeRowsToContents();
		widget.resizeColumnsToContents();
		
		/* Stretch the vertical headers last element */
		//table.verticalHeader().setStretchLastSection(true);
		widget.horizontalHeader().setStretchLastSection(true);
        
        addSheeps();
	
	}
	
	/**
	 * Test function for adding sheep
	 */
	public void addSheeps(){
		
		//this.setWindowTitle("Statistics: " + name);
		QTableWidgetItem item;
		widget.setRowCount(6);
		
		for(int i = 0; i < 6; i++) {
		 	for(int j = 0; j < widget.columnCount(); j++) {
		      item = new QTableWidgetItem("Test value");
		      widget.setItem(i, j, item);
		   }
		}
		
		widget.resizeRowsToContents();
		widget.resizeColumnsToContents();
		
	}
	
	/**
	 * Adds the messages to the informationview
	 * @param messages
	 */
	protected void addSheep(Sheep sheep, ArrayList<Message> messages){
		//sheep.get
	}
}
