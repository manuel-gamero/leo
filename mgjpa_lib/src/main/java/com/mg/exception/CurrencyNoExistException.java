package com.mg.exception;

/**
 * 
 * @author MJGP
 *
 */
public class CurrencyNoExistException extends Exception {

	private static final long serialVersionUID = -2254737609064301536L;

	public CurrencyNoExistException(String s) {
		super(s);
	}

	public CurrencyNoExistException(String s, Exception e) {
		super(s, e);
	}
	
	public CurrencyNoExistException(Exception e) {
		super(e);		
	}
	
}
