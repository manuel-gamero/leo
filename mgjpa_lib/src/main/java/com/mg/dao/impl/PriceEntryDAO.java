package com.mg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mg.model.PriceEntry;

public class PriceEntryDAO extends GenericDaoImpl<PriceEntry> {
	
	private static final long serialVersionUID = 1L;

	public PriceEntryDAO() {
		super(PriceEntry.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public PriceEntry findEntityById(int id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(" select distinct pe " +
							  " from PriceEntry pe " +
							  " where pe.id = :id", parameters);
	}
	
	
}
