package com.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import com.gui.logic.ServerLogic;

import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRegExp;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLayout.SizeConstraint;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QRegExpValidator;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QValidator;
import com.trolltech.qt.gui.QWidget;

import core.classes.User;

/** Show the settings for the user.
 * 
 * @author Gruppe 10
 */
public class UserSettings extends QWidget implements InputComponentHost
{
	public static final String CLASS_ICON = "./icons/farmer.png";

	private QComboBox qcbFarmCombo;
	private QGroupBox qgbFarmGroup,
					  qgbUserField,
					  qgbAccessGroup;
	private QLabel qlGaardLabel,
				   qlUsername,
				   qlUserEmail,
				   qlUserPhone;
	private QLineEdit qleUsername,
					  qleEmail,
					  qlePhone;
	private QPushButton qpbBtnAlarm;
	
	private AccessListWidget alwAccessList;
	
	public Signal0 signalFarmUpdate;
	
	private List<ComponentConnector> lComponents = new ArrayList<ComponentConnector>();
		
	/** Constructor. Initialize..
	 * @param parent the host of THIS
	 */
	public UserSettings(SettingsMenu parent)
    {
        super(parent);
        
        initAccessRights();
        initGaardSettings();
        initConnectEvents();
        initUserInput();
        initLayout();
        
        this.signalFarmUpdate = new Signal0();

        addConnector(this.qleUsername, "text", com.storage.UserStorage.class, "setUserName", String.class);
        addConnector(this.qleEmail, "text", com.storage.UserStorage.class, "setUserMail", String.class);
        addConnector(this.qlePhone, "text", com.storage.UserStorage.class, "setUserPhone", String.class);
    }
	
	private void initAccessRights()
	{
		this.alwAccessList = new AccessListWidget(this);
		this.qgbAccessGroup = new QGroupBox(tr("Endre brukerrettigheter"));			
	}
	
    
    @SuppressWarnings("unused")
    /** Function used to call for an alarm
     */
	private void toggleAlarm()
    {
    	new AlarmPromptDialog(this).show();
    }
	
	private <T> void addConnector
	(QWidget qwInputObject,
	 String sInputMethod, 
	 Class<T> outputClass,
	 String sOutputMethod,
	 Class<?> outPutClass)
	{
		ComponentConnector ccO;
		
		try
		{
			ccO = new ComponentConnector(qwInputObject, qwInputObject.getClass().getMethod(sInputMethod), outputClass.getDeclaredMethod(sOutputMethod, outPutClass));
			this.lComponents.add(ccO);
		}
		
		catch (NoSuchMethodException|SecurityException e)
		{
        	System.out.println("error: " + e.getMessage());
		}
	}
	
    
    @SuppressWarnings("unused")
	private void processUserData(ArrayList<User> lUsers)
    {
    	this.alwAccessList.recieveUserData(lUsers);
    }
	
	@SuppressWarnings("unused")
	/** Handle for whenever the farm is changed by the user
	 */
	private void farmChanged()
	{
		com.storage.UserStorage.setCurrentFarm(this.qcbFarmCombo.currentIndex());
		signalFarmUpdate.emit();
	}
	
	/** Initialize event-driven actions
	 */
	private void initConnectEvents()
	{
		this.qcbFarmCombo.currentIndexChanged.connect(this, "farmChanged()");
		this.qpbBtnAlarm.clicked.connect(this, "toggleAlarm()");
	}
	
	/** Initialize the groupbox for farm settings.
	 */
	private void initGaardSettings()
	{
		this.qgbFarmGroup = new QGroupBox(tr("G�rd-innstillinger"));
		this.qlGaardLabel = new QLabel(tr("G�rd:"));
        this.qcbFarmCombo = new QComboBox();
        this.qpbBtnAlarm = new QPushButton(tr("Simuler alarm for angitt g�rd"));
        
        this.qcbFarmCombo.addItem(tr("Gård 0"));
        this.qcbFarmCombo.addItem(tr("Gård 1"));
        
        this.qcbFarmCombo.setCurrentIndex(com.storage.UserStorage.getCurrentFarm());
	}
	
