package com.net;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import com.sun.mail.smtp.*;

import core.classes.GpsPosition;
import core.settings.Settings;
public class MailNotifier {

	private String username;
	private String password;
	private Settings settings;
	private Session session;

	public MailNotifier(Settings settings) {
		this.settings = settings;
		username = settings.getGoogleUser();
		password = settings.getGooglePassword();
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

	public void notifyUser(String recipient, GpsPosition gps) {
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
