package com.mg.service.search.autocompleter.task;

import java.util.TimerTask;

import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.search.SearchServiceImpl;

/**
 * A timer task to be scheduled to fetch data from the DB on a predefined
 * interval.
 * 
 * @author MJGP
 * 
 */
public class SearchAutocompleterTask extends TimerTask {

	@Override
	public void run() {
		
			try {
				ServiceLocator.getService(SearchServiceImpl.class).loadAutocompleterData();
			} catch (ServiceLocatorException sle) {
				// TODO Auto-generated catch block
				sle.printStackTrace();
			}
		
	}

}
