package com.gui;
/********************************************************************************
 ** Form generated from reading ui file 'mainVIEWv2.jui'
 **
 ** Created by: Qt User Interface Compiler version 4.7.1
 **
 ** WARNING! All changes made in this file will be lost when recompiling ui file!
 ********************************************************************************/
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.webkit.QWebView;

public class UiMainWindow implements com.trolltech.qt.QUiForm<QMainWindow>
{
    public QAction actionExit;
    public QAction actionAbout;
    public QAction actionInformation_Window;
    public QAction actionMap;
    public QAction actionUndo;
    public QAction actionAbout_Qt_Jambi;
    public QAction actionSettings;
    public QWidget centralwidget;
    public QGridLayout gridLayout;
    public QSplitter splitter;
    public QWebView MAPWIDGET;
    public QTabWidget tabWidget;
    public QWidget tabInformation;
    public QGridLayout gridLayout_3;
    public QVBoxLayout verticalLayout;
    public QGridLayout gridLayout_4;
    public QFormLayout formLayout_2;
    public QLabel label;
    public QLineEdit lEName;
    public QLabel label_2;
    public QLabel label_3;
    public QLabel label_4;
    public QLineEdit lEFarmId;
    public QLabel label_5;
    public QCheckBox chbAlive;
    public QDoubleSpinBox dSBWeight;
    public QDateEdit dEBirthdaye;
    public QGridLayout gridLayout_8;
    public QSpacerItem horizontalSpacer;
    public QSpacerItem horizontalSpacer_2;
    public QPushButton pbTabInformationUpdate;
    public QPushButton pbTabInformationReset;
    public QWidget tabAddMessages;
    public QGridLayout gridLayout_9;
    public QTableWidget tableWidget;
    public QHBoxLayout horizontalLayoutTabMessages;
    public QLabel lblTabMessages;
    public QSpacerItem horizontalSpacer_4;
    public QComboBox cmbTabMessages;
    public QWidget tabAddSheep;
    public QGridLayout gridLayout_5;
    public QVBoxLayout verticalLayout_3;
    public QGridLayout gridLayout_11;
    public QGridLayout gridLayout_12;
    public QPushButton pBSubmit_Add;
    public QSpacerItem horizontalSpacer_6;
    public QSpacerItem horizontalSpacer_7;
    public QFormLayout formLayout_5;
    public QLabel label_11;
    public QLineEdit lEName_Add_2;
    public QLabel label_12;
    public QDateEdit dEBirthdate_Add;
    public QLabel label_13;
    public QDoubleSpinBox dSBWeight_Add_2;
    public QLabel label_14;
    public QLineEdit lEFar_Add;
    public QLabel label_15;
    public QCheckBox cBAlive_Add;
    public QMenuBar menubar;
    public QMenu menuFile;
    public QMenu menuEdit;
    public QMenu menuView;
    public QMenu menuAbout;
    public QStatusBar statusbar;
    public QDockWidget dockWidget;
    public QWidget dockWidgetContents;
    public QGridLayout gridLayout_2;
    public QListWidget listWidget;
    public QVBoxLayout verticalLayout_4;
    public QHBoxLayout horizontalLayout;
    public QRadioButton rbAscDesc;
    public QSpacerItem horizontalSpacer_3;
    public QComboBox cmbDockFarmId;
    public QLineEdit lineEdit;
    
    private QMainWindow qmw = null;
    
    public QMainWindow getMother() { return this.qmw; } 

    public UiMainWindow() { super(); }

