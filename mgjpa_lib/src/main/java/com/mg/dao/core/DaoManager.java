package com.mg.dao.core;

import com.mg.exception.DaoException;


/**
 * DAO manager interface.
 * It provides a unique access point to all DAOs in the system as
 * well as other methods needed to manipulate transactions.
 * 
 * @author MJGP
 *
 */
public interface DaoManager {
	
	/**
	 * Sets commit status to TRUE or FALSE. 
	 * @param commitTransaction
	 */
	void setCommitTransaction(boolean commitTransaction);
	
	/**
	 * Indicates whether the transaction is set to be committed.
	 * @return
	 */
	boolean isCommitTransaction();	
	
	/**
	 * All Database access operation should invoke this method
	 * for it is designed to handle acquiring connections to the DB, 
	 * committing and rolling back transactions, handling exception,
	 * and cleaning up the transaction garbage.	
	 * 
	 * @param command the DAO command used
	 * @return an object representing the returned result or null
	 * @throws DaoException
	 */
	Object executeAndHandle(DaoCommand command) throws DaoException;
}
