package com.gui.widgets;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;

/** Prompt user whether he/she really wants to issue an alert
 * 
 * @author Gruppe 10
 */
public class AlarmPromptDialog extends QDialog 
{
	private QLabel qlPrompt;
	private QPushButton qpbBtnOk,
					    qpbBtnCancel;

	/** Constructor. Initialize..
	 * 
	 * @param parent the issuer of this class
	 */
	public AlarmPromptDialog(UserSettings parent)
	{
		super(parent);
		
		initWidgets();
		initLayout();
		initConnectEvents();
	}
	
	/** Setup all event-triggers
	 */
	private void initConnectEvents()
	{
		this.qpbBtnCancel.clicked.connect(this, "close()");
		this.qpbBtnOk.clicked.connect(this, "triggerAlarm()");
	}
	
	@SuppressWarnings("unused")
	/** Trigger an alarm and close THIS.
	 * 
	 * @see ClientSocket.invokeAlert()
	 */
	private void triggerAlarm()
	{
		//ServerLogic.getClientsocket().invokeAlert(com.storage.UserStorage.getUser().getFarmlist().get(
			//	com.storage.UserStorage.getCurrentFarm()));
		
		super.close();
	}

	/** Initialize the widgets used in THIS 
	*/
	private void initWidgets()
	{
		this.qpbBtnCancel = new QPushButton(tr("&Avbryt"));
		this.qpbBtnOk = new QPushButton(tr("&Ok"));
		this.qlPrompt = new QLabel(tr("Du vil n� sette av en alarm, og alle registrerte administratorer vil bli tilsendt varsler"));
	}
	
	/** Initialize the layouts used in THIS
	 */
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