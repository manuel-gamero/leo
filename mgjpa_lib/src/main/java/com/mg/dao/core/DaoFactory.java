package com.mg.dao.core;

import javax.persistence.EntityManager;

import com.mg.dao.BasicDao;

/**
 * Defines DAOs, manages transaction boundaries, and assures that 
 * database operations get executed using the same 
 * transaction (The {@link SqlSession} in MyBatis world).
 *  
 * @author MJGP
 * 
 */
public class DaoFactory extends DaoManagerImpl {
	
	
	public static <T extends BasicDao> T getDAO(Class<T> clazz) {
		// use conversation's entityManager by default
		return DaoFactory.getDAO(clazz,SqlEntityManager.getEntityManager());
	}

	public static <T extends BasicDao> T getDAO(Class<T> clazz,
			EntityManager entityManager) {
		T t;
		try {
			t = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		t.setEntityManager(entityManager);
		return t;
	}
	
}
