package org.conxworks.paas.monitoring.alertbot.notifier.itest;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.amdatu.bndtools.test.BaseOSGiServiceTest;
import org.conxworks.paas.monitoring.alertbot.api.IAlertNotifier;


public class SendSMSTest extends BaseOSGiServiceTest<IAlertNotifier> {
	private volatile IAlertNotifier notifier;
	
	public SendSMSTest() {
		super(IAlertNotifier.class);
	}
	
	@Override
	public void setUp() throws Exception {
	}
	
    public void testSendSMS() throws InterruptedException {
    }
}
