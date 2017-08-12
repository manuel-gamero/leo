package com.mg.service.search.autocompleter.core;

import java.util.Set;

/**
 * Normalizer interface
 * 
 * @author MJGP
 * 
 * @param <K>
 * @param <V>
 */
public interface NormalizerStrategy<K, V extends Completable<V>> {

	/**
	 * Normalizes the input String, stuff like lower casing, space removal,
	 * matching a limited set of characters etc...
	 * 
	 * @param input
	 * @return
	 */
	String normalize(String input);

	/**
	 * Generates JSON format result String.
	 * 
	 * @param query
	 * @param result
	 * @return
	 */
	String convertToJSON(String query, Set<AutocompleterElement<K, V>> result);

	/**
	 * Returns an empty JSON based result.
	 * 
	 * @param query
	 * @return
	 */
	String getDummyJSON(String query);

}
