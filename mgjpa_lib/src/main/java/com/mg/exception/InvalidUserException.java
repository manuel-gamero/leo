package com.mg.exception;

/**
 * 
 * @author MJGP
 *
 */
public class InvalidUserException extends Exception {

	private static final long serialVersionUID = -2254737609064301536L;

	public InvalidUserException(String s) {
		super(s);
	}

	public InvalidUserException(String s, Exception e) {
		super(s, e);
	}
	
	public InvalidUserException(Exception e) {
		super(e);		
	}
	
}
