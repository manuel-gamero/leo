package com.mg.web.struts.action.shopping.payment.paypal.beans;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.CurrencyNoExistException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.web.struts.action.shopping.payment.paypal.AbstractPaypalAction;
import com.mg.web.struts.action.shopping.payment.paypal.ShoppingCartPaypal;
import com.opensymphony.xwork2.Action;

public class ExpressCheckoutDetails extends AbstractPaypalAction{

	private static Logger log = LogManager.getLogger(ExpressCheckoutDetails.class);
	
	public ExpressCheckoutDetails(ShoppingCartPaypal shoppingCartAction){
		setShoppingCartAction(shoppingCartAction);
	}
	
	@Override
	public String execute(Map<String, String> params)
			throws UnsupportedEncodingException, ServiceLocatorException,
			ServiceException {
		// TODO Auto-generated method stub
		return Action.SUCCESS;
	}

	@Override
	public String getUrl() throws ServiceLocatorException, UnsupportedEncodingException, CurrencyNoExistException, ServiceException {
		String url = getBasicParamUrl("GetExpressCheckoutDetails");
		url += "&TOKEN=" + getShoppingCartAction().getToken();
		return url;
	}

	

}
