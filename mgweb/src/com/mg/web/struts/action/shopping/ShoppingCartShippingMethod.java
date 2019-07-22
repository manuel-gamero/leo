package com.mg.web.struts.action.shopping;

import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.MethodShippingDTO;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.service.user.UserServiceImpl;
import com.mg.web.WebConstants;
import com.opensymphony.xwork2.Preparable;

public class ShoppingCartShippingMethod extends BasicShoppingCart implements Preparable {

	private static Logger log = LogManager.getLogger(ShoppingCartShippingMethod.class);
	private static final long serialVersionUID = 1155604242419622177L;
	private Integer shippingSelect;
	private Integer billingSelect;
	private boolean sameBillingAddress;
	private List<MethodShippingDTO> shippingMethod;
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
			log.debug("ShoppingCartShippingMethod debug execute");
			ShoppingCart shoppingCart = getShoppingCart();
			if(shoppingCart != null && shoppingCart.getMethodShipping() != null){
				setMethodCode(shoppingCart.getMethodShipping().getCode());
			}
			
			if(shippingSelect != null && shoppingCart != null){
				if( shoppingCart.getShippingAddressId() == null){
					shoppingCart.setShippingAddressId(ServiceLocator.getService(UserServiceImpl.class).getUserAddress(shippingSelect));
					request.getSession().setAttribute(WebConstants.SHOPPING_CART, shoppingCart);
				}
				else{
					if( shoppingCart.getShippingAddressId().getId() != shippingSelect ){
						shoppingCart.setShippingAddressId(ServiceLocator.getService(UserServiceImpl.class).getUserAddress(shippingSelect));
						request.getSession().setAttribute(WebConstants.SHOPPING_CART, shoppingCart);
					}
				}
			}
			shoppingCart.setTaxes( getTaxes() );
			setShippingMethod(DTOFactory.getMethodShippingDTOList(ServiceLocator.getService(ShoppingServiceImpl.class).getAllMethodShipping(shoppingCart.getShippingAddressId().getCountry()), getCurrentLanguage(), getCurrentCurrencyCode()));
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

	public int getShippingSelect() {
		return shippingSelect;
	}

	public void setShippingSelect(int shippingSelect) {
		this.shippingSelect = shippingSelect;
	}

	public int getBillingSelect() {
		return billingSelect;
	}

	public void setBillingSelect(int billingSelect) {
		this.billingSelect = billingSelect;
	}

	public boolean isSameBillingAddress() {
		return sameBillingAddress;
	}

	public void setSameBillingAddress(boolean sameBillingAddress) {
		this.sameBillingAddress = sameBillingAddress;
	}

	public List<MethodShippingDTO> getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(List<MethodShippingDTO> shippingMethod) {
		this.shippingMethod = shippingMethod;
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
	
	public String getAction(){
		if(isAllowPaymentMethod()){
			return getText("url.shoppingcart.payment");
		}
		else{
			return getText("url.shoppingcart.summary");
		}
	}
}
