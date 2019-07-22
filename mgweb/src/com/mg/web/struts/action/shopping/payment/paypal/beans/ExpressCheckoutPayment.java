package com.mg.web.struts.action.shopping.payment.paypal.beans;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.enums.PaymentCVX;
import com.mg.enums.PaymentCodeReturn;
import com.mg.enums.PaymentMethodType;
import com.mg.enums.ShoppingCartStatus;
import com.mg.exception.CurrencyNoExistException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.ShoppingCart;
import com.mg.model.Transaction;
import com.mg.service.ServiceLocator;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.shopping.payment.paypal.AbstractPaypalAction;
import com.mg.web.struts.action.shopping.payment.paypal.ShoppingCartPaypal;
import com.mg.web.util.ConnectionWebUtils;
import com.opensymphony.xwork2.Action;

public class ExpressCheckoutPayment extends AbstractPaypalAction {

	private ShoppingCart shoppingCart;
	
	private static Logger log = LogManager.getLogger(ExpressCheckoutPayment.class);
	
	public ExpressCheckoutPayment(ShoppingCartPaypal shoppingCartAction){
		setShoppingCartAction(shoppingCartAction);
	}
	
	@Override
	public String execute(Map<String, String> params)
			throws UnsupportedEncodingException, ServiceLocatorException,
			ServiceException, ParseException {
		// Save transaction
		Transaction transaction = generateTranscation(getShoppingCart(), params);
		// Modify shoppingCart with the last transaction
		getShoppingCart().setPurchaseDate(getPurchaseDate(getValueParam(params.get("PAYMENTREQUEST_0_ORDERTIME"))));

		// save PAYERID in paypal instance
		getShoppingCart().getPaypal().setPayerid(getShoppingCartAction().getPayerID());
		getShoppingCart().getPaypal().setUpdateDate(new Date());

		getShoppingCart().setStatusCode(ShoppingCartStatus.PAIMENT);
		// ServiceLocator.getService(ShoppingServiceImpl.class).updateShoppingCart(shoppingCart);

		// Send email to user
		log.debug("Sending email for reference: " + getShoppingCart().getReference());
		getShoppingCartAction().sendEmailToUser(getShoppingCart());

		// Inactive coupon if the user has used one
		getShoppingCartAction().inactiveCoupon( getShoppingCart().getUsers() );

		getShoppingCart().setPaymentMethod(PaymentMethodType.PAYPAL);
		getShoppingCart().setTransaction(transaction);

		ServiceLocator.getService(ShoppingServiceImpl.class).updateTransaction(transaction);
		
		getShoppingCartAction().getRequest().getSession().removeAttribute(WebConstants.SHOPPING_CART);
		getShoppingCartAction().getRequest().getSession().removeAttribute(WebConstants.SHOPPING_CART_ITEMS);

		return Action.SUCCESS;
	}

	@Override
	public String getUrl() throws ServiceLocatorException, UnsupportedEncodingException, CurrencyNoExistException, ServiceException {
		String url = getBasicParamUrl("DoExpressCheckoutPayment");
		url += "&TOKEN=" + getShoppingCartAction().getToken();
		url += "&PAYMENTREQUEST_0_PAYMENTACTION=" + ConnectionWebUtils.getParameter("Sale");
		url += "&PAYMENTREQUEST_0_CURRENCYCODE=" + ConnectionWebUtils.getParameter(getShoppingCart().getCurrency());
		url += "&PAYMENTREQUEST_0_AMT=" + ConnectionWebUtils.getParameter(getShoppingCartAction().getTotal().setScale(2,RoundingMode.CEILING).toString());
		url += "&PAYERID=" + ConnectionWebUtils.getParameter(getShoppingCartAction().getPayerID());		
		return url;
	}

	private Transaction generateTranscation(ShoppingCart shoppingCart, Map<String, String> params) throws ParseException, UnsupportedEncodingException {
		Transaction transaction = new Transaction();
		transaction.setShoppingCart(shoppingCart);
		transaction.setBrand(PaymentMethodType.getEnumByCode(getValueParam(params.get("PAYMENTINFO_0_TRANSACTIONTYPE"))));
		transaction.setCbmasquee(getValueParam(params.get("PAYMENTINFO_0_REASONCODE")));
		transaction.setCodeRetour(PaymentCodeReturn.getPaymentCodeReturn(getValueParam(params.get("PAYMENTINFO_0_PAYMENTSTATUS"))));
		transaction.setCreationDate(getPurchaseDate(getValueParam(params.get("TIMESTAMP"))));
		transaction.setCvx(PaymentCVX.getEnumByCode(getValueParam(params.get("PAYMENTINFO_0_PAYMENTTYPE"))));
		transaction.setMac(getValueParam(params.get("PAYMENTINFO_0_TRANSACTIONID")));
		transaction.setMontant(getValueParam(params.get("PAYMENTINFO_0_AMT")) + getValueParam(params.get("PAYMENTINFO_0_CURRENCYCODE")));
		transaction.setPurchaseDate(getPurchaseDate(getValueParam(params.get("PAYMENTINFO_0_ORDERTIME"))));
		transaction.setTexteLibre(getValueParam(params.get("PAYMENTINFO_0_PENDINGREASON")));
		transaction.setVld(getValueParam(params.get("PAYMENTINFO_0_TAXAMT")));
		transaction.setBincb(params.get("PAYMENTINFO_0_SECUREMERCHANTACCOUNTID"));
		transaction.setHpancb(params.get("PAYMENTINFO_0_TRANSACTIONTYPE"));
		/*
		transaction.setCbenregistree(cbenregistree);
		transaction.setFiltragecause(PaymentFraudCode.getEnumByCode(filtragecause));
		transaction.setIpclient(ipclient);
		transaction.setOriginecb(originecb);
		transaction.setOriginetr(originetr);
		transaction.setStatus3ds(status3ds);*/
		transaction.setTpe("PAYPAL-LEO01");
		
		return transaction;
	}
	
	private Date getPurchaseDate(String purchaseDate){
		Date date = null;
		if( purchaseDate != null ){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			try {
				date = formatter.parse(purchaseDate);
			} catch (Exception e) {
				date = new Date();
				log.error(e.getMessage(), e);
			}
		}
		return date;
	}
	
	public ShoppingCart getShoppingCart() throws ServiceException, ServiceLocatorException{
		if( shoppingCart == null ){
			shoppingCart = ServiceLocator.getService(ShoppingServiceImpl.class).getShoppingCartByToken(getShoppingCartAction().getToken());
		}
		return shoppingCart;
	}
	
	

}
