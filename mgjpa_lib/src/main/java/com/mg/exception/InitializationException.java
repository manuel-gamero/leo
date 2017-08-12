package com.mg.exception;


/**
 * 
 * @author MJGP
 *
 */
public class InitializationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7641059790046273212L;

	public InitializationException(String s) {
		super(s);
	}

	public InitializationException(String s, Exception e) {
		super(s, e);
	}
	
	public InitializationException(Exception e) {
		super(e);		
	}
	
}
