package org.conxworks.paas.monitoring.poller.impl.httpclient;

import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.conxworks.paas.monitoring.poller.api.IPoller;
import org.conxworks.paas.monitoring.poller.api.Location;
import org.conxworks.paas.monitoring.poller.api.PollingException;
import org.osgi.service.log.LogService;

public class HttpClientPollerImpl implements IPoller {
	private volatile LogService logger;

	private HttpClient m_client;

	public void start() {
	}

	@Override
	public int ping(Location loc) throws PollingException {
		HttpGet request = new HttpGet(loc.getUrl());
		HttpResponse response = null;
		try {
			m_client = new DefaultHttpClient();
			response = m_client.execute(request);
		} catch (UnknownHostException e) {
			return HttpStatus.SC_BAD_GATEWAY;
		} catch (Exception e) {
			throw new PollingException(e);
		} 

		return response.getStatusLine().getStatusCode();
	}

}
