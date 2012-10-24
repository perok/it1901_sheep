package com.gui;

import java.util.Arrays;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.Qt.Alignment;
import com.trolltech.qt.core.Qt.AlignmentFlag;
import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QDateEdit;
import com.trolltech.qt.gui.QDoubleSpinBox;
import com.trolltech.qt.gui.QFormLayout;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QTabBar;
import com.trolltech.qt.gui.QTabWidget;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import com.trolltech.qt.gui.QWidget;

/** Hold statistics
 * 
 * @author Gruppe 10 <3
 *
 */
public class StatisticsWidget extends QWidget 
{
	private QHBoxLayout qhbMainLayout;
	
	/*Table*/
	private QTableWidget table = null;
	private QTableWidgetItem item = null;
	/*Tab*/
	private QTabWidget qTab = null;
	private QWidget qTab1 = null;
	private QWidget qTab2 = null;
	
	private QWidget formLayoutWidget;
	
	/*Tab1*/
    private QGroupBox gRBSheep;
    private QFormLayout formLayout;
    private QLabel txtBirthdate;
    private QDateEdit dEBirthdate;
    private QLabel txtWeight;
    private QDoubleSpinBox dSBWeight;
    private QLabel txtAlive;
    private QCheckBox cbAlive;
    private QLabel txtFarm;
    private QLineEdit lEFarm;
    
    /*Tab2*/
    private QComboBox cmbShowMessageType;
    
    /* Layouts */
    
    private QGridLayout qGLTab2 = null;
    private QGridLayout qGLTab1 = null;
	
	
	/** Constructor. Initialize..
	 */
	public StatisticsWidget()
	{
		this.setWindowTitle("Statistics");
		
		initLayout();
		initWidgets();
		
		addSheeps();
	}
	
	
	/** Initialize the main layout of THIS
	 */
	private void initLayout()
	{
		this.qhbMainLayout = new QHBoxLayout();
		
		super.setLayout(qhbMainLayout);
	}
	
	/** Initialize widgets in the current object context
	 */
	private void initWidgets()
	{
		this.setObjectName("qStatWidget");
		
		/*Set up tab & tabs*/
        qTab = new QTabWidget(this);
        qTab.setObjectName("qTab");
        
        //qTab.setGeometry(new QRect(10, 20, 421, 271));
        qTab.setAutoFillBackground(false);
		
        qTab1 = new QWidget();
        qTab1.setObjectName("qTab1");
        
        qTab2 = new QWidget();
        qTab2.setObjectName("qTab2");
        
        

        
        qTab.addTab(qTab1, com.trolltech.qt.core.QCoreApplication.translate("Form", "Information", null));
        qTab.addTab(qTab2, com.trolltech.qt.core.QCoreApplication.translate("Form", "Messages", null));
        
        
        
        /*
         * Tab 1
         * 
         */
        
        //gRBSheep = new QGroupBox(qTab1);
        //gRBSheep.setObjectName("gRBSheep");
        
        qGLTab1 = new QGridLayout(qTab1);
        qGLTab1.setObjectName("gridLayout");
        
        
        formLayout = new QFormLayout(formLayoutWidget);
        formLayout.setFieldGrowthPolicy(com.trolltech.qt.gui.QFormLayout.FieldGrowthPolicy.AllNonFixedFieldsGrow);
        formLayout.setLabelAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignLeft,com.trolltech.qt.core.Qt.AlignmentFlag.AlignVCenter));
        formLayout.setFormAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignLeft,com.trolltech.qt.core.Qt.AlignmentFlag.AlignVCenter));
        formLayout.setObjectName("formLayout");
        
        txtBirthdate = new QLabel(formLayoutWidget);
        txtBirthdate.setObjectName("txtBirthdate");


        dEBirthdate = new QDateEdit(formLayoutWidget);
        dEBirthdate.setObjectName("dEBirthdate");

        formLayout.addRow(txtBirthdate, dEBirthdate);

        txtWeight = new QLabel(formLayoutWidget);
        txtWeight.setObjectName("txtWeight");



        dSBWeight = new QDoubleSpinBox(formLayoutWidget);
        dSBWeight.setObjectName("dSBWeight");

        formLayout.addRow(txtWeight, dSBWeight);

        txtAlive = new QLabel(formLayoutWidget);
        txtAlive.setObjectName("txtAlive");


        cbAlive = new QCheckBox(formLayoutWidget);
        cbAlive.setObjectName("cbAlive");

        formLayout.addRow(txtAlive, cbAlive);

        txtFarm = new QLabel(formLayoutWidget);
        txtFarm.setObjectName("txtFarm");


        lEFarm = new QLineEdit(formLayoutWidget);
        lEFarm.setObjectName("lEFarm");

        formLayout.addRow(txtFarm, lEFarm);
        
        qGLTab1.addLayout(formLayout, 0, 0, 1, 1);
        
        /*
         * Tab 2
         * 
         */
        
        qGLTab2 = new QGridLayout(qTab2);
        qGLTab2.setObjectName("gridLayout_2");
        
        cmbShowMessageType = new QComboBox(qTab2);
        cmbShowMessageType.setObjectName("cmbShowMessageType");
        qGLTab2.addWidget(cmbShowMessageType, 0, 0, 1, 1);
        
		table = new QTableWidget(qTab2);
		table.setObjectName("qTableWidget");
        
        
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);
        sizePolicy1.setHorizontalStretch((byte)1);
        sizePolicy1.setVerticalStretch((byte)1);
        sizePolicy1.setHeightForWidth(table.sizePolicy().hasHeightForWidth());
        table.setSizePolicy(sizePolicy1);
        
		
		
		/* Horizontal headers */
		String[] list = {"Message #","Message Date", "Sheep #", "Farm #", "Name", "Birthdate","Alive", "Weight", "Location"};
		table.setColumnCount(list.length);
		table.setHorizontalHeaderLabels(Arrays.asList(list));
		
		/* We don't need the vertical header */
		table.verticalHeader().setVisible(false);
		
		/* Rezise rows and columns to needed size */
		table.resizeRowsToContents();
		table.resizeColumnsToContents();
		
		/* Stretch the vertical headers last element */
		//table.verticalHeader().setStretchLastSection(true);
		table.horizontalHeader().setStretchLastSection(true);
		
		/* Define the size */
		//table.setMinimumHeight(20);
		
		/* Add it*/
		//qTab
		//qhbMainLayout.addWidget(table);
		qGLTab2.addWidget(table, 1, 0, 1, 1);
		
		
        qhbMainLayout.addWidget(qTab);
        this.connectSlotsByName();
        
        txtBirthdate.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "Birthdate", null));
        txtWeight.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "Weight", null));
        txtAlive.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "Alive", null));
        cbAlive.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "No", null));
        txtFarm.setText(com.trolltech.qt.core.QCoreApplication.translate("Form", "Farm #", null));
		
	}
	
	/**
	 * Adds sheep information to the current window
	 * 
	 * TODO: Hook up to SHeeListWidget
	 * TODO: How should this look?
	 * 
	 * @param ArrayList<Sheep> sheep
	 */
	public void addSheeps(){
		
		//this.setWindowTitle("Statistics: " + name);
		
		table.setRowCount(6);
		
		for(int i = 0; i < 6; i++) {
		 	for(int j = 0; j < table.columnCount(); j++) {
		      item = new QTableWidgetItem("Test value");
		      table.setItem(i, j, item);
		   }
		}
		
		table.resizeRowsToContents();
		table.resizeColumnsToContents();
		
	}
}

/* EOF */