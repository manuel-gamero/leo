package com.mg.exception;

/**
 * General Exception thrown by the autocompleter.
 * 
 * @author MJGP
 * 
 */
public class AutocompleterException extends Exception {

	private static final long serialVersionUID = -4167796439367623238L;

	public AutocompleterException(String s) {
		super(s);
	}

	public AutocompleterException(String s, Throwable e) {
		super(s, e);
	}

	public AutocompleterException(Throwable e) {
		super(e);
	}
}
