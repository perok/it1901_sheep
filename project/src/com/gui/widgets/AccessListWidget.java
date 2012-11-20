package com.gui.widgets;


// TODO: the chain of signals for recieving users is long and not intelligent.
//		 There should be ONE signal, and then a chain down using direct calls to methods.

import java.util.ArrayList;
import com.gui.logic.ServerLogic;
import com.trolltech.qt.gui.QAbstractItemView;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
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
	private QListWidget qlwNonadminList,
						qlwAdminList;
	private QHBoxLayout qhblMainLayout;
	private QLabel qlNonadmin,
				   qlAdmin;
	private QPushButton qpbBtnAddUsers,
						qpbBtnRemoveUsers;
	private ArrayList<User> lUsers;
	private ArrayList<Farm>	lFarms;
		
	/** Constructor. Initialize..
	 *
	 * @param parent
	 */
	public AccessListWidget(UserSettings parent)
	{
		super(parent);
		
		initWidgets();
		initLayout();
		initConnectEvents();
		
		this.lUsers = new ArrayList<User>();
		this.lFarms = new ArrayList<Farm>();
		
		/* After this line is done, a signal sends info back to THIS */
		ServerLogic.getClientsocket().listUsers();
		ServerLogic.getClientsocket().listFarms();
	}
	
	public void farmListRecieved()
	{
		System.out.println("farm-data recieved");
		this.lFarms = com.storage.UserStorage.getFarmList();
		
		setupUserList();
	}
	
	/** Set up event-triggers
	 */
	private void initConnectEvents()
	{
		this.qpbBtnAddUsers.clicked.connect(this, "transferToAdmin()");	
		this.qpbBtnRemoveUsers.clicked.connect(this, "transferFromAdmin()");
		
		((UserSettings)super.parent()).signalFarmUpdate.connect(this, "updateUserLists()");
	}
	
	private boolean isAdmin(User uUser)
	{
		Farm fCurrentFarm = com.storage.UserStorage.getFarmList().get( ((UserSettings)super.parent()).getFarmIndex());
		
		/* For each farm user is admin of... */ 
		for(Farm f : uUser.getFarmlist()) 
		{
			/* ...check if that farm is the one currently selected */
			if(f.isEqual(fCurrentFarm) == true) 
			{
				return true;
			}
		}
		
		return false;
	}
		
	private void updateUserLists()
	{		
		/* For each user */
		for(User u : this.lUsers)
		{
			QListWidgetItem qlwiCur = new QListWidgetItem();
			qlwiCur.setText(u.getName());
			
			if(isAdmin(u) == true) { this.qlwAdminList.addItem(qlwiCur); }
			else				   { this.qlwNonadminList .addItem(qlwiCur);  }
			
			/* Should not be possible to edit permissions for the current user */
			if(u.getName().equals(com.storage.UserStorage.getUser().getName()) == true) 
			{
				qlwiCur.blockSignals(true); 
			}
		}		
	}
	
	private void setupUserList()
	{
		if(this.lFarms != null 
		&& this.lFarms.isEmpty() == false
		&& this.lUsers != null
		&& this.lUsers.isEmpty() == false)
		{
			updateUserLists();
		}
	}
	
	
	/** Recive a list of users, and update the widget displaying the users
	 * 
	 * @param lUsers a list of all the users
	 */
	public void recieveUserData(ArrayList<User> lUsers)
	{
		System.out.println("user data recieved");
		this.lUsers = lUsers;
		setupUserList();
	}
	
	@SuppressWarnings("unused")
	/** For every selected non-admin user, escalate privileges to admin.
	 */
	private void transferFromAdmin()
	{
		/* For each selected item in Admin-list */
		for(QListWidgetItem qlwi : this.qlwAdminList.selectedItems())
		{
			if(qlwi.text().equals(com.storage.UserStorage.getUser().getName()) == true) { continue; }
			/* Remove (graphically) and insert in non-admin-list */
			this.qlwNonadminList.insertItem(0, qlwi.clone());
			this.qlwAdminList.takeItem(this.qlwAdminList.row(qlwi));
		}
	}
	
	@SuppressWarnings("unused")
	/** For every selected admin-user, lower priviliges to non-admin
	 */
	private void transferToAdmin()
	{
		/* For each selected item in non-admin list */
		for(QListWidgetItem qlwi : this.qlwNonadminList.selectedItems())
		{
			/* Remove (graphically) and insert in admin-list */
			this.qlwAdminList.insertItem(0, qlwi.clone());
			this.qlwNonadminList.takeItem(this.qlwNonadminList.row(qlwi));
		}
	}
	
	/** Initialize all widgets used in THIS
	 */
	private void initWidgets()
	{
		this.qlwNonadminList = new QListWidget();
		this.qlwAdminList = new QListWidget();
		this.qpbBtnAddUsers = new QPushButton(tr("=>"));
		this.qpbBtnRemoveUsers = new QPushButton(tr("<="));
		this.qlAdmin = new QLabel(tr("Non-administrators"));
		this.qlNonadmin = new QLabel(tr("Administrators"));
		
		this.qlwNonadminList.setSelectionMode(QAbstractItemView.SelectionMode.ExtendedSelection);
		this.qlwAdminList.setSelectionMode(QAbstractItemView.SelectionMode.ExtendedSelection);
	}
		
	/** Return the layout used to display this widgets
	 * 
	 * @see AlertSettings.initLayout()
	 * @return the layout used to display this widget.
	 */
	public QHBoxLayout getLayout()
	{
		return this.qhblMainLayout;
	}
	
	/** Initialize the main layout of THIS
	 */
	private void initLayout()
	{
		this.qhblMainLayout = new QHBoxLayout();
		QVBoxLayout qvblNonAdminLay = new QVBoxLayout();
		QVBoxLayout qvblAdminLay = new QVBoxLayout();
		QVBoxLayout qvblButtonLayout = new QVBoxLayout();
		
		qvblAdminLay.addWidget(this.qlAdmin);
		qvblAdminLay.addWidget(this.qlwAdminList);
		
		qvblNonAdminLay.addWidget(this.qlNonadmin);
		qvblNonAdminLay.addWidget(this.qlwNonadminList);
		
		qvblButtonLayout.addWidget(this.qpbBtnAddUsers);
		qvblButtonLayout.addWidget(this.qpbBtnRemoveUsers);
		
		this.qhblMainLayout.addLayout(qvblAdminLay);
		this.qhblMainLayout.addLayout(qvblButtonLayout);
		this.qhblMainLayout.addLayout(qvblNonAdminLay);
		
		this.qhblMainLayout.setWidgetSpacing(10);
		
		super.setLayout(qhblMainLayout);	
	}
}

/* EOF */