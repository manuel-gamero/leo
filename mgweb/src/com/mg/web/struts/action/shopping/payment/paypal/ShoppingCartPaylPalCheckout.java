package com.mg.web.struts.action.shopping.payment.paypal;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.annotation.Action;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.shopping.BasicShoppingCart;
import com.mg.web.struts.action.shopping.payment.paypal.beans.ExpressCheckout;
import com.mg.web.struts.action.shopping.payment.paypal.beans.ExpressCheckoutDetails;
import com.mg.web.struts.action.shopping.payment.paypal.beans.ExpressCheckoutPayment;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class ShoppingCartPaylPalCheckout extends BasicShoppingCart implements Preparable, ShoppingCartPaypal{

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(ShoppingCartPaylPalCheckout.class);
	private String paypalUrl;
	private String token;
	private String payerID;
	private boolean termsConditions;

	@Override
	public void validate() {
		if(! termsConditions){
			String error = getText("bolsos.shoppingcart.summary.error.terms");
			ActionContext.getContext().getSession().put(WebConstants.ERRORACTION, error);
			addActionError(error);
		}
	}

	@Override
	public void prepare() throws Exception {
		super.prepare();
	}

	@Override
	@Action(value="token = #token , payerID = #payerID , termsConditions = #termsConditions , paypalUrl = #paypalUrl ")
	public String execute() {
		try {
			if (!isValidShoppingCart()) {
				return ERROR;
			} else {
				// Apply coupon
				applyCoupon();
				// Save values in shopping cart object
				saveShoppingCart();

				ExpressCheckout expressCheckout = new ExpressCheckout(this);
				paypalUrl = expressCheckout.actionPaypal();
				
				log.debug("Calling paypal url: " + paypalUrl);
				return SUCCESS;
			}
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	@Action(value="token = #token , payerID = #payerID ")
	public String GetExpressCheckoutDetailsPayment() {
		try {
			log.debug(">>> ExpressCheckout init " );
			ExpressCheckoutDetails checkoutDetail = new ExpressCheckoutDetails(this);
			checkoutDetail.setPaypalAction(new ExpressCheckoutPayment(this));
			log.debug("<<< ExpressCheckout end");
			
			return checkoutDetail.actionPaypal();
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String getPaypalUrl() {
		return paypalUrl;
	}

	public void setPaypalUrl(String paypalUrl) {
		this.paypalUrl = paypalUrl;
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
	
	public boolean isTermsConditions() {
		return termsConditions;
	}

	public void setTermsConditions(boolean termsConditions) {
		this.termsConditions = termsConditions;
	}


}
