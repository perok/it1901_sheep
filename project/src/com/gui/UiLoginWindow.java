/********************************************************************************
 ** Form generated from reading ui file 'untitled.jui'
 **
 ** Created by: Qt User Interface Compiler version 4.7.1
 **
 ** WARNING! All changes made in this file will be lost when recompiling ui file!
 ********************************************************************************/

package com.gui;

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
    public QLineEdit txtEditUsername;
    public QLabel label_2;
    public QLineEdit txtEditPassword;
    public QGridLayout gridLayout_2;
    public QPushButton btnLogin;
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

        txtEditUsername = new QLineEdit(centralwidget);
        txtEditUsername.setObjectName("txtEditUsername");

        formLayout.addRow(label, txtEditUsername);

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


        gridLayout.addLayout(gridLayout_2, 3, 0, 1, 1);

        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer, 0, 0, 1, 1);

        verticalSpacer_2 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer_2, 4, 0, 1, 1);

        MainWindow.setCentralWidget(centralwidget);
        retranslateUi(MainWindow);

        
        btnLogin.clicked.connect(this, "loginCheck()");
        
        MainWindow.connectSlotsByName();
        
        
        
    } // setupUi

    void retranslateUi(@SuppressWarnings("unused") QMainWindow MainWindow)
    {
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Username", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Password", null));
        btnLogin.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Login", null));
    } // retranslateUi
    
    /**
     * A method for checking login autorization
     */
    public void loginCheck(){
    	if(isPassWordAndUsernName(txtEditUsername.text(), txtEditPassword.text())){
    		signalTryLogin.emit(txtEditUsername.text(), txtEditPassword.text());
    	}
    	else{
    		System.out.println("Input correct shiet mafacka");
    		txtEditPassword.setText(null);
    		txtEditPassword.setFocus();
    	}
    }
    
    /**
     * First we check if username and password != null, then we dont need to do the sql shit
     * 
     * @param username
     * @param password
     * @return
     */
    private static boolean isPassWordAndUsernName(String username, String password){
    	if(username.length() == 0 || password.length() == 0)
    		return false;
    	return true;
    	
    }

	@Override
	public void setupUi(QMainWindow arg0) {
		setupUi(arg0, 800, 800);
		
	}

}

