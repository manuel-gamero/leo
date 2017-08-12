package com.mg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mg.model.CouponsType;

public class CouponsTypeDAO extends GenericDaoImpl<CouponsType> {
	
	private static final long serialVersionUID = 1L;

	public CouponsTypeDAO() {
		super(CouponsType.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	public CouponsType find(String typeCode) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("typeCode", typeCode);    

		return  findOneResult(" select distinct c " +
							" from CouponsType c " +
							" where c.typeCode = :typeCode", parameters);
	}

}
