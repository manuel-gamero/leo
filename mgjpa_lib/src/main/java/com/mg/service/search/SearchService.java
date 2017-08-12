package com.mg.service.search;

import java.util.Map;

import com.mg.exception.InitializationException;
import com.mg.exception.ServiceException;
import com.mg.service.Service;
import com.mg.service.search.autocompleter.Autocompleter;
import com.mg.service.search.autocompleter.core.AutocompleterUnit;

/**
 * Search service interface.
 * 
 * @author MJGP
 *
 */
public interface SearchService extends Service{
	
	/**
	 * Initializes search service.
	 * 
	 * @throws InitializationException
	 */
	public void initialize() throws InitializationException;
	
	/**
	 * Cleans up and collects important data.
	 */
	void shutdown();
	
	/**
	 * Returns the seach autocompleter.
	 * @return
	 */
	//Autocompleter<Long, AutocompleterUnit> getMainSearchAutocompleter();
	
	
	/**
	 * Returns the seach autocompleter.
	 * @return
	 */
	Autocompleter<Long, AutocompleterUnit> getSeriesSearchAutocompleter();
	
	/**
	 * Initializes autocompleter's data from DB, XML, or
	 * a remote webservice.
	 */
	 void loadAutocompleterData();
	 
	 Map<Long, String> getAllSeriesMap() throws ServiceException;

		

	
	
	
	
}