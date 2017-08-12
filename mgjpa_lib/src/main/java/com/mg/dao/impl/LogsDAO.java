package com.mg.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.model.Logs;

public class LogsDAO extends GenericDaoImpl<Logs> {
	
	private static final long serialVersionUID = 1L;

	public LogsDAO() {
		super(Logs.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public List<Logs> getLogsRangeDate(Date startDate, Date endDate){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);
		
		return findResults("select distinct l " +
						   " from Logs l " +
						   " where l.creationDate >= :startDate " +
						   " and l.creationDate <= :endDate ", parameters);
	}

}
