package com.mg.service.cache;

import com.mg.exception.CacheException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Product;
import com.mg.service.Service;

/**
 * Cache service interface.
 * @author MJGP
 *
 */
public interface CacheService  extends Service{

	/**
	 * Initializes the cache manager, this should be called before using the
	 * cache.
	 */
	void initialize() throws CacheException, ServiceLocatorException;
	
	/**
	 * Gets the default cache.
	 * @return
	 */	
	Cache<Object> getDefaultCache();	
	
	/**
	 * Gets the reference to the product cache.
	 * @return
	 */
	Cache<Product> getProductCache();
	
	/**
	 * Shuts down the cache manager collects the statistics.
	 */
	void shutdown() throws CacheException;

	void resetCacheProduct() throws CacheException, ServiceLocatorException;
	
	void resetCacheDefault() throws CacheException, ServiceLocatorException;
	
	void resetCacheTranslations() throws CacheException, ServiceLocatorException;
	
	void removeProductDTO(int id) throws CacheException;

}
