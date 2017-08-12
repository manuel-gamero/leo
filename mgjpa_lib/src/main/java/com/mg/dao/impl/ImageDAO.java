package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.mg.enums.ImageType;
import com.mg.model.Image;

public class ImageDAO extends GenericDaoImpl<Image> {

	private static final long serialVersionUID = 1L;

	public ImageDAO() {
		super(Image.class);
	}
	
	public Image findEntity(String fileName, ImageType type){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("realName", fileName);  
		parameters.put("type", type);  

		return findOneResult("select t from Image t " +
							 " where t.realName = :realName " +
							 " and typeCode = :type ", parameters);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	@Fetch (FetchMode.SELECT)
	public List<Image> getAll(){

		return  findResults(" select distinct i " +
							" from Image i " );
	}
	
	public Image findEntityById(int id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(" select  i " +
							" from Image i " +
							" where i.id = :id", parameters);
	}
	
	public boolean updateImageName(int id, String name){
		
		String query = "update Image set name = :name" +
					   " where id = :id";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    
		parameters.put("name", name);    
		
		updateQuery(query, parameters);
		
		return true;
	}
	
	public boolean updateImageLocation(int id, String location){
		
		String query = "update Image set realName = :location" +
					   " where id = :id";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    
		parameters.put("location", location);    
		
		updateQuery(query, parameters);
		
		return true;
	}

}
