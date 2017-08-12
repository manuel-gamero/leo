package com.mg.web.struts.action.shopping.payment.paypal;

import org.apache.log4j.Logger;

import com.mg.annotation.Action;
import com.mg.web.struts.action.shopping.BasicShoppingCart;
import com.mg.web.struts.action.shopping.payment.paypal.beans.ExpressCheckoutDetails;
import com.mg.web.struts.action.shopping.payment.paypal.beans.ExpressCheckoutPayment;
import com.opensymphony.xwork2.Preparable;

public class ShoppingCartPaylPalCheckoutResponde extends BasicShoppingCart implements Preparable,ShoppingCartPaypal{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ShoppingCartPaylPalCheckoutResponde.class);
	private String token;
	private String payerID;

	@Override
	public void prepare() throws Exception {
		super.prepare();
	}

	@Action(value="token = #token , payerID = #payerID ")
	@Override
	public String execute() {
		try {
			log.debug(">>> ExpressCheckout init " );
			ExpressCheckoutDetails checkoutDetail = new ExpressCheckoutDetails(this);
			checkoutDetail.setPaypalAction(new ExpressCheckoutPayment(this));
			log.debug("token: " + token + " payerId: " + payerID);
			log.debug("<<< ExpressCheckout end");
			
			return checkoutDetail.actionPaypal();
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPayerID() {
		return payerID;
	}

	public void setPayerID(String payerID) {
		this.payerID = payerID;
	}
}
