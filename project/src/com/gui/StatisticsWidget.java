package com.gui;

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
	private QWidget qwStatWidget;
	QPushButton btnOk = new QPushButton(tr("ok"));
	
	QTableWidget table = new QTableWidget(1000, 5);
	QTableWidgetItem item = null;
	
	/** Constructor. Initialize..
	 */
	public StatisticsWidget()
	{
		initWidgets();
		initLayout();
		
		poop();
	}
	
	private void poop()
	{
		//btnOk.setMinimumHeight(20);
		//qhbMainLayout.addWidget(btnOk);
		
		table.setMinimumHeight(20);
		qhbMainLayout.addWidget(table);
		//table.
		
		for(int i = 0; i < 1000; i++) {
			 	for(int j = 0; j < 5; j++) {
			      item = new QTableWidgetItem("Test value");
			      table.setItem(i, j, item);
			   }
			}
		
		
		
		super.setLayout(qhbMainLayout);		
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
		this.qwStatWidget = new QWidget();
	}
}

/* EOF */