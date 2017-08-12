package com.mg.dao.core;

import javax.persistence.EntityManager;

import com.mg.exception.DaoException;


/**
 * It should be implemented by all database access operations.
 * The logic that communicates with the DB should go inside 
 * the execute method. 
 * 
 * @author MJGP 
 */
public interface DaoCommand {
	
	/**
	 * The logic this method encapsulates will be passed to
	 * a an implementation of a {@link DaoManager} where it
	 * gets executed.
	 * @param entityManager 
	 *  
	 * @return the result object or null.
	 */
	Object execute(EntityManager entityManager) throws DaoException;
}
