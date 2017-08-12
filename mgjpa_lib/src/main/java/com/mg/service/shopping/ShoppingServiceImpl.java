package com.mg.service.shopping;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.MethodShippingDAO;
import com.mg.dao.impl.PaypalDAO;
import com.mg.dao.impl.ShoppingCartDAO;
import com.mg.dao.impl.TaxesDAO;
import com.mg.dao.impl.TransactionDAO;
import com.mg.enums.Country;
import com.mg.exception.DaoException;
import com.mg.exception.ServiceException;
import com.mg.model.MethodShipping;
import com.mg.model.Paypal;
import com.mg.model.ShoppingCart;
import com.mg.model.Taxes;
import com.mg.model.Transaction;
import com.mg.service.ServiceImpl;
import com.mg.util.translation.TranslationUtils;
import com.mg.util.translation.Translations;

/**
 * Provides all shoppingCart logic in the system.
 * 
 *
 */
public class ShoppingServiceImpl extends ServiceImpl implements ShoppingService {

	@Override
	public MethodShipping getMethodShipping(final Integer id) throws ServiceException {
		MethodShipping result;
		try {
			daoManager.setCommitTransaction(true);
			result = (MethodShipping) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(MethodShippingDAO.class, em).findEntityById(id);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
	@Override
	public MethodShipping getMethodShipping(final String code) throws ServiceException {
		MethodShipping result;
		try {
			daoManager.setCommitTransaction(true);
			result = (MethodShipping) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(MethodShippingDAO.class, em).findEntityByCode(code);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MethodShipping> getAllMethodShipping(final Country country) throws ServiceException {
		List<MethodShipping> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<MethodShipping>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(MethodShippingDAO.class, em).getAll(country);				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MethodShipping> getAllMethodShipping() throws ServiceException {
		List<MethodShipping> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<MethodShipping>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(MethodShippingDAO.class, em).getAll();				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@Override
	public int saveMethodShipping(final MethodShipping methodShipping, final Translations translationsName, final Translations translationsDesc)
			throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {
					TranslationUtils.updateBaicTranslaction(em, methodShipping, translationsName, translationsDesc);
					if(methodShipping.getId() > 0){
						methodShipping.setCreationDate(new Date());
						DaoFactory.getDAO(MethodShippingDAO.class, em).update(methodShipping);
					}
					else{
						DaoFactory.getDAO(MethodShippingDAO.class, em).save(methodShipping);
					}
					return(methodShipping.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}

	@Override
	public void updateMethodShipping(final MethodShipping methodShipping)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(MethodShippingDAO.class, em).update(methodShipping);
					return(methodShipping);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}

	@Override
	public void deleteMethodShipping(final MethodShipping methodShipping)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(MethodShippingDAO.class, em).delete(methodShipping);
					return(methodShipping);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}

	@Override
	public Taxes getTaxes(final Integer id) throws ServiceException {
		Taxes result;
		try {
			daoManager.setCommitTransaction(true);
			result = (Taxes) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(TaxesDAO.class, em).find(id);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Taxes> getAllTaxes() throws ServiceException {
		List<Taxes> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Taxes>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(TaxesDAO.class, em).findAll();				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@Override
	public int saveTaxes(final Taxes taxes) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					taxes.setCreationDate(new Date());
					DaoFactory.getDAO(TaxesDAO.class, em).save(taxes);
					return(taxes.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}

	@Override
	public void updateTaxes(final Taxes taxes) throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(TaxesDAO.class, em).update(taxes);
					return(taxes);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}

	@Override
	public void deleteTaxes(final Taxes taxes) throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(TaxesDAO.class, em).delete(taxes);
					return(taxes);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}

	@Override
	public Taxes getTaxes(final String code) throws ServiceException {
		Taxes result;
		try {
			daoManager.setCommitTransaction(true);
			result = (Taxes) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(TaxesDAO.class, em).find(code);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
	@Override
	public int saveShoppingCart(final ShoppingCart shoppingCart)
			throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {
					shoppingCart.setCreationDate(new Date());
					DaoFactory.getDAO(ShoppingCartDAO.class, em).save(shoppingCart);
					return(shoppingCart.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShoppingCart> getAllShoppingCart() throws ServiceException {
		List<ShoppingCart> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<ShoppingCart>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(ShoppingCartDAO.class, em).getAll();				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShoppingCart> getAllShoppingCartByUser(final Integer userId)
			throws ServiceException {
		List<ShoppingCart> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<ShoppingCart>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(ShoppingCartDAO.class, em).getAllByUser(userId) ;				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@Override
	public void updateShoppingCart(final ShoppingCart shoppingCart)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(ShoppingCartDAO.class, em).update(shoppingCart);
					return(shoppingCart);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}

	@Override
	public ShoppingCart getShoppingCartById(final Integer id) throws ServiceException {
		ShoppingCart result;
		try {
			daoManager.setCommitTransaction(true);
			result = (ShoppingCart) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(ShoppingCartDAO.class, em).findEntityById(id);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	@Override
	public ShoppingCart getShoppingCartByReference(final String reference)
			throws ServiceException {
		ShoppingCart result;
		try {
			daoManager.setCommitTransaction(true);
			result = (ShoppingCart) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(ShoppingCartDAO.class, em).findEntityByReference(reference);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	@Override
	public Transaction getTransaction(final Integer id) throws ServiceException {
		Transaction result;
		try {
			daoManager.setCommitTransaction(true);
			result = (Transaction) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(TransactionDAO.class, em).find(id);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getAllTransaction() throws ServiceException {
		List<Transaction> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Transaction>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(TransactionDAO.class, em).findAll() ;				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@Override
	public int saveTransaction(final Transaction transaction) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {
					transaction.setCreationDate(new Date());
					DaoFactory.getDAO(TransactionDAO.class, em).save(transaction);
					return(transaction.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}
	
	@Override
	public void updateTransaction(final Transaction transaction)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(TransactionDAO.class, em).update(transaction);
					return(transaction);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}
	
	@Override
	public Paypal getPaypal(final String token) throws ServiceException {
		Paypal result;
		try {
			daoManager.setCommitTransaction(true);
			result = (Paypal) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(PaypalDAO.class, em).getPaypalByToken(token);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
	@Override
	public void updatePaypal(final Paypal paypal)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(PaypalDAO.class, em).update(paypal);
					return(paypal);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}
	
	@Override
	public ShoppingCart getShoppingCartByToken(final String token) throws ServiceException {
		ShoppingCart result;
		try {
			daoManager.setCommitTransaction(true);
			result = (ShoppingCart) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(ShoppingCartDAO.class, em).getShoppingCartByToken(token);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

}
