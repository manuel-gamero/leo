package com.mg.service.shopping;

import java.util.List;

import com.mg.enums.Country;
import com.mg.exception.ServiceException;
import com.mg.model.MethodShipping;
import com.mg.model.Paypal;
import com.mg.model.ShoppingCart;
import com.mg.model.Taxes;
import com.mg.model.Transaction;
import com.mg.service.Service;
import com.mg.util.translation.Translations;

/**
 * ShoppingCart service interface.
 * 
 *
 */
public interface ShoppingService extends Service {

	public MethodShipping getMethodShipping (final Integer id) throws ServiceException;
	public MethodShipping getMethodShipping(final String code) throws ServiceException;
	public List<MethodShipping> getAllMethodShipping()throws ServiceException;
	public List<MethodShipping> getAllMethodShipping(Country country)throws ServiceException;
	public int saveMethodShipping(MethodShipping methodShipping, Translations translationsName, Translations translationsDesc) throws ServiceException;
	public void updateMethodShipping(final MethodShipping methodShipping) throws ServiceException;
	public void deleteMethodShipping(MethodShipping methodShipping) throws ServiceException;
	
	public Taxes getTaxes (final Integer id) throws ServiceException;
	public Taxes getTaxes (final String code) throws ServiceException;
	public List<Taxes> getAllTaxes()throws ServiceException;
	public int saveTaxes(Taxes taxes) throws ServiceException;
	public void updateTaxes(Taxes taxes) throws ServiceException;
	public void deleteTaxes(Taxes taxes) throws ServiceException;
	
	public List<ShoppingCart> getAllShoppingCart()throws ServiceException;
	public List<ShoppingCart> getAllShoppingCartByUser(Integer userId)throws ServiceException;
	public int saveShoppingCart(ShoppingCart shoppingCart) throws ServiceException;
	public void updateShoppingCart(ShoppingCart shoppingCart) throws ServiceException;
	public ShoppingCart getShoppingCartById(Integer id) throws ServiceException;
	public ShoppingCart getShoppingCartByReference(String reference) throws ServiceException;
	
	public Transaction getTransaction (final Integer id) throws ServiceException;
	public List<Transaction> getAllTransaction()throws ServiceException;
	public int saveTransaction(Transaction transaction) throws ServiceException;
	public void updateTransaction(final Transaction transaction) throws ServiceException ;
	Paypal getPaypal(String token) throws ServiceException;
	void updatePaypal(Paypal paypal) throws ServiceException;
	
	/**
	 * @param token from paypal
	 * @return the ShoppingCart related with a paypal transaction.
	 * @throws ServiceException
	 */
	ShoppingCart getShoppingCartByToken(String token) throws ServiceException;
	
}
