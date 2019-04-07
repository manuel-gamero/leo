//package com.mg.service.suggestion;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//
//import org.apache.log4j.Logger;
//
//import com.mg.dao.core.DaoCommand;
//import com.mg.dao.core.DaoFactory;
//import com.mg.dao.impl.ItemDAO;
//import com.mg.dao.impl.SuggestionsProductDAO;
//import com.mg.dao.impl.SuggestionsUserDAO;
//import com.mg.exception.DaoException;
//import com.mg.exception.ServiceException;
//import com.mg.model.Product;
//import com.mg.model.SuggestionsProduct;
//import com.mg.model.SuggestionsUser;
//import com.mg.model.Users;
//import com.mg.service.ServiceImpl;
//import com.mg.service.device.DeviceServiceImpl;
//
//public class SuggestionServiceImpl extends ServiceImpl implements SuggestionService{
//
//private static final Logger log = Logger.getLogger(DeviceServiceImpl.class);
//	
//	public SuggestionServiceImpl() {
//		super();
//	}
//	
//	@Override
//	public Product getProductBySuggestion(final String suggestion) throws ServiceException {
//		SuggestionsProduct result = null;
//		try {
//			daoManager.setCommitTransaction(true);
//			result = (SuggestionsProduct) daoManager
//					.executeAndHandle(new DaoCommand() {
//						@Override
//						public Object execute(EntityManager em)
//								throws DaoException {
//							return DaoFactory.getDAO(SuggestionsProductDAO.class, em).getProductBySuggestion(suggestion);
//						}
//					});
//		} catch (DaoException de) {
//			throw (new ServiceException(de));
//		}
//		return result.getProduct();
//	}
//	
//	@Override
//	public int saveSuggestion(final Users users, final String sessionGuid, final Product product, final String suggestion) throws ServiceException {
//		int id = 0;
//		try {
//			daoManager.setCommitTransaction(true);
//			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
//				@Override
//				public Object execute(EntityManager em) throws DaoException {	
//					SuggestionsUser entity = new SuggestionsUser();
//					SuggestionsProduct suggestionsProduct = DaoFactory.getDAO(SuggestionsProductDAO.class, em).getProductBySuggestionSession(suggestion, sessionGuid);
//					if( suggestionsProduct != null ){
//						entity.setCount(suggestionsProduct.getSuggestionsUser().getCount() + 1);
//					}
//					else{
//						suggestionsProduct = new SuggestionsProduct();
//						suggestionsProduct.setProduct(product);
//						suggestionsProduct.setSuggestion(suggestion);
//						entity.setCount(1);
//					}
//					entity.setUsers(users);
//					entity.setSessionGuid(sessionGuid);
//					entity.setSuggestionsProduct(suggestionsProduct);
//					DaoFactory.getDAO(SuggestionsUserDAO.class, em).save(entity);
//					return(entity.getId());
//				}
//			});
//		} catch (DaoException de) {
//			throw new ServiceException(de);
//		}
//		return id;
//	}
//	
//	@Override
//	public int saveSuggestion(final Users users, final String sessionGuid, final Product product, final String suggestion, final SuggestionsUser suggestionsUser) throws ServiceException {
//		int id = 0;
//		try {
//			daoManager.setCommitTransaction(true);
//			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
//				@Override
//				public Object execute(EntityManager em) throws DaoException {	
//					if( suggestionsUser != null ){
//						suggestionsUser.setCount(suggestionsUser.getCount() + 1);
//						DaoFactory.getDAO(SuggestionsUserDAO.class, em).update(suggestionsUser);
//						return(suggestionsUser.getId());
//					}
//					else{
//						SuggestionsUser entity = new SuggestionsUser();
//						SuggestionsProduct suggestionsProduct= new SuggestionsProduct();
//						suggestionsProduct.setProduct(product);
//						suggestionsProduct.setSuggestion(suggestion);
//						suggestionsProduct.setSuggestionsUser(entity);
//						entity.setCount(1);
//						entity.setUsers(users);
//						entity.setSessionGuid(sessionGuid);
//						entity.setSuggestionsProduct(suggestionsProduct);
//						DaoFactory.getDAO(SuggestionsUserDAO.class, em).save(entity);
//						return(entity.getId());
//					}
//				}
//			});
//		} catch (DaoException de) {
//			throw new ServiceException(de);
//		}
//		return id;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Object[]> getSuggestionByCustomProduct(int productId, final String session) throws ServiceException {
//		List<Object[]> result = null;
//		try {
//			daoManager.setCommitTransaction(true);
//			result = (List<Object[]>) daoManager
//					.executeAndHandle(new DaoCommand() {
//						@Override
//						public Object execute(EntityManager em)
//								throws DaoException {
//							return DaoFactory.getDAO(ItemDAO.class, em).getSuggestionByCustomProduct(productId, session);
//						}
//					});
//		} catch (DaoException de) {
//			throw (new ServiceException(de));
//		}
//		return result;
//	}
//}

