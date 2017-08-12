package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.model.Translation;

public class TranslationDAO extends GenericDaoImpl<Translation> {

	private static final long serialVersionUID = 1L;

	public TranslationDAO() {
		super(Translation.class);
	}
	

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	public List<Translation> getAll(){
		return  findResults(" select distinct t from Translation t " +
							" join fetch t.translationEntries te " );
	}
	
	public Translation findEntityById(Integer id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    
		
		return  findOneResult(" select distinct t from Translation t " +
							" join fetch t.translationEntries te " +
							" where t.id = :id", parameters );
	}
}
