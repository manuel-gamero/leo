package com.mg.service.search.autocompleter;

import java.util.Collection;
import java.util.Set;

import com.mg.exception.AutocompleterException;
import com.mg.service.search.autocompleter.core.AutocompleterConfigurator;
import com.mg.service.search.autocompleter.core.AutocompleterElement;
import com.mg.service.search.autocompleter.core.Completable;

/**
 * Autocompleter interface.
 * 
 * @author MJGP
 * 
 * @param <K>
 * @param <V>
 */
public interface Autocompleter<K, V extends Completable<V>> {

	/**
	 * Builds the autocompleter from a list of elements. Data
	 * is cleared and then rebuilt from the database on a
	 * predefined interval.
	 * 
	 * @param elementsList
	 */
	void build(Collection<AutocompleterElement<K, V>> elements);

	/**
	 * Adds a new element to the autocompleter.
	 * 
	 * @param e
	 */
	void add(AutocompleterElement<K, V> e);

	/**
	 * Fetches an element from the autocompleter.
	 * 
	 * @param k
	 * @return
	 */
	AutocompleterElement<K, V> get(K k);

	/**
	 * Gets all elements
	 * 
	 * @return
	 */
	Collection<AutocompleterElement<K, V>> getAllElements();

	/**
	 * Remvoes an element
	 * 
	 * @param k
	 */
	boolean remove(K k);

	/**
	 * Replaces an existing element by another modified one.
	 * 
	 * @param e
	 */
	void replace(AutocompleterElement<K, V> e);

	/**
	 * Adds 1 to the element score.
	 * 
	 * @param k
	 */
	void increaseScore(K k);

	/**
	 * adds any number to the element score.
	 * 
	 * @param k
	 * @param score
	 */
	void increaseScore(K k, double score);

	/**
	 * Removes all autocompleter elements.
	 */
	void clear();

	/**
	 * Removes all autocompleter elements and clears the cache if true is passed
	 * in.
	 * 
	 * @param clearCache
	 */
	void clear(boolean clearCache);

	/**
	 * Returns a JSON String of the query result.
	 * 
	 * @param query
	 *            the query provided
	 * @return a JSON representation of the result
	 * @throws AutocompleterException
	 */
	String autocompleteJSON(String query);

	/**
	 * Returns a JSON String of the query result.
	 * 
	 * @param query
	 * @param numberOfResults
	 * @return
	 * @throws AutocompleterException
	 */
	String autocompleteJSON(String query, int numberOfResults);

	/**
	 * Provides a set of elements depending on the passed query. This method is
	 * useful in case the returned elements set should pass through another sort
	 * of filter before rendering the final result.<br>
	 * Note that if you pass false for trustPassedQuery then the query will get
	 * normalized; otherwise, the query will be treated as is.
	 * 
	 * @param query
	 * @param numberOfResults
	 * @param trustPassedQuery
	 * @return
	 */
	Set<AutocompleterElement<K, V>> autocomplete(String query,
			int numberOfResults, boolean trustPassedQuery)
			throws AutocompleterException;

	/**
	 * Returns the configurator used by this autocompleter.
	 * 
	 * @return
	 */
	AutocompleterConfigurator getConfigurator();

	/**
	 * Sets the configurator used by this autocompleter.
	 * 
	 * @param configurator
	 */
	void setConfigurator(AutocompleterConfigurator configurator);

	/**
	 * Reports the current definition and status of this autocompleter.
	 */
	void printReport();

}
