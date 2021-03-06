package org.conxworks.paas.monitoring.alertbot.utest;

import com.twilio.sdk.auth.AccessToken;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class TestExample {

  // Find your Account Sid and Token at twilio.com/console
  public static final String APIKEY_SID = "SKd8af0e88f1470e14eae7251720f84ff3";
  public static final String APIKEY_SECRET = "Hpo3vdnQfv4S9oEf4od3tERik1QJIUTg";
  public static final String ACCOUNT_SID = "AC209c62c65fef119415cb347379a479dc";
  public static final String AUTH_TOKEN = "7cebf18fed1f17ef7e8a22244a80c76e";

  public static void main(String[] args) throws TwilioRestException {
    TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

    // Build a filter for the MessageList
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("Body", "Hello from Anathi"));
    params.add(new BasicNameValuePair("To", "+12152801971"));
    params.add(new BasicNameValuePair("From", "+12158838500"));

    MessageFactory messageFactory = client.getAccount().getMessageFactory();
    Message message = messageFactory.create(params);
    System.out.println(message.getSid());
  }
}
