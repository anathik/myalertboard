package org.conxworks.paas.monitoring.alertbot.api;

public interface ISMSAlertNotifier {
	public void notifyViaSMS(NotificationMessage message) throws AlertNotificationException;
}
