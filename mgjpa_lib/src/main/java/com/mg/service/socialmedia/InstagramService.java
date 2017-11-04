package com.mg.service.socialmedia;

import java.util.List;

import com.mg.exception.InitializationException;
import com.mg.exception.ServiceException;
import com.mg.model.Audit;
import com.mg.model.Translation;
import com.mg.service.Service;
import com.mg.service.dto.InstagramDTO;
import com.mg.service.search.autocompleter.core.AutocompleterConfigurator;

/**
 * Configuration service interface that is used whenever a client desires
 * accessing configuration properties.
 *  
 * @author MJGP
 *
 */
public interface InstagramService extends Service{
	
	
	/**
	 * Initializes the Instagram service. 
	 * @throws InitializationException
	 */
	void initialize();
	
	/**
	 * @return the list of instagram images that they are in the feed.
	 */
	List<InstagramDTO> getFeed();
	
	/**
	 * Connect to Instagram to get the new feed. 
	 */
	
	void resetFeed();
	
}
