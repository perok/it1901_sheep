package com.gui.widgets;


// TODO: the chain of signals for recieving users is long and not intelligent.
//		 There should be ONE signal, and then a chain down using direct calls to methods.

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.gui.logic.ServerLogic;
import com.net.ClientSocket;
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
//TODO: this class is both a graphical component AND does a lot of logics,
//		for the sake of clean code it might be worth to fix up :-/
public class AccessListWidget extends QWidget implements InputComponentHost
{
	private QListWidget qlwNonadminList,
						qlwAdminList;
	private QHBoxLayout qhblMainLayout;
	private QLabel qlNonadmin,
				   qlAdmin;
	private QPushButton qpbBtnAddUsers,
						qpbBtnRemoveUsers;
	
	private HashMap<String, User> mUserAdmins = new HashMap<String, User>();
	private HashMap<String, User> mUserNonadmins = new HashMap<String, User>();
	private ArrayList<User> lUsers;
	private ArrayList<Farm>	lFarms;
	private List<ComponentConnector> lComponents;
	private ArrayList<User> lAdminUsers,
					   		lNonAdminUsers;
	
	/** Hold the index of the farm selected before the current one */
	public int iFormerFarmIndex = 0; //Lazy fix, no time, just making it public
	
		
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
		
		this.lAdminUsers = new ArrayList<User>();
		this.lNonAdminUsers = new ArrayList<User>();
		
		this.lComponents = new ArrayList<ComponentConnector>();
						
