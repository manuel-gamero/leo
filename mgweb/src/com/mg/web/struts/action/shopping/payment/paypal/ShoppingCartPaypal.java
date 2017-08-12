package com.mg.web.struts.action.shopping.payment.paypal;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Coupons;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;
import com.mg.model.Users;

public interface ShoppingCartPaypal {
	
	public ShoppingCart getShoppingCart();
	public String getToken();
	public void sendEmailToUser(ShoppingCart shoppingCart);
	public void inactiveCoupon(Users users) throws ServiceException, ServiceLocatorException;
	public HttpServletRequest getRequest();
	public BigDecimal getTotal();
	public String getPayerID();
	public Coupons getCoupon();
	public List<Item> getShoppingCartItems();
	public BigDecimal getSubTotal();
	public BigDecimal getTaxes();
	public BigDecimal getShippingPrice();
	public BigDecimal getExtraTextPrice();
	public BigDecimal getTotalDiscountCoupon();
	public String getCurrentLanguage();
	public String getCurrentCurrencyCode();
	public void registeCouponToUser(Users users, Coupons coupon) throws ServiceException, ServiceLocatorException;

}
