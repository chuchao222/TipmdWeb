package com.tipmd.webapp.service;

@SuppressWarnings("serial")
public class TipmdServiceException extends Exception {
	
	public TipmdServiceException() {
		super();
	}
	
	public TipmdServiceException(String msg) {
		super(msg);
	}
	
	public TipmdServiceException(Throwable cause) {
		super(cause);
	}
	
	public TipmdServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public TipmdServiceException(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(msg, cause, enableSuppression, writableStackTrace);
	}
}
