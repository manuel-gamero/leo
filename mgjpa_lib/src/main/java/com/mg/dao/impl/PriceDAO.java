package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.model.Price;

public class PriceDAO extends GenericDaoImpl<Price> {
	
	private static final long serialVersionUID = 1L;

	public PriceDAO() {
		super(Price.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public Price findEntityById(int id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(" select distinct p " +
							" from Price p " +
							" join fetch p.priceEntries pe " +
							" where p.id = :id", parameters);
	}
	
	public List<Price> getAllByProduct(Integer productId){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", productId);   
		
		return  findResults(" select distinct p " +
							" from Price p " +
							" join fetch p.priceEntries pe " +
							" join fetch p.products pr " +
							" where pr.id = :id", parameters);
	}
	
	public List<Price> getAllByMethodShipping(Integer methodId){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", methodId);   
		
		return  findResults(" select distinct p " +
							" from Price p " +
							" join fetch p.priceEntries pe " +
							" join fetch p.methodShippings m" +
							" where m.id = :id", parameters);
	}
}
