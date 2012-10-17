package com.gui;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QPushButton;
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
		btnOk.setMinimumHeight(20);
		qhbMainLayout.addWidget(btnOk);
		
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