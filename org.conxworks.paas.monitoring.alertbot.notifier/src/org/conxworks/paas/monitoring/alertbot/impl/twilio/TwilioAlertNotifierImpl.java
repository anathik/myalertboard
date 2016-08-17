package org.conxworks.paas.monitoring.alertbot.impl.twilio;

import com.twilio.sdk.auth.AccessToken;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.conxworks.paas.monitoring.alertbot.api.AlertNotificationException;
import org.conxworks.paas.monitoring.alertbot.api.IAlertNotifier;
import org.conxworks.paas.monitoring.alertbot.api.NotificationMessage;

public class TwilioAlertNotifierImpl implements IAlertNotifier {
	
	  public static final String APIKEY_SID = "SKd8af0e88f1470e14eae7251720f84ff3";
	  public static final String APIKEY_SECRET = "Hpo3vdnQfv4S9oEf4od3tERik1QJIUTg";
	  public static final String ACCOUNT_SID = "AC209c62c65fef119415cb347379a479dc";
	  public static final String AUTH_TOKEN = "7cebf18fed1f17ef7e8a22244a80c76e";
	  
	  
	  private TwilioRestClient m_client;
	  
	
	public void start() {
		m_client  = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	}

	@Override
	public void notifyViaSMS(NotificationMessage message) throws AlertNotificationException {
	    // Build a filter for the MessageList
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("Body", message.getBody()));
	    
	    // Create To list
	    List<String> list = message.getTo();
	    StringBuffer sb = new StringBuffer();
    	if (list.size() == 1) 
    		sb.append(list.get(0));
    	else {
		    for (int i=0; i<list.size(); i++) {
		    	if (i != ( list.size()-1)) 
		    		sb.append(list.get(i)+",");
		    	else 
		    		sb.append(list.get(i));
		    }
    	}
	    
	    params.add(new BasicNameValuePair("To",  sb.toString()));
	    
	    
	    params.add(new BasicNameValuePair("From", message.getFrom()));

	    try {
			MessageFactory messageFactory = m_client.getAccount().getMessageFactory();
			Message smsMessage = messageFactory.create(params);
			System.out.println(smsMessage.getSid());
		} catch (TwilioRestException e) {
			throw new  AlertNotificationException(e);
		}
	}

	@Override
	public void notifyViaEmail(NotificationMessage message) throws AlertNotificationException {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