package com.mg.service.suggestion;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.ItemDAO;
import com.mg.dao.impl.SuggestionsDAO;
import com.mg.exception.DaoException;
import com.mg.exception.ServiceException;
import com.mg.model.Product;
import com.mg.model.Suggestions;
import com.mg.model.Users;
import com.mg.service.ServiceImpl;
import com.mg.service.device.DeviceServiceImpl;

public class SuggestionServiceImpl extends ServiceImpl implements SuggestionService{

private static final Logger log = Logger.getLogger(DeviceServiceImpl.class);
	
	public SuggestionServiceImpl() {
		super();
	}
	
	@Override
	public Product getProductBySuggestion(final String suggestion) throws ServiceException {
		Suggestions result = null;
		try {
			daoManager.setCommitTransaction(true);
			result = (Suggestions) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(SuggestionsDAO.class, em).getProductBySuggestion(suggestion);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return result.getProduct();
	}
	
	@Override
	public int saveSuggestion(final Users users, final String sessionGuid, final Product product, final String suggestion) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					Suggestions suggestions = DaoFactory.getDAO(SuggestionsDAO.class, em).getProductBySuggestionSession(suggestion, sessionGuid);
					if( suggestions != null ){
						suggestions.setCount(suggestions.getCount() + 1);
					}
					else{
						suggestions = new Suggestions();
						suggestions.setProduct(product);
						suggestions.setSuggestion(suggestion);
						suggestions.setCount(1);
						suggestions.setUsers(users);
						suggestions.setSessionGuid(sessionGuid);
					}
					DaoFactory.getDAO(SuggestionsDAO.class, em).save(suggestions);
					return(suggestions.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}
	
	@Override
	public int saveSuggestion(final Users users, final String sessionGuid, final Integer productId, final String suggestion, final Integer count, final Integer suggestionId) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					
					if( suggestionId != null ){
						Suggestions entity = DaoFactory.getDAO(SuggestionsDAO.class, em).find(suggestionId);
						entity.setId(suggestionId);
						entity.setCount(count + 1);
						DaoFactory.getDAO(SuggestionsDAO.class, em).update(entity);
						return(entity.getId());
					}
					else{
						Suggestions entity = new Suggestions();
						Product product = new Product(productId);
						entity.setProduct(product);
						entity.setSuggestion(suggestion);
						entity.setCount(1);
						entity.setUsers(users);
						entity.setSessionGuid(sessionGuid);
						DaoFactory.getDAO(SuggestionsDAO.class, em).save(entity);
						return(entity.getId());
					}
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getSuggestionByCustomProduct(int productId, final String session, final String currency) throws ServiceException {
		List<Object[]> result = null;
		try {
			daoManager.setCommitTransaction(true);
			result = (List<Object[]>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(ItemDAO.class, em).getSuggestionByCustomProduct(productId, session, currency);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return result;
	}

}
