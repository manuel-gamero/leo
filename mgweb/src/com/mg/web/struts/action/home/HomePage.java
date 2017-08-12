package com.mg.web.struts.action.home;

import org.apache.log4j.Logger;

import com.mg.annotation.Action;
import com.mg.exception.ServiceLocatorException;
import com.mg.web.RequestAtributeConstants;
import com.mg.web.struts.action.BasicAction;

/**
 * The main landing page action on the site.
 * 
 * @author MJGP
 *
 */
public class HomePage extends BasicAction {
		
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(HomePage.class);
	
	public HomePage() throws ServiceLocatorException {
		super();	
	}
	
	@Action
	@Override
	public String execute() {
		
		setPageTitleKey(TITLE_PAGE_PARAM);
		setPageFbTitle(TITLE_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_TITLE_PARAM_1, getText("bolsos.pages.home.title"));
		request.setAttribute(RequestAtributeConstants.PAGE_FB_TITLE_PARAM_1, getText("bolsos.pages.home.title"));
		/*
		// page header customization  
		request.setAttribute(RequestAtributeConstants.PAGE_TITLE_PARAM_1, gameInfo.getName());
		request.setAttribute(RequestAtributeConstants.PAGE_KEYWORDS_PARAM_1, gameInfo.getName());
		request.setAttribute(RequestAtributeConstants.PAGE_DESCRIPTION_PARAM_1, gameInfo.getName());
		*/
		
		return SUCCESS;
	}
	
}
