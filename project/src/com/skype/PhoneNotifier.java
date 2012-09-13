package com.skype;

import com.skype.api.Conversation;
import com.skype.api.Sms;
import com.skype.tutorial.appkeypair.AppKeyPairMgr;
import com.skype.tutorial.util.MySession;
import com.skype.tutorial.util.SignInMgr;

public class PhoneNotifier {
	public static String TAG = "SMS_TAG";
	public static String APPKEYPATH = "appkey\\skypekit-cert.pem";
	public static String SKYPEKITPATH = "skypesdk\\windows-x86-skypekit.exe";
	public static String ACCOUNTNAME = "";
	public static String PASSWORD = "";
	public static String MESSAGE = "Sau under angrep! Dette er en test";
	public static int SMS_SEND_TIMEOUT_LIMIT = 30;
	
	private String[] numbers = {"004797565328"};
	
	private AppKeyPairMgr appKey = new AppKeyPairMgr();
	MySession mySession = new MySession();
	
	public static void main(String[] args) {
		PhoneNotifier a = new PhoneNotifier();
		if(a.runSkypekit()){
			if(a.connect()){
				a.sendSMS();
			}
		}
	}
	
	public boolean runSkypekit()
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
	
	public boolean connect()
	{
		if(!(appKey.resolveAppKeyPairPath(APPKEYPATH)) || !(appKey.isValidCertificate()))
		{
			System.out.println("Failed to authenticate Key Pairs");
			return false;
		}
		
		mySession.doCreateSession(TAG, ACCOUNTNAME, APPKEYPATH);
		
		if(mySession.mySignInMgr.Login(TAG, mySession, PASSWORD))
		{
			System.out.println("Connected");
			return true;
		}
		System.out.println("Failed to authenticate the User or the Password");
		return false;		
	}

	public void disconnect()
	{
		mySession.mySignInMgr.Logout(TAG, mySession);
		
		if (mySession != null) 
			mySession.doTearDownSession();
			
		System.out.println("Disconnected");
		
	}

	
	public boolean sendSMS()
	{
		if(!mySession.isLoggedIn())
		{
			System.out.println("Error: Not logged in");
			return false;
		}
		
		Sms sms = mySession.mySkype.createOutgoingSms();
		
		Sms.SetBodyResponse smsBodyResponse = sms.setBody(MESSAGE);
		
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
	
	public boolean sendSmsMessage(Sms mySms, String[] smsTargets) 
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
	
	public void callUser() {
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
}

