package com.gui;

import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/** A widget that show settings for sheep alerts
 * 
 * @author Gruppe 10
 *
 */
public class AlertSettings extends QWidget
{
	public static final String CLASS_ICON = "./icons/alert.png";
	
	private QGroupBox qgbUpdateGroup;
	private QCheckBox qcbSmsCheckbox;
	private QCheckBox qcbMailCheckbox;
	private QCheckBox qcbCallCheckbox;
	
	private QGroupBox qgbPackageGroup;
	
	private QListWidget qlwPackageList;
	private QListWidgetItem qlwiQtItem;
	private QListWidgetItem qlwiQsaItem;
	private QListWidgetItem qlwiTeamBuilderItem;
	
	private QPushButton qpbBtnAlarm;
	
	/** Constructor. Initialize..
	 * 
	 * @param parent host of THIS
	 */
    public AlertSettings(QWidget parent) 
    {
        super(parent);

        initCheckBox(); 
        initPackageList();
        initWidgets();  
        initConnectEvents();
        initLayout();
    }
    
    /** Initialize event-driven actions
     */
    private void initConnectEvents()
    {
    	this.qpbBtnAlarm.clicked.connect(this, "dispatchAlarm()");
    }
    
    @SuppressWarnings("unused")
    /** Function used to call for an alarm
     */
	private void dispatchAlarm()
    {
    	System.out.println("BEEP");
    }
    
    /** Initialize the widgets belonging to THIS
     */
    private void initWidgets()
    {
    	this.qpbBtnAlarm = new QPushButton(tr("Simuler alarm"));
    }        
    
    /** Initialize */
    private void initPackageList()
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
    
    /** Initialize the layout of THIS
     */
    private void initLayout()
    {
    	/** The layout for sheep alerts */
    	QVBoxLayout qbvSheepSettingsLayout   = new QVBoxLayout();
    	/** The main layout, holding all sub-layouts to THIS */
    	QVBoxLayout qvbMainLayout 			 = new QVBoxLayout();
    	/** The layout for the package manager */
    	QVBoxLayout qvbPackageLayout 		 = new QVBoxLayout();        	
    	
        qbvSheepSettingsLayout.addWidget(qcbSmsCheckbox);
        qbvSheepSettingsLayout.addWidget(qcbMailCheckbox);
        qbvSheepSettingsLayout.addWidget(qcbCallCheckbox);
        
        qvbPackageLayout.addWidget(qlwPackageList);

        qvbMainLayout.addWidget(qgbUpdateGroup);
        qvbMainLayout.addWidget(qgbPackageGroup);
        qvbMainLayout.addWidget(qpbBtnAlarm);
        qvbMainLayout.addSpacing(12);
        qvbMainLayout.addStretch(1);
        
        this.qgbUpdateGroup .setLayout(qbvSheepSettingsLayout);
        this.qgbPackageGroup.setLayout(qvbPackageLayout);
        super			    .setLayout(qvbMainLayout);
    }
    
    /** Initialize checkboxes-widgets to be used in THIS
     */
    private void initCheckBox()
    {
    	/* - Title */
        this.qgbUpdateGroup = new QGroupBox(tr("Ved en alarm.."));
        
        /* - Checkboxes */
        this.qcbSmsCheckbox = new QCheckBox(tr("Send SMS"));
        this.qcbMailCheckbox   = new QCheckBox(tr("Send mail"));
        this.qcbCallCheckbox   = new QCheckBox(tr("Ring på telefon"));        	
    }

}

/* EOF */