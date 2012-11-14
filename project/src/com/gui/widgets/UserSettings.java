package com.gui.widgets;

import com.trolltech.qt.core.QRegExp;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QRegExpValidator;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QValidator;
import com.trolltech.qt.gui.QWidget;

/** Show the settings for the user.
 * 
 * @author Gruppe 10
 */
public class UserSettings extends QWidget // implements ChangeNotifier
{
	public static final String CLASS_ICON = "./icons/farmer.png";
	
	private QComboBox qcbFarmCombo;
	private QGroupBox qgbFarmGroup;
	private QGroupBox qgbUserField;
	private QLabel qlGaardLabel;
	private QLabel qlUsername;
	private QLabel qlUserSurname;
	private QLineEdit qleUsername;
	private QLineEdit qleUserSurname;
	
	@SuppressWarnings("unused")
	private int iSelectedCombo = 0;
	
	/** Constructor. Initialize..
	 * @param parent the host of THIS
	 */
	public UserSettings(QWidget parent)
    {
        super(parent);
                    
        initGaardSettings();
        initConnectEvents();
        initUserInput();
        initLayout();
    }
	
	@SuppressWarnings("unused")
	/** Handle for whenever the farm is changed by the user
	 */
	private void farmChanged()
	{
		System.out.println("changed farm to " + this.qcbFarmCombo.currentIndex());
		
		this.iSelectedCombo = this.qcbFarmCombo.currentIndex();
	}
	
	/** Initialize event-driven actions
	 */
	private void initConnectEvents()
	{
		this.qcbFarmCombo.currentIndexChanged.connect(this, "farmChanged()");
	}
	
	/** Initialize the groupbox for farm settings.
	 */
	private void initGaardSettings()
	{
		this.qgbFarmGroup = new QGroupBox(tr("G�rd-innstillinger"));
		this.qlGaardLabel = new QLabel(tr("G�rd:"));
        this.qcbFarmCombo = new QComboBox();
        
        this.qcbFarmCombo.addItem(tr("G�rd 0"));
        this.qcbFarmCombo.addItem(tr("G�rd 1"));
	}
	
	/** Initialize fields for use with user input
	 */
	private void initUserInput()
	{
		final int LABEL_WIDTH = 59;
		this.qgbUserField = new QGroupBox(tr("Brukerdata"));
		this.qleUsername 	= new QLineEdit();
		this.qleUserSurname = new QLineEdit();
		this.qlUsername    = new QLabel(tr("Fornavn:"));
		this.qlUserSurname = new QLabel(tr("Etternavn:"));
		QRegExp qreNameInput = new QRegExp("^[A-Za-z\\ ]+$");
		QValidator qvRegex = new QRegExpValidator(qreNameInput, this.qleUsername);
		
		this.qlUsername.setFixedWidth(LABEL_WIDTH);
		this.qlUserSurname.setFixedWidth(LABEL_WIDTH);
		
		/* Settings for input-field for first name */
		this.qleUsername.setMaxLength(20);
		this.qleUsername.setAlignment(Qt.AlignmentFlag.AlignRight);
		this.qleUsername.setValidator(qvRegex);
		
		/* Settings for input-field for surname */
		this.qleUserSurname.setMaxLength(20);
		this.qleUserSurname.setAlignment(Qt.AlignmentFlag.AlignRight);
		this.qleUserSurname.setValidator(qvRegex);
	}
    
	/** Initialize all the layouts and add them to THIS.
	 */
	// POT_TODO: How far do we go before making an acquaintance with QGridLayout?
    private void initLayout()
    {
    	 QHBoxLayout qhblFarmsLayout  = new QHBoxLayout();
    	 QHBoxLayout qhblUserLay 	  = new QHBoxLayout();
    	 QHBoxLayout qhblSurUserLay	  = new QHBoxLayout();
    	 QVBoxLayout qvblUserLay 	  = new QVBoxLayout();
    	 QVBoxLayout qvblFarmsLayout  = new QVBoxLayout();
    	 QVBoxLayout qvblMainLayout   = new QVBoxLayout();
    	 
    	 /* Each line for name entry is a horizontal box layout that gets added to a vboxlayout */    	 
    	 qhblUserLay.addWidget(this.qlUsername);
    	 qhblUserLay.addWidget(this.qleUsername);
    	 
    	 qhblSurUserLay.addWidget(this.qlUserSurname);
    	 qhblSurUserLay.addWidget(this.qleUserSurname);
    	 
    	 /* - Note: Vertical layout here, horizontal in the lines above */
    	 qvblUserLay.addLayout(qhblUserLay);
    	 qvblUserLay.addLayout(qhblSurUserLay);
    	 
         qhblFarmsLayout.addWidget(this.qlGaardLabel);
         qhblFarmsLayout.addWidget(this.qcbFarmCombo);
         qvblFarmsLayout.addLayout(qhblFarmsLayout);
         
         /* Apply all the comboboxgroups to the main layout */
         qvblMainLayout.addWidget(this.qgbFarmGroup);
         qvblMainLayout.addWidget(this.qgbUserField);
         qvblMainLayout.addStretch(1);
         
         /* Apply the layout to comboboxes */
         this.qgbFarmGroup  .setLayout(qvblFarmsLayout);
         this.qgbUserField  .setLayout(qvblUserLay);
         super			    .setLayout(qvblMainLayout);
    }
}

/* EOF */