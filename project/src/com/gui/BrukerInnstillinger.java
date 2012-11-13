package com.gui;

import com.trolltech.qt.QtBlockedSlot;
import com.trolltech.qt.core.QRegExp;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QInputDialog;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QRegExpValidator;
import com.trolltech.qt.gui.QTextItem;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QValidator;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QValidator.QValidationData;
import com.trolltech.qt.gui.QValidator.State;

public class BrukerInnstillinger extends QWidget // implements ChangeNotifier
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
	//private QInputDialog
	
	private int iSelectedCombo = 0;
	
	public BrukerInnstillinger(QWidget parent)
    {
        super(parent);
                    
        initGaardSettings();
        initConnectEvents();
        initUserInput();
        initLayout();
    }
	
	@SuppressWarnings("unused")
	private void farmChanged()
	{
		System.out.println("changed farm to " + this.qcbFarmCombo.currentIndex());
		
		this.iSelectedCombo = this.qcbFarmCombo.currentIndex();
	}
	
	private void initConnectEvents()
	{
		this.qcbFarmCombo.currentIndexChanged.connect(this, "farmChanged()");
	}
	
	private void initGaardSettings()
	{
		this.qgbFarmGroup = new QGroupBox(tr("Gård-innstillinger"));
		this.qlGaardLabel = new QLabel(tr("Gård:"));
        this.qcbFarmCombo = new QComboBox();
        
        this.qcbFarmCombo.addItem(tr("Gård 0"));
        this.qcbFarmCombo.addItem(tr("Gård 1"));
	}
	
	private void initUserInput()
	{
		this.qgbUserField = new QGroupBox(tr("Brukerdata"));
		this.qleUsername = new QLineEdit();
		this.qleUserSurname = new QLineEdit();
		this.qlUsername = new QLabel(tr("Fornavn:"));
		this.qlUserSurname = new QLabel(tr("Etternavn:"));
		
		QRegExp qreNameInput = new QRegExp("^[A-Za-z\\ ]+$");
		QValidator qvRegex = new QRegExpValidator(qreNameInput, this.qleUsername);
		
		this.qleUsername.setMaxLength(20);
		this.qleUsername.setAlignment(Qt.AlignmentFlag.AlignRight);
		this.qleUsername.setValidator(qvRegex);
		
		this.qleUserSurname.setMaxLength(20);
		this.qleUserSurname.setAlignment(Qt.AlignmentFlag.AlignRight);
		this.qleUserSurname.setValidator(qvRegex);
	}
    
	/** Initialize all the layouts and add them to THIS.
	 */
	// POT_TODO: How far do I go before making an acquaintance with QGridLayout?
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