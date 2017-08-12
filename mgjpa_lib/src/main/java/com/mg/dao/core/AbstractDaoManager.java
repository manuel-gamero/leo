package com.mg.dao.core;

/**
 * Abstract data access manager.
 * 
 * Subclasses should define their own transaction management
 * approach.
 * 
 * In fact, DAOs are not aware of the transaction and they 
 * shouldn't be; They are participants in a bigger unit
 * of work. A DAO Manager will offer a unique access point in 
 * the system to lookup all DAOs, manage transaction boundaries, and 
 * handle all the exception thrown from data access operations. 
 *      
 * @author MJGP
 *
 */
public abstract class AbstractDaoManager implements DaoManager{
	
	protected boolean commitTransaction = false;
	
	public boolean isCommitTransaction() {
		return commitTransaction;
	}

	public void setCommitTransaction(boolean commitTransaction) {
		this.commitTransaction = commitTransaction;
	}

	protected AbstractDaoManager(boolean commitTransaction){
		setCommitTransaction(commitTransaction);
	}
	
}
