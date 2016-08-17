package org.conxworks.paas.monitoring.alertbot;

import org.apache.http.HttpStatus;
import org.conxworks.paas.monitoring.alertbot.api.IAlertNotifier;
import org.conxworks.paas.monitoring.poller.api.IPoller;
import org.conxworks.paas.monitoring.poller.api.Location;
import org.conxworks.paas.monitoring.poller.api.PollingException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PollingJob implements Job {
	
	private volatile IAlertNotifier notifier;
	private volatile IPoller poller;

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		//1. Get URL to poll
		String url = "http://www.cnn.com";
		//2. Poll URL
		int status = -1;
		try {
			status = poller.ping(new Location(url));
		} catch (PollingException e) {
			throw new JobExecutionException(e);
		}
		//3a. If return status is OK, do nothing
		//3b. If return status is NOT OK, do stuff
		if (status != HttpStatus.SC_OK) {
			
		}
	}

}
