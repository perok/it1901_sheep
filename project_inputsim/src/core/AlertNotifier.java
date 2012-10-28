package core;

import java.util.ArrayList;
import com.db.DatabaseConnector;
import com.net.MailNotifier;
import com.skype.PhoneNotifier;
import core.classes.*;
import core.settings.Settings;

/** A thread that constantly checks for new alerts 
 * and notifies users if alerts are found
 * 
 * @author Lars Erik
 *
 */
public class AlertNotifier implements Runnable{
	private DatabaseConnector db;
	private PhoneNotifier skype;
	private ArrayList<SheepAlert> alerts;
	private MailNotifier mail;
	private Settings settings;

	/** Constructor. Adds notifiers with settings paramter.
	 * 
	 * @param settings
	 */
	public AlertNotifier(Settings settings) {
		this.settings = settings;
		db = new DatabaseConnector(settings);
		skype = new PhoneNotifier(settings);
		mail = new MailNotifier(settings);
	}

	/** Polls database for unotified alerts.
	 * If any are found, the admin is notified and 
	 * 
	 */
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

	/** Finds the phone number for the admin of farm and send him an sms with the skype object
	 * 
	 * @param sheepAlert
	 */
	private void notifyPhone(SheepAlert sheepAlert) {
		GpsPosition gps = sheepAlert.getGpsPosition();
		String recipient = db.getAlertResponderEmail(sheepAlert.getFarmId());
		String[] numbers = {recipient};
		skype.setNumbers(numbers);
		skype.notifyPhone();
	}

	/** Find the email of the admin of the farm and send him an email with the mail object.
	 * 
	 * @param sheepAlert
	 */
	private void notifyEmail(SheepAlert sheepAlert) {
		GpsPosition gps = sheepAlert.getGpsPosition();
		String recipient = db.getAlertResponderEmail(sheepAlert.getFarmId());
		mail.notifyUser(recipient, gps);
	}

}
