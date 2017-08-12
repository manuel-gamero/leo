package com.mg.service.search.autocompleter.core;

/**
 * This interface must be implemented by all objects providing
 * auto-completion functionality.
 * 
 * @author MJGP
 * 
 */
public interface Completable<V> extends Comparable<V> {

	/**
	 * The completable unique Id
	 * 
	 * @return
	 */
	long getId();

	/**
	 * Gets the String value this entity provides
	 * 
	 * @return the String value provided
	 */
	String getAutocompletionText();

}
