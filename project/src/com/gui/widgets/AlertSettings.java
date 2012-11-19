package com.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QStyleFactory;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

import core.classes.User;

/** A widget that show settings for sheep alerts
 * 
 * @author Gruppe 10
 *
 */
public class AlertSettings extends QWidget implements InputComponentHost
{
	public static final String CLASS_ICON = "./icons/alert.png";
	
	private List<ComponentConnector> lComponents = new ArrayList<ComponentConnector>();
	
	private QGroupBox qgbStyleGroup,	
					  qgbUpdateGroup,
					  qgbTOAlert;
	private QCheckBox qcbSmsCheckbox,
					  qcbMailCheckbox,
					  qcbCallCheckbox;
	private QCheckBox qcbMapSetting1;
	private QListWidget qlwQtStyleList;
	
	
	/** Constructor. Initialize..
	 * 
	 * @param parent host of THIS
	 * @param lThemes a list of strings with the currently availible themes
	 */
    public AlertSettings(SettingsMenu parent) 
    {
        super(parent);

        initCheckBox(); 
        initThemeList();
        initMapSettings();
        initWidgets();  
        initConnectEvents();
        initLayout();
        
        //TODO: add inputcomponents here
    }
    
    private void initMapSettings()
    {
    	this.qgbTOAlert = new QGroupBox(tr("Varsler fra Map"));
        this.qcbMapSetting1 = new QCheckBox(tr("Setting 1"));
    }
    
    @SuppressWarnings("unused")
    /** When the user asks for it, change the theme.
     * The new theme is the current item in the selection box
     */
    private void updateTheme()
    {
    	String sSelectedText = this.qlwQtStyleList.currentItem().text();
    	
    	try
    	{
    		QApplication.setStyle(sSelectedText);
    	}
    	catch(Throwable t)
    	{
    		System.out.println("Attempt to set new theme failed");
    	}
    }
    
    /** Initialize event-driven actions
     */
    private void initConnectEvents()
    {
    	this.qlwQtStyleList.currentItemChanged.connect(this, "updateTheme()");
    	
    }
        
    /** Initialize the widgets belonging to THIS
     */
    private void initWidgets()
    {
    	//TODO: init all "standalone" widgets here
    }        
    
    /** Initialize the theme selector
     */
    private void initThemeList()
    {
    	String sCurrentStyle = QApplication.style().objectName();
    	this.qgbStyleGroup = new QGroupBox(tr("Applikasjons-stil"));
    	this.qlwQtStyleList = new QListWidget();
    	
    	/* For each of passed arguments */
    	for(String s : QStyleFactory.keys())
    	{
    		/* Add an item to the box, and set the text given by arg */
    		QListWidgetItem cur = new QListWidgetItem(this.qlwQtStyleList);
    		cur.setText(s);
    		
    		/* If one of the string matches the current theme, 
    		 * set it to the currently selected item */
    		if(s.toLowerCase().equalsIgnoreCase(sCurrentStyle))
    		{
    			this.qlwQtStyleList.setCurrentItem(cur);
    		}
    	}
    }
    
    /** Initialize the layout of THIS
     */
    private void initLayout()
    {
    	/** The layout for sheep alerts */
    	QVBoxLayout qbvSheepSettingsLayout   = new QVBoxLayout();
    	/** The main layout, holding all sub-layouts to THIS */
    	QVBoxLayout qvblMainLayout 			 = new QVBoxLayout();
    	/** The layout for the theme manager */
    	QVBoxLayout qvbThemeLayout 		 = new QVBoxLayout();
    	QVBoxLayout qvbMapSettingsLayout = new QVBoxLayout();
    	
        qbvSheepSettingsLayout.addWidget(this.qcbSmsCheckbox);
        qbvSheepSettingsLayout.addWidget(this.qcbMailCheckbox);
        qbvSheepSettingsLayout.addWidget(this.qcbCallCheckbox);
        
        qvbMapSettingsLayout.addWidget(this.qcbMapSetting1);        
        
        qvbThemeLayout.addWidget(this.qlwQtStyleList);

        qvblMainLayout.addWidget(this.qgbUpdateGroup);
        qvblMainLayout.addWidget(this.qgbStyleGroup);
        qvblMainLayout.addWidget(this.qgbTOAlert);
        qvblMainLayout.addSpacing(12);
        qvblMainLayout.addStretch(1);
        
        this.qgbUpdateGroup .setLayout(qbvSheepSettingsLayout);
        this.qgbStyleGroup.setLayout(qvbThemeLayout);
        this.qgbTOAlert		.setLayout(qvbMapSettingsLayout);
        super			    .setLayout(qvblMainLayout);
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

	@Override
	public void writeChange() 
	{
		for(ComponentConnector cc : this.lComponents)
		{
			cc.writeChanges();
		}	
	}

}

/* EOF */