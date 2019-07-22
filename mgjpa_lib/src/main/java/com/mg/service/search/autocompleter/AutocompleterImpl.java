package com.mg.service.search.autocompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.AutocompleterException;
import com.mg.service.search.autocompleter.cache.LRUCache;
import com.mg.service.search.autocompleter.cache.LRUCacheImpl;
import com.mg.service.search.autocompleter.core.AutocompleterConfigurator;
import com.mg.service.search.autocompleter.core.AutocompleterElement;
import com.mg.service.search.autocompleter.core.Completable;
import com.mg.service.search.autocompleter.core.DefaultNormalizerStrategy;
import com.mg.service.search.autocompleter.core.NormalizerStrategy;
/**
 * 
 * An implementation of the {@link Autocompleter} interface.<br>
 * Each autocmpleter is aware of what data it's working on, has its own
 * {@link AutocompleterConfigurator} that allows a smoother control on its
 * behavior and performance, manages its own LRU cache {@link LRUCache}, and it
 * knows the normalization strategy it must use to normalize queries,
 * autocompletion elements names, and the JSON data format it provides to the
 * external world. <br>
 * <br>
 * This approach gives us a flexible yet powerful component that can be used
 * generically for all autocompletion services.
 * 
 * @author MJGP
 * 
 * @param <K>
 * @param <V>
 */
