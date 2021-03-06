package org.conxworks.paas.monitoring.alertbot.factory;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.log.LogService;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;

public class PollingMonitorFactory implements ManagedServiceFactory {
	public static final String PID = "org.conxworks.paas.monitoring.configuration";
	
	public static final String ATTRIBUTE_POLLING_HOSTNAME = "pollinghostname";
	public static final String ATTRIBUTE_POLLING_PORT = "pollingport";
	public static final String ATTRIBUTE_POLLING_PATH = "pollingpath";
	public static final String ATTRIBUTE_POLLING_PROTOCOL = "pollingprotocol";
	public static final String ATTRIBUTE_POLLING_COUNT = "pollingcount";
	public static final String ATTRIBUTE_POLLING_PERIOD_UNIT = "pollingperiodunit";
	public static final String ATTRIBUTE_POLLING_PERIOD = "pollingperiod";
	
    private volatile LogService m_logService;

    private volatile DependencyManager m_dependencyManager;
    
    private volatile BundleContext m_bundleContext;
    
    private final Map<String, Component> m_components = new ConcurrentHashMap<>();	
	
	   @Override
	    public String getName() {
	        return PID;
	    }

	    @Override
	    public void updated(String pid, Dictionary properties)
	        throws ConfigurationException {

	        if (m_components.containsKey(pid)) {
	            m_logService.log(LogService.LOG_INFO, "Removing monitor configuration " + pid);
	            Component component = m_components.remove(pid);
	            m_dependencyManager.remove(component);
	        }

	        String hostname = getRequiredStringProperty(properties, ATTRIBUTE_POLLING_HOSTNAME);
	        
	        System.out.println(String.format("Configuration %s has prop %s = %s", pid, ATTRIBUTE_POLLING_HOSTNAME, hostname));

/*	        DelegatingDataSource ds = new DelegatingDataSource(userName, password, driverClassName, jdbcUrl, managed, validationQuery);
	        Component component = m_dependencyManager
	            .createComponent()
	            .setInterface(DataSource.class.getName(), dsProps)
	            .setImplementation(ds)
	            .add(m_dependencyManager.createServiceDependency().setService(LogService.class).setRequired(false));

	        m_logService.log(LogService.LOG_INFO, "Registering datasource [pid: " + pid + " jdbcUrl: "+jdbcUrl+"] ");

	        synchronized (m_components) {
	            m_dependencyManager.add(component);
	            m_components.put(pid, component);
	        }*/
	    }	
	    
	    @Override
	    public void deleted(String pid) {
	    	System.out.println(String.format("Deleted configuration %s...s", pid));
	    }
	    
	    private String getRequiredStringProperty(Dictionary<String, ?> properties,
	            String key) throws ConfigurationException {
	            String userName = (String) properties.remove(key);
	            if (userName == null) {
	                throw new ConfigurationException(key, "Property " + key + " is required");
	            }
	            return userName;
	        }
}
