package org.conxworks.paas.monitoring.poller.utest;

import org.conxworks.paas.monitoring.poller.api.IPoller;
import org.conxworks.paas.monitoring.poller.api.Location;
import org.conxworks.paas.monitoring.poller.api.PollingException;
import org.conxworks.paas.monitoring.poller.impl.httpclient.HttpClientPollerImpl;

public class TestExample {

  public static void main(String[] args) throws  PollingException {
	  IPoller poller = new HttpClientPollerImpl();
	  ((HttpClientPollerImpl)poller).start();
	  int result = poller.ping(new Location("http://www.cnn.com"));
	  System.out.println("Status Code: "+result);
  }
}
