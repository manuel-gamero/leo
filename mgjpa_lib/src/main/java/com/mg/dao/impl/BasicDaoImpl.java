package com.mg.dao.impl;

import javax.persistence.EntityManager;

import com.mg.dao.BasicDao;

/**
 * 
 * @author MJGP
 *
 */
class BasicDaoImpl implements BasicDao{
	
	protected EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
		
}
