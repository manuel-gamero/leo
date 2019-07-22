package com.mg.web.struts.action.shopping.payment.rbc;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.enums.ShoppingCartStatus;
import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class ShoppingCartPaymentVerify extends BasicAction  implements Preparable{

	private static final long serialVersionUID = 5889902524032581061L;
	private static final Logger log = LogManager.getLogger(ShoppingCartPaymentVerify.class);

	@Override
	public void prepare() {
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
			if (getShoppingCart() != null && getShoppingCartItems() != null) {
				ShoppingCart shoppingCart = getShoppingCart();
				
				ShoppingCart shoppingCartVerify = ServiceLocator.getService(ShoppingServiceImpl.class).getShoppingCartByReference(shoppingCart.getReference());
				
				if(shoppingCartVerify == null){
					if(shoppingCart != null && shoppingCart != null && shoppingCart.getUsers() != null){
						log.warn("Reference: " + shoppingCart.getReference() + " is not in the database user: " +  shoppingCart.getUsers().getLogin());
					}
				}
				if(shoppingCartVerify != null && shoppingCartVerify.getStatusCode().equals(ShoppingCartStatus.CANCEL)){
					request.getSession().removeAttribute(WebConstants.SHOPPING_CART);
					request.getSession().removeAttribute(WebConstants.SHOPPING_CART_ITEMS);
					return ERROR;
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			managerException(e);
			return ERROR;
		}		
	}
}
