package org.conxworks.paas.monitoring.alertbot.api;

public interface IEmailAlertNotifier {
	public void notifyViaEmail(NotificationMessage message) throws AlertNotificationException;
}
