package com.gui.sheepList;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

/** List some sheep in a widget
 * 
 * @author andesil
 */
public class SheepListWidget extends QDockWidget
//public class SheepListWidget extends QWidget
{
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
		   Object leftData = sourceModel().data(qmiLeft);
	       Object rightData = sourceModel().data(qmiRight);
           String sLeftString = leftData.toString();
           String sRightString = rightData.toString();

           return sLeftString.compareTo(sRightString) < 0;
		}
	}

	/* End class SortSheep */
	/* Begin class SheepListWidget */
	
	
	public SheepListWidget()
	{
		this.ssProxyModel = new SortSheep();
		this.qsimModel = createMailModel(this);
		this.qtvModelView = new QTreeView();
		this.mainLayout = new QHBoxLayout();
		
		this.ssProxyModel.setSourceModel(this.qsimModel);
		this.ssProxyModel.setDynamicSortFilter(true);
		
		addSheep();
		this.qtvModelView.setModel(this.ssProxyModel);
		this.qtvModelView.setSortingEnabled(true);
		this.mainLayout.addWidget(qtvModelView);
		
		//super.setLayout(this.mainLayout);
		super.setWidget(qtvModelView);
	}
	
	// For test and debugging purposes
	public static void main(String[] args)
	{
		QApplication.initialize(args);
		SheepListWidget listWidget = new SheepListWidget();
		listWidget.show();
		
		QApplication.exec();
	}
	
	private QStandardItemModel createMailModel(QObject parent)
	{
		/* Rows, columns, parent */
		QStandardItemModel qsimModel = new QStandardItemModel(0, 1, parent);
		
		qsimModel.setHeaderData(0, Qt.Orientation.Horizontal, tr("Sheep ID"));
		return qsimModel;
	}
	
	private void addSheep()
	{
		for(int iPos = 0; iPos < 10; iPos++)
		{
			String sSheepName = "sheep <" + Integer.toString((int)(Math.random() * 9)) + ">";
			this.qsimModel.insertRow(0);
			this.qsimModel.setData(this.qsimModel.index(0,  0), sSheepName);
		}
	}
	
	private SortSheep ssProxyModel;
	
	private QHBoxLayout mainLayout;
	private QStandardItemModel qsimModel;
	private QTreeView qtvModelView;
}

/* EOF */