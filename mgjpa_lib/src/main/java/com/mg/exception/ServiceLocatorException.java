package com.mg.exception;


/**
 * 
 * @author MJGP
 *
 */
public class ServiceLocatorException extends Exception {

	private static final long serialVersionUID = -7151158236549019001L;

	public ServiceLocatorException() {
		super();
	}
	
	public ServiceLocatorException(String s) {
		super(s);
	}

	public ServiceLocatorException(String s, Exception e) {
		super(s, e);
	}
	
	public ServiceLocatorException(Exception e) {
		super(e);		
	}
	
}
