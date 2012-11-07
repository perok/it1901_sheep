package com.gui.logic;

import java.util.ArrayList;
import java.util.List;

import com.storage.Sheeps;
import com.storage.UserStorage;
import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt.MatchFlags;
import com.trolltech.qt.core.Qt.SortOrder;
import com.trolltech.qt.gui.QAbstractItemView.SelectionMode;
import com.trolltech.qt.gui.QItemSelection;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QSortFilterProxyModel;
import com.trolltech.qt.gui.QStandardItem;
import com.trolltech.qt.gui.QStandardItemModel;
import com.trolltech.qt.gui.QTreeView;

import core.classes.Sheep;

public class sheepListWidgetHandler extends QSignalEmitter{
	
	/** A class that acts a way to filter and sort data passed between a model and a view
	 */
	private class SortSheep extends QSortFilterProxyModel
	{
		/** Compare one table entry to the next
		 * 
		 * @param qmiLeft first entry in the list, or "left-most-entry" relative to param right
		 * @param qmiRight second entry in the list, or "rigt-most-entry" relative to param left
		 * @usage used autonomously via super-methods from QSortFilterProxyModel
		 * @return true if left string is greater than rightstring
		 */
		@Override
		protected boolean lessThan(QModelIndex qmiLeft, QModelIndex qmiRight)
		{
		   Object leftData  = sourceModel().data(qmiLeft);
	       Object rightData = sourceModel().data(qmiRight);
           String sLeftString = leftData.toString();
           String sRightString = rightData.toString();

           return sLeftString.compareTo(sRightString) < 0;
		}
	}
	
	//private SortSheep ssProxyModel;
	//private QStandardItemModel qsimModel;
	
	//private QTreeView qtvModelView;
	
	private QListWidget qlWidget;
	
	private int QtSheepDataRole = 32;
	
	SortOrder sortOrder = SortOrder.AscendingOrder;
	
	Signal1<Sheep> sheepSelected;
	Signal1<ArrayList<Sheep>> multiSheepSelect;
	protected Signal1<String> statusBarMessage;
	
	
	/** Constructor. Initialize..
	 */
	public sheepListWidgetHandler(QListWidget qlWidget)//QTreeView qtvModView)
	{
		sheepSelected = new Signal1<Sheep>();
		multiSheepSelect = new Signal1<ArrayList<Sheep>>();
		statusBarMessage = new Signal1<String>();
		
		//this.ssProxyModel = new SortSheep();
		
		this.qlWidget = qlWidget;
		qlWidget.setSortingEnabled(true);
		qlWidget.sortItems(sortOrder);

		//Select multiple items
		this.qlWidget.setSelectionMode(SelectionMode.ExtendedSelection);
		
		//doubleclick event
		this.qlWidget.itemDoubleClicked.connect(this, "onSheepDoubleClicked(QListWidgetItem)");
		
		//this.qlWidget.itemSelectionChanged.connect(this, "multipleSheepsSelected(QItemSelection, QItemSelection)");
		
		//this.qsimModel = createSheepModel(this);
		//this.qtvModelView = qtvModView;
		//this.ssProxyModel.setSourceModel(this.qsimModel);
		//this.ssProxyModel.setDynamicSortFilter(true);
		

		//this.qtvModelView.setModel(this.ssProxyModel);
		//this.qtvModelView.setSortingEnabled(true);
		//this.qlWidget.setModel(this.ssProxyModel);
		
		//super.setLayout(this.mainLayout);
		
		/*Click event*/
		//qtvModelView.selectionModel().selectionChanged.connect(this, "onSheepClicked(QItemSelection, QItemSelection)");
	}
	
		
	/** Initialize how sheeps should be listed
	 * 
	 * @param parent the graphical "host" of this widget 
	 * @return a modeled layout of our list of sheep
	 */
	/*
	private QStandardItemModel createSheepModel(QObject parent)
	{
														/* Rows, columns, parent 
		QStandardItemModel qsimModel = new QStandardItemModel(0, 1, parent);
		
		qsimModel.setHeaderData(0, Qt.Orientation.Horizontal, tr("Sheep ID"));
		return qsimModel;
	}*/
	
	
	
	/**
	 * Receives the qsimModels click events
	 * 
	 * @param item Item doubleclicked
	 */
	public void onSheepDoubleClicked(QListWidgetItem item){//QItemSelection selected, QItemSelection deselected){
		
		if(item.data(QtSheepDataRole) != null){
			Sheep dClicked = (Sheep)item.data(QtSheepDataRole);
			sheepSelected.emit(dClicked);
		}
	}
	
	public void multipleSheepsSelected(QItemSelection selected, QItemSelection deselected){
		System.out.println("hello");
	}
	
	
	/** debug and test purposes - add sheep */
	public void addSheep()
	{
		statusBarMessage.emit("Populating Sheeps");
		/*
		for(int iPos = 0; iPos < 10; iPos++)
		{
			String sSheepName = "sheep <" + Integer.toString((int)(Math.random() * 9)) + ">";
			
			QListWidgetItem item = new QListWidgetItem();
			item.setData(QtSheepDataRole, new Sheep(23, sSheepName, 23, 3432,true, 3432));
			item.setData(0, sSheepName);
			qlWidget.addItem(item);
			
			//this.qsimModel.insertRow(0);
			//this.qsimModel.setData(this.qsimModel.index(0, 0), sSheepName);
		}*/
		//updateSheepList();
		statusBarMessage.emit("done");
		int currentFarm = 0;
		for(Sheep sheep : UserStorage.getUser().getFarmlist().get(currentFarm).getSheepList()){
			QListWidgetItem item = new QListWidgetItem();
			item.setData(QtSheepDataRole, sheep);
			item.setData(0, sheep.getName());
		}
	}
	
	//Qt MatchContains 1 == contained in the item
	private void searchSheeps(String searchString){
		qlWidget.findItems(searchString, new MatchFlags(1));
	}
	
	private void changeSortOrder(){
		if(sortOrder == SortOrder.AscendingOrder)
			sortOrder = SortOrder.DescendingOrder;
		else
			sortOrder = SortOrder.AscendingOrder;
		
		qlWidget.sortItems(sortOrder);
	}
	
	
	/**
	 * Updates the sheep list
	 *
	 * Adds only new sheep to the tree
	 * 
	 * @Param sheeps the sheeps being added
	 */
	public void updateSheepList(){
		ArrayList<Sheep> sheeps = Sheeps.getSHeeps();
		//Sheep sau = new Sheep(25, "Olga", 0, 250291, true, 70);
		
		if(sheeps != null){
			/* Takes out the previous column and all the sheep referances*/
			List<QStandardItem> preCol = qsimModel.takeColumn(0);
			List<Sheep> data = new ArrayList<Sheep>();
			
			for (QStandardItem item : preCol){
				if(item.data() instanceof Sheep)
					data.add((Sheep)item.data());
			}
			
			for(Sheep sheep : sheeps){
				/*Add only new sheep*/
				if(!data.contains(sheep)){
					QStandardItem item = new QStandardItem();
					item.setText(sheep.getId() + " " +sheep.getName());
					item.setData(sheep);
					
					qsimModel.appendRow(item);
				}
			}
			
			/*Insert back the previous column*/
			qsimModel.insertColumn(0, preCol);
		}		
	}
}
