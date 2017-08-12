/**
 * 
 */
package com.mg.web.exception;

/**
 * Wrap all the paypal exception and errors.
 * 
 * @author MJGP
 * 
 */
public class PaypalException extends Exception {

	private static final long serialVersionUID = -5271672689358802087L;

	public PaypalException(String s) {
		super(s);
	}

	public PaypalException(String s, Exception e) {
		super(s, e);
	}
	
	public PaypalException(Exception e) {
		super(e);		
	}
	
}
