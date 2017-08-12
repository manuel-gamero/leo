package com.mg.dao;

import java.util.List;

/**
 * Generic DAO that provides database's common CRUD operations. 
 * 
 * @author MJGP
 */

public interface GenericDao<T> extends BasicDao {

	public void flush() ;
	public void joinTransaction() ;
	public void save(T entity);
	public void delete(T entity) ;
	public T update(T entity) ;
	public T find(int entityID) ;
	public T findReferenceOnly(int entityID);
	public List<T> findAll() ;
}
