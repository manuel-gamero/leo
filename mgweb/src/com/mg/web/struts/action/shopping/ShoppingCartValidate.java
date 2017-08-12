package com.mg.web.struts.action.shopping;

import org.apache.log4j.Logger;

import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.struts.action.BasicAction;

public class ShoppingCartValidate extends BasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8169779400392417426L;
	private static Logger log = Logger.getLogger(ShoppingCartValidate.class);
	private boolean validate;
	

	@Override
	public String execute() {
		try{
			log.debug("ShoppingCartValidate debug execute");
			ShoppingCart shoppingCart = null;
			if(getShoppingCart() != null){
				shoppingCart = getShoppingCart();
			}
			else{
				validate = false;
				return (ERROR);
			}
			if( validate(shoppingCart) ){
				ServiceLocator.getService(ShoppingServiceImpl.class).saveShoppingCart(shoppingCart);
			}
			else{
				validate = false;
				return (ERROR);
			}
			validate = true;
			return SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			managerException(e);
			return ERROR;
		}
	}

	private boolean validate(ShoppingCart shoppingCart) {
		if(shoppingCart.getUsers() == null || shoppingCart.getMethodShipping() == null ||
		   shoppingCart.getPaymentMethod() == null || shoppingCart.getShippingAddressId() == null ||
		   shoppingCart.getShippingFees() == null || shoppingCart.getShoppingCartItems() == null ||
		   shoppingCart.getTaxes() == null || shoppingCart.getTotal() == null){
			return false;
		}
		return true;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

}
