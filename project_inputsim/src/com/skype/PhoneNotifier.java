package com.skype;

import com.skype.api.Conversation;
import com.skype.api.Sms;
import com.skype.tutorial.appkeypair.AppKeyPairMgr;
import com.skype.tutorial.util.MySession;
import com.skype.tutorial.util.SignInMgr;
import core.settings.Settings;


public class PhoneNotifier {
	private static final String TAG = "SMS_TAG";
	private static final int SMS_SEND_TIMEOUT_LIMIT = 30;
	private static final String APPKEYPATH = "appkey\\skypekit-cert.pem";
	private static final String SKYPEKITPATH = "skypesdk\\windows-x86-skypekit.exe";
	
	private String username;
	private String password;
	private String message = "Sau under angrep! Dette ikke er en test";
	private String[] numbers = {"+47"};
	private AppKeyPairMgr appKey = new AppKeyPairMgr();
	MySession mySession = new MySession();
	
	
	/**Constructor. Sets username and password given in settings file.
	 * 
	 * @param Settings settings
	 * @return PhoneNotifier
	 */
	public PhoneNotifier(Settings settings) {
		username = settings.getSkypeUser();
		password = settings.getSkypePassword();
	}
	
	public PhoneNotifier() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getNumbers() {
		return numbers;
	}

	public void setNumbers(String[] numbers) {
		
		this.numbers[0] = "+47" + numbers[0];
	}

	/**Starts the skype runtime in order to use the sdk. Requiered to use PhoneNotifier.
	 * 
	 * @return void
	 */
	private boolean runSkypekit()
	{
		try
		{
			Runtime rt = Runtime.getRuntime();
			rt.exec(SKYPEKITPATH);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**The public method available to send an Sms with a pre initialized PhoneNotifier.
	 * 
	 */
	public void notifyPhone() {
		this.runSkypekit();
		if(this.connect())
			this.sendSMS();
	}
	
	/**Connects to skype using username and password set in constructor.
	 * 
	 * @return booelean
	 */
	private boolean connect()
	{
		if(!(appKey.resolveAppKeyPairPath(APPKEYPATH)) || !(appKey.isValidCertificate()))
		{
			System.out.println("Failed to authenticate Key Pairs");
			return false;
		}
		
		mySession.doCreateSession(TAG, username, APPKEYPATH);
		
		if(mySession.mySignInMgr.Login(TAG, mySession, password))
		{
			System.out.println("Connected");
			return true;
		}
		System.out.println("Failed to authenticate the User or the Password");
		return false;		
	}

	/**Disconnects from session
	 * 
	 */
	private void disconnect()
	{
		mySession.mySignInMgr.Logout(TAG, mySession);
		
		if (mySession != null) 
			mySession.doTearDownSession();
			
		System.out.println("Disconnected");
		
	}


	/**Handles presets for the sendSmsMessage method
	 * 
	 * @return boolean
	 */
	private boolean sendSMS()
	{
		if(!mySession.isLoggedIn())
		{
			System.out.println("Error: Not logged in");
			return false;
		}
		
		Sms sms = mySession.mySkype.createOutgoingSms();
		
		Sms.SetBodyResponse smsBodyResponse = sms.setBody(message);
		
		if (smsBodyResponse.result != Sms.SetBodyResult.BODY_OK) 
		{
			System.out.println("Error: Could not create the SMS content");
  			return false;
 		}
		
		if(!sms.setTargets(numbers))
		{
			System.out.println("Error: Could not set the target");
			return false;
		}
		
		sendSmsMessage(sms, numbers);
		
		return true;
	}
	
	/**Handles the actual sending of the sms. Takes and Sms from sendSms() and a String[]
	 * of numbers to send the Sms to.
	 * 
	 * @param mySms
	 * @param smsTargets
	 * @return
	 */
	private boolean sendSmsMessage(Sms mySms, String[] smsTargets) 
	{
		int i = 0;
        Sms.Status smsStatus = null;

 		Conversation myConversation = mySession.mySkype.getConversationByParticipants(smsTargets, true, true);
		myConversation.postSms(mySms, "unused");

		// Loop until we can confirm that the send has completed, has failed, or our polling has timed out
		i = 0;
		while ((i < SMS_SEND_TIMEOUT_LIMIT) && (!mySession.smsHasFailed) && (!mySession.smsWasSent)) 
		{
			try 
			{
				Thread.sleep(SignInMgr.DELAY_INTERVAL);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
				return(false);
			}
			MySession.myConsole.printf("\t %d...%n", i++);
		}
		if (mySession.smsWasSent) 
		{
			MySession.myConsole.println("SMS sent!");
			disconnect();
		}
		else if (i == SMS_SEND_TIMEOUT_LIMIT) 
		{
			MySession.myConsole.printf("Notification polling period timed out!%nCould not confirm that SMS was sent to all recipients.%n",
					(Object[])null);
		}
		else 
		{
        	Sms.FailureReason smsFailureReason = mySms.getFailureReason();
        	MySession.myConsole.printf("Error! Could not send SMS message: %s%n", smsFailureReason.toString());
			return(false);
		}
		MySession.myConsole.println();

		// Loop until we can confirm that delivery has completed, has failed, or our polling has timed out

		try 
		{
			Thread.sleep(SignInMgr.DELAY_INTERVAL);
		}
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return(false);
		}    
        return(true);
	}
	
	/**Not in use
	 * @deprecated
	 */
	private void callPhone() {
		Conversation myConversation = (Conversation)mySession.mySkype.getConversationByParticipants(numbers, true, true);
		
		myConversation.ringOthers(numbers, false, "LOLOLOL");
		mySession.callActive = true;
		while (mySession.callActive) {
			try {
				Thread.sleep(SignInMgr.DELAY_INTERVAL);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
	}
	/**Used to test module
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PhoneNotifier a = new PhoneNotifier(new Settings());
		a.notifyPhone();
	}
}

