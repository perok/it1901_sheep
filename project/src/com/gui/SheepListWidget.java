package com.gui;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.QVariant;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

import com.storage.Sheeps;

import core.classes.Sheep;

/** List some sheep in a widget
 * 
 * @author Gruppe 10 <3
 */
public class SheepListWidget extends QDockWidget
//public class SheepListWidget extends QWidget
{ 
	// super.setWindowFlags(Qt::Widget);
	
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
		
		public void FUCKTARS(){
			
		}
	}

	/* End class SortSheep */
	/* Begin class SheepListWidget */
	

	/** Constructor. Initialize..
	 */
	public SheepListWidget()
	{
		this.ssProxyModel = new SortSheep();
		this.qsimModel = createSheepModel(this);
		this.qtvModelView = new QTreeView();
		this.mainLayout = new QHBoxLayout();
		
		this.ssProxyModel.setSourceModel(this.qsimModel);
		this.ssProxyModel.setDynamicSortFilter(true);
		
		addSheep();
		this.qtvModelView.setModel(this.ssProxyModel);
		this.qtvModelView.setSortingEnabled(true);
		this.mainLayout.addWidget(qtvModelView);
		
		super.setAllowedAreas(Qt.DockWidgetArea.LeftDockWidgetArea, Qt.DockWidgetArea.RightDockWidgetArea);
		
		//super.setLayout(this.mainLayout);
		super.setWidget(qtvModelView);
	}
	
	/** debug and test purposes - add sheep */
	private void addSheep()
	{

		for(int iPos = 0; iPos < 10; iPos++)
		{
			String sSheepName = "sheep <" + Integer.toString((int)(Math.random() * 9)) + ">";
			this.qsimModel.insertRow(0);
			this.qsimModel.setData(this.qsimModel.index(0, 0), sSheepName);
		}
		updateSheepList();
	}
	
	/**
	 * Updates the sheep list
	 *
	 * Adds only new sheep to the tree
	 *
	 * @param  None
	 * @return None
	 */
	public void updateSheepList(){
		ArrayList<Sheep> sheeps = Sheeps.getSHeeps();
		//Sheep sau = new Sheep(25, "Olga", 0, 250291, true, 70);
		
		
		/* Takes out the previous column and all the sheep referances*/
		List<QStandardItem> preCol = qsimModel.takeColumn(0);
		List<Sheep> data = new ArrayList<Sheep>();
		
		for (QStandardItem item : preCol){
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
		
		/* Experimental
		//for(Sheep sheep : sheeps){
			//this.qsimModel.insertRow(0);
			
			QStandardItem item = new QStandardItem();
			item.setText(sau.getId() + " " +sau.getName());
			item.setData(sau);
			
			qsimModel.appendRow(item);
			//System.out.println(qsimModel.takeColumn(0).size());
			
			//qsimModel.
			List<QStandardItem> lul = qsimModel.takeColumn(0);
			
			for(QStandardItem hj : lul){
				System.out.println("w");
				if(hj.data() != null)
					System.out.println(((Sheep)hj.data()).getName());
			}
			
			qsimModel.insertColumn(0, lul);
			
			//System.out.println(qsimModel.children().size());
			
			QObject st = qsimModel.findChild(QStandardItem.class, sau.getId() + " " + sau.getName());
			
			
			
			if(qsimModel.findChildren().contains(sau)){System.out.println("FANT DEG");}
			//int index = this.qsimModel.indexFromItem(item).row();
			
			//System.out.println(qsimModel.takeItem(index).text());
			
			
			//qsimModel.setD
			QVariant qv = new QVariant();
			
			
			//System.out.println("TROL" + u.size());
			//QModelIndex index = 
			
			//this.qsimModel.setData(this.qsimModel.index(0, 0), sau.getId());
			
			/*
			if (this.qsimModel.findItems("26") != null){
				List l = this.qsimModel.findItems("25");
				
				//qsimModel.
				
				System.out.println("Æ FANT!" + l.size());
				}*/
		//}
		
	}
	

	/** Initialize how sheeps should be listed
	 * 
	 * @param parent the graphical "host" of this widget 
	 * @return a modeled layout of our list of sheep
	 */
	private QStandardItemModel createSheepModel(QObject parent)
	{
														/* Rows, columns, parent */
		QStandardItemModel qsimModel = new QStandardItemModel(0, 1, parent);
		
		qsimModel.setHeaderData(0, Qt.Orientation.Horizontal, tr("Sheep ID"));
		return qsimModel;
	}
	
	private QHBoxLayout mainLayout;
	private SortSheep ssProxyModel;
	private QStandardItemModel qsimModel;
	private QTreeView qtvModelView;
}

/* EOF */