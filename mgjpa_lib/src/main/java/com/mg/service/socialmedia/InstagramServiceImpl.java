package com.mg.service.socialmedia;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;
import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;

import com.mg.service.ServiceImpl;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.InstagramDTO;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.exception.ExceptionHandler;

/**
 * This is the service to deal with instagram to get the feed and to keep update the feed 
 * for l'atelier de leo.
 * 
 * @author MJGP
 *
 */
public class InstagramServiceImpl extends ServiceImpl implements InstagramService{
	
	private static final Logger log = LogManager.getLogger(InstagramServiceImpl.class);

	private static List<InstagramDTO> feed;
	private static final int MAX_NUM = 12;
	
	@Override
	public synchronized void initialize() {
		log.debug("initialize InstagramServiceImpl" );
		List<InstagramDTO> instagramFeed = getFeedFromInstagram();
		
		if( instagramFeed != null ){
			log.debug("instagramFeed inizialize with "+ instagramFeed.size() + " images" );
			//If we get images from Instagram
			feed = instagramFeed;
		}
	}

	@Override
	public List<InstagramDTO> getFeed() {
		if( feed == null ){
			initialize();
		}
		return feed;
	}

	@Override
	public void resetFeed() {
		log.debug("reset feed for InstagramServiceImpl" );
		initialize();
	}
		
	private List<InstagramDTO> getFeedFromInstagram(){
		List<InstagramDTO> instagramFeed = null;
		try {
			String accessToken = ServiceLocator.getService(ConfigServiceImpl.class).getInstagramAccessToken();
			String clientSecret = ServiceLocator.getService(ConfigServiceImpl.class).getInstagramClientSecret();
			String userId = ServiceLocator.getService(ConfigServiceImpl.class).getInstagramUserId();

			log.debug("accessToken: " + accessToken);
			log.debug("log.debug clientSecret: " + clientSecret);
			log.debug("userId: " + userId);

			Token token = new Token(accessToken, clientSecret);
			Instagram instagram = new Instagram(token);

			MediaFeed mediaFeed;

			mediaFeed = instagram.getRecentMediaFeed(userId);
			List<MediaFeedData> mediaFeeds = mediaFeed.getData();

			log.debug("mediaFeeds.size: " + mediaFeeds.size());
			if (mediaFeeds.size() > 0) {
				int i = 0;
				instagramFeed = new ArrayList<InstagramDTO>();
				for (MediaFeedData mediaData : mediaFeeds) {
					log.debug("mediaData.getLink(): " + mediaData.getLink());
					log.debug("mediaData.getImages().getStandardResolution().getImageUrl(): "
							+ mediaData.getImages().getStandardResolution().getImageUrl());
					instagramFeed.add(new InstagramDTO(mediaData.getLink(),
							mediaData.getImages().getStandardResolution().getImageUrl()));
					i++;
					
					if(i >= MAX_NUM){
						break;
					}
				}
			}

		} catch (Exception e) {
			ExceptionHandler.handleException(e, null, null);
		}
		
		return instagramFeed;
	}
	
}
