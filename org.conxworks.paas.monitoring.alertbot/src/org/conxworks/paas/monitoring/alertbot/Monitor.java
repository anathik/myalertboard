package org.conxworks.paas.monitoring.alertbot;

import java.util.ArrayList;
import java.util.List;

import org.amdatu.scheduling.annotations.RepeatInterval;
import org.apache.http.HttpStatus;
import org.conxworks.paas.monitoring.alertbot.api.AlertNotificationException;
import org.conxworks.paas.monitoring.alertbot.api.IAlertNotifier;
import org.conxworks.paas.monitoring.alertbot.api.NotificationMessage;
import org.conxworks.paas.monitoring.poller.api.IPoller;
import org.conxworks.paas.monitoring.poller.api.Location;
import org.conxworks.paas.monitoring.poller.api.PollingException;
import org.osgi.service.log.LogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Monitor implements Job {
	
	private volatile IAlertNotifier notifier;
	private volatile IPoller poller;
	private volatile LogService logger;
	
	private String url;
	
	
	public Monitor(String url) {
		super();
		this.url = url;
	}

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		//2. Poll URL
		int status = -1;
		try {
			status = poller.ping(new Location(url));
			System.out.println(String.format("Ping to %s returned %d",url,status));
		} catch (PollingException e) {
			e.printStackTrace();
			throw new JobExecutionException(e);
		}
		//3a. If return status is OK, do nothing
		//3b. If return status is NOT OK, do stuff
		if (status != HttpStatus.SC_OK) {
			List<String> to = new ArrayList<>();
			to.add("+12152801971");
			String body = String.format("Site %s not reachable", url);
			NotificationMessage message = new NotificationMessage("+12158838500", to, body, "ConX Alert");
			try {
				notifier.notifyViaSMS(message);
			} catch (AlertNotificationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
