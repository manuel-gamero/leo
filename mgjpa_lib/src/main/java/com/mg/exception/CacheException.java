package com.mg.exception;

/**
 * 
 * @author MJGP
 *
 */
public class CacheException extends Exception {
		
	private static final long serialVersionUID = 9074484483346020566L;

	public CacheException(String s) {
		super(s);
	}

	public CacheException(String s, Exception e) {
		super(s, e);
	}
	
	public CacheException(Exception e) {
		super(e);		
	}
	
}
