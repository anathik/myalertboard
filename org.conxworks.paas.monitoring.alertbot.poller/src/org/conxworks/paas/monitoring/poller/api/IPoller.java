package org.conxworks.paas.monitoring.poller.api;


public interface IPoller {
	public int ping(Location message) throws PollingException;
}
