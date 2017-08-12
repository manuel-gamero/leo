package com.mg.web.struts.action.ajax;

import org.apache.log4j.Logger;

import com.mg.annotation.Action;
import com.mg.exception.ServiceLocatorException;
import com.mg.web.struts.action.BasicAction;

/**
 * The main landing page action on the site.
 * 
 * @author MJGP
 *
 */
public class AuditActionAjax extends BasicAction {
		
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AuditActionAjax.class);
	private String audit;
	
	public AuditActionAjax() throws ServiceLocatorException {
		super();	
	}

	@Override
	@Action(value="audit = #audit ")
	public String execute() {
		return SUCCESS;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	
}
