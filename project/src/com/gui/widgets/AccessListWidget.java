package com.gui.widgets;

import com.gui.logic.ServerLogic;
import com.trolltech.qt.gui.QAbstractItemView;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;

import com.trolltech.qt.gui.QWidget;

public class AccessListWidget extends QWidget
{
	private QListWidget qlwUserList,
						qlwAdminList;
	private QHBoxLayout qhblMainLayout;
	private QPushButton qpbBtnAddUsers,
						qpbBtnRemoveUsers;
	
	public AccessListWidget(UserSettings parent)
	{		
		initWidgets();
		initLayout();
		initConnectEvents();
		
		addUsers();
	}
	
	private void initConnectEvents()
	{
		this.qpbBtnAddUsers.clicked.connect(this, "transferToAdmin()");
		this.qpbBtnRemoveUsers.clicked.connect(this, "transferFromAdmin()");
	}
	
	@SuppressWarnings("unused")
	private void transferFromAdmin()
	{
		for(QListWidgetItem qlwi : this.qlwAdminList.selectedItems())
		{
			this.qlwUserList.insertItem(0, qlwi.clone());
			this.qlwAdminList.takeItem(this.qlwAdminList.row(qlwi));
		}				
	}
	
	@SuppressWarnings("unused")
	private void transferToAdmin()
	{
		for(QListWidgetItem qlwi : this.qlwUserList.selectedItems())
		{
			this.qlwAdminList.insertItem(0, qlwi.clone());
			this.qlwUserList.takeItem(this.qlwUserList.row(qlwi));
		}
	}
	
	public void addUsers()
	{
		ServerLogic.getClientsocket().listUsers();
		
		for(int iPos = 0; iPos <= 10; iPos++)
		{
			String s = "abc" + Integer.toString((int)(Math.random() * 9));
			
			QListWidgetItem cur = new QListWidgetItem(qlwUserList);
			cur.setText(s);
		}		
		
		for(int iPos = 0; iPos <= 3; iPos++)
		{
			String s = "abc" + Integer.toString((int)(Math.random() * 9));
			
			QListWidgetItem cur = new QListWidgetItem(this.qlwAdminList);
			cur.setText(s);
		}
	}
	
	private void initWidgets()
	{
		this.qlwUserList = new QListWidget();
		this.qlwAdminList = new QListWidget();
		this.qpbBtnAddUsers = new QPushButton(tr("=>"));
		this.qpbBtnRemoveUsers = new QPushButton(tr("<="));
		
		this.qlwUserList.setSelectionMode(QAbstractItemView.SelectionMode.ExtendedSelection);
		this.qlwAdminList.setSelectionMode(QAbstractItemView.SelectionMode.ExtendedSelection);
	}
		
	public QHBoxLayout getLayout()
	{
		this.qhblMainLayout.setSpacing(10);
		//this.qhblMainLayout.setStretchF
		return this.qhblMainLayout;
	}
	
	private void initLayout()
	{
		qhblMainLayout = new QHBoxLayout();
		QVBoxLayout qvblButtonLayout = new QVBoxLayout();
		
		qvblButtonLayout.addWidget(this.qpbBtnAddUsers);
		qvblButtonLayout.addWidget(this.qpbBtnRemoveUsers);
		
		qhblMainLayout.addWidget(this.qlwUserList);
		qhblMainLayout.addLayout(qvblButtonLayout);
		qhblMainLayout.addWidget(this.qlwAdminList);
		
		qhblMainLayout.setWidgetSpacing(10);
		
		super.setLayout(qhblMainLayout);	
	}

//	public static void main(String[] args)
//	{
//		QApplication.initialize(args);
//		
//		new AccessListWidget(null).show();
//		QApplication.exec();
//		
//	}
}

/* EOF */