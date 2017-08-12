package com.mg.service.cache;

import java.util.List;

import com.mg.exception.CacheException;

/**
 * Cache interface.
 * 
 * @author MJGP
 * 
 * @param <T>
 */
public interface Cache<T> {

	/**
	 * Fetches an entry from this cache
	 * 
	 * @param key
	 *            used
	 * @return
	 */
	T fetch(String key) throws CacheException;

	/**
	 * Stores a new entry in this cache
	 * 
	 * @param key
	 * @param value
	 */
	void store(String key, T value) throws CacheException;

	/**
	 * Removes an entry from this cache
	 * 
	 * @param key
	 */
	boolean remove(String key) throws CacheException;

	/**
	 * Removes all entries from this cache.
	 * 
	 * @param results
	 */
	void invalidate() throws CacheException;

	/**
	 * Gets the list of keys in this cache
	 * 
	 * @return list of keys
	 */
	List<String> getKeys() throws CacheException;

	/**
	 * Enable or disable statistics status.
	 * @param status
	 */
	void setStatisticsEnabled(boolean status);
	
	/**
	 * States whether the statistics are enabled or no.
	 * @return
	 */
	boolean isStatisticsEnabled();
	
	/**
	 * Returns the number of hits
	 * 
	 * @return long value
	 */
	long getCacheHits();

	/**
	 * Returns the number of misses
	 * 
	 * @return long value
	 */
	long getCacheMisses();

	/**
	 * Clears this cache's statistics
	 */
	void clearStatistics();

	/**
	 * Gets the size of the memory store for this cache Warning: This method can
	 * be very expensive to run. Allow approximately 1 second per 1MB of
	 * entries. Running this method could create liveness problems because the
	 * object lock is held for a long period
	 * 
	 * @return the approximate size of the memory store in bytes
	 */
	long getSizeInMemory();

	/**
	 * The count of elements currently contained in the cache.
	 * 
	 * @return The count of element.
	 */
	long getElementCountInMemory();

	/**
	 * The count of elements currently contained in the cache disk store.
	 * 
	 * @return the count of elements on disk
	 */
	long getElementCountOnDisk();

	/**
	 * Get the name of the cache region
	 */
	String getCacheName();
	
	/**
	 * Prints this cache statistics repory
	 * @return
	 */
	String printReport();
	
}
