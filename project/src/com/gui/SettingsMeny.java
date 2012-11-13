package alt;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt.AlignmentFlag;
import com.trolltech.qt.core.Qt.ItemFlag;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QListView;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QStackedWidget;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class SettingsMeny extends QDialog
{
	private QListWidget qlwParentcontentsWidget;
    private QStackedWidget qswPagesWidget;
    private QPushButton qpbCloseButton;
      
    public SettingsMeny(QWidget parent)
	{
	    super(parent);
	    
	    initWidget();
	    initConnectEvents();
	    initLayout();
	    initIcons();
	    
	    super.setWindowTitle(tr("Innstillinger"));
	    super.setWindowIcon(new QIcon("./sheep.png"));
	}

	protected void changePage(QListWidgetItem current, QListWidgetItem previous)
    {
        this.qswPagesWidget.setCurrentIndex(this.qlwParentcontentsWidget.row(current));
    }

	@SuppressWarnings("unused")
	private void checkForChange()
	{
		
	}

    private void initConnectEvents()
    {
    	this.qpbCloseButton.clicked.connect(this, "checkForChange()");
    	this.qpbCloseButton.clicked.connect(this, "close()");
    }
    
    
    private void initIcons() 
	{
    	QListWidgetItem qlwiBtnIconUser = new QListWidgetItem(this.qlwParentcontentsWidget);
    	QListWidgetItem qlwiBtnIconApp  = new QListWidgetItem(this.qlwParentcontentsWidget);
	    
	    qlwiBtnIconUser.setIcon(new QIcon(BrukerInnstillinger.CLASS_ICON));
	    qlwiBtnIconUser.setText(tr("Bruker"));
	    qlwiBtnIconUser.setTextAlignment(AlignmentFlag.AlignHCenter.value());
	    qlwiBtnIconUser
	            .setFlags(ItemFlag.ItemIsSelectable, ItemFlag.ItemIsEnabled);
	
	    qlwiBtnIconApp.setIcon(new QIcon(SauInnstillinger.CLASS_ICON));
	    qlwiBtnIconApp.setText(tr("Applikasjon"));
	    qlwiBtnIconApp.setTextAlignment(AlignmentFlag.AlignHCenter.value());
	    qlwiBtnIconApp
	            .setFlags(ItemFlag.ItemIsSelectable, ItemFlag.ItemIsEnabled);
	
	    this.qlwParentcontentsWidget.currentItemChanged.connect(this,
	            "changePage(QListWidgetItem , QListWidgetItem)");
	
	}

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

	private void initWidget()
    {
        this.qpbCloseButton = new QPushButton(tr("Close"));
        
    	this.qlwParentcontentsWidget = new QListWidget(this);
        this.qlwParentcontentsWidget.setViewMode(QListView.ViewMode.IconMode);
        this.qlwParentcontentsWidget.setIconSize(new QSize(96, 84));
        this.qlwParentcontentsWidget.setMovement(QListView.Movement.Static);
        this.qlwParentcontentsWidget.setMaximumWidth(128);
        this.qlwParentcontentsWidget.setSpacing(12);
        this.qlwParentcontentsWidget.setCurrentRow(0);

        this.qswPagesWidget = new QStackedWidget(this);
        this.qswPagesWidget.addWidget(new BrukerInnstillinger(this));
        this.qswPagesWidget.addWidget(new SauInnstillinger(this));
       
    }
    
    public static void main(String args[])
    {
        QApplication.initialize(args);

        SettingsMeny dialog = new SettingsMeny(null);
        dialog.show();

        QApplication.exec();
    }
}

/* EOF */