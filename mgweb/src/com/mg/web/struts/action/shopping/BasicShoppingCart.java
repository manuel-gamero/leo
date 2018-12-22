package com.mg.web.struts.action.shopping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mg.coupon.CouponAbstract;
import com.mg.enums.CouponStatus;
import com.mg.enums.CouponType;
import com.mg.enums.PaymentMethodType;
import com.mg.enums.ShoppingCartItemStatus;
import com.mg.enums.ShoppingCartStatus;
import com.mg.exception.CurrencyNoExistException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Coupons;
import com.mg.model.CouponsUser;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;
import com.mg.model.ShoppingCartItem;
import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.coupon.CouponServiceImpl;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.CommonUtils;
import com.mg.util.currency.CurrencyUtils;
import com.mg.web.struts.action.BasicAction;

public class BasicShoppingCart extends BasicAction {

	private static Logger log = Logger.getLogger(BasicShoppingCart.class);
	private static final long serialVersionUID = 1155604242419622177L;
	public static final BigDecimal ZERO = new BigDecimal(0.00).setScale(2, RoundingMode.CEILING);
	
	public BigDecimal getSubTotal(){
		try{
			ShoppingCart shoppingCart = getShoppingCart();
			return shoppingCart.getTotal();
		}
		catch(Exception e){
			managerException(e);
			return ZERO;
		}
	}
	
	public BigDecimal getExtraTextPrice(){
		try{
			String extraPrice = ServiceLocator.getService(ConfigServiceImpl.class).getPriceExtraText(getCurrentCurrencyCode());
			List<Item> itemList = getShoppingCartItems();
			BigDecimal partialTotal = ZERO;
			if(itemList!=null){
				for (Item item : itemList) {
					if(item.getText() != null && item.getText().trim().length() > 0){
						partialTotal = partialTotal.add( new BigDecimal(extraPrice) );	
					}
					
				}
			}
			return partialTotal;
		}
		catch(Exception e){
			managerException(e);
			return ZERO;
		}
	}
	
	public BigDecimal getShippingPrice(){
		try{
			ShoppingCart shoppingCart = getShoppingCart();
			if (shoppingCart != null && shoppingCart.getMethodShipping() != null ){
				return CurrencyUtils.priceToLocale(shoppingCart.getMethodShipping().getPrice(), getCurrentCurrencyCode());
			}
			return ZERO;
		}
		catch(Exception e){
			managerException(e);
			return ZERO;
		}
	}
	
	public BigDecimal getTotal(){
		try{
			if(getSubTotal() == null){
				log.warn("getTotal is NULL");
				calculeSubTotal();
			}
			
			BigDecimal total = getSubTotal();			
			
			if(getShippingPrice() != null){
				total = total.add(getShippingPrice());
			}
			
			if(getTaxes() != null){
				total = total.add(getTaxes());
			}
			
			if(getExtraTextPrice() != null){
				total = total.add(getExtraTextPrice());
			}
			
			if(getTotalDiscountCoupon() != null){
				total = total.subtract(getDiscount(getTotalDiscountCoupon()));
			}
			
			if ( total.floatValue() < 0 ){
				return ZERO;
			}
			else{
				return total;
			}
		}
		catch(Exception e){
			managerException(e);
			return ZERO;
		}
	}
	
	public BigDecimal getTaxes(){
		try{
			ShoppingCart shoppingCart = getShoppingCart();
			BigDecimal taxes = ZERO;
			if (shoppingCart != null && shoppingCart.getShippingAddressId() != null ){
				if(!ServiceLocator.getService(ConfigServiceImpl.class).isTaxesEnable()){
					taxes = ZERO;
				}
				else{
					taxes = shoppingCart.getShippingAddressId().getProvince().getTax();
				}
			}
			log.debug("taxes: " + taxes);
			log.debug("getSubTotal(): " + getSubTotal());
			return (getSubTotal().add(getShippingPrice()).add(getExtraTextPrice())).multiply(taxes.divide(new BigDecimal(100)));
		}
		catch(Exception e){
			managerException(e);
			return ZERO;
		}
	}
	
	public String getTaxesShoppingCart() throws NumberFormatException, CurrencyNoExistException, ServiceLocatorException, ServiceException{
		return CurrencyUtils.displayPriceLocale( CommonUtils.formatPrice(getTaxes()), getCurrentLanguage(), getCurrentCurrencyCode());
	}
	
	public String getSubTotalShoppingCart() throws CurrencyNoExistException{
		return CurrencyUtils.displayPriceLocale( getSubTotal(), getCurrentLanguage(), getCurrentCurrencyCode());
	}
	
