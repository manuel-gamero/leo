package com.mg.service.search.autocompleter.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A LRU cache implementation based on {@link LinkedHashMap}
 * 
 * @author MJGP
 * 
 * @param <K>
 * @param <V>
 */
public class LRUCacheImpl<K, V> implements LRUCache<K, V> {

	private static final float loadFactor = 0.70f;

	private Map<K, V> linkedMap;
	private int cacheSize;

	public LRUCacheImpl(int cacheSize) {
		this.cacheSize = cacheSize;
		int linkedHashMapCapacity = (int) Math.ceil(cacheSize / loadFactor) + 1;

		linkedMap = new LinkedHashMap<K, V>(linkedHashMapCapacity, loadFactor,
				true) {

			private static final long serialVersionUID = -1975313131823607851L;

			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > LRUCacheImpl.this.cacheSize;
			}
		};
	}

	@Override
	public synchronized V get(K k) {
		return linkedMap.get(k);
	}

	@Override
	public synchronized boolean containsKey(K k) {
		return linkedMap.containsKey(k);
	}

	@Override
	public synchronized void put(K k, V v) {
		linkedMap.put(k, v);
	}

	@Override
	public synchronized V remove(K k) {
		return linkedMap.remove(k);
	}

	@Override
	public synchronized boolean isEmpty() {
		return linkedMap.isEmpty();
	}

	@Override
	public synchronized void clear() {
		linkedMap.clear();
	}

	@Override
	public synchronized int getNumberOfElementsInCache() {
		return linkedMap.size();
	}

	@Override
	public synchronized Collection<Map.Entry<K, V>> getAll() {
		return new ArrayList<Map.Entry<K, V>>(linkedMap.entrySet());
	}

}