public class AutocompleterImpl<K, V extends Completable<V>> implements
		Autocompleter<K, V> {

	private static final Logger log = LogManager.getLogger(AutocompleterImpl.class);
	
	/*
	 * Natural ordering tree set to host our elements. It is ordered by its
	 * element comparing strategy (compareTo) or by a determined comparator (if
	 * supplied through its constructor).
	 */
	private NavigableSet<AutocompleterElement<K, V>> elementsSet = new TreeSet<AutocompleterElement<K, V>>();

	// A least recently used cache (the eldest is removed when we exceed the
	// cache maximum size)
	private LRUCache<String, String> cache;

	// The configurator this autocompleter uses
	private AutocompleterConfigurator configurator;

	// The normalization strategy this autocompleter uses to clean Strings and
	// generate JSON
	private NormalizerStrategy<K, V> normalizer;

	// A score comparator that we use if this autocompleter is configured to
	// sort results by score
	private Comparator<AutocompleterElement<K, V>> comparator = new Comparator<AutocompleterElement<K, V>>() {
		public int compare(AutocompleterElement<K, V> first,
				AutocompleterElement<K, V> second) {
			double diff = first.getScore() - second.getScore();
			if (diff > 0) {
				return -1;
			} else if (diff < 0) {
				return 1;
			} else {
				return first.compareTo(second);
			}
		}
	};

	public AutocompleterImpl(AutocompleterConfigurator configurator) {
		this(configurator, new DefaultNormalizerStrategy<K, V>());
	}

	public AutocompleterImpl(AutocompleterConfigurator configurator,
			NormalizerStrategy<K, V> normalizer) {
		this.configurator = configurator;
		this.normalizer = normalizer;
		if (this.configurator.isCachingEnabled()) {
			this.cache = new LRUCacheImpl<String, String>(this.configurator
					.getNumberOfQueriesToCache());
		} else {
			this.cache = new LRUCacheImpl<String, String>(0);
		}
	}

	@Override
	public AutocompleterConfigurator getConfigurator() {
		return configurator;
	}

	@Override
	public void setConfigurator(AutocompleterConfigurator configurator) {
		this.configurator = configurator;
	}

	@Override
	public void build(Collection<AutocompleterElement<K, V>> elements) {
		clear();

		for (AutocompleterElement<K, V> e : elements) {
			add(e);
		}
	}

	@Override
	public void add(AutocompleterElement<K, V> e) {

		e.setNormalizedAutocompletionText(normalizer.normalize(e.getValue()
				.getAutocompletionText()));

		synchronized (elementsSet) {
			elementsSet.add(e);
		}
	}

	@Override
	public AutocompleterElement<K, V> get(K k) {
		AutocompleterElement<K, V> returnValue = null;
		for (AutocompleterElement<K, V> e : elementsSet) {
			if (e.getKey().equals(k)) {
				returnValue = e;
			}
		}

		return returnValue;
	}

	@Override
	public Collection<AutocompleterElement<K, V>> getAllElements() {
		return new ArrayList<AutocompleterElement<K, V>>(elementsSet);
	}

	@Override
	public boolean remove(K k) {
		synchronized (elementsSet) {
			return elementsSet.remove(get(k));
		}
	}

	@Override
	public void increaseScore(K k) {
		increaseScore(k, 1);
	}

	@Override
	public void increaseScore(K k, double score) {
		AutocompleterElement<K, V> e = get(k);
		if (e != null) {
			e.setScore(e.getScore() + score);
			replace(e);
		}
	}

	public void replace(AutocompleterElement<K, V> e) {
		remove(e.getKey());
		add(e);
	}

	@Override
	public void clear() {
		clear(true);
	}

	@Override
	public void clear(boolean clearCache) {
		synchronized (elementsSet) {
			elementsSet.clear();
		}

		if (clearCache) {
			cache.clear();
		}
	}

	@Override
	public String autocompleteJSON(String query) {
		return autocompleteJSON(query, configurator.getNumberOfResults());
	}

	@Override
	public String autocompleteJSON(String query, int numberOfResults) {
		String cleandQuery = normalizer.normalize(query);
		String result = null;

		try {
			if (cleandQuery != null && cleandQuery.length() > 0) {
				boolean considerCache = considerCache(cleandQuery.length());
				if (considerCache) {
					result = cache.get(cleandQuery);
				}

				if (result == null) {
					Set<AutocompleterElement<K, V>> resultSet = autocomplete(
							cleandQuery, numberOfResults, true);
					result = normalizer.convertToJSON(cleandQuery, resultSet);

					if (considerCache && resultSet.size() > 0) {
						cache.put(cleandQuery, result);
					}
				}
			}
		} catch (AutocompleterException e) {
			// Something went wrong while trying to serve query
			result = normalizer.getDummyJSON(cleandQuery);
		}
		
		return result;
	}

	/**
	 * A convenient method to check if a query should be considered for caching
	 * 
	 * @param queryLength
	 * @return
	 */
	private boolean considerCache(int queryLength) {
		return (getConfigurator().isCachingEnabled() && queryLength <= getConfigurator()
				.getNumberOfQueriesToCache());
	}

	@Override
	public Set<AutocompleterElement<K, V>> autocomplete(String query,
			int numberOfResults, boolean trustPassedQuery)
			throws AutocompleterException {

		Set<AutocompleterElement<K, V>> resultSet;
		try {
			/*
			 * I need a set implementation that keeps the insertion order
			 * (natural) and another one in case the result should be ordered by
			 * elements score.
			 */

			resultSet = (configurator.isOrderByScore()) ? new TreeSet<AutocompleterElement<K, V>>(
					this.comparator)
					: new LinkedHashSet<AutocompleterElement<K, V>>();

			if (!trustPassedQuery) {
				if (query == null || query.length() == 0) {
					return resultSet;
				}

				query = normalizer.normalize(query);
			}

			AutocompleterElement<K, V> fromElement = new AutocompleterElement<K, V>(
					null, null);
			AutocompleterElement<K, V> toElement = new AutocompleterElement<K, V>(
					null, null);

			// Consider the passed query as the element autocmpletion text, it
			// serves as a comparasion element
			fromElement.setNormalizedAutocompletionText(query);

			char lastQueryChar = query.charAt(query.length() - 1);
			String nextChar = query.substring(0, query.length() - 1)
					+ ((char) (lastQueryChar + 1));

			toElement.setNormalizedAutocompletionText(nextChar);

			NavigableSet<AutocompleterElement<K, V>> setPortionView = elementsSet
					.subSet(fromElement, true, toElement, false);

			for (AutocompleterElement<K, V> e : setPortionView) {

				if (e.getNormalizedAutocompletionText().startsWith(query)) {
					resultSet.add(e);
				}
			}

			if (resultSet.size() < numberOfResults) {
				if (configurator.isMatchInfix()) {
					setPortionView = elementsSet.headSet(fromElement, true);
					for (AutocompleterElement<K, V> e : setPortionView) {
						if (e.getNormalizedAutocompletionText().contains(query)) {
							resultSet.add(e);
						}
					}

					if (resultSet.size() < numberOfResults) {
						setPortionView = elementsSet.tailSet(toElement, true);
						for (AutocompleterElement<K, V> e : setPortionView) {
							if (e.getNormalizedAutocompletionText().contains(
									query)) {
								resultSet.add(e);
							}
						}
					}
				}
			}

			// Do we need to limit the returned result?
			if (resultSet.size() > numberOfResults) {
				Iterator<AutocompleterElement<K, V>> iter = resultSet
						.iterator();
				Set<AutocompleterElement<K, V>> limitedResultSet = new LinkedHashSet<AutocompleterElement<K, V>>();

				for (int i = 0; i < numberOfResults; i++) {
					limitedResultSet.add(iter.next());
				}

				resultSet = limitedResultSet;
			}

		} catch (Exception e) {
			throw new AutocompleterException(
					"Something went wrong while trying to serve query: "
							+ query, e);
		}

		return resultSet;
	}

	@Override
	public String toString() {
		return "Report generated on: " + new Date()
				+ "\nAutocompleter:{\nTotal elements=" + elementsSet.size()
				+ ", \nqueries in cache=" + cache.getNumberOfElementsInCache()
				+ ", \nConfigurator=" + getConfigurator().toString() + "\n}";
	}

	@Override
	public void printReport() {
		if(log.isDebugEnabled())
		log.debug(toString());
	}

}
