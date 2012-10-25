package core;

import java.util.ArrayList;

import com.db.DatabaseConnector;
import com.net.MailNotifier;
import com.skype.PhoneNotifier;
import core.classes.*;
import core.settings.Settings;

public class AlertNotifier implements Runnable{
	private DatabaseConnector db;
	private PhoneNotifier skype;
	private ArrayList<SheepAlert> alerts;
	private MailNotifier mail;
	private Settings settings;

	public AlertNotifier(Settings settings) {
		db = new DatabaseConnector(settings);
		skype = new PhoneNotifier(settings);
		mail = new MailNotifier(settings);
	}

	@Override
	public void run() {
		while (true) {
			if(db.newAlertExists()) {
				alerts = db.getNewSheepAlert();
				if(!alerts.isEmpty()) {
					System.out.println("Alert found");
					notifyPhone(alerts.get(0));
					notifyEmail(alerts.get(0));
					db.alertNotified(alerts.get(0).getId());
				}
			}
		}
	}

	private void notifyPhone(SheepAlert sheepAlert) {
		GpsPosition gps = sheepAlert.getGpsPosition();
		String recipient = db.getAlertResponderEmail(sheepAlert.getFarmId());
		String[] numbers = {recipient};
		skype.setNumbers(numbers);
		skype.notifyPhone();
	}

	private void notifyEmail(SheepAlert sheepAlert) {
		GpsPosition gps = sheepAlert.getGpsPosition();
		String recipient = db.getAlertResponderEmail(sheepAlert.getFarmId());
		mail.notifyUser(recipient, gps);
	}

}
