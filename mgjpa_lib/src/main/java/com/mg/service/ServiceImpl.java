package com.mg.service;

import com.mg.dao.core.DaoManager;
import com.mg.dao.core.DaoManagerFactory;


/**
 * 
 * @author MJGP
 *
 */
public class ServiceImpl implements Service{

	protected DaoManager daoManager;
	
	public ServiceImpl() {
		super();
		setDaoManager(DaoManagerFactory.getDaoManager());
	}
	
	public DaoManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DaoManager daoManager) {
		this.daoManager = daoManager;
	}
	
	public ServiceLocator getServiceLocator(){
		return ServiceLocator.getInsance();
	}
	
	public ServiceLocator getServiceLocatorAdmin(){
		return ServiceLocator. getInsance();
	}
}
