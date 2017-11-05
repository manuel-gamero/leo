package com.mg.dao.impl;

import java.util.List;
import com.mg.model.Jobs;

public class JobsDAO extends GenericDaoImpl<Jobs> {
	
	private static final long serialVersionUID = 1L;

	public JobsDAO() {
		super(Jobs.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public List<Jobs> getJobs(){		
		return findAll();
	}

}
