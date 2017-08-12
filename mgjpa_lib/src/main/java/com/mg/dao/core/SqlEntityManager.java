package com.mg.dao.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.log4j.Logger;

/**
 *  
 * @author MJGP
 *
 */
public class SqlEntityManager {

	public SqlEntityManager(){}

	private static final Logger log = Logger.getLogger(SqlEntityManager.class);

	/*
	 *  Once created, the SqlSessionFactory should exist for the duration of application execution. 
	 *  There should be little or no reason to ever dispose of it or recreate it (Ibatis Docs).
	 */	
	private static EntityManagerFactory entityManagerFactory ;
	
	static {
		try {
			setEntityManagerFactory(Persistence.createEntityManagerFactory("mgPostgress"));
		} catch (Exception e) {
			log.error("A problem has happened while initialize entity manager factory.", e);
		}
	}
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private static EntityManager entityManager;

	
	public static synchronized EntityManager getEntityManager() {
	/*	if(entityManagerFactory != null){
			if(entityManager == null || (entityManager != null && !entityManager.isOpen()) ){
				entityManager = entityManagerFactory.createEntityManager();
			}
		}
		
		log.debug("entityManager created...");*/
		
		return entityManager;
	}
	
	public static void beginTransaction() {
		/*if(entityManager == null){
			entityManager = getEntityManager();
		}*/
		setEntityManager(entityManagerFactory.createEntityManager());
		
		String trasactionId = getEntityManager().getTransaction().toString(); 
		getEntityManager().getTransaction().begin();
		
		log.debug("Begin transaction ... " + trasactionId);

	}

	public static void commit() {
		if(getEntityManager() != null ){
			String trasactionId = getEntityManager().getTransaction().toString(); 
			getEntityManager().getTransaction().commit();
			log.debug("committing transaction ... " + trasactionId);
		}
		else{
			log.debug("committing transaction NULL...");
		}
	}

	public static void rollback() {
		if(getEntityManager() != null && getEntityManager().getTransaction().isActive()){
			String trasactionId = getEntityManager().getTransaction().toString(); 
			getEntityManager().getTransaction().rollback();
			log.debug("Rolling back transaction ... " + trasactionId);
		}
		else{
			log.debug("Rolling back transaction no ACTIVE...");
		}
	}

	public static void close() {
		String trasactionId = getEntityManager().getTransaction().toString();
		if(getEntityManager() != null && getEntityManager().isOpen()){
			getEntityManager().close();
			log.debug("closing sql session... " + trasactionId);
		}
		else{
			log.debug("closing sql session ALREADY!... " );
		}
		
		
		
	}

	public static void commitAndClose() {
		commit();
		close();
	}

	public static synchronized EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static synchronized void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		SqlEntityManager.entityManagerFactory = entityManagerFactory;
	}

	public static synchronized void setEntityManager(EntityManager entityManager) {
		SqlEntityManager.entityManager = entityManager;
	}



}
