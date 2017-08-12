/**
 * 
 */
package com.mg.web.exception;

/**
 * Action general exception
 * 
 * @author MJGP
 * 
 */
public class BasicActionException extends Exception {

	private static final long serialVersionUID = -5271672689358802087L;

	public BasicActionException(String s) {
		super(s);
	}

	public BasicActionException(String s, Exception e) {
		super(s, e);
	}
	
	public BasicActionException(Exception e) {
		super(e);		
	}
	
}
