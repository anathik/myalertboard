package org.conxworks.paas.monitoring.alertbot.notifier.itest;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.amdatu.bndtools.test.BaseOSGiServiceTest;
import org.conxworks.paas.monitoring.alertbot.api.IEmailAlertNotifier;


public class SendSMSTest extends BaseOSGiServiceTest<IEmailAlertNotifier> {
	private volatile IEmailAlertNotifier notifier;
	
	public SendSMSTest() {
		super(IEmailAlertNotifier.class);
	}
	
	@Override
	public void setUp() throws Exception {
	}
	
    public void testSendSMS() throws InterruptedException {
    }
}
