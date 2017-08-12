package com.mg.service.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Timer;

import org.apache.log4j.Logger;

import com.mg.exception.InitializationException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceImpl;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.search.autocompleter.Autocompleter;
import com.mg.service.search.autocompleter.AutocompleterImpl;
import com.mg.service.search.autocompleter.core.AutocompleterConfigurator;
import com.mg.service.search.autocompleter.core.AutocompleterElement;
import com.mg.service.search.autocompleter.core.AutocompleterUnit;
import com.mg.service.search.autocompleter.task.SearchAutocompleterTask;

/**
 * Provides all search related logic in the system.
 * 
 * @author MJGP
 *
 */
public class SearchServiceImpl extends ServiceImpl implements SearchService {

	private static final Logger log = Logger.getLogger(SearchServiceImpl.class);
	
	// Java timers work with milliseconds
	private static final long TIME_UNIT_ONE_SECOND = 1000;
	private static final long TIME_UNIT_ONE_MINUTE = 60 * TIME_UNIT_ONE_SECOND;
	
	private static final SearchService INSTANCE = new SearchServiceImpl();
	
	/**
	 * A singleton search service
	 * 
	 * @return
	 */
	public static SearchService getInstance() {
		return INSTANCE;
	}
	
	private SearchServiceImpl() {
		if (INSTANCE != null) {
			throw new IllegalStateException(this.getClass()	+ " instance is already created");
		}
	}
	

	private Autocompleter<Long, AutocompleterUnit> seriesSearchAutocompleter;
	
	
	public Autocompleter<Long, AutocompleterUnit> getSeriesSearchAutocompleter() {
		return seriesSearchAutocompleter;
	}


	Timer mainSearchTimer;
	
	@Override
	public void initialize() throws InitializationException {
		AutocompleterConfigurator mainSearchConfigurator = AutocompleterConfigurator.DEFAULT;
		try {
			mainSearchConfigurator = ServiceLocator.getService(ConfigService.class).getSearchConfigurator();
		} catch (Exception e) {
			throw new InitializationException(
					"Something wrong with configuration service", e);
		} finally {
			// get sure we start up with functional autocompleters
			//mainSearchAutocompleter = new AutocompleterImpl<Long, AutocompleterUnit>(mainSearchConfigurator);
			seriesSearchAutocompleter = new AutocompleterImpl<Long, AutocompleterUnit>(mainSearchConfigurator);
			loadAutocompleterData();	
			scheduleTimers();
		}
	}
	

	@Override
	public void shutdown() {
		if (mainSearchTimer != null)
			mainSearchTimer.cancel();	
		
		//getMainSearchAutocompleter().printReport();
		getSeriesSearchAutocompleter().printReport();
	}

	private void scheduleTimers() {
		int refreshInterval = getSeriesSearchAutocompleter().getConfigurator()
				.getRefreshInterval();

		// Is configured to refresh?
		if (refreshInterval > 0) {
			mainSearchTimer = new Timer();
			mainSearchTimer.scheduleAtFixedRate(
					new SearchAutocompleterTask(), refreshInterval
							* TIME_UNIT_ONE_MINUTE, refreshInterval
							* TIME_UNIT_ONE_MINUTE);
		}
	}
	
	@Override
	public void loadAutocompleterData(){			
		
		try{
			/*
			 *  TODO This is only a test to make the autocompleter work
			 *  on game names.
			 *  You have to fill it up with the correct search terms
			 */
			SearchService searchService = ServiceLocator.getService(SearchService.class);
			
			Collection<AutocompleterElement<Long, AutocompleterUnit>> elements = new
			  ArrayList<AutocompleterElement<Long, AutocompleterUnit>>();
			AutocompleterUnit unit;
/*			Map<Long, String> map = searchService.getAllGamesMap();
			log.debug("autocompleter initializing:"+map.size());

			for (Map.Entry<Long, String>  entry : map.entrySet()) {
				unit = new AutocompleterUnit(entry.getKey(), entry.getValue());
				AutocompleterElement<Long, AutocompleterUnit> element = 
					new	AutocompleterElement<Long, AutocompleterUnit>(entry.getKey(), unit);
				elements.add(element);				
			} 

			searchService.getMainSearchAutocompleter().build(elements);
			
			// fill autocompleter for game series
			elements.clear();*/
			
			Map<Long, String> mapSeries = searchService.getAllSeriesMap(); 

			for (Map.Entry<Long, String>  entry : mapSeries.entrySet()) {
				unit = new AutocompleterUnit(entry.getKey(), entry.getValue());
				AutocompleterElement<Long, AutocompleterUnit> element = 
					new	AutocompleterElement<Long, AutocompleterUnit>(entry.getKey(), unit);
				elements.add(element);				
			} 

			searchService.getSeriesSearchAutocompleter().build(elements);
			
			
			
			
			if(log.isInfoEnabled()){
				log.debug("Search autocompleter has been successfully initialized.");
			}					
			
		}catch (ServiceLocatorException sle) {
			// TODO: handle exception
		}catch (ServiceException se) {
			// TODO: handle exception
		}		
	}

	@Override
	public Map<Long, String> getAllSeriesMap() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
