package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.mg.model.Collection;

public class CollectionDAO extends GenericDaoImpl<Collection> {

	private static final long serialVersionUID = 1L;

	public CollectionDAO() {
		super(Collection.class);
	}
	
	public Collection findEntity(String code){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", code);    

		return findOneResult("select t from Collection t where t.code = :code", parameters);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	@Fetch (FetchMode.SELECT)
	public List<Collection> getAll(){

		return  findResults(" select  c " +
							" from Collection c " );
	}
	
	public List<Collection> getAllEntities(){

		return  findResults(" select distinct c " +
							" from Collection c " +
							" left join fetch c.customComponentCollections ccc" +
							" left join fetch ccc.customComponent cc" +
							" left join fetch ccc.image ccci" +
							" left join fetch c.image ci " +
							" order by cc.code asc " );
	}
	
	public List<Collection> getAllActiveEntities(){

		return  findResults(" select distinct c " +
							" from Collection c " +
							" left join fetch c.customComponentCollections ccc" +
							" left join fetch ccc.customComponent cc" +
							" left join fetch ccc.image ccci" +
							" left join fetch c.image ci " +
							" where ccc.statusCode = 'ACTIVE' " +
							" order by cc.code asc " );
	}

	public Collection findEntityById(int id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(" select distinct c " +
							" from Collection c " +
							" left join fetch c.customComponentCollections ccc" +
							" left join fetch ccc.customComponent cc" +
							" left join fetch ccc.image ccci" +
							" left join fetch c.image ci " +
							" where c.id = :id " +
							" order by cc.code asc", parameters);
	}

	public List<Collection> getAllCollectionCount(){
		return findResults("select distinct c, (select count(*) from Collection t where t.id = c.id) as count " +
						   " from Collection c " +
						   " join fetch c.image ci " +
						   " where c.statusCode = 'ACTIVE' ");
	}
}
