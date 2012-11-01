package com.gui;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QFormLayout;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QLineEdit.EchoMode;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QSpacerItem;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

//initialize fields
public class LoginWindowWidget extends QWidget{
	private QGridLayout gridLayout;
    private QVBoxLayout verticalLayout;
    private QFormLayout formLayout;
    private QLabel label;
    private QLineEdit txtEditUsername;
    private QLabel label_2;
    private QLineEdit txtEditPassword;
    private QSpacerItem verticalSpacer_2;
    private QGridLayout gridLayouButton;
    private QPushButton btnLogin;
    private QSpacerItem verticalSpacer;
    
    /* Signals */
    public Signal2<String, String> tryLogin;
	
    
    public LoginWindowWidget() { 
    	super(); 
  
    	tryLogin = new Signal2<String, String>();
    	
    	QWidget Form = this;
        
    	Form.setObjectName("Form");
        Form.resize(new QSize(779, 583).expandedTo(Form.minimumSizeHint()));
        
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Preferred, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(Form.sizePolicy().hasHeightForWidth());
        
        Form.setSizePolicy(sizePolicy);
        Form.setLayoutDirection(com.trolltech.qt.core.Qt.LayoutDirection.LeftToRight);
        
        gridLayout = new QGridLayout(Form);
        gridLayout.setObjectName("gridLayout");
        gridLayout.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetDefaultConstraint);
        
        verticalLayout = new QVBoxLayout();
        verticalLayout.setObjectName("verticalLayout");
        verticalLayout.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetDefaultConstraint);
        
        formLayout = new QFormLayout();
        formLayout.setObjectName("formLayout");
        formLayout.setFieldGrowthPolicy(com.trolltech.qt.gui.QFormLayout.FieldGrowthPolicy.FieldsStayAtSizeHint);
        formLayout.setFormAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignHCenter,com.trolltech.qt.core.Qt.AlignmentFlag.AlignTop));
        
        label = new QLabel(Form);
        label.setObjectName("label");

        formLayout.addWidget(label);

        txtEditUsername = new QLineEdit(Form);
        txtEditUsername.setObjectName("txtEditUsername");

        formLayout.addWidget(txtEditUsername);

        label_2 = new QLabel(Form);
        label_2.setObjectName("label_2");

        formLayout.addWidget(label_2);

        txtEditPassword = new QLineEdit(Form);
        txtEditPassword.setObjectName("txtEditPassword");
        txtEditPassword.setEchoMode(EchoMode.Password);
        
        formLayout.addWidget(txtEditPassword);

        verticalLayout.addLayout(formLayout);

        gridLayout.addLayout(verticalLayout, 1, 0, 1, 1);

        verticalSpacer_2 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer_2, 0, 0, 1, 1);

        gridLayouButton = new QGridLayout();
        gridLayouButton.setSpacing(6);
        gridLayouButton.setObjectName("gridLayouButton");
        gridLayouButton.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetDefaultConstraint);
        
        btnLogin = new QPushButton(Form);
        btnLogin.setObjectName("btnLogin");
        btnLogin.setEnabled(true);
        btnLogin.clicked.connect(this, "loginCheck()");
        
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Fixed);
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(btnLogin.sizePolicy().hasHeightForWidth());
        
        btnLogin.setSizePolicy(sizePolicy1);
        btnLogin.setMinimumSize(new QSize(0, 0));
        btnLogin.setMaximumSize(new QSize(100, 16777215));
        btnLogin.setLayoutDirection(com.trolltech.qt.core.Qt.LayoutDirection.LeftToRight);

        gridLayouButton.addWidget(btnLogin, 0, 0, 1, 1);


        gridLayout.addLayout(gridLayouButton, 3, 0, 1, 1);

        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer, 6, 0, 1, 1);

        retranslateUi(Form);

        Form.connectSlotsByName();
    } // setupUi
    
    
    void retranslateUi(QWidget Form)
    {
        Form.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Form", "Form", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "Username", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "Password", null));
        btnLogin.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "Login", null));
    } // retranslateUi
  
   
    /**
     * Key event(Enter) for loginCheck
     */
    public void keyPressEvent(QKeyEvent event){
    	if (event.key() == Qt.Key.Key_Return.value()){
    		loginCheck();
    	}
    	else{
    	super.keyPressEvent(event);
    	}
   	}
    
    /**
     * A method for checking login autorization
     */
    private void loginCheck(){
    	if(isPassWordAndUsernName(txtEditUsername.text(), txtEditPassword.text())){
    		tryLogin.emit(txtEditUsername.text(), txtEditPassword.text());
    	}
    	else{
    		QMessageBox.critical(this, "login", "Wrong username or password, please try again! " );
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
    private boolean isPassWordAndUsernName(String username, String password){
    	if(username.length() == 0 || password.length() == 0)
    		return false;
    	return true;
    	
    }
}
