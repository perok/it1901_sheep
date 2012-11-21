package com.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QStyleFactory;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/** A widget that show settings for sheep alerts
 * 
 * @author Gruppe 10
 *
 */
public class AlertSettings extends QWidget implements InputComponentHost
{
	public static final String CLASS_ICON = "classpath:res/alert.png";
	
	private List<ComponentConnector> lComponents = new ArrayList<ComponentConnector>();
	
	private QGroupBox qgbStyleGroup,	
					  qgbUpdateGroup;
	private QCheckBox qcbSmsCheckbox,
					  qcbMailCheckbox;
	private QCheckBox qcbMapSetting1;
	private QListWidget qlwQtStyleList;
	
	
	/** Constructor. Initialize..
	 * 
	 * @param parent host of THIS
	 * @param lThemes a list of strings with the currently available themes
	 */
    public AlertSettings(SettingsMenu parent) 
    {
        super(parent);

        initCheckBox(); 
        initThemeList();
        initWidgets();  
        initConnectEvents();
        initLayout();
        
        //TODO: add inputcomponents here
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
    
    /** Initialize the theme selector area
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
    	/** The layout for map-settings area */
    	
        qbvSheepSettingsLayout.addWidget(this.qcbSmsCheckbox);
        qbvSheepSettingsLayout.addWidget(this.qcbMailCheckbox);
        
        qvbThemeLayout.addWidget(this.qlwQtStyleList);

        //qvblMainLayout.addWidget(this.qgbUpdateGroup);
        qvblMainLayout.addWidget(this.qgbStyleGroup);
        qvblMainLayout.addSpacing(12);
        qvblMainLayout.addStretch(1);
        
        this.qgbUpdateGroup .setLayout(qbvSheepSettingsLayout);
        this.qgbStyleGroup.setLayout(qvbThemeLayout);
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
    }

	@Override
	/** @see parent of THIS
	 * @see InputComponentHost
	 */
	public void writeChange() 
	{
		/* For all input-components... */
		for(ComponentConnector cc : this.lComponents)
		{
			/* Write the change */
			cc.writeChanges();
		}	
	}

}

/* EOF */