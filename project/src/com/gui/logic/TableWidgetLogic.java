package com.gui.logic;

import java.util.ArrayList;
import java.util.Arrays;

import com.storage.Constants;
import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Global.QtMsgType;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;

import core.classes.Message;
import core.classes.Sheep;
import core.classes.SheepStatus;
import core.classes.SheepAlert;


import com.storage.UserStorage;

public class TableWidgetLogic {
	
	private QTableWidget widget;
	
	/**
	 * Initializes the logic for the given QTableWidget
	 * 
	 * @param widget The QTableWidget that shall be handled by this class
	 */
	public TableWidgetLogic(QTableWidget widget){
		this.widget = widget;

        /* Horizontal headers */
		String[] list = {"Message #","Timestamp", "Temperature", "Latitude - Longditude"};
		widget.setColumnCount(list.length);
		widget.setHorizontalHeaderLabels(Arrays.asList(list));
		
		widget.horizontalHeader().setClickable(true);
		widget.setSortingEnabled(true);
		
		/* We don't need the vertical header */
		widget.verticalHeader().setVisible(false);
		
		/* Rezise rows and columns to needed size */
		widget.resizeRowsToContents();
		widget.resizeColumnsToContents();

		/* Stretch the vertical headers last element */
		//table.verticalHeader().setStretchLastSection(true);
		widget.horizontalHeader().setStretchLastSection(true); 
		
		/* Signals */
		widget.horizontalHeader().clicked.connect(this, "horizontalHeader_clicked(QModelIndex)");
	}

	/**
	 * Updated the table with the information from the given sheep.
	 * @param selectedSheep Sheep that shall show it's data
	 */
	public void updateMessages(Sheep selectedSheep){
		ArrayList<Message> messages = new ArrayList<Message>();
		
		//Not null
		if(selectedSheep.getRecentStatuses() != null){
			//Through every message
			for(Message msg : selectedSheep.getRecentStatuses()){
				//What is the current message type
				if(UserStorage.getCurrentMessageType() == Constants.sheepStatus){
					if(msg instanceof SheepStatus){
						messages.add(msg);
					}
				}
				else
					if(msg instanceof SheepAlert){
						messages.add(msg);
					}
					
			}
			
			addSheep(selectedSheep, messages);
		}
	}
	
	
	/**
	 * Adds the messages to the informationview
	 * @param messages
	 */
	protected void addSheep(Sheep sheep, ArrayList<Message> messages){ // Alerts
		widget.setRowCount(messages.size());
		
		if(messages != null){
			int y = 0;
			for(Message message : messages){
				if(message instanceof SheepStatus)
					System.out.println("SHEEPSTAUS");
				else
					System.out.println("ALARM");
				
				int i = 0;
				QTableWidgetItem item = new QTableWidgetItem();
				
				item.disableGarbageCollection();
				item.setData(Constants.QtSheepDataRole, sheep);
				item.setData(Qt.ItemDataRole.DisplayRole, message.getId());
				//item.setFlags(Qt.ItemFlag.ItemIsSelectable);
				widget.setItem(y, i, item);
				i++;
				
				item = new QTableWidgetItem();
				item.disableGarbageCollection();
				item.setData(Qt.ItemDataRole.DisplayRole, message.getTimestamp());
				item.setData(Constants.QtSheepDataRole, sheep);
				widget.setItem(y, i, item);
				i++;
				
				item = new QTableWidgetItem();
				item.setData(Qt.ItemDataRole.DisplayRole, message.getTemperature());
				item.setData(Constants.QtSheepDataRole, sheep);
				item.disableGarbageCollection();
				widget.setItem(y, i, item);

				i++;
				
				item = new QTableWidgetItem(message.getGpsPosition().getLatitute() + " - " + message.getGpsPosition().getLongditude());
				item.disableGarbageCollection();
				item.setData(Constants.QtSheepDataRole, sheep);
				widget.setItem(y, i, item);

				y++;
			}
		}
		
		widget.sortByColumn(1, Qt.SortOrder.DescendingOrder);
		widget.resizeRowsToContents();
		widget.resizeColumnsToContents();		
	}
	
	/* SIGNAL EVENTS*/
	/**
	 * Click event for the horizontal order.
	 * Changes the sortorder.
	 * @param x
	 */
	private void horizontalHeader_clicked(QModelIndex x){
		//The curretn column has a sort order
		if(widget.horizontalHeader().sortIndicatorSection() == x.column()){
			if(widget.horizontalHeader().sortIndicatorOrder() == Qt.SortOrder.AscendingOrder){
				widget.sortByColumn(x.column(), Qt.SortOrder.DescendingOrder);
			}
			else
				widget.sortByColumn(x.column(), Qt.SortOrder.AscendingOrder);
		}
		else //Default sorting order is ascending
			widget.sortByColumn(x.column(), Qt.SortOrder.AscendingOrder);

	}
}