	public String getShippingPriceShoppingCart() throws NumberFormatException, CurrencyNoExistException{
		return CurrencyUtils.displayPriceLocale( getShippingPrice(), getCurrentLanguage(), getCurrentCurrencyCode());
	}
	
	public String getTotalDiscountCouponShoppingCart() throws NumberFormatException, CurrencyNoExistException{
		BigDecimal totalDicsount = getTotalDiscountCoupon();
		if( ZERO.compareTo(totalDicsount) != 0){
			return  "-" + CurrencyUtils.displayPriceLocale( totalDicsount, getCurrentLanguage(), getCurrentCurrencyCode());
		}
		else{
			return  CurrencyUtils.displayPriceLocale( ZERO, getCurrentLanguage(), getCurrentCurrencyCode());
		}
		
	}
	
	public String getTotalShoppingCart() throws NumberFormatException, CurrencyNoExistException, ServiceLocatorException, ServiceException{
		try{
			String total = CommonUtils.formatPrice(getTotal());
			String totalPrice = CurrencyUtils.displayPriceLocale( total, getCurrentLanguage(), getCurrentCurrencyCode());
			return totalPrice;
		}
		catch(Exception e){
			log.error(e);
		}
		return("0.00");
	}
	
	public String getExtraTextPriceShoppingCart() throws CurrencyNoExistException, ServiceLocatorException{
		return CurrencyUtils.displayPriceLocale( getExtraTextPrice(), getCurrentLanguage(), getCurrentCurrencyCode());
	}
	
	protected void setTotalShoppingCart(){
		if(getShoppingCart() != null){
			getShoppingCart().setTotalShopping( getSubTotal().add(getShippingPrice()).add(getTaxes()).add(getExtraTextPrice()).setScale(2, RoundingMode.CEILING));
		}
	}
	
	protected void calculeSubTotal() throws CurrencyNoExistException{
		ShoppingCart shoppingCart = getShoppingCart();
		List<Item> itemList = getShoppingCartItems();
		BigDecimal partialTotal = ZERO;
		if(itemList!=null){
			for (Item item : itemList) {
				partialTotal = partialTotal.add( CurrencyUtils.priceToLocale(item.getProduct().getPrice(), getCurrentCurrencyCode()) ).subtract( getDiscount(item.getDiscountPrice()) );
			}
		}
		log.debug("Calculating partialTotal = " + partialTotal);
		log.debug("Calculating discount = " + getDiscount(shoppingCart.getDiscountTotal()));
		
		BigDecimal total = partialTotal.subtract( getDiscount(shoppingCart.getDiscountTotal()) );
		log.debug("Setting total = " + total);
		
		shoppingCart.setTotal(total);
}
	
	private BigDecimal getDiscount(BigDecimal item){
		if (item != null){
			return item;
		}
		else{
			return ZERO;
		}
	}
	
	public BigDecimal getTotalDiscountCoupon(){
		try {
			ShoppingCart shoppingCart = getShoppingCart();
			BigDecimal total = getDiscount(shoppingCart.getDiscountExtras())
					.add(getDiscount(shoppingCart.getDiscountShippingFees()))
					.add(getDiscount(shoppingCart.getDiscountTaxes()))
					.add(getDiscount(shoppingCart.getDiscountTotal()))
					.add(getDiscount(shoppingCart.getDiscountTotalShopping())).setScale(2, RoundingMode.CEILING);
			return total;
		}
		catch(Exception e){
			managerException(e);
			return ZERO;
		}
	}
	
	protected void saveShoppingCart() throws CurrencyNoExistException {
		UserSessionDTO userSession = getUserSession();
		ShoppingCart shoppingCart = getShoppingCart();
		// Reference: unique, alphaNum (A-Z a-z 0-9), 12 characters max
		final String reference = refPaiement( userSession.getId() );
		shoppingCart.setTaxes( getTaxes() );
		//Set values shoppingCart
		shoppingCart.setUsers( DTOFactory.getUser(userSession) );
		//MethodShipping methodShipping = ServiceLocator.getService(ShoppingServiceImpl.class).getMethodShipping(methodCode);
		//shoppingCart.setMethodShipping(methodShipping);
		shoppingCart.setShippingFees( getShippingPrice() );
		//shoppingCart.setTotal(getTotal(itemList));
		if(getSubTotal() != null){
			shoppingCart.setTotal( getSubTotal() );
		}
		else{
			log.warn("Current shoppingCart total: " + shoppingCart.getTotal() + " TRYING TO SET NULL.");
		}
		shoppingCart.setExtras( getExtraTextPrice() );
		shoppingCart.setShoppingCartItems(CreateShoppingCartItemList(getShoppingCartItems(), shoppingCart));
		shoppingCart.setCreationDate(new Date());
		shoppingCart.setStatusCode(ShoppingCartStatus.NEW);
		shoppingCart.setCurrency(getCurrentCurrencyCode());
		shoppingCart.setReference(reference);
		shoppingCart.setTotalShopping( getTotal().setScale(2, RoundingMode.CEILING));
		shoppingCart.setPaymentMethod(PaymentMethodType.NO_METHOD);
		shoppingCart.setTransaction(null);
	}

