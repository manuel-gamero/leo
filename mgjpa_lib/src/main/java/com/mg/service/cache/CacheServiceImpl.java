package com.mg.service.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;

import org.apache.log4j.Logger;

import com.mg.exception.CacheException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Collection;
import com.mg.model.CustomComponentImage;
import com.mg.model.Product;
import com.mg.model.Translation;
import com.mg.model.TranslationEntry;
import com.mg.service.ServiceImpl;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.ProductDTO;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.util.text.StringUtils;

/**
 * A service class to manage the caching subsystem on the site.
 * The service should be initialized on application startup, after initializing
 * the {@link ConfigService}. 
 * 
 * @author MJGP
 * 
 */
public class CacheServiceImpl extends ServiceImpl implements CacheService {

	private static final Logger log = Logger.getLogger(CacheServiceImpl.class);
	
	public static final String CACHE_CONFIG_FILE = "/ehcache.xml";
	
	public static final String DEFAULT_CACHE_NAME = "defaultCache";	
	public static final String PRODUCT_DTO_CACHE_NAME = "productDTOCache";
	public static final String COLLECTION_CACHE_NAME = "collectionCache";
	public static final String TRANSLATION_CACHE_NAME = "translationCache";	
	private static final Map<String, Integer> urlCache = new Hashtable<String, Integer>();
	
	private static final CacheService INSTANCE = new CacheServiceImpl();

	/**
	 * A singleton cache service
	 * 
	 * @return
	 */
	public CacheServiceImpl() {
		super();
	}
	
	private boolean cacheServiceInitialized = false;
	private CacheManager cacheManager;
	private Cache<Object> defaultCache;
	private Cache<Product> productCache;
	private Cache<Translation> translationCache;
	private Cache<Collection> collectionCache;
	
	protected CacheManager getCacheManager() {
		return cacheManager;
	}

	protected void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	public synchronized void initialize() throws CacheException, ServiceLocatorException {
		if(!isCacheServiceInitialized()){
			InputStream input = null;
			try {				
				input = CacheServiceImpl.class.getResourceAsStream(CACHE_CONFIG_FILE);
				setCacheManager(CacheManager.create(input));			
				initializeCaches();
				
				if(log.isInfoEnabled()){
					log.info("Cache service has been successfully initialized.");
				}
				
				setCacheServiceInitialized(true);
				
			} catch (net.sf.ehcache.CacheException ce) {
				setCacheServiceInitialized(false);
				throw new CacheException("Something went wrong while trying to initialize EhCache manager", ce);
			}finally{
				if(null != input){
					try{
						input.close();
					}catch (IOException ioe) {
						if(log.isDebugEnabled()){
							log.debug("Could not close the input stream upon initializing the cache service.");
						}
						
					}
					
				}
			}
		}		
	}

	/**
	 * Initializes all caches we want to use.
	 */
	private void initializeCaches() throws CacheException, ServiceLocatorException{
		log.debug("INIT CACHE");
		try{			
			boolean statisticsEnabled = ServiceLocator.getService(ConfigServiceImpl.class).isCacheStatisticsEnabled();
			boolean charged = ServiceLocator.getService(ConfigServiceImpl.class).isCacheStatisticsEnabled();
			
			// Initialize our default cache
				if (!cacheManager.cacheExists(DEFAULT_CACHE_NAME)){
					cacheManager.addCache(DEFAULT_CACHE_NAME);
				}
				
				if (!cacheManager.cacheExists(PRODUCT_DTO_CACHE_NAME)){
					cacheManager.addCache(PRODUCT_DTO_CACHE_NAME);
				}
				
				if (!cacheManager.cacheExists(TRANSLATION_CACHE_NAME)){
					cacheManager.addCache(TRANSLATION_CACHE_NAME);
				}
				
					setDefaultCache(new CacheImpl<Object>(cacheManager.getCache(DEFAULT_CACHE_NAME), statisticsEnabled));
					
					setProductCache(new CacheImpl<Product>(cacheManager.getCache(PRODUCT_DTO_CACHE_NAME), statisticsEnabled));
					
					setCollectionCache(new CacheImpl<Collection>(cacheManager.getCache(COLLECTION_CACHE_NAME), statisticsEnabled));
					
					setTranslationCache(new CacheImpl<Translation>(cacheManager.getCache(TRANSLATION_CACHE_NAME), statisticsEnabled));
					
					initializeProductCache();
					log.info("Product cache Initialized");
					initializeCollectionCache();
					log.info("Collection cache Initialized");
					initializeDefaultCache();
					log.info("Default cache Initialized");
					initializeTranslationCache();
					log.info("Translation cache Initialized");
					initializeUrlCache();
					log.info("UrlCache cache Initialized");
					
		}catch (IllegalStateException ise) {
			throw new CacheException(ise);
		}catch (net.sf.ehcache.CacheException ce) {
			throw new CacheException(ce);
		}
	}
	
	private void initializeCollectionCache() {
		try {
			int count = 0;
			List<Collection> collectionList = ServiceLocator.getService(CollectionServiceImpl.class).getAllActiveCollectionEntities();
			for (Collection collection : collectionList) {
				collectionCache.store(Collection.class + "_" + collection.getId(), collection);
				count++;
			}
			log.info( count + " elements stored in collectionCache");
		} catch (Exception e) {
			log.error(e.getCause());
		}		
	}

