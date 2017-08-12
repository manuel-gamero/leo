package com.mg.web.struts.action.shopping.payment.paypal.beans;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map;

import com.mg.exception.CurrencyNoExistException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.web.exception.PaypalException;
import com.mg.web.struts.action.shopping.payment.paypal.ShoppingCartPaypal;

public interface PaypalAction {

	public String getUrl() throws ServiceLocatorException, UnsupportedEncodingException, CurrencyNoExistException, ServiceException;
	
	public String execute(Map<String, String> params) throws UnsupportedEncodingException, ServiceLocatorException, ServiceException, ParseException;
	
	public ShoppingCartPaypal getShoppingCartAction();
	
	public void setShoppingCartAction(ShoppingCartPaypal shoppingCartAction);
	
	public String actionPaypal() throws PaypalException;
	
	PaypalAction getPaypalAction();
	
	void setPaypalAction(PaypalAction paypalAction);
}