	final private String refPaiement(int iuserId) {
		SimpleDateFormat formatter = new SimpleDateFormat("YYMMddHHmmss");
		Date now = new Date();
		return formatter.format(now);
	}
	
	private Set<ShoppingCartItem> CreateShoppingCartItemList(List<Item> itemList, ShoppingCart shoppingCart) throws CurrencyNoExistException {
		Set<ShoppingCartItem> shoppingCartItemSet = new HashSet<ShoppingCartItem>();
		for (Item item : itemList) {
			shoppingCartItemSet.add(createShoppingCartImte(item, shoppingCart));
		}
		return shoppingCartItemSet;
	}

	private ShoppingCartItem createShoppingCartImte(Item item, ShoppingCart shoppingCart) throws CurrencyNoExistException {
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
		Set<ShoppingCartItem> shoppingCartItemSet = new HashSet<ShoppingCartItem>();
		shoppingCartItemSet.add(shoppingCartItem);
		item.setShoppingCartItems(shoppingCartItemSet);
		shoppingCartItem.setCreationDate(new Date());
		shoppingCartItem.setDiscount("0");
		shoppingCartItem.setItem(item);
		Set<ShoppingCartItem> items = new HashSet<ShoppingCartItem>(); 
		items.add(shoppingCartItem);
		item.setShoppingCartItems(items);
		shoppingCartItem.setPrice(CurrencyUtils.priceToLocale(item.getProduct().getPrice(), getCurrentCurrencyCode()));
		shoppingCartItem.setQuantity(1);
		shoppingCartItem.setShoppingCart(shoppingCart);
		shoppingCartItem.setStatusCode(ShoppingCartItemStatus.NEW);
		return shoppingCartItem;
	}

	protected boolean isValidShoppingCart(){
		if(getShoppingCart() == null || getShoppingCartItems() == null){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void registeCouponToUser(Users users, Coupons coupon) throws ServiceException, ServiceLocatorException {
		if(coupon != null ){
			Set<CouponsUser> couponsUserSet = new HashSet<CouponsUser>();
			if( !coupon.getCouponsType().getPromotion()){ //if it is not a promotion, we follow the normal status chain.
				coupon.setStatusCode(CouponStatus.SEND);
			}
			CouponsUser couponsUser = new CouponsUser();
			couponsUser.setCoupons(coupon);
			couponsUser.setUsers(users);
			couponsUserSet.add(couponsUser);
			coupon.setCouponsUsers(couponsUserSet);
			ServiceLocator.getService(CouponServiceImpl.class).updateCoupons(coupon);
		}
		
	}
	
	public String getPaymentType(){
		if(getCoupon() != null && getCoupon().getCouponsType().getTypeCode().equals(CouponType.FREE)){
			return "desjardins";
		}
		if(("CAD").equals(getCurrentCurrencyCode())){
			if(!isAllowPaymentMethod()){
				try {
					return ServiceLocator.getService(ConfigServiceImpl.class).getPaymentDefault();
				} catch (ServiceLocatorException e) {
					managerException(e);
					return "desjardins";
				}
			}
			else{
				return getShoppingCart().getPaymentType();
			}
		}
		else{
			return "paypal";
		}
	}
	
	public void setPaymentType(String paymentType){
		getShoppingCart().setPaymentType(paymentType);
	}
	
	public boolean isAllowPaymentMethod(){
		try {
			String currencyCode = getCurrentCurrencyCode();
			String allowCode = ServiceLocator.getService(ConfigServiceImpl.class).getPaymentAllow();
			return allowCode.contains(currencyCode);
		} catch (ServiceLocatorException e) {
			managerException(e);
			return false;
		}
	}
	
	protected void resetCoupon() throws CurrencyNoExistException {
		CouponAbstract.cleanDiscount(getShoppingCart(), getShoppingCartItems());
		calculeSubTotal();
		setTotalShoppingCart();
	}
	
	protected void calculateShopingCart() throws CurrencyNoExistException, ServiceException, ServiceLocatorException{
		resetCoupon();
		//Apply coupon
		applyCoupon();
		//Calculate and set the partial total for lines
		calculeSubTotal();
		//Calculate and set the total shopping cart
		setTotalShoppingCart();
	}
}
