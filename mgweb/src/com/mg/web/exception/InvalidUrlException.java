/**
 * 
 */
package com.mg.web.exception;

/**
 * Invalid url exception
 * 
 * @author MJGP
 * 
 */
public class InvalidUrlException extends Exception {

	private static final long serialVersionUID = -5271672689358802087L;

	public InvalidUrlException(String s) {
		super(s);
	}

	public InvalidUrlException(String s, Exception e) {
		super(s, e);
	}
	
	public InvalidUrlException(Exception e) {
		super(e);		
	}
	
}
