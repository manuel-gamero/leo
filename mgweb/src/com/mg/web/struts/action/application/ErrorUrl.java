package com.mg.web.struts.action.application;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;

import com.mg.web.struts.action.BasicAction;

public class ErrorUrl extends BasicAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private static final Logger log = LogManager.getLogger(ErrorUrl.class);
	
	@Override
	public void prepare() throws Exception {
	}
	
	@Override
	public String execute(){
		log.debug("Error no resource. ");
		ServletActionContext.getResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
		return SUCCESS;
	}

	
}
