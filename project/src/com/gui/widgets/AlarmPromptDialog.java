package com.gui.widgets;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;

public class AlarmPromptDialog extends QDialog 
{
	private QLabel qlPrompt;
	private QPushButton qpbBtnOk,
					    qpbBtnCancel;

	public AlarmPromptDialog(UserSettings parent)
	{
		super(parent);
		
		initWidgets();
		initLayout();
		initConnectEvents();
	}
	
	private void initConnectEvents()
	{
		this.qpbBtnCancel.clicked.connect(this, "close()");
		this.qpbBtnOk.clicked.connect(this, "triggerAlarm()");
	}
	
	@SuppressWarnings("unused")
	private void triggerAlarm()
	{
		//ServerLogic.getClientsocket().invokeAlert(com.storage.UserStorage.getUser().getFarmlist().get(
			//	com.storage.UserStorage.getCurrentFarm()));
		
		super.close();
	}

	private void initWidgets()
	{
		this.qpbBtnCancel = new QPushButton(tr("&Avbryt"));
		this.qpbBtnOk = new QPushButton(tr("&Ok"));
		this.qlPrompt = new QLabel(tr("Du vil nå sette av en alarm, og alle registrerte administratorer vil bli tilsendt varsler"));
	}
	
	private void initLayout()
	{
		QHBoxLayout qhblButtonLayout = new QHBoxLayout();
		QVBoxLayout qvblMainLayout = new QVBoxLayout();
		
		qhblButtonLayout.addWidget(this.qpbBtnCancel);
		qhblButtonLayout.addWidget(this.qpbBtnOk);
		
		qvblMainLayout.addWidget(this.qlPrompt);
		qvblMainLayout.addLayout(qhblButtonLayout);
		
		super.setLayout(qvblMainLayout);
	}
}

/* EOF */