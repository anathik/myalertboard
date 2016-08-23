package org.conxworks.paas.monitoring.alertbot.impl.sendgrid;

import java.io.IOException;
import com.sendgrid.*;

import org.conxworks.paas.monitoring.alertbot.api.AlertNotificationException;
import org.conxworks.paas.monitoring.alertbot.api.IEmailAlertNotifier;
import org.conxworks.paas.monitoring.alertbot.api.NotificationMessage;

public class SendgridAlertNotifierImpl implements IEmailAlertNotifier{
	
	private SendGrid sg;

	public void start() {
		this.sg = new SendGrid("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");
	}
	
	@Override
	public void notifyViaEmail(NotificationMessage message) throws AlertNotificationException {
	    Email from = new Email(message.getFrom());
	    String subject = "WARNING: Alertbot Notification";
	    Email to = new Email(message.getTo().get(0));
	    Content content = new Content("text/plain", message.getBody());
	    Mail mail = new Mail(from, subject, to, content);

	    Request request = new Request();
	    try {
	      request.method = Method.POST;
	      request.endpoint = "mail/send";
	      request.body = mail.build();
	      Response response = sg.api(request);
	      System.out.println(response.statusCode);
	      System.out.println(response.body);
	      System.out.println(response.headers);
	    } catch (IOException ex) {
	      throw new AlertNotificationException(ex);
	    }
	}
	}