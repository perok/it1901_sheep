package com.gui.widgets;

import java.util.ArrayList;
import com.gui.logic.ServerLogic;
import com.trolltech.qt.gui.QAbstractItemView;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;

import com.trolltech.qt.gui.QWidget;

import core.classes.Farm;
import core.classes.User;

/** Host a list of admin and non-admin users,
 * offer a way to raise/lower priviliges for given users.
 * 
 * @author Gruppe 10
 *
 */
public class AccessListWidget extends QWidget
{
	private QListWidget qlwUserList,
						qlwAdminList;
	private QHBoxLayout qhblMainLayout;
	private QPushButton qpbBtnAddUsers,
						qpbBtnRemoveUsers;
	
	/** Constructor. Initialize..
	 *
	 * @param parent
	 */
	public AccessListWidget(UserSettings parent)
	{		
		initWidgets();
		initLayout();
		initConnectEvents();
		
		/* After this line is done, a signal sends info back to THIS */
		ServerLogic.getClientsocket().listUsers();
	}
	
	/** Set up event-triggers
	 * 
	 */
	private void initConnectEvents()
	{
		this.qpbBtnAddUsers.clicked.connect(this, "transferToAdmin()");
		this.qpbBtnRemoveUsers.clicked.connect(this, "transferFromAdmin()");
	}
	
	/** Recive a list of users, and update the widget displaying the users
	 * 
	 * @param lUsers a list of all the users
	 */
	public void recieveUserData(ArrayList<User> lUsers)
	{
		/* There's no point doing anything with no users */
		if(lUsers.isEmpty() == true) { return; }

		int iFarm = com.storage.UserStorage.getCurrentFarm();
		String sCurrentUserName = com.storage.UserStorage.getUser().getName();		
		ArrayList<Farm> lFarm = com.storage.UserStorage.getUser().getFarmlist();
		Farm fCurrentFarm = lFarm.get(iFarm);
		
		/* For each user */
		for(User u : lUsers)
		{
			//if(u.getName().equals(sCurrentUserName)) { continue; }
			System.out.println(u.getFarmlist());
			
			/* Make an item and insert it into the list */
			QListWidgetItem cur = new QListWidgetItem(this.qlwUserList);
			cur.setText(u.getName());
		}
	}
	
	@SuppressWarnings("unused")
	/** For every selected non-admin user, escalate priviliges to admin.
	 */
	private void transferFromAdmin()
	{
		/* For each selected item in Admin-list */
		for(QListWidgetItem qlwi : this.qlwAdminList.selectedItems())
		{
			/* Remove (graphically) and insert in non-admin-list */
			this.qlwUserList.insertItem(0, qlwi.clone());
			this.qlwAdminList.takeItem(this.qlwAdminList.row(qlwi));
		}
	}
	
	@SuppressWarnings("unused")
	/** For every selected admin-user, lower priviliges to non-admin
	 */
	private void transferToAdmin()
	{
		/* For each selected item in non-admin list */
		for(QListWidgetItem qlwi : this.qlwUserList.selectedItems())
		{
			/* Remove (graphically) and insert in admin-list */
			this.qlwAdminList.insertItem(0, qlwi.clone());
			this.qlwUserList.takeItem(this.qlwUserList.row(qlwi));
		}
	}
	
	/** Initialize all widgets used in THIS
	 */
	private void initWidgets()
	{
		this.qlwUserList = new QListWidget();
		this.qlwAdminList = new QListWidget();
		this.qpbBtnAddUsers = new QPushButton(tr("=>"));
		this.qpbBtnRemoveUsers = new QPushButton(tr("<="));
		
		this.qlwUserList.setSelectionMode(QAbstractItemView.SelectionMode.ExtendedSelection);
		this.qlwAdminList.setSelectionMode(QAbstractItemView.SelectionMode.ExtendedSelection);
	}
		
	/** Return the layout used to display this widgets
	 * 
	 * @see AlertSettings.initLayout()
	 * @return the layout used to display this widget.
	 */
	public QHBoxLayout getLayout()
	{
		//this.qhblMainLayout.setSpacing(10);
		return this.qhblMainLayout;
	}
	
	/** Initialize the main layout of THIS
	 */
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