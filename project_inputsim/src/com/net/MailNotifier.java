package com.net;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import core.classes.GPSPosition;
import core.settings.Settings;

/**Notifies users by mail.
 * 
 * @author Lars Erik
 *
 */
public class MailNotifier {

	private String username;
	private String password;
	private Settings settings;
	private Session session;

	/**Constructor that creates session with google smtp with username and password
	 * given in the settings file.
	 * 
	 * @param settings
	 */
	public MailNotifier(Settings settings) {
		this.settings = settings;
		username = this.settings.getGoogleUser();
		password = this.settings.getGooglePassword();
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	/**Sends an email to recipient with a warning containing the gps position.
	 * 
	 * @param recipient
	 * @param gps
	 */
	public void notifyUser(String recipient, GPSPosition gps) {
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipient));
			message.setSubject("SheepShield alert. Urgent!");
			message.setText("Dear SheepShield user,"
					+ "\n\n We have registered a possible attack on your herd. Please respond, this is urgent." +
					"\n The sheep in question was last registered at coordinates " + gps.getLatitute()
					+ " Latitude and " + gps.getLongditude() + " Longditude. ");

			Transport.send(message);
			System.out.println(recipient);
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	

	
}
