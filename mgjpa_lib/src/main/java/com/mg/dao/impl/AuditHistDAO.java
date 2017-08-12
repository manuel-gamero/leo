package com.mg.dao.impl;

import com.mg.model.AuditHist;

public class AuditHistDAO extends GenericDaoImpl<AuditHist> {
	
	private static final long serialVersionUID = 1L;

	public AuditHistDAO() {
		super(AuditHist.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

}
