package com.mg.service.search;

import java.util.List;
import java.util.Map;

import com.mg.exception.InitializationException;
import com.mg.exception.ServiceException;
import com.mg.model.Product;
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

		

	 /**
	 * However, when introducing Hibernate Search in an existing application, 
	 * you have to create an initial Lucene index for the data already present in your database.
	 * 
	 * This will rebuild your index to make sure your index and your database is in synch.
	 */
	void restart() throws ServiceException;
	
	List<Product> searchProduct( String query )throws ServiceException;
	
	
	
	
}