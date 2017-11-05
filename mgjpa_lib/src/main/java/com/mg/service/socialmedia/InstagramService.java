package com.mg.service.socialmedia;

import java.util.List;

import com.mg.exception.InitializationException;
import com.mg.service.Service;
import com.mg.service.dto.InstagramDTO;

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
