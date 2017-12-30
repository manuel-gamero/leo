package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public Jobs findByName(String jobName){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("jobName", jobName);    

		return  findOneResult(" select j from Jobs j " +
							  " where j.name = :jobName " , parameters);
	}

}
