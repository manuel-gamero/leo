package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.enums.Country;
import com.mg.model.MethodShipping;

public class MethodShippingDAO extends GenericDaoImpl<MethodShipping> {
	
	private static final long serialVersionUID = 1L;

	public MethodShippingDAO() {
		super(MethodShipping.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public MethodShipping findEntityById(int id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(" select distinct ms " +
							" from MethodShipping ms " +
							//" join fetch ms.translationByNameTransId mstn " +
							//" left join fetch mstn.translationEntries ten " +
							//" join fetch ms.translationByDescriptionTransId mstd " +
							//" left join fetch mstd.translationEntries ted " +
							" left join fetch ms.price ppr " +
							" left join fetch ppr.priceEntries " +
							" where ms.id = :id", parameters);
	}
	
	public MethodShipping findEntityByCode(String code){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", code);    

		return  findOneResult(" select distinct ms " +
							" from MethodShipping ms " +
							//" join fetch ms.translationByNameTransId mstn " +
							//" left join fetch mstn.translationEntries ten " +
							//" join fetch ms.translationByDescriptionTransId mstd " +
							//" left join fetch mstd.translationEntries ted " +
							" left join fetch ms.price ppr " +
							" left join fetch ppr.priceEntries " +
							" where ms.code = :code", parameters);
	}
	
	public List<MethodShipping> getAll(Country country){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("country", country);   
		
		return  findResults(" select distinct ms " +
							" from MethodShipping ms " +
							//" join fetch ms.translationByNameTransId mstn " +
							//" left join fetch mstn.translationEntries ten " +
							//" join fetch ms.translationByDescriptionTransId mstd " +
							//" left join fetch mstd.translationEntries ted " +
							" left join fetch ms.price ppr " +
							" left join fetch ppr.priceEntries " +
							" where ms.country = :country " +
							" and ms.statusCode = 'ACTIVE'", parameters);
	}
	
	public List<MethodShipping> getAll(){  
		
		return  findResults(" select distinct ms " +
							" from MethodShipping ms "/* +
							" join fetch ms.translationByNameTransId mstn " +
							" left join fetch mstn.translationEntries ten " +
							" join fetch ms.translationByDescriptionTransId mstd " +
							" left join fetch mstd.translationEntries ted "*/);
	}
}
