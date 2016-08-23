package org.conxworks.paas.monitoring.alertbot.factory;

import java.util.Dictionary;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.log.LogService;
import org.quartz.Job;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.amdatu.scheduling.constants.Constants;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;
import org.conxworks.paas.monitoring.alertbot.Monitor;
import org.conxworks.paas.monitoring.alertbot.api.IEmailAlertNotifier;
import org.conxworks.paas.monitoring.alertbot.api.ISMSAlertNotifier;
import org.conxworks.paas.monitoring.poller.api.IPoller;

public class PollingMonitorFactory implements ManagedServiceFactory {
	public static final String PID = "org.conxworks.paas.monitoring.configuration";
	
	public static final String ATTRIBUTE_POLLING_HOSTNAME = "pollinghostname";
	public static final String ATTRIBUTE_POLLING_PORT = "pollingport";
	public static final String ATTRIBUTE_POLLING_PATH = "pollingpath";
	public static final String ATTRIBUTE_POLLING_PROTOCOL = "pollingprotocol";
	public static final String ATTRIBUTE_REPEAT_COUNT = "repeat.count";
	public static final String ATTRIBUTE_REPEAT_INTERVAL_PERIOD = "repeat.interval.period";
	public static final String ATTRIBUTE_EPEAT_INTERVAL_VALUE = "repeat.interval.value";
	
    private volatile LogService m_logService;

    private volatile DependencyManager m_dependencyManager;
    
    private volatile BundleContext m_bundleContext;
    
    private final Map<String, String> m_pidToUrlMap = new ConcurrentHashMap<>();
    private final Map<String, Component> m_components = new ConcurrentHashMap<>();	
	
	   @Override
	    public String getName() {
	        return PID;
	    }

	    @Override
	    public void updated(String pid, Dictionary properties)
	        throws ConfigurationException {
	    	
	    	System.out.println(String.format("Processing configuration %s...", pid));

	    	
	        if (m_components.containsKey(pid)) {
	            m_logService.log(LogService.LOG_INFO, "Removing monitor configuration " + pid);
	            Component component = m_components.remove(pid);
	            m_dependencyManager.remove(component);
	        }

	        String hostname = getRequiredStringProperty(properties, ATTRIBUTE_POLLING_HOSTNAME);
	        String protocol = getRequiredStringProperty(properties, ATTRIBUTE_POLLING_PROTOCOL);
	        String port = getRequiredStringProperty(properties, ATTRIBUTE_POLLING_PORT);
	        
	        Integer repeatCount = Integer.parseInt(getRequiredStringProperty(properties, ATTRIBUTE_REPEAT_COUNT));
	        String repeatIntervalPeriod =getRequiredStringProperty(properties, ATTRIBUTE_REPEAT_INTERVAL_PERIOD);
	        Long repeatIntervalValue = Long.parseLong(getRequiredStringProperty(properties, ATTRIBUTE_EPEAT_INTERVAL_VALUE));
	        
	        String url = protocol+"://"+hostname+":"+port;
	        
	        System.out.println(String.format("Creating monitorfor site %s",url));
	        
			properties = new Properties();
			properties.put(Constants.REPEAT_COUNT, repeatCount);
			properties.put(Constants.REPEAT_INTERVAL_PERIOD, repeatIntervalPeriod);
			properties.put(Constants.REPEAT_INTERVAL_VALUE, repeatIntervalValue);
			Component component = m_dependencyManager.createComponent()
					.setInterface(Job.class.getName(), properties)
					.setImplementation(new Monitor(url))
					.add(m_dependencyManager.createServiceDependency()
		                	.setService(ISMSAlertNotifier.class)
		                	.setRequired(true))
		            .add(m_dependencyManager.createServiceDependency()
		                	.setService(IEmailAlertNotifier.class)
		                	.setRequired(true))
		            .add(m_dependencyManager.createServiceDependency()
		                	.setService(IPoller.class)
		                	.setRequired(true))
		                .add(m_dependencyManager.createServiceDependency()
		                    .setService(LogService.class)
		                    .setRequired(false));
			
	        synchronized (m_components) {
	            m_dependencyManager.add(component);
	            m_components.put(pid, component);
	            
	            
	            System.out.println(String.format("Processing %d monitors...", m_components.size()));
	        }
	    }	
	    
	    @Override
	    public void deleted(String pid) {
	    	m_components.remove(pid);
	    	System.out.println(String.format("Deleted configuration %s...", pid));
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
