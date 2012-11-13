package alt;

import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QInputDialog;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class BrukerInnstillinger extends QWidget
{
	public static final String CLASS_ICON = "./farmer.png";
	
	private QComboBox qcbFarmCombo;
	private QGroupBox qgbConfigGroup;
	private QGroupBox qgbUserSettings;
	private QLabel qlGaardLabel;
	//private QInputDialog
	
	private int iSelectedCombo = 0;
	
	public BrukerInnstillinger(QWidget parent)
    {
        super(parent);
                    
        initGaardSettings();
        initConnectEvents();
        initLayout();
    }
	
	private void initUserInput()
	{
		
	}
	
	@SuppressWarnings("unused")
	private void farmChanged()
	{
		System.out.println("changed farm to " + this.qcbFarmCombo.currentIndex());
		
		this.iSelectedCombo = this.qcbFarmCombo.currentIndex();
	}
	
	private void initConnectEvents()
	{
		this.qcbFarmCombo.currentIndexChanged.connect(this, "farmChanged()");
	}
	
	private void initGaardSettings()
	{
		this.qgbConfigGroup = new QGroupBox(tr("Gård-innstillinger"));
		this.qlGaardLabel = new QLabel(tr("Gård:"));
        this.qcbFarmCombo = new QComboBox();
        
        this.qcbFarmCombo.addItem(tr("Gård 0"));
        this.qcbFarmCombo.addItem(tr("Gård 1"));
	}
    
    private void initLayout()
    {
    	 QHBoxLayout qhblServerLayout = new QHBoxLayout();
    	 QVBoxLayout qvblConfigLayout = new QVBoxLayout();
    	 QVBoxLayout qvblMainLayout   = new QVBoxLayout();
    	 
         qhblServerLayout.addWidget(qlGaardLabel);
         qhblServerLayout.addWidget(qcbFarmCombo);
         
         qvblConfigLayout.addLayout(qhblServerLayout);
         
         qvblMainLayout.addWidget(qgbConfigGroup);
         qvblMainLayout.addStretch(1);
         
         this.qgbConfigGroup.setLayout(qvblConfigLayout);
         super			    .setLayout(qvblMainLayout);
    }
}