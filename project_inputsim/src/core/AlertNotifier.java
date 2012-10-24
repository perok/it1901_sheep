package core;

import java.util.ArrayList;

import com.db.DatabaseConnector;
import com.skype.PhoneNotifier;
import core.classes.*;
import core.settings.Settings;

public class AlertNotifier implements Runnable{
	private DatabaseConnector db;
	private PhoneNotifier skype;
	private ArrayList<SheepAlert> alerts;
	private Settings settings;

	public AlertNotifier(Settings settings) {
		db = new DatabaseConnector(settings);
		skype = new PhoneNotifier();
	}

	@Override
	public void run() {
		while (true) {
			if(db.newAlertExists()) {
				alerts = db.getNewSheepAlert();
				if(!alerts.isEmpty()) {
					System.out.println("Alert found");
					notifyPhone();
					notifyEmail();
					db.alertNotified(alerts.get(0).getId());
				}
			}
		}
	}

	private void notifyPhone() {

	}

	private void notifyEmail() {

	}

}
