package com.mg.exception;


/**
 * 
 * @author MJGP
 *
 */
public class ServiceException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3870408849535605439L;

	public ServiceException(String s) {
		super(s);
	}

	public ServiceException(String s, Exception e) {
		super(s, e);
	}
	
	public ServiceException(Exception e) {
		super(e);		
	}
	
}
