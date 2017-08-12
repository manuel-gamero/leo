package com.mg.web.struts.action.tools;

import org.apache.log4j.Logger;

import com.mg.service.cache.ResetCacheDefaultTask;
import com.mg.service.cache.ResetCacheProductTask;
import com.mg.service.cache.ResetCacheTranslationsTask;
import com.mg.service.cache.ResetCollectionsTask;
import com.mg.web.struts.action.BasicAction;

public class ResetCacheProduct extends BasicAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private static Logger log = Logger.getLogger(ResetCacheProduct.class);	
	
	
	@Override
	public String execute(){
		try{
			log.debug("Refresh product cache Init");
			(new ResetCacheProductTask()).start();
			log.debug("Refresh product cache End");
			
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String resetDefaultCache(){
		try{
			log.debug("Refresh default cache Init");
			(new ResetCacheDefaultTask()).start();
			log.debug("Refresh default cache End");
			
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}
	
	public String resetTranslationsCache(){
		try{
			log.debug("Refresh translations cache Init");
			(new ResetCacheTranslationsTask()).start();
			log.debug("Refresh translations cache End");
			
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}
	
	public String resetCollections(){
		try{
			log.debug("Refresh Collections cache Init");
			(new ResetCollectionsTask()).start();
			log.debug("Refresh Collections cache End");
			
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}
	


}
