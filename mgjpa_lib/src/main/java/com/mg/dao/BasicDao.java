package com.mg.dao;

import javax.persistence.EntityManager;

/**
 * An interface that All DAOs should implement.
 * 
 * @author MJGP
 * 
 */
public interface BasicDao {	
	
	void setEntityManager(EntityManager entityManager);
}
