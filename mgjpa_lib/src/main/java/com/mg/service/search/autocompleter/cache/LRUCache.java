package com.mg.service.search.autocompleter.cache;

import java.util.Collection;
import java.util.Map;

/**
 * Least Recently Used cache interface
 * 
 * @author MJGP
 * 
 * @param <K>
 * @param <V>
 */
public interface LRUCache<K, V> {

	/**
	 * Fetches an element from the cache
	 * 
	 * @param k
	 * @return
	 */
	V get(K k);

	/**
	 * Checks whether the cache contains this key or no
	 * 
	 * @param k
	 * @return
	 */
	boolean containsKey(K k);

	/**
	 * Saves an element in the cache
	 * 
	 * @param k
	 * @param v
	 */
	void put(K k, V v);

	/**
	 * Removes an element from the cache
	 * 
	 * @param k
	 */
	V remove(K k);

	/**
	 * Check if the cache is empty
	 * 
	 * @return
	 */
	boolean isEmpty();

	/**
	 * Clears all elements in the cache
	 */
	void clear();

	/**
	 * Returns the current number of elements in the cache
	 * 
	 * @return
	 */
	int getNumberOfElementsInCache();

	/**
	 * Gets a collection of cache elements
	 * 
	 * @return
	 */
	Collection<Map.Entry<K, V>> getAll();

}
