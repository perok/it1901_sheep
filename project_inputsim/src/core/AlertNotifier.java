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
		
	}

	/** Polls database for unotified alerts.
	 * If any are found, the admin is notified and 
	 * 
	 */
	@Override
	public void run() {
		db = new DatabaseConnector(settings);
		skype = new PhoneNotifier(settings);
		mail = new MailNotifier(settings);
	}

	/** Finds the phone number for the admin of farm and send him an sms with the skype object
	 * 
	 * @param sheepAlert
	 */
	private void notifyPhone(SheepAlert sheepAlert) {
		GPSPosition gps = sheepAlert.getGpsPosition();
		int recipient = db.getAlertResponderPhone(sheepAlert.getFarmId());
		skype.setNumber(Integer.toString(recipient));
		skype.notifyPhone(gps);
	}

	/** Find the email of the admin of the farm and send him an email with the mail object.
	 * 
	 * @param sheepAlert
	 */
	private void notifyEmail(SheepAlert sheepAlert) {
		GPSPosition gps = sheepAlert.getGpsPosition();
		String recipient = db.getAlertResponderEmail(sheepAlert.getFarmId());
		mail.notifyUser(recipient, gps);
	}
	
	/** Method called by simulator to notify users about alerts
	 * 
	 * @param sheepAlert
	 */
	public void recieveAlert(SheepAlert sheepAlert) {
		notifyPhone(sheepAlert);
		notifyEmail(sheepAlert);
		db.insertSheepAlert(sheepAlert);
		db.alertNotified(sheepAlert.getId());
	}
	
	/** Inserts SheepStatus into the db.
	 * 
	 * @param status
	 */
	public void recieveStatus(String[][] status) {
		db.insertSheepStatus(status);
	}

}
