package com.gui; 

//Fikse qboxlayout til Qlineedit, og strings.

import com.net.ClientSocket;
import core.settings.Settings;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.AlignmentFlag;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QFormLayout;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLayout;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QLineEdit.EchoMode;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QSpacerItem;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

//initialize fields
public class LoginWindow extends QWidget{
	public QGridLayout gridLayout;
    public QVBoxLayout verticalLayout;
    public QFormLayout formLayout;
    public QLabel label;
    public QLineEdit txtEditUsername;
    public QLabel label_2;
    public QLineEdit txtEditPassword;
    public QSpacerItem verticalSpacer_2;
    public QGridLayout gridLayouButton;
    public QPushButton btnLogin;
    public QSpacerItem verticalSpacer;
	/*
    QLabel label;
    QPushButton login, cancel;
    QLineEdit userName, passWord;
   /*
    public LoginWindow(QWidget parent) {
    	
    	super(parent);
    	setFocusPolicy(Qt.FocusPolicy.StrongFocus);
    	
        setWindowTitle("Login Window");
        initUI();  
        
        resize(800, 600);
        move(300, 50);
        show();
    }
    
    private void initUI() {
    	
        QVBoxLayout vbox = new QVBoxLayout(this);
        QHBoxLayout hbox = new QHBoxLayout();
        QGridLayout textBox = new QGridLayout();
        
        login = new QPushButton("Login", this);
        cancel = new QPushButton("Cancel", this);

        hbox.addWidget(login, 1, Qt.AlignmentFlag.AlignRight);
        hbox.addWidget(cancel);

        vbox.addStretch(1);
        vbox.addLayout(hbox);
        
        //Makes 2 textbox for input
        userName = new QLineEdit(this);
        passWord = new QLineEdit(this);
        userName.move(350, 160);
        passWord.move(350, 200);
        //set password textbox to show "*" instead of letters
        passWord.setEchoMode(EchoMode.Password);
        
        //textBox.addWidget(userName, 0, 0);
        //textBox.addWidget(passWord, 0, 1);
        
        //textBox.addLayout(textBox, 0, 0); 
        
        
        String Username;
        String usernameText = "Username:";
        label = new QLabel(usernameText, this);
        label.setFont(new QFont("Snoofer" , 12));
        label.move(270, 160);
        //String Password
        String passwordText = "Password:";
        label = new QLabel(passwordText,this);
        label.setFont(new QFont("Snoofer" , 12));
        label.move(274, 200);
        //push button events
        cancel.clicked.connect(this, "exit()");
        login.clicked.connect(this, "loginCheck()");
    }*/
    
    public LoginWindow() { 
    	super(); 
    	
    	//Remove this when integrated in the application
    	//It has a main function
    	setFocusPolicy(Qt.FocusPolicy.StrongFocus);
    	
        setWindowTitle("Login Window");
        setupUi();  
        
        resize(800, 600);
        move(300, 50);
        show();	
    
    }

    public void setupUi()//QWidget Form)
    {
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
        Form.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Form", "Login Window", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "Username", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "Password", null));
        btnLogin.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "Login", null));
    } // retranslateUi
  
   
    //Key event(Enter) for loginCheck
    public void keyPressEvent(QKeyEvent event){
    	if (event.key() == Qt.Key.Key_Return.value()){
    		loginCheck();
    	}
    	else{
    	super.keyPressEvent(event);
    	}
   	}
    //A method for checking login autorization
    private void loginCheck(){
    		
    	if(isPassWordAndUsernName(txtEditUsername.text(), txtEditPassword.text())){
    		new LoginWindow();
    	}
    	else{
    		QMessageBox.critical(this, "login", "Wrong username or password, please try again! " );
    		txtEditPassword.setText(null);
    		txtEditPassword.setFocus();
    	}
    }
    public static void main(String[] args)  {
        QApplication.initialize(args);
        new LoginWindow();
        QApplication.exec();
    }
    
    //fikse dette mot nettverk Spørring!
    //First we check if username and password != null, then we dont need to do the sql shit
    private boolean isPassWordAndUsernName(String username, String password){
    	if(username.length() == 0 || password.length() == 0)
    		return false;
    	if(username.equals("Rugel") && password.equals("sheep"))
    		return true;
    	return false;
    	
    }
    /*
public class LoginWindow {
	
	String username;
	String password;
	
	ClientSocket connector = new ClientSocket(new Settings(),username);
	
	/**Sends login request to the server with username and password.
	 * 
	 * @param username
	 * @param password
	 */
    /*
	public void loginUser(String username, String password){
		connector.login(username, password);
	}*/
}
