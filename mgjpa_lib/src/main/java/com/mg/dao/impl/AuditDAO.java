package com.mg.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.model.Audit;

public class AuditDAO extends GenericDaoImpl<Audit> {
	
	private static final long serialVersionUID = 1L;

	public AuditDAO() {
		super(Audit.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public List<Audit> getAuditRangeDate(Date startDate, Date endDate){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);
		
		return findResults("select distinct a " +
						   " from Audit a " +
						   " where a.creationDate >= :startDate " +
						   " and a.creationDate <= :endDate " +
						   " order by a.sessionGuid, a.creationDate asc ", parameters);
	}

}
