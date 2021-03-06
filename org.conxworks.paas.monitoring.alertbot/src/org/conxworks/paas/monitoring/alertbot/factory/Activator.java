package org.conxworks.paas.monitoring.alertbot.factory;


import java.util.Properties;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.conxworks.paas.monitoring.alertbot.api.IEmailAlertNotifier;
import org.conxworks.paas.monitoring.poller.api.IPoller;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.log.LogService;

public class Activator extends DependencyActivatorBase {

	@Override
	public void destroy(BundleContext arg0, DependencyManager arg1) throws Exception {

	}

	@Override
	public void init(BundleContext arg0, DependencyManager dm) throws Exception {
		Properties props = new Properties();
		props.put(Constants.SERVICE_PID, PollingMonitorFactory.PID);

		dm.add(createComponent().setInterface(ManagedServiceFactory.class.getName(), props)
				.setImplementation(PollingMonitorFactory.class)
				.add(createServiceDependency().setService(LogService.class).setRequired(false)));

	}

}