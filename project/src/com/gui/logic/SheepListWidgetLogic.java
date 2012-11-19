package com.gui.logic;

import java.util.ArrayList;
import java.util.List;

import com.storage.Constants;
import com.storage.UserStorage;
import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.MatchFlags;
import com.trolltech.qt.core.Qt.SortOrder;
import com.trolltech.qt.gui.QAbstractItemView.SelectionMode;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;

import core.classes.Sheep;

public class SheepListWidgetLogic extends QSignalEmitter{	
	private QListWidget qlWidget;
	
	//Stores a reference the current sheeps in the view.
	ArrayList<QListWidgetItem> currentItems;
	
	//FIXME: sorting is done with strings and thus, numbered sheeps does'nt get sorted correctly
	SortOrder sortOrder = SortOrder.AscendingOrder;
	
	private QIcon sheepOkPicture;
	
	protected Signal1<Sheep> sheepSelected;
	
	protected Signal1<ArrayList<Sheep>> multiSheepSelect;
	
	protected Signal1<String> statusBarMessage;
	
	
	/** Constructor. Initialize..
	 */
	public SheepListWidgetLogic(QListWidget qlWidget)//QTreeView qtvModView)
	{
		sheepOkPicture = new QIcon("res/Sheep_WO_backround.png");
		
		sheepSelected = new Signal1<Sheep>();
		multiSheepSelect = new Signal1<ArrayList<Sheep>>();
		statusBarMessage = new Signal1<String>();
		
		//this.ssProxyModel = new SortSheep();
		
		this.qlWidget = qlWidget;
		qlWidget.setSortingEnabled(true);
		qlWidget.sortItems(sortOrder);

		//Select multiple items
		this.qlWidget.setSelectionMode(SelectionMode.ExtendedSelection);
	
		//this.qlWidget.contentsMargins().setLeft(200);
		
		//doubleclick event
		this.qlWidget.itemDoubleClicked.connect(this, "onSheepDoubleClicked(QListWidgetItem)");
		this.qlWidget.itemSelectionChanged.connect(this, "itemSelectionChanged()");
		
		
		//this.qsimModel = createSheepModel(this);
		//this.qtvModelView = qtvModView;
		//this.ssProxyModel.setSourceModel(this.qsimModel);
		//this.ssProxyModel.setDynamicSortFilter(true);
		
		//this.qtvModelView.setModel(this.ssProxyModel);
		//this.qtvModelView.setSortingEnabled(true);
		//this.qlWidget.setModel(this.ssProxyModel);
	}
	
	/**
	 * Receives an item in the view that is double clicked
	 * 
	 * @param item Item doubleclicked
	 */
	public void onSheepDoubleClicked(QListWidgetItem item){//QItemSelection selected, QItemSelection deselected){
		if(item.data(Constants.QtSheepDataRole) != null){
			Sheep dClicked = (Sheep)item.data(Constants.QtSheepDataRole);
			sheepSelected.emit(dClicked);
		}
	}
	
	/**
	 * Event fired when sheep(s) are selected
	 */
	public void itemSelectionChanged(){
		ArrayList<Sheep> sheepSelected = new ArrayList<Sheep>();
		
		for(QListWidgetItem item : currentItems){
			if (item.isSelected()){
				sheepSelected.add((Sheep)item.data(Constants.QtSheepDataRole));
			}
		}
		
		//Only fire event when more than one sheep is selected
		if(sheepSelected.size() != 1)
			multiSheepSelect.emit(sheepSelected);
	}
	
	/** debug and test purposes - add sheep */
	public void refreshSheepList()
	{
		statusBarMessage.emit("Populating Sheeps");
		currentItems = new ArrayList<QListWidgetItem>();
		
		//Empty list
		//Muligen sette på GC igjen??
		qlWidget.clear();
		
		//READ DIZ SHIT 
		// http://lists.qt.nokia.com/pipermail/qt-jambi-interest/2008-November/000746.html
		
		for(Sheep sheep : UserStorage.getUser().getFarmlist().get(UserStorage.getCurrentFarm()).getSheepList()){
			QListWidgetItem item = new QListWidgetItem();
			item.setData(Constants.QtSheepDataRole, sheep);
			item.setData(Qt.ItemDataRole.DisplayRole, sheep.getName());
			
			if(sheep.isAlive())
				item.setIcon(sheepOkPicture);
			
			//Qt should handle GC now. 
			item.disableGarbageCollection();
			
			qlWidget.addItem(item);
			currentItems.add(item);
		}
		
		statusBarMessage.emit("done");
	}
	
	/**
	 * Searches through the list of sheeps and shows the ones that match
	 * 
	 * @param searchString The searchstring given by the textinput
	 */
	public void searchSheeps(String searchString){
		List<QListWidgetItem> foundItems = qlWidget.findItems(searchString, new MatchFlags(Qt.MatchFlag.MatchContains));

		statusBarMessage.emit("Found: " + foundItems.size());

		for(QListWidgetItem item : currentItems){
			if(foundItems.contains(item)){
				item.setHidden(false);
			}
			else
				item.setHidden(true);
		}
	}
	/**
	 * Changes the sorting order from ascending order to descending order or vica versa
	 */
	public void changeSortOrder(){
		if(sortOrder == SortOrder.AscendingOrder)
			sortOrder = SortOrder.DescendingOrder;
		else
			sortOrder = SortOrder.AscendingOrder;
		
		qlWidget.sortItems(sortOrder);
	}
	
	/*
	 *  !! DEPRECATED !! 
	 */
	
	/** A class that acts a way to filter and sort data passed between a model and a view
	 */
//	private class SortSheep extends QSortFilterProxyModel
//	{
//		/** Compare one table entry to the next
//		 * 
//		 * @param qmiLeft first entry in the list, or "left-most-entry" relative to param right
//		 * @param qmiRight second entry in the list, or "rigt-most-entry" relative to param left
//		 * @usage used autonomously via super-methods from QSortFilterProxyModel
//		 * @return true if left string is greater than rightstring
//		 */
//		@Override
//		protected boolean lessThan(QModelIndex qmiLeft, QModelIndex qmiRight)
//		{
//		   Object leftData  = sourceModel().data(qmiLeft);
//	       Object rightData = sourceModel().data(qmiRight);
//           String sLeftString = leftData.toString();
//           String sRightString = rightData.toString();
//
//           return sLeftString.compareTo(sRightString) < 0;
//		}
//	}
//	
}