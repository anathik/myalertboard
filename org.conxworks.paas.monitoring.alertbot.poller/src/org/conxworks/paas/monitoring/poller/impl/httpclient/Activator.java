package org.conxworks.paas.monitoring.poller.impl.httpclient;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.conxworks.paas.monitoring.poller.api.IPoller;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

public class Activator extends DependencyActivatorBase {
    @Override
    public synchronized void init(BundleContext context, DependencyManager manager) throws Exception {
        manager.add(createComponent()
        	.setInterface(IPoller.class.getName(), null)
            .setImplementation(HttpClientPollerImpl.class)
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