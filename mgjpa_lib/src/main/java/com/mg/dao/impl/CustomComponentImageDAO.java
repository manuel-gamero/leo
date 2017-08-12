package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.mg.model.CustomComponentImage;

public class CustomComponentImageDAO extends GenericDaoImpl<CustomComponentImage> {

	private static final long serialVersionUID = 1L;
	private static final String selectClause = " select distinct cci " +
												" from CustomComponentImage cci "+ 
												" join fetch cci.customComponentCollection ccc " +
												" left join fetch cci.imageByImageMaskId im " +
												" left join fetch ccc.translationByDescriptionTransId ccctn " +
												" left join fetch ccctn.translationEntries ten " +
												" left join fetch ccc.translationByNameTransId ccctd " +
												" left join fetch ccctd.translationEntries ted " + 
												" join fetch ccc.customComponent cc ";

	public CustomComponentImageDAO() {
		super(CustomComponentImage.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	@Fetch (FetchMode.SELECT)
	public List<CustomComponentImage> getAll(){
		return  findResults(selectClause +
							" where cci.statusCode = 'ACTIVE' ");
	}
	
	public CustomComponentImage findEntityById(int id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(selectClause +
							  " where cci.id = :id", parameters);
	}

}
