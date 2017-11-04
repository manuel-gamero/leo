package com.mg.web.struts.action.home;

import java.util.List;

import org.apache.log4j.Logger;

import com.mg.annotation.Action;
import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.InstagramDTO;
import com.mg.service.socialmedia.InstagramServiceImpl;
import com.mg.web.RequestAtributeConstants;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

/**
 * The main landing page action on the site.
 * 
 * @author MJGP
 *
 */
public class HomePage extends BasicAction {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(HomePage.class);
	private List<InstagramDTO> instagramFeed;

	public HomePage() throws ServiceLocatorException {
		super();
	}

	@Override
	public void prepare() throws Exception {
		super.prepare();
		instagramFeed = ServiceLocator.getService(InstagramServiceImpl.class).getFeed();
	}

	@Action
	@Override
	public String execute() {
		
		setPageTitleKey(TITLE_PAGE_PARAM);
		setPageFbTitle(TITLE_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_TITLE_PARAM_1, getText("bolsos.pages.home.title"));
		request.setAttribute(RequestAtributeConstants.PAGE_FB_TITLE_PARAM_1, getText("bolsos.pages.home.title"));
		/*
		 * // page header customization
		 * request.setAttribute(RequestAtributeConstants.PAGE_TITLE_PARAM_1,
		 * gameInfo.getName());
		 * request.setAttribute(RequestAtributeConstants.PAGE_KEYWORDS_PARAM_1,
		 * gameInfo.getName()); request.setAttribute(RequestAtributeConstants.
		 * PAGE_DESCRIPTION_PARAM_1, gameInfo.getName());
		 */

		return SUCCESS;
	}

	public List<InstagramDTO> getInstagramFeed() {
		return instagramFeed;
	}

	public void setInstagramFeed(List<InstagramDTO> instagramFeed) {
		this.instagramFeed = instagramFeed;
	}
}
