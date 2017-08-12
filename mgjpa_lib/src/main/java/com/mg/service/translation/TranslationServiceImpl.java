package com.mg.service.translation;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.TranslationDAO;
import com.mg.exception.DaoException;
import com.mg.exception.ServiceException;
import com.mg.model.Translation;
import com.mg.service.ServiceImpl;

/**
 * Provides all translation related logic in the system.
 * 
 * 
 */
public class TranslationServiceImpl extends ServiceImpl implements TranslationService {

	private static final Logger log = Logger.getLogger(TranslationServiceImpl.class);

	public TranslationServiceImpl() {
		super();
	}

	@Override
	public int saveTranslation (final Translation translation)throws ServiceException {
		int id = 0;
		try {			
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					DaoFactory.getDAO(TranslationDAO.class, em).save(translation);
					return translation.getId();
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return id;
	}

	@Override
	public void updateTranslation (final Translation translation)throws ServiceException {
		try {			
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					DaoFactory.getDAO(TranslationDAO.class, em).update(translation);
					return translation.getId();
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
	}

}
