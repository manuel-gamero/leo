package com.mg.service.cache;

import org.apache.log4j.Logger;

import com.mg.service.ServiceLocator;

public class ResetCacheDefaultTask extends Thread {

	protected Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void run() {
			try {
				ServiceLocator.getService(CacheServiceImpl.class).resetCacheDefault();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		
	}
}