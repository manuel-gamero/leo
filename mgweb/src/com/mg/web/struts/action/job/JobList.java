package com.mg.web.struts.action.job;

import com.mg.dao.impl.JobsDAO;
import com.mg.model.Jobs;
import com.mg.service.ServiceLocator;
import com.mg.service.application.ApplicationServiceImpl;
import com.mg.web.struts.action.BasicListAction;

public class JobList extends BasicListAction<Jobs, JobsDAO>{

	private static final long serialVersionUID = 1155604242419622177L;	
	
	@Override
	public void prepare(){
		try{
			setItemList(ServiceLocator.getService(ApplicationServiceImpl.class).getJobs());
			setEntityClass(Jobs.class);
			setEntityClassDAO(JobsDAO.class);
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	@Override
	public String execute() {
		return SUCCESS;
	}

	@Override
	public String getTitle() {
		return "Jobs List";
	}
	
	@Override
	public String displayEntity(){
		super.displayEntity();
		return INPUT;
	}
	

	public String saveJob(){
		super.saveEntity();
		return SUCCESS;
	}
	
}
