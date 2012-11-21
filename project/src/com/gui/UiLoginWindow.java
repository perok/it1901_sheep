/********************************************************************************
 ** Form generated from reading ui file 'untitled.jui'
 **
 ** Created by: Qt User Interface Compiler version 4.7.1
 **
 ** WARNING! All changes made in this file will be lost when recompiling ui file!
 ********************************************************************************/

package com.gui;

import java.awt.Dialog;

import com.gui.logic.ServerLogic;
import com.gui.widgets.UserSettings;
import com.storage.UserStorage;
import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

/** The graphical interface for the login-window 
 * 
 */
public class UiLoginWindow extends QSignalEmitter implements com.trolltech.qt.QUiForm<QMainWindow>
{
    public QWidget centralwidget;
    public QGridLayout gridLayout;
    public QVBoxLayout verticalLayout;
    public QFormLayout formLayout;
    public QLabel label;
    public QLineEdit txtEditinputUsername;
    public QLabel label_2;
    public QLineEdit txtEditPassword, inputUsername;
    public QGridLayout gridLayout_2, gridLayout_3;
    public QPushButton btnLogin, btnRegister;
    public QSpacerItem verticalSpacer;
    public QSpacerItem verticalSpacer_2;
    
    /* Signals */
    public Signal2<String, String> signalTryLogin = new Signal2<String, String>();

    public UiLoginWindow() { 
    	super(); 
    }

    public void setupUi(QMainWindow MainWindow, int width, int height)
    {
    	signalTryLogin = new Signal2<String, String>();
    	
        MainWindow.setObjectName("MainWindow");
        
        MainWindow.resize(new QSize(width, height).expandedTo(MainWindow.minimumSizeHint()));
        centralwidget = new QWidget(MainWindow);
        centralwidget.setObjectName("centralwidget");
        gridLayout = new QGridLayout(centralwidget);
        gridLayout.setObjectName("gridLayout");
        verticalLayout = new QVBoxLayout();
        verticalLayout.setObjectName("verticalLayout");
        formLayout = new QFormLayout();
        formLayout.setObjectName("formLayout");
        formLayout.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetMinimumSize);
        formLayout.setFieldGrowthPolicy(com.trolltech.qt.gui.QFormLayout.FieldGrowthPolicy.FieldsStayAtSizeHint);
        formLayout.setFormAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
        label = new QLabel(centralwidget);
        label.setObjectName("label");

        txtEditinputUsername = new QLineEdit(centralwidget);
        txtEditinputUsername.setObjectName("txtEditinputUsername");

        formLayout.addRow(label, txtEditinputUsername);

        label_2 = new QLabel(centralwidget);
        label_2.setObjectName("label_2");

        txtEditPassword = new QLineEdit(centralwidget);
        txtEditPassword.setObjectName("txtEditPassword");
        txtEditPassword.setEchoMode(com.trolltech.qt.gui.QLineEdit.EchoMode.Password);

        formLayout.addRow(label_2, txtEditPassword);

        verticalLayout.addLayout(formLayout);


        gridLayout.addLayout(verticalLayout, 1, 0, 1, 1);

