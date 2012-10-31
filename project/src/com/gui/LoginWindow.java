package com.gui; 

//Fikse qboxlayout til Qlineedit, og strings.

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.AlignmentFlag;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLayout;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QLineEdit.EchoMode;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

//initialize fields
public class LoginWindow extends QWidget{
    QLabel label;
    QPushButton login, cancel;
    QLineEdit userName, passWord;
   
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
    }
    private void exit(){
    	System.exit(0);
    }
   
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
    		
    	if(isPassWordAndUsernName(userName.text(), passWord.text())){
    		new LoginWindow(null);
    	}
    	else{
    		QMessageBox.critical(this, "login", "Wrong username or password, please try again! " );
    		passWord.setText(null);
    		passWord.setFocus();
    	}
    }
    public static void main(String[] args)  {
        QApplication.initialize(args);
        new LoginWindow(null);
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
}
