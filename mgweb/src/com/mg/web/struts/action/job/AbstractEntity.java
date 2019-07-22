package com.mg.web.struts.action.job;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.annotation.FieldView;
import com.mg.dao.GenericDao;
import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.core.DaoManagerFactory;
import com.mg.exception.DaoException;
import com.mg.util.exception.ExceptionHandler;
import com.mg.util.reflexion.FieldFinder;
import com.mg.util.reflexion.ReflectionUtils;
import com.mg.web.struts.action.BasicListAction;

public class AbstractEntity <T, D extends GenericDao<T>> {
	private static final Logger log = LogManager.getLogger(BasicListAction.class);
	private Class<T> entityClass;
	private Class<D> entityClassDAO;
	private List<T> itemList;
	protected T entity;
	private int id;
	
	public List<String> getFieldsView(){
		List<Field> fields = FieldFinder.getInheritedPrivateFieldsAnnotated(entityClass, FieldView.class);
		List<String> nameFields = new ArrayList<String>();
		for (Field field : fields) {
			nameFields.add(field.getName());
		}
		
		log.debug(nameFields.size() + " fields are found.");
		return nameFields;
	}
	
	public Object getFieldValue(T t, String fieldName){
		try {
			return ReflectionUtils.getValueField(t, fieldName);
		} catch (Exception e) {
			ExceptionHandler.handleException(e, "No " + fieldName + " in class " + t.getClass().getName(), null);
		} 
		return null;
	}
	
	public Object getFieldValue(String fieldName){
		try {
			return ReflectionUtils.getValueField(entity, fieldName);
		} catch (Exception e) {
			ExceptionHandler.handleException(e, "No " + fieldName + " in class " + entity.getClass().getName(), null);
		} 
		return null;
	}

	public List<T> getItemList() {
		return itemList;
	}

	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Class<D> getEntityClassDAO() {
		return entityClassDAO;
	}

	public void setEntityClassDAO(Class<D> entityClassDAO) {
		this.entityClassDAO = entityClassDAO;
	}
	
	@SuppressWarnings("unchecked")
	public void displayEntity(){
		final int entityID = getId();
		final Class<D> clazzDAO = getEntityClassDAO();
		try {
			entity = (T) DaoManagerFactory.getDaoManager().executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(clazzDAO, em).find(entityID);		
				}
			}) ;
		} catch (DaoException e) {
			ExceptionHandler.handleException(e, null, null);
		}
	}
	
	public void saveEntity(){
		final T pEntity = entity;
		final Class<D> clazzDAO = getEntityClassDAO();
		try {
			DaoManagerFactory.getDaoManager().executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					DaoFactory.getDAO(clazzDAO, em).save(pEntity);
					return null;
				}
			}) ;
		} catch (DaoException e) {
			ExceptionHandler.handleException(e, null, null);
		}
	}

	public T getEntity() {
		return this.entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

}
