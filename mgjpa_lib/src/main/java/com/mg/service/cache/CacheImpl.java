package com.mg.service.cache;

import java.util.List;

import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;

import com.mg.exception.CacheException;

/**
 * An implementation of {@link Cache} based on EhCache.
 * 
 * @author MJGP
 * @param <T>
 */
public class CacheImpl<T> implements Cache<T> {
	
	protected net.sf.ehcache.Cache cache;

	CacheImpl(net.sf.ehcache.Cache cache) {
		this(cache, true);
	}

	CacheImpl(net.sf.ehcache.Cache cache, boolean statisticsEnabled) {
		this.cache = cache;	
		setStatisticsEnabled(statisticsEnabled);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T fetch(String key) throws CacheException {
		try {
			
			if (key == null) {
				return null;
			} else {
				Element element = cache.get(key);				
				if (element == null) {
					return null;
				} else {
					return (T) element.getValue();
				}
			}
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void store(String key, T value) throws CacheException {
		try {			
			this.cache.put(new Element(key, value));
		} catch (IllegalArgumentException e) {
			throw new CacheException(e);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}
	
	@Override
	public boolean remove(String key) throws CacheException {
		try {
			return this.cache.remove(key);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		}
	}
	
	@Override
	public void invalidate() throws CacheException {
		try {
			this.cache.removeAll();
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public final List<String> getKeys() throws CacheException {
		try {
			return this.cache.getKeys();
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}
	
	@Override
	public void setStatisticsEnabled(boolean status){
		this.cache.setStatisticsEnabled(status);
	}
	
	@Override
	public boolean isStatisticsEnabled(){
		return this.cache.isStatisticsEnabled();
	}
	
	@Override
	public long getCacheHits() {
		try {
			Statistics stats = this.cache.getStatistics();			
			
			return stats.getCacheHits();
		} catch (IllegalStateException e) {			
			return 0;
		}

	}

	@Override
	public long getCacheMisses() {
		try {
			Statistics stats = this.cache.getStatistics();
			return stats.getCacheMisses();
		} catch (IllegalStateException e) {
			return 0;
		}

	}

	@Override
	public void clearStatistics() {
		this.cache.clearStatistics();
	}

	@Override
	public long getSizeInMemory() {
		try {
			return cache.calculateInMemorySize();
		} catch (IllegalStateException e) {
			return 0;
		} catch (net.sf.ehcache.CacheException e) {
			return 0;
		}
	}
	
	@Override
	public long getElementCountInMemory() {
		try {
			return cache.getSize();
		} catch (IllegalStateException e) {
			return 0;
		} catch (net.sf.ehcache.CacheException e) {
			return 0;
		}
	}

	@Override
	public long getElementCountOnDisk() {
		return cache.getDiskStoreSize();
	}
	
	@Override
	public String getCacheName() {
		return cache.getName();
	}	
	
	@Override
	public String printReport(){
		StringBuilder stringBuilder = new StringBuilder("\nReport On Cache: " + getCacheName() + "\n");		
		stringBuilder.append("Hits: " + getCacheHits() + "\n");
		stringBuilder.append("Misses: " + getCacheMisses() + "\n");
		stringBuilder.append("Size in memory: " + getSizeInMemory() + "\n");
		stringBuilder.append("Elements in memory: " + getElementCountInMemory() + "\n");
		stringBuilder.append("Elements on disk: " + getElementCountOnDisk() + "\n");		
		
		return stringBuilder.toString();
		
	}
	
	

}