	private void initializeUrlCache() {
		try{
			List<Product> productList = ServiceLocator.getService(ProductServiceImpl.class).getAllProductForAdmin();
			for (Product item : productList) {
				if(item.getTranslationByNameTransId() != null){
					addUrlCache( item.getId(), translationCache.fetch(Translation.class + "_" + item.getTranslationByNameTransId().getId()) );
				}
			}
			
			List<Collection> collectionList = ServiceLocator.getService(CollectionServiceImpl.class).getAllCollection();
			for (Collection item : collectionList) {
				if(item.getTranslationByNameTransId() != null){
					addUrlCache( item.getId(), translationCache.fetch(Translation.class + "_" + item.getTranslationByNameTransId().getId()) );
				}
			}
			
		} catch (Exception e) {
			log.error(e.getCause(), e);
		}
		
	}

	private void initializeDefaultCache() {
		try{
			int count = 0;
			List<CustomComponentImage> list = ServiceLocator.getService(ImageServiceImpl.class).getAllCustomComponentImage();
			for (CustomComponentImage item : list) {
				defaultCache.store(CustomComponentImage.class + "_" + item.getId(), item);
				count++;
			}
			log.info( count + " elements stored in defaultCache");
		} catch (Exception e) {
			log.error(e.getCause());
		}
	}

	private void initializeProductCache() {
		try {
			int count = 0;
			List<Product> productList = ServiceLocator.getService(ProductServiceImpl.class).getAllProduct();
			for (Product product : productList) {
				productCache.store(Product.class + "_" + product.getId(), product);
				count++;
			}
			log.info( count + " elements stored in productCache");
		} catch (Exception e) {
			log.error(e.getCause());
		}
	}
	
	private void addUrlCache(Integer key, Translation translationByNameTransId) {
		for (TranslationEntry item : translationByNameTransId.getTranslationEntries()) {
			urlCache.put( StringUtils.generateUrl( item.getText() ), key );
		}
		
	}

	private void initializeTranslationCache() {
		try {
			int count = 0;
			List<Translation> translationList = ServiceLocator.getService(ConfigServiceImpl.class).getAllTranslation();
			for (Translation entry : translationList) {
				translationCache.store(Translation.class + "_" + entry.getId(), entry);
				count++;
			}
			log.info( count + " elements stored in productCache");
		} catch (Exception e) {
			log.error(e.getCause());
		}
	}

	public synchronized void shutdown() throws CacheException{		
		if (null != getCacheManager()) {
			
			if(log.isInfoEnabled()){
				log.info("The cache service is getting shut down...");
			}			
						
			collectStatistics();
						
			removeCaches();
			getCacheManager().shutdown();
			setCacheManager(null);
			setDefaultCache(null);
			setCacheServiceInitialized(false);			
		}
	}

	/**
	 * Collects the statistics and writes them to a log file
	 */
	private void collectStatistics() {
		printCacheStats(getDefaultCache());
		printCacheStats(getProductCache());
		printCacheStats(getCollectionCache());
		printCacheStats(getTranslationCache());
	}
	
	private void printCacheStats(Cache cache){
		// This may be later redirected to a separated log file.
		if(log.isInfoEnabled()){
			log.info(cache.printReport());
		}
	}
	
	private void removeCaches() throws CacheException{
		removeCache(DEFAULT_CACHE_NAME);
		removeCache(PRODUCT_DTO_CACHE_NAME);
		removeCache(COLLECTION_CACHE_NAME);
		removeCache(TRANSLATION_CACHE_NAME);
	}
	
	/**
	 * Destroys a cache by its name
	 */	
	private void removeCache(String cacheName) throws CacheException {
		try {
			if(log.isInfoEnabled()){
				log.info("Destroying cache: " + cacheName);
			}
			
			getCacheManager().removeCache(cacheName);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		}
	}
	

	@Override
	public Cache<Object> getDefaultCache() {
		return defaultCache;
	}

	public boolean isCacheServiceInitialized() {
		return cacheServiceInitialized;
	}

	public void setCacheServiceInitialized(boolean cacheServiceInitialized) {
		this.cacheServiceInitialized = cacheServiceInitialized;
	}

	public void setDefaultCache(Cache<Object> defaultCache) {
		this.defaultCache = defaultCache;
	}

	public Cache<Product> getProductCache() {
		return productCache;
	}

	public void setProductCache(Cache<Product> productCache) {
		this.productCache = productCache;
	}
	
	public void resetCacheProduct() throws CacheException, ServiceLocatorException{
		productCache.invalidate();
		initializeProductCache();
	}
	
	public void resetCacheCollection() throws CacheException, ServiceLocatorException{
		collectionCache.invalidate();
		initializeCollectionCache();
		urlCache.clear();
		initializeUrlCache();
	}
	
	public void resetCacheDefault() throws CacheException, ServiceLocatorException{
		defaultCache.invalidate();
		initializeDefaultCache();
	}
	
	public void resetCacheTranslations() throws CacheException, ServiceLocatorException{
		translationCache.invalidate();
		initializeTranslationCache();
	}

	public Cache<Translation> getTranslationCache() {
		return translationCache;
	}

	public void setTranslationCache(Cache<Translation> translationCache) {
		this.translationCache = translationCache;
	}

	public Map<String, Integer> getUrlcache() {
		return urlCache;
	}

	public Cache<Collection> getCollectionCache() {
		return collectionCache;
	}

	public void setCollectionCache(Cache<Collection> collectionCache) {
		this.collectionCache = collectionCache;
	}
	
	public void removeProductDTO(int id) throws CacheException{
		String productDTOKey = ProductDTO.class + "_" + id;
		for (String key : defaultCache.getKeys()) {
			if(key.startsWith(productDTOKey)){
				defaultCache.remove(key);
				break;
			}
		} 
	}
}
