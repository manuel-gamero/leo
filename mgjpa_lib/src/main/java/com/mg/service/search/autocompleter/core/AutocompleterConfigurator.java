package com.mg.service.search.autocompleter.core;


/**
 * A representation of the autocompleter configuration.
 * 
 * @author MJGP
 * 
 */
public class AutocompleterConfigurator {
	
	public static final AutocompleterConfigurator DEFAULT = new AutocompleterConfigurator(
			"Default", 10, true, false, true, 500, 5, 60);

	public static AutocompleterConfigurator valueOf(String name, int numberOfResults,
			boolean matchInfix, boolean orderByScore, boolean chachingEnabled,
			int numberOfQueriesToCache, int queryLengthToCache,
			int refreshInterval){
		return new AutocompleterConfigurator(name, numberOfResults, matchInfix,
				orderByScore, chachingEnabled, numberOfQueriesToCache, queryLengthToCache, refreshInterval);
	}
	
	private String name;
	private int numberOfResults;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfResults() {
		return numberOfResults;
	}

	public void setNumberOfResults(int numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

	public boolean isMatchInfix() {
		return matchInfix;
	}

	public void setMatchInfix(boolean matchInfix) {
		this.matchInfix = matchInfix;
	}

	public boolean isOrderByScore() {
		return orderByScore;
	}

	public void setOrderByScore(boolean orderByScore) {
		this.orderByScore = orderByScore;
	}

	public boolean isCachingEnabled() {
		return cachingEnabled;
	}

	public void setCachingEnabled(boolean cachingEnabled) {
		this.cachingEnabled = cachingEnabled;
	}

	public int getNumberOfQueriesToCache() {
		return numberOfQueriesToCache;
	}

	public void setNumberOfQueriesToCache(int numberOfQueriesToCache) {
		this.numberOfQueriesToCache = numberOfQueriesToCache;
	}

	public int getQueryLengthToCache() {
		return queryLengthToCache;
	}

	public void setQueryLengthToCache(int queryLengthToCache) {
		this.queryLengthToCache = queryLengthToCache;
	}

	public int getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(int refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	private boolean matchInfix;
	private boolean orderByScore;
	private boolean cachingEnabled;
	private int numberOfQueriesToCache;
	private int queryLengthToCache;
	private int refreshInterval;

	private AutocompleterConfigurator(String name, int numberOfResults,
			boolean matchInfix, boolean orderByScore, boolean chachingEnabled,
			int numberOfQueriesToCache, int queryLengthToCache,
			int refreshInterval) {
		
		setName(name);
		setNumberOfResults(numberOfResults);
		setMatchInfix(matchInfix);
		setOrderByScore(orderByScore);
		setCachingEnabled(chachingEnabled);
		setNumberOfQueriesToCache(numberOfQueriesToCache);
		setQueryLengthToCache(queryLengthToCache);
		setRefreshInterval(refreshInterval);
	}
	
	@Override
	public String toString() {
		return "{" + "name=" + name + ", numberOfResults=" + numberOfResults
				+ ", matchInfix=" + matchInfix + ", orderByScore="
				+ orderByScore + ", chachingEnabled=" + cachingEnabled
				+ ", numberOfQueriesToCache=" + numberOfQueriesToCache
				+ ", queryLengthToCache=" + queryLengthToCache
				+ ", refreshInterval=" + refreshInterval + "}";
	}

}