	/** Initialize fields for use with user input

	 */
	private void initUserInput()
	{
		final int LABEL_WIDTH = 59;
		this.qgbUserField = new QGroupBox(tr("Brukerdata"));
		this.qleUsername 	= new QLineEdit();
		this.qleEmail = new QLineEdit();
		this.qlePhone = new QLineEdit();
		this.qlUsername    = new QLabel(tr("Fornavn:"));
		this.qlUserEmail = new QLabel(tr("Epost:"));
		this.qlUserPhone = new QLabel(tr("Telefon"));
		QRegExp qreNameInput = new QRegExp("^[A-Za-z\\ ]+$"); /* Letters and space only */
		QRegExp qrePhoneInput = new QRegExp("^[0-9]+$");
		QRegExp qreMailInput = new QRegExp("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
		QValidator qvRegexName = new QRegExpValidator(qreNameInput, this.qleUsername);
		QValidator qvRegexPhone = new QRegExpValidator(qrePhoneInput, this.qlePhone);
		QValidator qvRegexMail = new QRegExpValidator(qreMailInput, this.qlePhone);
		
		this.qlUsername.setFixedWidth(LABEL_WIDTH);
		this.qlUserEmail.setFixedWidth(LABEL_WIDTH);
		this.qlUserPhone.setFixedWidth(LABEL_WIDTH);
		
		/* Settings for input-field for first name */
		this.qleUsername.setEnabled(true);
		this.qleUsername.setText(com.storage.UserStorage.getUser().getName());
		this.qleUsername.setMaxLength(25);
		this.qleUsername.setAlignment(Qt.AlignmentFlag.AlignRight);
		this.qleUsername.setValidator(qvRegexName);
		
		/* Settings for input-field for mail */
		this.qleEmail.setText(com.storage.UserStorage.getUser().getEmail());
		this.qleEmail.setEnabled(true);
		this.qleEmail.setMaxLength(25);
		this.qleEmail.setAlignment(Qt.AlignmentFlag.AlignRight);
		this.qleEmail.setValidator(qvRegexMail);
		
		/* Settings for phone */
		this.qlePhone.setText(Integer.toString(com.storage.UserStorage.getUser().getMobileNumber()));
		this.qlePhone.setEnabled(true);
		this.qlePhone.setMaxLength(25);
		this.qlePhone.setAlignment(Qt.AlignmentFlag.AlignRight);
		this.qlePhone.setValidator(qvRegexPhone);	
	}
	
	/** Initialize all the layouts and add them to THIS.
	 */
	// POT_TODO: How far do we go before making an acquaintance with QGridLayout?
    private void initLayout()
    {
    	 QHBoxLayout qhblAccessListLayout = this.alwAccessList.getLayout();
    	 QHBoxLayout qhblFarmsLayout  = new QHBoxLayout();
    	 QHBoxLayout qhblUserLay 	  = new QHBoxLayout();
    	 QHBoxLayout qhblMailLay	  = new QHBoxLayout();
    	 QHBoxLayout qhblPhoneLay	  = new QHBoxLayout();
    	 QVBoxLayout qvblUserLay 	  = new QVBoxLayout();
    	 QVBoxLayout qvblFarmsLayout  = new QVBoxLayout();
    	 QVBoxLayout qvblMainLayout   = new QVBoxLayout();
    	  
    	 this.qgbAccessGroup.setMaximumWidth(400); // FIXME: I would just like this to be the initial size
    	 
    	 /* Each line for name entry is a horizontal box layout that gets added to a vboxlayout */    	 
    	 qhblUserLay.addWidget(this.qlUsername);
    	 qhblUserLay.addWidget(this.qleUsername);
    	 
    	 qhblMailLay.addWidget(this.qlUserEmail);
    	 qhblMailLay.addWidget(this.qleEmail);
    	 
    	 qhblPhoneLay.addWidget(this.qlUserPhone);
    	 qhblPhoneLay.addWidget(this.qlePhone);    	 
    	 
    	 /* - Note: Vertical layout here, horizontal in the lines above */
    	 qvblUserLay.addLayout(qhblUserLay);
    	 qvblUserLay.addLayout(qhblMailLay);
    	 qvblUserLay.addLayout(qhblPhoneLay);
    	 
         qhblFarmsLayout.addWidget(this.qlGaardLabel);
         qhblFarmsLayout.addWidget(this.qcbFarmCombo);
         qvblFarmsLayout.addLayout(qhblFarmsLayout);
         qvblFarmsLayout.addWidget(qpbBtnAlarm);
         
         /* Apply all the comboboxgroups to the main layout */
         qvblMainLayout.addWidget(this.qgbFarmGroup);
         qvblMainLayout.addWidget(this.qgbUserField);
	     qvblMainLayout.addWidget(this.qgbAccessGroup);
         qvblMainLayout.addStretch(1);
                 
         /* Apply the layout to comboboxes */
         this.qgbFarmGroup  .setLayout(qvblFarmsLayout);
         this.qgbUserField  .setLayout(qvblUserLay);
         this.qgbAccessGroup.setLayout(qhblAccessListLayout);
         super			    .setLayout(qvblMainLayout);
    }

	@Override
	public void writeChange() 
	{
		User origUser = com.storage.UserStorage.getUser().copyShallowUser(),
			 newUser  = null;
		
		for(ComponentConnector cc : this.lComponents)
		{
			cc.writeChanges();
		}
		
		newUser = com.storage.UserStorage.getUser();
		
		if(origUser.shallowEquals(newUser) == false)
		{
			ServerLogic.getClientsocket().editUser(newUser);
		}
	}
	

}

/* EOF */