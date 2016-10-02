package org.conxworks.paas.monitoring.poller.api;

public class PollingException extends Exception {

	public PollingException() {
		super();
	}

	public PollingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PollingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PollingException(String message) {
		super(message);
	}

	public PollingException(Throwable cause) {
		super(cause);
	}
	
}
