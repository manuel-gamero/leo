package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.mg.model.CustomComponent;

public class CustomComponentDAO extends GenericDaoImpl<CustomComponent> {

	private static final long serialVersionUID = 1L;

	public CustomComponentDAO() {
		super(CustomComponent.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub

	}
	
	@Fetch (FetchMode.SELECT)
	public List<CustomComponent> getAll(){

		return  findResults(" select distinct c " +
							" from CustomComponent c " );//+
							//" join fetch c.customComponentCollections ");
	}
	
	public CustomComponent findEntityById(int id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(" select  c " +
							" from CustomComponent c " +
							" join fetch c.customComponentCollections " +
							" where c.id = :id", parameters);
	}
	

}
