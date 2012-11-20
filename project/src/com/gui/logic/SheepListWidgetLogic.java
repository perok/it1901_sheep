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
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;

import core.classes.Sheep;

public class SheepListWidgetLogic extends QSignalEmitter{	
	public QListWidget qlWidget;
	
	//Stores a reference the current sheeps in the view.
	private ArrayList<QListWidgetItem> currentItems;
	
	private SortOrder sortOrder = SortOrder.AscendingOrder;
	
	private QIcon sheepOkPicture;
	private QIcon sheepDeadPicture;
	private QIcon mapIcon;
	
	protected Signal1<Sheep> sheepSelected;
	
	protected Signal1<String> statusBarMessage;
	
	protected QAction actionContextDelete;
	protected QAction actionContextShowOnMap;
	
	protected Signal1<ArrayList<Sheep>> sheepsDelete;
	protected Signal1<ArrayList<Sheep>> sheepsShowOnMap;
	
	
	/** Constructor. Initialize..
	 */
	public SheepListWidgetLogic(QListWidget qlWidget)//QTreeView qtvModView)
	{
		sheepOkPicture = new QIcon("res/Sheep_WO_backround.png");
		sheepDeadPicture = new QIcon("res/dead_sheep.png");
		mapIcon = new QIcon("res/treasure-map-icon.png");
		
		sheepSelected = new Signal1<Sheep>();
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
		
		actionContextDelete = new QAction("delete", qlWidget);
		actionContextDelete.triggered.connect(this, "contextDelete_triggered(boolean)");
		actionContextDelete.setIcon(sheepDeadPicture);
		sheepsDelete = new Signal1<ArrayList<Sheep>>();
		
		actionContextShowOnMap = new QAction("Show in map", qlWidget);
		actionContextShowOnMap.triggered.connect(this, "actionContextShowOnMap_triggered(boolean)");
		actionContextShowOnMap.setIcon(mapIcon);
		sheepsShowOnMap = new Signal1<ArrayList<Sheep>>();
		
		qlWidget.addAction(actionContextDelete);
		qlWidget.addAction(actionContextShowOnMap);

		
		qlWidget.setContextMenuPolicy(Qt.ContextMenuPolicy.ActionsContextMenu);
		

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
	 *	Refreshes the sheep list in the widget.
	 *	Call when sheep data is updated.  
	 */
	public void refreshSheepList()
	{
		int currentROw = qlWidget.currentRow();
		
		
		if(UserStorage.getUser() == null)
			return;
		
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
			else
				item.setIcon(sheepDeadPicture);
			
			//Qt should handle GC now. 
			item.disableGarbageCollection();
			
			qlWidget.addItem(item);
			currentItems.add(item);
		}
		//Scrolls to the last row that was current TODO: Does it work?
		qlWidget.setCurrentRow(currentROw);
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
	
	/**
	 * Action for rightclick on sheep(s)
	 * @param vool
	 */
	public void contextDelete_triggered(boolean vool){
		ArrayList<Sheep> sheepSelected = new ArrayList<Sheep>();
		
		for(QListWidgetItem item : currentItems){
			if (item.isSelected()){
				sheepSelected.add((Sheep)item.data(Constants.QtSheepDataRole));
			}
		}
		
		sheepsDelete.emit(sheepSelected);
	}
	
	/**
	 * Action for rightclick show on map
	 * @param bool
	 */
	public void actionContextShowOnMap_triggered(boolean bool){
		ArrayList<Sheep> sheepSelected = new ArrayList<Sheep>();
		
		for(QListWidgetItem item : currentItems){
			if (item.isSelected()){
				sheepSelected.add((Sheep)item.data(Constants.QtSheepDataRole));
			}
		}
		
		//Only fire event when more than one sheep is selected
		sheepsShowOnMap.emit(sheepSelected);
	}
}