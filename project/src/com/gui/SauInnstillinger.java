package com.gui;

/* Interface for listener...? */

import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class SauInnstillinger extends QWidget
{
	public static final String CLASS_ICON = "./icons/alert.png";
	
	private QGroupBox qgbUpdateGroup;
	private QCheckBox qcbSystemCheckBox;
	private QCheckBox qcbAppsCheckBox;
	private QCheckBox qcbDocsCheckBox;
	
	private QGroupBox qgbPackageGroup;
	
	private QListWidget qlwPackageList;
	private QListWidgetItem qlwiQtItem;
	private QListWidgetItem qlwiQsaItem;
	private QListWidgetItem qlwiTeamBuilderItem;
	
	private QPushButton qpbBtnAlarm;
	
    public SauInnstillinger(QWidget parent) 
    {
        super(parent);

        initCheckBox(); 
        initComboList();
        initWidgets();  
        initConnectEvents();
        initLayout();
    }
    
    @SuppressWarnings("unused")
    private void initConnectEvents()
    {
    	this.qpbBtnAlarm.clicked.connect(this, "dispatchAlarm()");
    }
    
    
    @SuppressWarnings("unused")
	private void dispatchAlarm()
    {
    	System.out.println("BEEP");
    }
    
    private void initWidgets()
    {
    	this.qpbBtnAlarm = new QPushButton(tr("Simuler alarm"));
    }        
    private void initComboList()
    {
    	this.qgbPackageGroup = new QGroupBox(tr("Existing packages"));
    	this.qlwPackageList = new QListWidget();
    	this.qlwiQtItem = new QListWidgetItem(qlwPackageList);
    	this.qlwiQsaItem = new QListWidgetItem(qlwPackageList);
    	this.qlwiTeamBuilderItem = new QListWidgetItem(qlwPackageList);
    	
    	this.qlwiQtItem.setText(tr("Qt"));    	
    	this.qlwiQsaItem.setText(tr("QSA"));    	
    	this.qlwiTeamBuilderItem.setText(tr("Teambuilder"));
    }
    
    private void initLayout()
    {
    	QVBoxLayout qbvSauInnstillingsLayout = new QVBoxLayout();
    	QVBoxLayout qvbMainLayout 			 = new QVBoxLayout();
    	QVBoxLayout qvbPackageLayout 		 = new QVBoxLayout();        	
    	
        qbvSauInnstillingsLayout.addWidget(qcbSystemCheckBox);
        qbvSauInnstillingsLayout.addWidget(qcbAppsCheckBox);
        qbvSauInnstillingsLayout.addWidget(qcbDocsCheckBox);
        
        qvbPackageLayout.addWidget(qlwPackageList);

        qvbMainLayout.addWidget(qgbUpdateGroup);
        qvbMainLayout.addWidget(qgbPackageGroup);
        qvbMainLayout.addWidget(qpbBtnAlarm);
        qvbMainLayout.addSpacing(12);
        qvbMainLayout.addStretch(1);
        
        this.qgbUpdateGroup .setLayout(qbvSauInnstillingsLayout);
        this.qgbPackageGroup.setLayout(qvbPackageLayout);
        super			    .setLayout(qvbMainLayout);
    }
    
    private void initCheckBox()
    {
    	/* - Title */
        qgbUpdateGroup = new QGroupBox(tr("Ved en alarm.."));
        
        /* - Checkboxes */
        qcbSystemCheckBox = new QCheckBox(tr("Send SMS"));
        qcbAppsCheckBox   = new QCheckBox(tr("Send mail"));
        qcbDocsCheckBox   = new QCheckBox(tr("Ring p� telefon"));        	
    }

} /* End Class */
