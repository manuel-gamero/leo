package com.mg.web.struts.action.shopping;

import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.model.MethodShipping;
import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ItemShoppingCartDTO;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.WebConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class ShoppingCartSummary extends BasicShoppingCart implements Preparable {

	private static final long serialVersionUID = 5889902524032581061L;
	private static final Logger log = LogManager.getLogger(ShoppingCartSummary.class);
	private static final String ACTION_PAYPAL = "shoppingCartPaypalSend";
	private static final String ACTION_DESJARDINS = "shoppingCartPaymentSend";
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
		//retrieve errroraction that cames from another action
		if(ActionContext.getContext().getSession().get(WebConstants.ERRORACTION) != null){
			addActionError((String)ActionContext.getContext().getSession().get(WebConstants.ERRORACTION));
			ActionContext.getContext().getSession().remove(WebConstants.ERRORACTION);
		}
		ShoppingCart shoppingCart = getShoppingCart();
		
		try{
			if(getShoppingCart() == null){
				return ERROR;
			}
			
			if(shoppingCart != null){
				if(getShoppingCartItems() == null){
					return ERROR;
				}
				//Set values shoppingCart
				if( methodCode!=null ){
					MethodShipping methodShipping = ServiceLocator.getService(ShoppingServiceImpl.class).getMethodShipping(methodCode);
					if(methodShipping!=null){
						shoppingCart.setMethodShipping(methodShipping);
					}
				}
				else{
					if (shoppingCart.getMethodShipping() == null){
						//If the method shipping is not set, send user to shoppingcarshippingmethod page
						return "methodshipping";
					}
				}
				shoppingCart.setShippingFees( getShippingPrice() );
				shoppingCart.setTaxes( getTaxes() );
				request.getSession().setAttribute(WebConstants.SHOPPING_CART, shoppingCart);
				calculateShopingCart();
			}
		}
		catch(Exception e){
			log.debug(e.getMessage(), e);
			managerException(e);
			return ERROR;
		}
		return SUCCESS;	
	}

	public String getMethodCode() {
		return methodCode;
	}

	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}
	
	public List<ItemShoppingCartDTO> getShoppingCartList(){
		try {
			return DTOFactory.getItemShoppingCartDTOList(getShoppingCartItems(), getCurrentLanguage(), getCurrentCurrencyCode());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	public String getAction(){
		if( "desjardins".equals(getPaymentType()) ){
			return ACTION_DESJARDINS;
		}
		else{
			return ACTION_PAYPAL;
		}
	}


}