    public void setupUi(QMainWindow MainWindow, int width, int height)
    {
    	this.qmw = MainWindow;
    	
        MainWindow.setObjectName("MainWindow");
        MainWindow.setWindowModality(com.trolltech.qt.core.Qt.WindowModality.NonModal);
        MainWindow.resize(new QSize(width, height).expandedTo(MainWindow.minimumSizeHint()));
        MainWindow.setStyleSheet("Windows");
        MainWindow.setDocumentMode(false);
        MainWindow.setTabShape(com.trolltech.qt.gui.QTabWidget.TabShape.Rounded);
        MainWindow.setDockNestingEnabled(false);
        actionExit = new QAction(MainWindow);
        actionExit.setObjectName("actionExit");
        QFont font = new QFont();
        font.setPointSize(9);
        actionExit.setFont(font);
        actionAbout = new QAction(MainWindow);
        actionAbout.setObjectName("actionAbout");
        QFont font1 = new QFont();
        font1.setPointSize(9);
        actionAbout.setFont(font1);
        actionInformation_Window = new QAction(MainWindow);
        actionInformation_Window.setObjectName("actionInformation_Window");
        actionInformation_Window.setCheckable(true);
        actionInformation_Window.setChecked(true);
        QFont font2 = new QFont();
        font2.setPointSize(9);
        actionInformation_Window.setFont(font2);
        actionMap = new QAction(MainWindow);
        actionMap.setObjectName("actionMap");
        actionMap.setCheckable(true);
        actionMap.setChecked(true);
        QFont font3 = new QFont();
        font3.setPointSize(9);
        actionMap.setFont(font3);
        actionUndo = new QAction(MainWindow);
        actionUndo.setObjectName("actionUndo");
        QFont font4 = new QFont();
        font4.setPointSize(9);
        actionUndo.setFont(font4);
        actionAbout_Qt_Jambi = new QAction(MainWindow);
        actionAbout_Qt_Jambi.setObjectName("actionAbout_Qt_Jambi");
        QFont font5 = new QFont();
        font5.setPointSize(9);
        actionAbout_Qt_Jambi.setFont(font5);
        actionSettings = new QAction(MainWindow);
        actionSettings.setObjectName("actionSettings");
        QFont font6 = new QFont();
        font6.setPointSize(9);
        actionSettings.setFont(font6);
        centralwidget = new QWidget(MainWindow);
        centralwidget.setObjectName("centralwidget");
        gridLayout = new QGridLayout(centralwidget);
        gridLayout.setMargin(1);
        gridLayout.setObjectName("gridLayout");
        splitter = new QSplitter(centralwidget);
        splitter.setObjectName("splitter");
        splitter.setFrameShape(com.trolltech.qt.gui.QFrame.Shape.NoFrame);
        splitter.setOrientation(com.trolltech.qt.core.Qt.Orientation.Vertical);
        splitter.setHandleWidth(4);
        MAPWIDGET = new QWebView(splitter);
        MAPWIDGET.setObjectName("MAPWIDGET");
        MAPWIDGET.setMinimumSize(new QSize(0, 200));
        splitter.addWidget(MAPWIDGET);
        tabWidget = new QTabWidget(splitter);
        tabWidget.setObjectName("tabWidget");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(tabWidget.sizePolicy().hasHeightForWidth());
        tabWidget.setSizePolicy(sizePolicy);
        tabWidget.setTabPosition(com.trolltech.qt.gui.QTabWidget.TabPosition.North);
        tabWidget.setTabShape(com.trolltech.qt.gui.QTabWidget.TabShape.Triangular);
        tabWidget.setDocumentMode(false);
        tabWidget.setTabsClosable(false);
        tabWidget.setMovable(true);
        tabInformation = new QWidget();
        tabInformation.setObjectName("tabInformation");
        gridLayout_3 = new QGridLayout(tabInformation);
        gridLayout_3.setObjectName("gridLayout_3");
        verticalLayout = new QVBoxLayout();
        verticalLayout.setObjectName("verticalLayout");
        gridLayout_4 = new QGridLayout();
        gridLayout_4.setObjectName("gridLayout_4");
        formLayout_2 = new QFormLayout();
        formLayout_2.setObjectName("formLayout_2");
        formLayout_2.setFieldGrowthPolicy(com.trolltech.qt.gui.QFormLayout.FieldGrowthPolicy.FieldsStayAtSizeHint);
        formLayout_2.setFormAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
        
        //TAB 1
        label = new QLabel(tabInformation);
        label.setObjectName("label");

        lEName = new QLineEdit(tabInformation);
        lEName.setObjectName("lEName");
        lEName.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));

        label_2 = new QLabel(tabInformation);
        label_2.setObjectName("label_2");

        label_3 = new QLabel(tabInformation);
        label_3.setObjectName("label_3");

        label_4 = new QLabel(tabInformation);
        label_4.setObjectName("label_4");

        lEFarmId = new QLineEdit(tabInformation);
        lEFarmId.setObjectName("lEFarmId");
        //lEFarmId.setInputMethodHints(com.trolltech.qt.core.Qt.InputMethodHint.createQFlags(com.trolltech.qt.core.Qt.InputMethodHint.ImhUppercaseOnly));
        lEFarmId.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));

        label_5 = new QLabel(tabInformation);
        label_5.setObjectName("label_5");

        chbAlive = new QCheckBox(tabInformation);
        chbAlive.setObjectName("chbAlive");
        chbAlive.setTristate(false);


        dSBWeight = new QDoubleSpinBox(tabInformation);
        dSBWeight.setObjectName("dSBWeight");
        dSBWeight.setMinimumSize(new QSize(178, 0));
        dSBWeight.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));

        dEBirthdaye = new QDateEdit(tabInformation);
        dEBirthdaye.setObjectName("dEBirthdaye");
        dEBirthdaye.setMinimumSize(new QSize(178, 0));
        dEBirthdaye.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
        dEBirthdaye.setCalendarPopup(true);

        //Add tabs
        formLayout_2.addRow(label, lEName);
        formLayout_2.addRow(label_2, dEBirthdaye);
        formLayout_2.addRow(label_3, dSBWeight);
        formLayout_2.addRow(label_4, lEFarmId);
        formLayout_2.addRow(label_5, chbAlive);

        
        
        
        gridLayout_4.addLayout(formLayout_2, 0, 0, 1, 1);

        gridLayout_8 = new QGridLayout();
        gridLayout_8.setObjectName("gridLayout_8");
        horizontalSpacer = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout_8.addItem(horizontalSpacer, 0, 3, 1, 1);

        horizontalSpacer_2 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout_8.addItem(horizontalSpacer_2, 0, 0, 1, 1);

        pbTabInformationUpdate = new QPushButton(tabInformation);
        pbTabInformationUpdate.setObjectName("pbTabInformationUpdate");

        gridLayout_8.addWidget(pbTabInformationUpdate, 0, 1, 1, 1);

        pbTabInformationReset = new QPushButton(tabInformation);
        pbTabInformationReset.setObjectName("pbTabInformationReset");

        gridLayout_8.addWidget(pbTabInformationReset, 0, 2, 1, 1);


        gridLayout_4.addLayout(gridLayout_8, 1, 0, 1, 1);


        verticalLayout.addLayout(gridLayout_4);


        gridLayout_3.addLayout(verticalLayout, 0, 0, 1, 1);

        tabWidget.addTab(tabInformation, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Information", null));
        tabAddMessages = new QWidget();
        tabAddMessages.setObjectName("tabAddMessages");
        gridLayout_9 = new QGridLayout(tabAddMessages);
        gridLayout_9.setMargin(3);
        gridLayout_9.setObjectName("gridLayout_9");
        tableWidget = new QTableWidget(tabAddMessages);
        tableWidget.setObjectName("tableWidget");

        gridLayout_9.addWidget(tableWidget, 2, 0, 1, 1);

        horizontalLayoutTabMessages = new QHBoxLayout();
        horizontalLayoutTabMessages.setObjectName("horizontalLayoutTabMessages");
        lblTabMessages = new QLabel(tabAddMessages);
        lblTabMessages.setObjectName("lblTabMessages");

        horizontalLayoutTabMessages.addWidget(lblTabMessages);

        horizontalSpacer_4 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayoutTabMessages.addItem(horizontalSpacer_4);

        cmbTabMessages = new QComboBox(tabAddMessages);
        cmbTabMessages.setObjectName("cmbTabMessages");
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Preferred, com.trolltech.qt.gui.QSizePolicy.Policy.Fixed);
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(cmbTabMessages.sizePolicy().hasHeightForWidth());
        cmbTabMessages.setSizePolicy(sizePolicy1);
        cmbTabMessages.setMinimumSize(new QSize(0, 0));

        horizontalLayoutTabMessages.addWidget(cmbTabMessages);


        gridLayout_9.addLayout(horizontalLayoutTabMessages, 1, 0, 1, 1);

        tabWidget.addTab(tabAddMessages, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Messages", null));
        tabAddSheep = new QWidget();
        tabAddSheep.setObjectName("tabAddSheep");
        gridLayout_5 = new QGridLayout(tabAddSheep);
        gridLayout_5.setObjectName("gridLayout_5");
        gridLayout_5.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetDefaultConstraint);
        verticalLayout_3 = new QVBoxLayout();
        verticalLayout_3.setObjectName("verticalLayout_3");
        verticalLayout_3.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetNoConstraint);
        gridLayout_11 = new QGridLayout();
        gridLayout_11.setObjectName("gridLayout_11");
        gridLayout_12 = new QGridLayout();
        gridLayout_12.setObjectName("gridLayout_12");
        gridLayout_12.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetDefaultConstraint);
        pBSubmit_Add = new QPushButton(tabAddSheep);
        pBSubmit_Add.setObjectName("pBSubmit_Add");
        QSizePolicy sizePolicy2 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Fixed);
        sizePolicy2.setHorizontalStretch((byte)0);
        sizePolicy2.setVerticalStretch((byte)0);
        sizePolicy2.setHeightForWidth(pBSubmit_Add.sizePolicy().hasHeightForWidth());
        pBSubmit_Add.setSizePolicy(sizePolicy2);

        gridLayout_12.addWidget(pBSubmit_Add, 0, 1, 1, 1);

        horizontalSpacer_6 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout_12.addItem(horizontalSpacer_6, 0, 0, 1, 1);

        horizontalSpacer_7 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout_12.addItem(horizontalSpacer_7, 0, 2, 1, 1);


        gridLayout_11.addLayout(gridLayout_12, 1, 0, 1, 1);

        formLayout_5 = new QFormLayout();
        formLayout_5.setObjectName("formLayout_5");
        formLayout_5.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetDefaultConstraint);
        formLayout_5.setFieldGrowthPolicy(com.trolltech.qt.gui.QFormLayout.FieldGrowthPolicy.FieldsStayAtSizeHint);
        formLayout_5.setFormAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
        label_11 = new QLabel(tabAddSheep);
        label_11.setObjectName("label_11");

        lEName_Add_2 = new QLineEdit(tabAddSheep);
        lEName_Add_2.setObjectName("lEName_Add_2");
        lEName_Add_2.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));

        label_12 = new QLabel(tabAddSheep);
        label_12.setObjectName("label_12");

        dEBirthdate_Add = new QDateEdit(tabAddSheep);
        dEBirthdate_Add.setObjectName("dEBirthdate_Add");
        dEBirthdate_Add.setMinimumSize(new QSize(178, 0));
        dEBirthdate_Add.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
        dEBirthdate_Add.setMinimumDate(new QDate(1752, 9, 25));
        dEBirthdate_Add.setCalendarPopup(true);


        label_13 = new QLabel(tabAddSheep);
        label_13.setObjectName("label_13");

        dSBWeight_Add_2 = new QDoubleSpinBox(tabAddSheep);
        dSBWeight_Add_2.setObjectName("dSBWeight_Add_2");
        dSBWeight_Add_2.setMinimumSize(new QSize(178, 0));
        dSBWeight_Add_2.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));

        label_14 = new QLabel(tabAddSheep);
        label_14.setObjectName("label_14");

        lEFar_Add = new QLineEdit(tabAddSheep);
        lEFar_Add.setObjectName("lEFar_Add");
        lEFar_Add.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));

        label_15 = new QLabel(tabAddSheep);
        label_15.setObjectName("label_15");

        cBAlive_Add = new QCheckBox(tabAddSheep);
        cBAlive_Add.setObjectName("cBAlive_Add");
        
        //TAB2
        formLayout_2.addRow(label_11, lEName_Add_2);
        formLayout_2.addRow(label_12, dEBirthdate_Add);
        formLayout_2.addRow(label_13, dSBWeight_Add_2);
        formLayout_2.addRow(label_14, lEFar_Add);
        formLayout_2.addRow(label_15, cBAlive_Add);


        gridLayout_11.addLayout(formLayout_5, 0, 0, 1, 1);


        verticalLayout_3.addLayout(gridLayout_11);


        gridLayout_5.addLayout(verticalLayout_3, 0, 0, 1, 1);

        tabWidget.addTab(tabAddSheep, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Add Sheep", null));
        splitter.addWidget(tabWidget);

        gridLayout.addWidget(splitter, 0, 0, 1, 1);

        MainWindow.setCentralWidget(centralwidget);
        menubar = new QMenuBar(MainWindow);
        menubar.setObjectName("menubar");
        menubar.setGeometry(new QRect(0, 0, 974, 30));
        menubar.setMinimumSize(new QSize(1, 20));
        QFont font7 = new QFont();
        font7.setPointSize(7);
        menubar.setFont(font7);
        menuFile = new QMenu(menubar);
        menuFile.setObjectName("menuFile");
        QSizePolicy sizePolicy3 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Ignored, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy3.setHorizontalStretch((byte)0);
        sizePolicy3.setVerticalStretch((byte)0);
        sizePolicy3.setHeightForWidth(menuFile.sizePolicy().hasHeightForWidth());
        menuFile.setSizePolicy(sizePolicy3);
        QFont font8 = new QFont();
        font8.setPointSize(8);
        menuFile.setFont(font8);
        menuEdit = new QMenu(menubar);
        menuEdit.setObjectName("menuEdit");
        QFont font9 = new QFont();
        font9.setPointSize(8);
        menuEdit.setFont(font9);
        menuView = new QMenu(menubar);
        menuView.setObjectName("menuView");
        QFont font10 = new QFont();
        font10.setPointSize(8);
        menuView.setFont(font10);
        menuAbout = new QMenu(menubar);
        menuAbout.setObjectName("menuAbout");
        QFont font11 = new QFont();
        font11.setPointSize(8);
        menuAbout.setFont(font11);
        MainWindow.setMenuBar(menubar);
        statusbar = new QStatusBar(MainWindow);
        statusbar.setObjectName("statusbar");
        MainWindow.setStatusBar(statusbar);
        dockWidget = new QDockWidget(MainWindow);
        dockWidget.setObjectName("dockWidget");
        dockWidget.setMinimumSize(new QSize(252, 193));
        dockWidget.setFeatures(com.trolltech.qt.gui.QDockWidget.DockWidgetFeature.createQFlags(com.trolltech.qt.gui.QDockWidget.DockWidgetFeature.DockWidgetMovable));
        dockWidget.setAllowedAreas(com.trolltech.qt.core.Qt.DockWidgetArea.createQFlags(com.trolltech.qt.core.Qt.DockWidgetArea.LeftDockWidgetArea,com.trolltech.qt.core.Qt.DockWidgetArea.RightDockWidgetArea));
        dockWidgetContents = new QWidget();
        dockWidgetContents.setObjectName("dockWidgetContents");
        dockWidgetContents.setAcceptDrops(false);
        gridLayout_2 = new QGridLayout(dockWidgetContents);
        gridLayout_2.setMargin(0);
        gridLayout_2.setObjectName("gridLayout_2");
        listWidget = new QListWidget(dockWidgetContents);
        listWidget.setObjectName("listWidget");
        QFont font12 = new QFont();
        font12.setPointSize(9);
        listWidget.setFont(font12);
        listWidget.setSelectionMode(com.trolltech.qt.gui.QAbstractItemView.SelectionMode.ExtendedSelection);
        listWidget.setTextElideMode(com.trolltech.qt.core.Qt.TextElideMode.ElideMiddle);
        listWidget.setLayoutMode(com.trolltech.qt.gui.QListView.LayoutMode.SinglePass);
        listWidget.setSpacing(5);
        listWidget.setSortingEnabled(true);

        gridLayout_2.addWidget(listWidget, 1, 0, 1, 1);

        verticalLayout_4 = new QVBoxLayout();
        verticalLayout_4.setSpacing(3);
        verticalLayout_4.setObjectName("verticalLayout_4");
        verticalLayout_4.setContentsMargins(3, 3, 3, 0);
        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        rbAscDesc = new QRadioButton(dockWidgetContents);
        rbAscDesc.setObjectName("rbAscDesc");

        horizontalLayout.addWidget(rbAscDesc);

        horizontalSpacer_3 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout.addItem(horizontalSpacer_3);

        cmbDockFarmId = new QComboBox(dockWidgetContents);
        cmbDockFarmId.setObjectName("cmbDockFarmId");

        horizontalLayout.addWidget(cmbDockFarmId);


        verticalLayout_4.addLayout(horizontalLayout);

        lineEdit = new QLineEdit(dockWidgetContents);
        lineEdit.setObjectName("lineEdit");
        lineEdit.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));

        verticalLayout_4.addWidget(lineEdit);


        gridLayout_2.addLayout(verticalLayout_4, 0, 0, 1, 1);

        dockWidget.setWidget(dockWidgetContents);
        MainWindow.addDockWidget(com.trolltech.qt.core.Qt.DockWidgetArea.resolve(1), dockWidget);

        menubar.addAction(menuFile.menuAction());
        menubar.addAction(menuEdit.menuAction());
        menubar.addAction(menuView.menuAction());
        menubar.addAction(menuAbout.menuAction());
        menuFile.addAction(actionExit);
        menuEdit.addAction(actionUndo);
        menuView.addAction(actionInformation_Window);
        menuView.addAction(actionMap);
        menuAbout.addAction(actionAbout);
        menuAbout.addAction(actionAbout_Qt_Jambi);
        menuAbout.addAction(actionSettings);
        retranslateUi(MainWindow);

        tabWidget.setCurrentIndex(2);


        MainWindow.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow MainWindow)
    {
        actionExit.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Exit", null));
        actionExit.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Exit the program", null));
        actionAbout.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "About", null));
        actionInformation_Window.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Information", null));
        actionMap.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Map", null));
        actionUndo.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Undo", null));
        actionAbout_Qt_Jambi.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "About Qt Jambi", null));
        actionSettings.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Settings", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Name", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Birthdate", null));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Weight", null));
        label_4.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Farm #", null));
        label_5.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Alive", null));
        chbAlive.setText("");
        pbTabInformationUpdate.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Update", null));
        pbTabInformationReset.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Reset", null));
        tabWidget.setTabText(tabWidget.indexOf(tabInformation), com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Information", null));
        tableWidget.clear();
        tableWidget.setColumnCount(0);
        tableWidget.setRowCount(0);
        lblTabMessages.setText("");
        cmbTabMessages.clear();
        cmbTabMessages.addItem(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Messages", null));
        cmbTabMessages.addItem(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Alarms", null));
        tabWidget.setTabText(tabWidget.indexOf(tabAddMessages), com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Messages", null));
        pBSubmit_Add.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Submit", null));
        label_11.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Name", null));
        label_12.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Birthdate", null));
        label_13.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Weight", null));
        label_14.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Farm #", null));
        label_15.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Alive", null));
        cBAlive_Add.setText("");
        tabWidget.setTabText(tabWidget.indexOf(tabAddSheep), com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Add Sheep", null));
        menuFile.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "File", null));
        menuEdit.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Edit", null));
        menuView.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "View", null));
        menuAbout.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Help", null));
        dockWidget.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Sheeps", null));
        dockWidgetContents.setAccessibleName("");
        rbAscDesc.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Ascending", null));
        
    } // retranslateUi
    
    @Override
	public void setupUi(QMainWindow arg0) {
		setupUi(arg0, 800, 800);
		
	}
}
