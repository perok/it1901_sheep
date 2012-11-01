/********************************************************************************
 ** Form generated from reading ui file 'untitled.jui'
 **
 ** Created by: Qt User Interface Compiler version 4.7.1
 **
 ** WARNING! All changes made in this file will be lost when recompiling ui file!
 ********************************************************************************/

package com.gui;

import com.trolltech.qt.QSignalEmitter.Signal2;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_MainWindow implements com.trolltech.qt.QUiForm<QMainWindow>
{
    public QWidget centralwidget;
    public QGridLayout gridLayout;
    public QVBoxLayout verticalLayout;
    public QFormLayout formLayout;
    public QLabel label;
    public QLineEdit lineEdit;
    public QLabel label_2;
    public QLineEdit lineEdit_2;
    public QGridLayout gridLayout_2;
    public QPushButton pushButton;
    public QSpacerItem verticalSpacer;
    public QSpacerItem verticalSpacer_2;
    
    /* Signals */
    public Signal2<String, String> tryLogin;

    public Ui_MainWindow() { super(); }

    public void setupUi(QMainWindow MainWindow)
    {
        MainWindow.setObjectName("MainWindow");
        MainWindow.resize(new QSize(800, 600).expandedTo(MainWindow.minimumSizeHint()));
        centralwidget = new QWidget(MainWindow);
        centralwidget.setObjectName("centralwidget");
        gridLayout = new QGridLayout(centralwidget);
        gridLayout.setObjectName("gridLayout");
        verticalLayout = new QVBoxLayout();
        verticalLayout.setObjectName("verticalLayout");
        formLayout = new QFormLayout();
        formLayout.setObjectName("formLayout");
        formLayout.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetMinimumSize);
        formLayout.setFieldGrowthPolicy(com.trolltech.qt.gui.QFormLayout.FieldGrowthPolicy.FieldsStayAtSizeHint);
        formLayout.setFormAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));
        label = new QLabel(centralwidget);
        label.setObjectName("label");

        formLayout.addWidget(label);

        lineEdit = new QLineEdit(centralwidget);
        lineEdit.setObjectName("lineEdit");

        formLayout.addWidget(lineEdit);

        label_2 = new QLabel(centralwidget);
        label_2.setObjectName("label_2");

        formLayout.addWidget(label_2);

        lineEdit_2 = new QLineEdit(centralwidget);
        lineEdit_2.setObjectName("lineEdit_2");

        formLayout.addWidget(lineEdit_2);


        verticalLayout.addLayout(formLayout);


        gridLayout.addLayout(verticalLayout, 1, 0, 1, 1);

        gridLayout_2 = new QGridLayout();
        gridLayout_2.setObjectName("gridLayout_2");
        gridLayout_2.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetMinimumSize);
        pushButton = new QPushButton(centralwidget);
        pushButton.setObjectName("pushButton");
        pushButton.setMaximumSize(new QSize(140, 16777215));

        gridLayout_2.addWidget(pushButton, 0, 0, 1, 1);


        gridLayout.addLayout(gridLayout_2, 3, 0, 1, 1);

        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer, 0, 0, 1, 1);

        verticalSpacer_2 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(verticalSpacer_2, 4, 0, 1, 1);

        MainWindow.setCentralWidget(centralwidget);
        retranslateUi(MainWindow);

        MainWindow.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow MainWindow)
    {
        MainWindow.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "MainWindow", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Username", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Password", null));
        pushButton.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Login", null));
    } // retranslateUi

}

