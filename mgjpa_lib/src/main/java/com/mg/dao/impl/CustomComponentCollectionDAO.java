package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.mg.model.CustomComponentCollection;

public class CustomComponentCollectionDAO extends GenericDaoImpl<CustomComponentCollection> {

	private static final long serialVersionUID = 1L;

	public CustomComponentCollectionDAO() {
		super(CustomComponentCollection.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	@Fetch (FetchMode.SELECT)
	public List<CustomComponentCollection> getAll(){

		return  findResults(" select distinct ccc " +
							" from CustomComponentCollection ccc " );
	}
	
	public CustomComponentCollection findEntityById(int id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(" select distinct ccc " +
							" from CustomComponentCollection ccc " +
							" join fetch ccc.customComponent cc " +
							" join fetch ccc.collection c " +
							" join fetch ccc.image im " +
							" left join fetch ccc.customComponentImages cci " +
							//" left join fetch ccc.translationByDescriptionTransId ccctn " +
							//" left join fetch ccctn.translationEntries ten " +
							//" left join fetch ccc.translationByNameTransId ccctd " +
							//" left join fetch ccctd.translationEntries ted " +
							" where ccc.id = :id", parameters);
	}
	
	public CustomComponentCollection findEntityByImage(String imageName, Integer customComponentId){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("imageName", imageName);    
		parameters.put("customComponentId", customComponentId);    

		return  findOneResult(" select distinct ccc " +
							" from CustomComponentCollection ccc " +
							" join fetch ccc.customComponent cc " +
							" join fetch ccc.image im " +
							" where im.name = :imageName " +
							" and cc.id = :customComponentId ", parameters);
	}
	
	/**
	 * @param imageName
	 * @return CustomComponentCollection list without translation. In order to provide a translation for 
	 * these that don´t have it.
	 * That happens at add/edit translation CustomComponentCollection time.Once I change the translation
	 * in thre translation's popup
	 */
	public List<CustomComponentCollection> findEntityByImageForTranslate(String imageName){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("imageName", imageName);       

		return  findResults(" select distinct ccc " +
							" from CustomComponentCollection ccc " +
							" join fetch ccc.image im " +
							" where im.name = :imageName " + 
							" and ( ( ccc.translationByNameTransId is null ) or " +
							"       ( ccc.translationByDescriptionTransId is null ) ) ", parameters);
	}
	
	/**
	 * @param imageName
	 * @return CustomComponentCollection to reuse its translations.
	 * Searching image with same name and get its translation to be reused.
	 */
	public CustomComponentCollection findTranslateForCustomCompoentCollection(String imageName){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("imageName", imageName);       

		List<CustomComponentCollection> resultList =  findResults(" select distinct ccc " +
							" from CustomComponentCollection ccc " +
							" join fetch ccc.image im " +
							" where im.name = :imageName " + 
							" and ccc.translationByNameTransId is not null " +
							" and ccc.translationByDescriptionTransId is not null ", parameters);
		return (resultList.size() > 0 ? resultList.get(0) : null);
	}
}
