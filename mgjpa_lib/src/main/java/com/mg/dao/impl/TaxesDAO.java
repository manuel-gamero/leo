package com.mg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mg.model.Taxes;

public class TaxesDAO extends GenericDaoImpl<Taxes> {
	
	private static final long serialVersionUID = 1L;

	public TaxesDAO() {
		super(Taxes.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public Taxes find(String code) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", code);    

		return  findOneResult(" select distinct t " +
							" from Taxes t " +
							" where t.code = :code", parameters);
	}


}
