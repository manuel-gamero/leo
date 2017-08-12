package com.mg.web.struts.action.application;

import java.util.ArrayList;
import java.util.List;

import com.mg.exception.CacheException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Translation;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.web.struts.action.BasicAction;

public class TranslationList extends BasicAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private List<Translation> translationList;
	
	@Override
	public String execute() {
		try{
			setTranslationList(getTranslationCacheList());
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	private List<Translation> getTranslationCacheList() throws CacheException, ServiceLocatorException {
		List<String> keyList = ServiceLocator.getService(CacheServiceImpl.class).getTranslationCache().getKeys();
		List<Translation> translationList = new ArrayList<Translation>();
		for (String key : keyList) {
			translationList.add(ServiceLocator.getService(CacheServiceImpl.class).getTranslationCache().fetch(key));
		}
		return translationList;
	}

	public List<Translation> getTranslationList() {
		return translationList;
	}

	public void setTranslationList(List<Translation> translationList) {
		this.translationList = translationList;
	}


}
