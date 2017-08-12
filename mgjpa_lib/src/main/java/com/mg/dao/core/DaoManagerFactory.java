package com.mg.dao.core;


/**
 * Simple DAO manager factory.
 * 
 * 
 * @author MJGP
 *
 */
public class DaoManagerFactory {
	
	public static DaoManager getDaoManager(){
		return getDaoManager(false);
	}
	
	public static DaoManager getDaoManager(boolean commitTransaction){		
		return new DaoManagerImpl(commitTransaction);
	}
	
}
