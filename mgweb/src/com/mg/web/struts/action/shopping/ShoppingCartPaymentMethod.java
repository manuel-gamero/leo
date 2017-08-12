package com.mg.web.struts.action.shopping;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Preparable;

public class ShoppingCartPaymentMethod extends BasicShoppingCart implements Preparable {

	private static Logger log = Logger.getLogger(ShoppingCartPaymentMethod.class);
	private static final long serialVersionUID = 1155604242419622177L;
	private String methodCode;
	
	@Override
	public void prepare(){
		try{
			super.prepare();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			managerException(e);
		}
	}

	@Override
	public String execute() {
		try{
			log.debug("ShoppingCartPaymentMethod debug execute");
			getShoppingCart().setTaxes( getTaxes() );
			setTotalShoppingCart();
			//Apply coupon
			applyCoupon();
			return SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			managerException(e);
			return ERROR;
		}
	}

	public String getMethodCode() {
		return methodCode;
	}

	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}

	public String getChecked(String code){
		log.debug("Code : " + code);
		return "";
	}

}
