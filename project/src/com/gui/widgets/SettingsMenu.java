package com.gui.widgets;

import java.util.List;
import java.util.ArrayList;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt.AlignmentFlag;
import com.trolltech.qt.core.Qt.ItemFlag;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QListView;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QStackedWidget;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/** Dialog for settings. Used as a host for widgets 
 * that display setting-options for the user.
 * 
 * @author Gruppe 10
 */
public class SettingsMenu extends QDialog
{
	public static final String CLASS_ICON = "./icons/sheep.png";
	
	private AlertSettings asAlertWidget;
	private UserSettings usUserWidget;

	private List<DyanmicComponentHost> lDynamicComponents = new ArrayList<DyanmicComponentHost>();

	private QListWidget qlwParentcontentsWidget;
    private QStackedWidget qswPagesWidget;
    private QPushButton qpbCloseButton;
     
    /** Constructor. Initialize
     *
     * @param parent host of this window. Can be set to null as this is popup dialog window.
     */
    public SettingsMenu(QWidget parent)
	{
	    super(parent);
	    
	    /* Initialize */
	    initWidgets();
	    initConnectEvents();
	    initLayout();
	    initIcons();
	    
	    /* Set properties of THIS */
	    super.setWindowTitle(tr("Innstillinger"));
	    super.setWindowIcon(new QIcon(CLASS_ICON));
	}
    
    /** The configuration dialog holds different widgets.
     * This function can be used to change between them.
     * 
     * @param current the widget we wish to focus
     * @param previous deprecated.
     */
	protected void changePage(QListWidgetItem current, QListWidgetItem previous)
    {
        this.qswPagesWidget.setCurrentIndex(this.qlwParentcontentsWidget.row(current));
    }

	@SuppressWarnings("unused")
	/** If anything has been modified, this is were it is handled.
	 */
	private void checkForChange()
	{
		this.usUserWidget.writeChange();
		System.out.println("uh oh, V-E-R-N, uh oh");
		/*-
		 *  For each of modifiable input *
		 *  do
		 *  		isModified && writeChange
		 * 	done
		 */	
	}

	/** Initialize event-driven actions
	 */
    private void initConnectEvents()
    {
    	this.qpbCloseButton.clicked.connect(this, "checkForChange()");
    	this.qpbCloseButton.clicked.connect(this, "close()");
    	this.qlwParentcontentsWidget.currentItemChanged.connect(this,
	            "changePage(QListWidgetItem , QListWidgetItem)");
    }
    
    
    /** Set the icons of the different configuration widgets
     */
    private void initIcons() 
	{
    	QListWidgetItem qlwiBtnIconUser = new QListWidgetItem(this.qlwParentcontentsWidget);
    	QListWidgetItem qlwiBtnIconApp  = new QListWidgetItem(this.qlwParentcontentsWidget);
	    
    	/* For user-settings */
	    qlwiBtnIconUser.setIcon(new QIcon(UserSettings.CLASS_ICON));
	    qlwiBtnIconUser.setText(tr("Bruker"));
	    qlwiBtnIconUser.setTextAlignment(AlignmentFlag.AlignHCenter.value());
	    qlwiBtnIconUser.setFlags(ItemFlag.ItemIsSelectable, ItemFlag.ItemIsEnabled);
	
	    /* For alert-settings */
	    qlwiBtnIconApp.setIcon(new QIcon(AlertSettings.CLASS_ICON));
	    qlwiBtnIconApp.setText(tr("Applikasjon"));
	    qlwiBtnIconApp.setTextAlignment(AlignmentFlag.AlignHCenter.value());
	    qlwiBtnIconApp.setFlags(ItemFlag.ItemIsSelectable, ItemFlag.ItemIsEnabled);
	}

    /** Initialize the layouts of THIS.
     * Note that each of the widgets we're using as components have their own layouts.
     */
	private void initLayout()
	{
		 QHBoxLayout qhblButtonsLayout 	  = new QHBoxLayout();
		 QHBoxLayout qhblHorizontalLayout = new QHBoxLayout();
		 QVBoxLayout qvblMainLayout 	  = new QVBoxLayout();
		 
	     qhblHorizontalLayout.addWidget(this.qlwParentcontentsWidget);
	     qhblHorizontalLayout.addWidget(this.qswPagesWidget, 1);
	     
	     qhblButtonsLayout.addStretch(1);
	     qhblButtonsLayout.addWidget(qpbCloseButton);
	
	     qvblMainLayout.addStretch(1);
	     qvblMainLayout.addLayout(qhblHorizontalLayout);
	     qvblMainLayout.addLayout(qhblButtonsLayout);
	     qvblMainLayout.addSpacing(12);
	     
	     super.setLayout(qvblMainLayout);
	}

	/** Initialize widgets of THIS
	 */
	private void initWidgets()
    {
		this.asAlertWidget = new AlertSettings(this);
		this.usUserWidget = new UserSettings(this);
        this.qpbCloseButton = new QPushButton(tr("Close"));
    	this.qlwParentcontentsWidget = new QListWidget(this);
    	this.qswPagesWidget = new QStackedWidget(this);
    	
    	this.qlwParentcontentsWidget.setCurrentRow(0);
        this.qlwParentcontentsWidget.setIconSize(new QSize(96, 84));
        this.qlwParentcontentsWidget.setMaximumWidth(128);
        this.qlwParentcontentsWidget.setMovement(QListView.Movement.Static);
        this.qlwParentcontentsWidget.setSpacing(12);
        this.qlwParentcontentsWidget.setViewMode(QListView.ViewMode.IconMode);

        this.qswPagesWidget.addWidget(this.usUserWidget);
        this.qswPagesWidget.addWidget(this.asAlertWidget);
        
        this.lDynamicComponents.add(this.asAlertWidget);
        this.lDynamicComponents.add(this.usUserWidget);
    }
	    
//	/** For test and debugging purposes
//	 * 
//	 * @see MainWindow.main(args)
//	 * @param args arguments to pass over for QApplication
//	 */
//    public static void main(String args[])
//    {
//        QApplication.initialize(args);
//
//        SettingsMenu dialog = new SettingsMenu(new MainWindow(null));
//        dialog.show();
//
//        QApplication.exec();
//    }
}

/* EOF */