        gridLayout_2 = new QGridLayout();
        gridLayout_2.setObjectName("gridLayout_2");
        gridLayout_2.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetMinimumSize);
        btnLogin = new QPushButton(centralwidget);
        btnLogin.setObjectName("btnLogin");
        btnLogin.setMaximumSize(new QSize(140, 16777215));
        
        gridLayout_2.addWidget(btnLogin, 0, 0, 1, 1);
        
        gridLayout_3 = new QGridLayout();
        gridLayout_3.setObjectName("gridLayout_3");
        gridLayout_3.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetMinimumSize);
        btnRegister = new QPushButton(centralwidget);
        btnRegister.setObjectName("Register new user");
        btnRegister.setMaximumSize(new QSize(140, 16777215));

        gridLayout_2.addWidget(btnRegister, 1, 0, 1, 1);
        gridLayout.addLayout(gridLayout_3, 3, 0, 1, 1);
        gridLayout_3.setColumnStretch(1, 1);

        gridLayout.addLayout(gridLayout_2, 3, 0, 1, 1);

        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer, 0, 0, 1, 1);

        verticalSpacer_2 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer_2, 4, 0, 1, 1);

        MainWindow.setCentralWidget(centralwidget);
        retranslateUi(MainWindow);

        
        btnLogin.clicked.connect(this, "loginCheck()");
        btnRegister.clicked.connect(this, "registerUser()");
        MainWindow.connectSlotsByName();
        
      
    } // setupUi

    void retranslateUi(QMainWindow MainWindow)
    {
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Username", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Password", null));
        btnLogin.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Login", null));
        btnRegister.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Register new user", null));
    } // retranslateUi
    
    /**
     * A method for checking login autorization
     */
    public void loginCheck(){
    	if(isPassWordAndUsernName(txtEditinputUsername.text(), txtEditPassword.text())){
    		signalTryLogin.emit(txtEditinputUsername.text(), txtEditPassword.text());
    	}
    	else{
    		System.out.println("Input correct shiet mafacka");
    		txtEditPassword.setText(null);
    		txtEditPassword.setFocus();
    	}
    }
    public void registerUser(){
    	new RegisterUser().show();
    }
    
    /**
     * First we check if inputUsername and password != null, then we dont need to do the s shit
     * 
     * @param inputUsername
     * @param password
     * @return
     */
    private static boolean isPassWordAndUsernName(String inputUsername, String password){
    	if(inputUsername.length() == 0 || password.length() == 0)
    		return false;
    	return true;
    	
    }

	@Override
	public void setupUi(QMainWindow arg0) {
		setupUi(arg0, 800, 800);
		
	}

	public class RegisterUser extends QWidget {

	    QLineEdit password, name, phone, email, username;
	    QVBoxLayout layout;
	    QLabel  inputUsername, inputEmail, inputPhone;
	    private QPushButton register = new QPushButton("Register");
	    
	    public RegisterUser() {

	        setWindowTitle("Register ny bruker");
	        
	        initUI();

	        move(400, 300);
	        show();
	        setFixedSize(250,200);
	    }
	    private void initLayout(){
	    	 
	    	 layout = new QVBoxLayout();
	    	 QHBoxLayout qhblUserLay 	  = new QHBoxLayout();
	    	 QHBoxLayout qhblMailLay	  = new QHBoxLayout();
	    	 QHBoxLayout qhblPhoneLay	  = new QHBoxLayout();
	    	  
	    	 
	    	 /* Each line for name entry is a horizontal box layout that gets added to a vboxlayout */    	 
	    	 qhblUserLay.addWidget(this.inputUsername);
	    	 qhblUserLay.addWidget(this.username);
	    	 
	    	 qhblMailLay.addWidget(this.inputEmail);
	    	 qhblMailLay.addWidget(this.email);
	    	 
	    	 qhblPhoneLay.addWidget(this.inputPhone);
	    	 qhblPhoneLay.addWidget(this.phone);
	    	 
	    	 layout.addLayout(qhblUserLay);
	    	 layout.addLayout(qhblMailLay);
	    	 layout.addLayout(qhblPhoneLay);
	    	 layout.addWidget(register);
	    	 setLayout(layout);
	    }
	    

	    /*Brukernavn
	     * passord
	     * navn
	     * telefonnummer
	     * epost
	     */
	    private void initUI() {

	    	
	    	final int LABEL_WIDTH = 59;
			this.username 	= new QLineEdit();
			this.email = new QLineEdit();
			this.phone = new QLineEdit();
			this.inputUsername = new QLabel(tr("Fornavn:"));
			this.inputEmail = new QLabel(tr("Epost:"));
			this.inputPhone = new QLabel(tr("Telefon +47"));
			

			QRegExp qreNameInput = new QRegExp("^[A-Za-z\\ ]+$"); /* Letters and space only */
			QRegExp qrphoneInput = new QRegExp("^[0-9]+$"); /* Numbers only */
			QRegExp qreMailInput = new QRegExp( /* Valid email-addresses only, */
					"\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b"); /* Note that user can type invalid email by not finishing the text, e.g.	**
					 														   ** someuser@domain    (suppressing the .com-ending)						*/
			QValidator qvRegexName = new QRegExpValidator(qreNameInput, this.inputUsername);
			QValidator qvRegexPhone = new QRegExpValidator(qrphoneInput, this.phone);
			QValidator qvRegexMail = new QRegExpValidator(qreMailInput, this.phone);
			
			this.inputUsername.setFixedWidth(LABEL_WIDTH);
			this.inputEmail.setFixedWidth(LABEL_WIDTH);
			this.inputPhone.setFixedWidth(LABEL_WIDTH);
			
			/* Settings for input-field for first name */
			this.username.setEnabled(true);
			this.username.setMaxLength(25);
			this.username.setAlignment(Qt.AlignmentFlag.AlignRight);
			this.username.setValidator(qvRegexName);
			
			/* Settings for input-field for mail */
			this.email.setEnabled(true);
			this.email.setMaxLength(25);
			this.email.setAlignment(Qt.AlignmentFlag.AlignRight);
			this.email.setValidator(qvRegexMail);
			
			/* Settings for phone */
			this.phone.setEnabled(true);
			this.phone.setMaxLength(8);
			this.phone.setAlignment(Qt.AlignmentFlag.AlignRight);
			this.phone.setValidator(qvRegexPhone);	
			initLayout();
	    }
	    // f� inn til databasen, med generering av ID
	    public boolean validInput(){
	    	return false;	    	
	    }

	    public void main(String[] args) {
	        QApplication.initialize(args);
	        new RegisterUser();
	        QApplication.exec();
	    }
	}
		
	}


/* EOF */
