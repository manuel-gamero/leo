package com.mg.web.struts.action.application;

import java.util.Date;
import java.util.List;

import com.mg.model.Audit;
import com.mg.service.ServiceLocator;
import com.mg.service.application.ApplicationServiceImpl;
import com.mg.web.struts.action.BasicAction;

public class AuditList extends BasicAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private List<Audit> auditList;
	private Date startDate;
	private Date endDate;
	
	@Override
	public String execute() {
		try{
			if(startDate != null && endDate != null ){
				setAuditList( ServiceLocator.getService(ApplicationServiceImpl.class).getAuditByDates(startDate, endDate) );
			}
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Audit> getAuditList() {
		return auditList;
	}

	public void setAuditList(List<Audit> auditList) {
		this.auditList = auditList;
	}


	

}