		/* After this line is done, a signal sends info back to THIS */
		ServerLogic.getClientsocket().listUsers();
		ServerLogic.getClientsocket().listFarms();
	}
	
	/** Handle whenever a farm list is recieved
	 */
	public void farmListRecieved()
	{
		this.lFarms = com.storage.UserStorage.getFarmList();
		ServerLogic.getClientsocket().listUsers(); /* When the farms are updated, the users might be too */
		
		setupUserList(); /* Make sure to list a fresh view of the users */
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

	/** Check if a given user is admin or not
	 * 
	 * @param uUser the given admin
	 * @return true if param is has access right to the current farm, false if not
	 */
	private boolean hasAccessRights(User uUser)	
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
		
		/* User does not have access rights for this farm */
		return false;
	}
	
	/**Sets up user list
	 */
	private void setupUserList()
	{
		/* Make sure there's valid input data */
		if(this.lFarms != null 
		&& this.lFarms.isEmpty() == false
		&& this.lUsers != null
		&& this.lUsers.isEmpty() == false)
		{
			/* Data is valid - update */
			updateUserLists();
		}
	}
	
	
	/** Recive a list of users, and update the widget displaying the users
	 * 
	 * @param lUsers a list of all the users
	 */
	public void recieveUserData(ArrayList<User> lUsers)
	{
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
			if(qlwi.text().equals(com.storage.UserStorage.getUser().getName()) == true) { continue; }
			/* Remove (graphically) and insert in admin-list */
			this.qlwAdminList.insertItem(0, qlwi.clone());
			this.qlwNonadminList.takeItem(this.qlwNonadminList.row(qlwi));
		}
	}
	
	/** Clear out data before applying fresh user-data.
	 */
	private void eraseLists()
	{
		/* Simply creating a new list will remove it's properties and dependencies,
		 * therefore we need to manually remove each listWidget */
		while(this.qlwAdminList.count() > 0) 	{ this.qlwAdminList.takeItem(0); }
		while(this.qlwNonadminList.count() > 0) { this.qlwNonadminList.takeItem(0);	}
			
		this.lAdminUsers = new ArrayList<User>();
		this.lNonAdminUsers = new ArrayList<User>();
		
		this.mUserAdmins = new HashMap<String, User>();
		this.mUserNonadmins = new HashMap<String, User>();
	}
	
	// This is ugly and not good with OOP, but I'm running out of time.
	private ArrayList<User> readNewAdminList()
	{
		ArrayList<User> lNewAdmins = new ArrayList<User>();
		
		/* For every user listed under admins */
		for(int iPos = 0; iPos < this.qlwAdminList.count(); iPos++)
		{
			QListWidgetItem cur = this.qlwAdminList.item(iPos);
			
			/* If this user isn't part of the original admin list */
			if(this.mUserAdmins.containsKey(cur.text()) == false)
			{
				/* Add the user to the list of new, updated */
				lNewAdmins.add(this.mUserNonadmins.get(cur.text()));
			}
		}
		return lNewAdmins;
	}
	
	private ArrayList<User> readNewNonadminList()
	{
		ArrayList<User> lNewNonAdmins = new ArrayList<User>();
		
		/* For every user listed under non-admins */
		for(int iPos = 0; iPos < this.qlwNonadminList.count(); iPos++)
		{
			QListWidgetItem cur = this.qlwNonadminList.item(iPos);
			/* If this user wasn't in the original list of non-admins */
			if(this.mUserNonadmins.containsKey(cur.text()) == false)
			{
				/* Add the user to the list of new, update */
				lNewNonAdmins.add(this.mUserAdmins.get(cur.text()));
			}
		}
		
		return lNewNonAdmins;
	}
	
	/** From the given lists, write any updates made by the user.
	 */
	private void updateAccessRights()
	{
		/* For each user currently in the access rights list */
		for(User u : readNewAdminList())
		{
			/* ... give access rights */
			ServerLogic.getClientsocket().addAccessRights(u, this.lFarms.get(iFormerFarmIndex));
		}
		
		/* For every new user in the non-access list */
		for(User u : readNewNonadminList())
		{
			/** ... remove access rights */
			ServerLogic.getClientsocket().removeAccessRights(u, this.lFarms.get(iFormerFarmIndex));			
		}
	}
	
	/** Given that there has been obtained new data, update the lists shown.
	 * Old changes are saved before displaying data.
	 */
	private void updateUserLists()
	{
		updateAccessRights();
		eraseLists();
		
		/* For each user */
		for(User u : this.lUsers)
		{
			QListWidgetItem qlwiCur = new QListWidgetItem();
			String sName = u.getName();
			
			qlwiCur.setText(sName);
			
			/* If a user has access rights */
			if(hasAccessRights(u) == true) 
			{
				this.qlwAdminList.addItem(qlwiCur); /* Add as graphical component to list */
				this.lAdminUsers.add(u);		 /* For later referencing */
				this.mUserAdmins.put(sName,  u); /* Hashing so that I can quickly look up data later */
			}
			else /* User does not have access rights */
			{ // @see above..
				this.qlwNonadminList.addItem(qlwiCur);
				this.lNonAdminUsers.add(u);
				this.mUserNonadmins.put(sName,  u);
			}
		}
	}

	/** Set up event-triggers
	 */
	private void initConnectEvents()
	{
		this.qpbBtnAddUsers.clicked.connect(this, "transferToAdmin()");	
		this.qpbBtnRemoveUsers.clicked.connect(this, "transferFromAdmin()");
		
		((UserSettings)super.parent()).signalFarmUpdate.connect(ServerLogic.getClientsocket(), "listUsers()");
		((UserSettings)super.parent()).signalFarmUpdate.connect(this, "updateUserLists()");
	}

	/** Initialize the main layout of THIS
	 */
	private void initLayout()
	{
		this.qhblMainLayout = new QHBoxLayout();
		QVBoxLayout qvblNonAccessRightsLay = new QVBoxLayout();
		QVBoxLayout qvblAccessRightsLay = new QVBoxLayout();
		QVBoxLayout qvblButtonLayout = new QVBoxLayout();
		
		qvblAccessRightsLay.addWidget(this.qlAdmin);
		qvblAccessRightsLay.addWidget(this.qlwAdminList);
		
		qvblNonAccessRightsLay.addWidget(this.qlNonadmin);
		qvblNonAccessRightsLay.addWidget(this.qlwNonadminList);
		
		qvblButtonLayout.addWidget(this.qpbBtnAddUsers);
		qvblButtonLayout.addWidget(this.qpbBtnRemoveUsers);
		
		this.qhblMainLayout.addLayout(qvblNonAccessRightsLay);
		this.qhblMainLayout.addLayout(qvblButtonLayout);
		this.qhblMainLayout.addLayout(qvblAccessRightsLay);
		
		super.setLayout(qhblMainLayout);
	}

	/** Initialize all widgets used in THIS
	 */
	private void initWidgets()
	{
		this.qlwNonadminList = new QListWidget();
		this.qlwAdminList = new QListWidget();
		this.qpbBtnAddUsers = new QPushButton(tr("-->"));
		this.qpbBtnRemoveUsers = new QPushButton(tr("<--"));
		this.qlAdmin = new QLabel(tr("Brukerrettigheter"));
		this.qlNonadmin = new QLabel(tr("Ikke brukerrettigheter"));
		
		/* Enables for single-select and/or multiple */
		this.qlwNonadminList.setSelectionMode(QAbstractItemView.SelectionMode.ExtendedSelection);
		this.qlwAdminList.setSelectionMode(QAbstractItemView.SelectionMode.ExtendedSelection);
	}

	@Override
	public void writeChange() 
	{
		updateAccessRights();		
	}
}

/* EOF */