package com.mg.exception;

/**
 * 
 * @author MJGP
 *
 */
public class ExtensionNotSupportedException extends Exception {

	private static final long serialVersionUID = -2254737609064301536L;

	public ExtensionNotSupportedException(String s) {
		super(s);
	}

	public ExtensionNotSupportedException(String s, Exception e) {
		super(s, e);
	}
	
	public ExtensionNotSupportedException(Exception e) {
		super(e);		
	}
	
}
