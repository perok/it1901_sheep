package com.gui.logic;

import java.util.ArrayList;
import java.util.Arrays;

import javax.print.attribute.standard.SheetCollate;

import com.storage.Constants;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;

import core.classes.GPSPosition;
import core.classes.Message;
import core.classes.Sheep;
import core.classes.SheepStatus;
import core.classes.SheepAlert;


import com.storage.UserStorage;
import com.storage.MessageType;

public class TableWidgetLogic {
	
	private QTableWidget widget;
	
	public TableWidgetLogic(QTableWidget widget){
		this.widget = widget;

        /* Horizontal headers */
		String[] list = {"Message #","Timestamp", "Temperature", "Latitude - Longditude"};
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
	}
	
	public void updateMessages(Sheep selectedSheep){
		System.out.println("TABLE with SHEEP: " + selectedSheep.getName() + "  " + selectedSheep.getRecentStatuses().size());
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
			
			System.out.println("SIZE: " + messages.size());
			addSheep(selectedSheep, messages);
		}
	}
	
	
	/**
	 * Adds the messages to the informationview
	 * @param messages
	 */
	protected void addSheep(Sheep sheep, ArrayList<Message> messages){ // Alerts
		
		//Hva med å ha ovenfor felt for; 
		
		//Og fyllle inn
		
		widget.setRowCount(messages.size());
		
		if(messages != null){
			int y = 0;
		
			for(Message message : messages){
				if(message instanceof SheepStatus)
					System.out.println("SHEEPSTAUS");
				else
					System.out.println("ALARM");
				
				int i = 0;
				QTableWidgetItem item = new QTableWidgetItem(message.getId());
				item.disableGarbageCollection();
				item.setData(Constants.QtSheepDataRole, sheep);
				widget.setItem(y, i, item);
				System.out.println(y + "  " + i + "  " + item.data(Qt.ItemDataRole.DisplayRole));
				i++;
				
				item = new QTableWidgetItem(message.getTimestamp());
				item.disableGarbageCollection();
				item.setData(Constants.QtSheepDataRole, sheep);
				widget.setItem(y, i, item);
				System.out.println(y + "  " + i + "  " + item.data(Qt.ItemDataRole.DisplayRole));

				i++;
				
				item = new QTableWidgetItem(String.valueOf(message.getTemperature()));
				item.disableGarbageCollection();
				item.setData(Constants.QtSheepDataRole, sheep);
				widget.setItem(y, i, item);
				System.out.println(y + "  " + i + "  " + item.data(Qt.ItemDataRole.DisplayRole));

				i++;
				
				item = new QTableWidgetItem(message.getGpsPosition().getLatitute() + " - " + message.getGpsPosition().getLongditude());
				item.disableGarbageCollection();
				item.setData(Constants.QtSheepDataRole, sheep);
				widget.setItem(y, i, item);
				System.out.println(y + "  " + i + "  " + item.data(Qt.ItemDataRole.DisplayRole));

				y++;
			}
		}
		
		widget.resizeRowsToContents();
		widget.resizeColumnsToContents();		
	}
}
