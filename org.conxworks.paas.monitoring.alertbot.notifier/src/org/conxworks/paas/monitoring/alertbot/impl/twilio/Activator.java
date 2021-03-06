package org.conxworks.paas.monitoring.alertbot.impl.twilio;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.conxworks.paas.monitoring.alertbot.api.IEmailAlertNotifier;
import org.conxworks.paas.monitoring.alertbot.api.ISMSAlertNotifier;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

public class Activator extends DependencyActivatorBase {
    @Override
    public synchronized void init(BundleContext context, DependencyManager manager) throws Exception {
        manager.add(createComponent()
        	.setInterface(ISMSAlertNotifier.class.getName(), null)
            .setImplementation(TwilioAlertNotifierImpl.class)
            .setCallbacks(null, "start", null, null)//init, start, stop and destroy.
            .add(createServiceDependency()
                .setService(LogService.class)
                .setRequired(false))
            );
    }

    @Override
    public synchronized void destroy(BundleContext context, DependencyManager manager) throws Exception {
    }
}