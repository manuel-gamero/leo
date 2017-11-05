package com.mg.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import com.mg.dao.GenericDao;
import com.mg.model.BasicModel;

abstract class GenericDaoImpl<T extends BasicModel> implements GenericDao<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(GenericDaoImpl.class);
	//private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("mgPostgress");
	private EntityManager em;

	private Class<T> entityClass;

	public void setEntityManager(EntityManager em){
		this.em = em;
	}
	
	public void flush() {
		em.flush();
	}

	/*public void joinTransaction() {
		em = emf.createEntityManager();
		em.joinTransaction();
	}
*/
	public GenericDaoImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void save(T entity) {
		Date creationDate = new Date();
		entity.setCreationDate(creationDate);
		em.persist(entity);
	}

	public void delete(T entity) {
		T entityToBeRemoved = em.merge(entity);
		//em.remove(em.contains(entity) ? entity : em.merge(entity));
		em.remove(entityToBeRemoved);
	}

	public T update(T entity) {
		return em.merge(entity);
	}

	public T find(int entityID) {
		return em.find(entityClass, entityID);
	}

	public T findReferenceOnly(int entityID) {
		return em.getReference(entityClass, entityID);
	}

	// Using the unchecked because JPA does not have a
	// em.getCriteriaBuilder().createQuery()<T> method
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

	// Using the unchecked because JPA does not have a
	// query.getSingleResult()<T> method
	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;
		
		log.debug(" namedQuery: " + namedQuery );
		try {
			Query query = em.createQuery(namedQuery);
			query.setFirstResult(0).setMaxResults(1);
			log.debug(" getFirstResult: " + query.getFirstResult() + " getMaxResults: " + query.getMaxResults());

			// Method that will populate parameters if they are passed not null and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			log.warn("No result found for named query: " + namedQuery, e);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			log.error(e.getMessage(), e);
		}

		return result;
	}
	
	protected boolean updateQuery(String namedQuery, Map<String, Object> parameters) {
			try {
				Query query = em.createQuery(namedQuery);

				// Method that will populate parameters if they are passed not null and empty
				if (parameters != null && !parameters.isEmpty()) {
					populateQueryParameters(query, parameters);
				}
				int result = query.executeUpdate();
				if(result > 0){
					return true;
				}
			} catch (Exception e) {
				log.error("Error while running query: " + namedQuery, e);
			}

			return false;
		}
		
	@SuppressWarnings("unchecked")
	protected List<T> findResults(String namedQuery, Map<String, Object> parameters) {
		List<T> result = null;
		log.debug(" namedQuery: " + namedQuery );
		try {
			Query query = em.createQuery(namedQuery);
			log.debug(" findResults: " + query.getFirstResult() + " getMaxResults: " + query.getMaxResults());

			// Method that will populate parameters if they are passed not null and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (List<T>) query.getResultList();

		} catch (NoResultException e) {
			log.error("No result found for named query: " + namedQuery, e);
		} catch (Exception e) {
			System.out.println("Error while running query: " + namedQuery);
			log.error(e.getMessage(), e);
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> findResults(String namedQuery) {
		List<T> result = null;
		log.debug(" namedQuery: " + namedQuery );
		try {
			Query query = em.createQuery(namedQuery);
			log.debug(" findResults: " + query.getFirstResult() + " getMaxResults: " + query.getMaxResults());

			result = (List<T>) query.getResultList();

		} catch (NoResultException e) {
			log.warn("No result found for named query: " + namedQuery, e);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			log.error(e.getMessage(), e);
		}

		return result;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}
