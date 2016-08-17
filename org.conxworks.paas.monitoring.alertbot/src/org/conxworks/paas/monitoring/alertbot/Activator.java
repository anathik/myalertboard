package org.conxworks.paas.monitoring.alertbot;

import java.util.Properties;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.conxworks.paas.monitoring.alertbot.api.IAlertNotifier;
import org.conxworks.paas.monitoring.poller.api.IPoller;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.amdatu.scheduling.constants.Constants;
import org.quartz.Job;

public class Activator extends DependencyActivatorBase {
    @Override
    public synchronized void init(BundleContext context, DependencyManager manager) throws Exception {
		Properties properties = new Properties();
		properties.put(Constants.REPEAT_COUNT, new Integer(10));
		properties.put(Constants.REPEAT_INTERVAL_PERIOD, "millisecond");
		properties.put(Constants.REPEAT_INTERVAL_VALUE, new Long(50));
		manager.add(createComponent()
				.setInterface(Job.class.getName(), properties)
				.setImplementation(PollingJob.class)
	            .add(createServiceDependency()
	                	.setService(IAlertNotifier.class)
	                	.setRequired(true))
	                .add(createServiceDependency()
	                	.setService(IPoller.class)
	                	.setRequired(true))
	                .add(createServiceDependency()
	                    .setService(LogService.class)
	                    .setRequired(false))
	                );
    }

    @Override
    public synchronized void destroy(BundleContext context, DependencyManager manager) throws Exception {
    }
}