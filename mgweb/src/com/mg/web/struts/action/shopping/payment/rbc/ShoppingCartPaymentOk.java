package com.mg.web.struts.action.shopping.payment.rbc;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.CurrencyNoExistException;
import com.mg.model.ShoppingCart;
import com.mg.service.dto.UserSessionDTO;
import com.mg.util.communication.Receipt;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class ShoppingCartPaymentOk extends BasicAction  implements Preparable{

	private static final long serialVersionUID = 5889902524032581061L;
	private static final Logger log = LogManager.getLogger(ShoppingCartPaymentOk.class);

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
		if (getShoppingCart() != null && getShoppingCartItems() != null) {
			try {
				//sendEmailToUser();
				request.getSession().removeAttribute(WebConstants.SHOPPING_CART);
				request.getSession().removeAttribute(WebConstants.SHOPPING_CART_ITEMS);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				managerException(e);
				return ERROR;
			}
		}
		return SUCCESS;
	}

	private void sendEmailToUser() throws CurrencyNoExistException {
		UserSessionDTO userSession = getUserSession();
		ShoppingCart shoppingCart = getShoppingCart();
		String body = shoppingCart.getPaymentMethod() + "Total : " + shoppingCart.getTotal() ;
		Receipt.orderSummaryConfirmation(userSession, shoppingCart, getCurrentLanguage(), getCurrentCurrencyCode());
		//Mail.send(userSession.getLogin(), "Order detail "  + shoppingCart.getReference(), body);
		
	}
	

}
