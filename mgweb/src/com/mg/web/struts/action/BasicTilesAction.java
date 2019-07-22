package com.mg.web.struts.action;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.ServiceLocatorException;
import com.mg.web.RequestAtributeConstants;

/**
 * The main landing page action on the site.
 * 
 * @author MJGP
 *
 */
public class BasicTilesAction extends BasicAction {
		
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(BasicTilesAction.class);
	private String resource;
	
	public BasicTilesAction() throws ServiceLocatorException {
		super();	
	}

	@Override
	public String execute() {
		setMetadataPage();
		return SUCCESS;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	private void setMetadataPage() {
		if ( resource != null){
			String namePageResource = getText("bolsos.pages." + resource + ".title");
			if(namePageResource!=null){
				setPageTitleKey(TITLE_PAGE_PARAM);
				setPageFbTitle(TITLE_PAGE_PARAM);
				request.setAttribute(RequestAtributeConstants.PAGE_TITLE_PARAM_1, namePageResource);
				request.setAttribute(RequestAtributeConstants.PAGE_FB_TITLE_PARAM_1, namePageResource);
			}
			String descPageResource = getText("bolsos.pages." + resource + ".description");
			if(descPageResource != null){
				setPageDescriptionKey(DESCR_PAGE_PARAM);
				setPageFbDescription(DESCR_PAGE_PARAM);
				request.setAttribute(RequestAtributeConstants.PAGE_DESCRIPTION_PARAM_1 , descPageResource);
				request.setAttribute(RequestAtributeConstants.PAGE_FB_DESCRIPTION_PARAM_1, descPageResource);
			}
		}
	}
}
