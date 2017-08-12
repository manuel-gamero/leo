package com.mg.web.struts.action;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.mg.enums.CouponStatus;
import com.mg.exception.CacheException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Coupons;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;
import com.mg.model.Translation;
import com.mg.model.TranslationEntry;
import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.coupon.CouponServiceImpl;
import com.mg.service.dto.CollectionDTO;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.util.communication.Receipt;
import com.mg.util.exception.ExceptionHandler;
import com.mg.util.translation.TranslationUtils;
import com.mg.web.MessageKeyConstants;
import com.mg.web.WebConstants;
import com.mg.web.util.LocaleUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * This is the basic action where common properties and methods should go.
 * 
 * @author MJGP
 * 
 */

public class BasicAction extends ActionSupport implements ServletRequestAware,
		Preparable {

	private static final long serialVersionUID = 1L;

	private static final String NAMESPACES_SEPARATOR = "/";
	protected static final String HOME = "home";
	protected static final String SHOPPING = "shopping";
	protected static final String SUCESSADMIN = "success_admin";
	protected static final String FREE = "free";
	protected static final String SUMMARY = "summary";
	protected static final String ERRORFREE = "errorFree";
	protected static final String TITLE_PAGE = "bolsos.pages.title";
	protected static final String TITLE_PAGE_PARAM = "bolsos.pages.title.param";
	protected static final String DESCR_PAGE_PARAM = "bolsos.pages.description.param";
	protected static final String URL_PAGE_PARAM = "bolsos.pages.url.param";
	
	protected Logger log = Logger.getLogger(this.getClass());

	protected HttpServletRequest request;

	private boolean ajax = false;
	private String currentLanguage;
	private Currency currentCurrency;
	private String pageTitleKey;
	private String pageKeywordsKey;
	private String pageDescriptionKey;
	private String referer;
	private String pageFbTitle;
	private String pageFbDescription;
	private String pageFbUrl;
	private String pageFbImage;
	//String to get from the coupon applied
	private String couponText;
	private String url;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public String getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(String currentLanguage) {
		log.debug("Setting currentLanguage with : " + currentLanguage);
		this.currentLanguage = currentLanguage;
		LocaleUtils.setSessionLocale(request, currentLanguage);
	}

	public String getPageTitleKey() {
		return pageTitleKey;
	}

	public void setPageTitleKey(String pageTitleKey) {
		this.pageTitleKey = pageTitleKey;
	}

	public String getPageKeywordsKey() {
		return pageKeywordsKey;
	}

	public void setPageKeywordsKey(String pageKeywordsKey) {
		this.pageKeywordsKey = pageKeywordsKey;
	}

	public String getPageDescriptionKey() {
		return pageDescriptionKey;
	}

	public void setPageDescriptionKey(String pageDescriptionKey) {
		this.pageDescriptionKey = pageDescriptionKey;
	}

	@Override
	public void prepare() throws Exception {
		/*
		 * filling of message keys header information
		 */
		String headerKey = null;
		String namesSpace = ServletActionContext.getActionMapping()
				.getNamespace();
		String actionName = ServletActionContext.getActionMapping().getName();
		if (namesSpace != null && !namesSpace.equals(NAMESPACES_SEPARATOR)) {
			headerKey = namesSpace.replace(NAMESPACES_SEPARATOR, ".");
		}
		if (actionName != null) {
			headerKey = (headerKey != null ? headerKey + "." : ".")
					+ actionName;
		}

		setPageTitleKey(MessageKeyConstants.PAGE_HEADER_TITLE_KEY + headerKey);
		setPageKeywordsKey(MessageKeyConstants.PAGE_HEADER_KEYWORDS_KEY
				+ headerKey);
		setPageDescriptionKey(MessageKeyConstants.PAGE_HEADER_DESCRIPTION_KEY
				+ headerKey);

		currentLanguage = LocaleUtils.getSessionLanguage(request);
		currentCurrency = LocaleUtils.getSessionCurrency(request);
	}

	@Override
	public String execute() {
		ServletActionContext.getResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
		return ERROR;
	}
	protected void setRefereUrl() {
		log.debug("Setting referer url");
		this.referer = request.getHeader("referer");
	}
	/**
	 * Use this method when if an Action can be use in a Ajax way. If the action
	 * succeed, it chooses to return "success" in the classic way and "none" in
	 * a Ajax way. method do the choice with an "ajax=true" parameter in the
	 * request
	 * 
	 * @return "success" in the classic way and "none" in a Ajax way.
	 */
	public String getSuccessResult() {
		if (ajax) {
			return NONE;
		} else {
			return SUCCESS;
		}
	}

	/**
	 * Use this method when if an Action can be use in a Ajax way. If the action
	 * failed, it chooses to return "input" in the classic way and "none" in a
	 * Ajax way. method do the choice with an "ajax=true" parameter in the
	 * request
	 * 
	 * @return "success" in the classic way and "none" in a Ajax way.
	 */
	public String getInputResult() {
		if (ajax) {
			return NONE;
		} else {
			return INPUT;
		}
	}

	public String getRedirectResult() {
		if (ajax) {
			return NONE;
		} else {
			return "next";
		}
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void managerException(Exception e, String desc, String var) {
		try {
			ExceptionHandler.handleException(e, desc, var);
		} catch (Exception ex) {
			log.error(e.getMessage());
		}
	}

	public void managerException(Exception e, Class c, String var) {
		try {
			ExceptionHandler.handleException(e, c, null, var);
		} catch (Exception ex) {
			log.error(e.getMessage());
		}
	}

	public void managerException(Exception e, Class c, String desc, String var) {
		try {
			ExceptionHandler.handleException(e, c, desc, var);
		} catch (Exception ex) {
			log.error(e.getMessage());
		}
	}

	public void managerException(Exception e) {
		try {
			ExceptionHandler.handleException(e, null, null);
		} catch (Exception ex) {
			log.error(e.getMessage());
		}
	}

	public void managerException(Exception e, String var) {
		try {
			ExceptionHandler.handleException(e, null, var);
		} catch (Exception ex) {
			log.error(e.getMessage());
		}
	}
	
	public void managerExceptionBySupport(Exception e) {
		try {
			ExceptionHandler.handleExceptionBySupport(e);
		} catch (Exception ex) {
			log.error(e.getMessage());
		}
	}

	public Currency getCurrentCurrency() {
		return currentCurrency;
	}

	public void setCurrentCurrency(Currency currentCurrency) {
		this.currentCurrency = currentCurrency;
	}
	
	public String getCurrentCurrencyCode() {
		return currentCurrency.getCurrencyCode();
	}

	protected void setUserSession(UserSessionDTO userSession) {
		if (userSession != null) {
			request.getSession().setAttribute(WebConstants.USER, userSession);
			// the maximum time to timeout the session is 5 hours
			request.getSession().setMaxInactiveInterval(5 * 60 * 60);
		}
	}

	protected UserSessionDTO getUserSession() {
		UserSessionDTO userSession = null;
		if (request.getSession().getAttribute(WebConstants.USER) != null) {
			userSession = (UserSessionDTO) request.getSession().getAttribute(WebConstants.USER);
		}
		return userSession;
	}
	
	private UserSessionDTO getUserSession(Users user) {
		UserSessionDTO userSession = UserSessionDTO.valueOf(user.getId(), user.getLogin(), 
									0, 0, 0, user.getEmail(), 
									user.getTypeCode(), user.getStatusCode(), user.getUserAddresses());
		return userSession;
	}
	
	public List<CollectionDTO> getCollectionDTOList(){
		try {
			return DTOFactory.getCollectionDTOList(ServiceLocator.getService(CollectionServiceImpl.class).getAllCollectionCountGroup(), getCurrentLanguage());
		} catch (Exception e) {
			log.error(" ERROR : " + e.getMessage());
			managerException(e);
		}
		return null;
	}
	
	public String getTranslaction(Integer id , String lang) throws CacheException, ServiceLocatorException{
		Translation entry = ServiceLocator.getService(CacheServiceImpl.class).getTranslationCache().fetch(Translation.class + "_" + id);
		for (TranslationEntry item : entry.getTranslationEntries()) {
			if (item.getLangCode().equals(lang)) {
				return item.getText();
			}

		}
		return lang;
	}
	
	public String getTranslaction(Integer id ) throws CacheException, ServiceLocatorException{
		Translation entry = ServiceLocator.getService(CacheServiceImpl.class).getTranslationCache().fetch(Translation.class + "_" + id);
		for (TranslationEntry item : entry.getTranslationEntries()) {
			if (item.getLangCode().equals(getCurrentLanguage())) {
				return item.getText();
			}

		}
		return null;
	}

	public String getReferer() {
		log.debug(" referer : " + referer);
		return referer;
	}
	
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	public ShoppingCart getShoppingCart(){
		ShoppingCart shoppingCart = (ShoppingCart)request.getSession().getAttribute(WebConstants.SHOPPING_CART);
		if(shoppingCart == null){
			shoppingCart = new ShoppingCart();
			request.getSession().setAttribute(WebConstants.SHOPPING_CART, shoppingCart);
		}
		return shoppingCart;
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getShoppingCartItems(){
		List<Item> list = (List<Item>)request.getSession().getAttribute(WebConstants.SHOPPING_CART_ITEMS);
		if(list == null){
			list = new ArrayList<Item>();
			request.getSession().setAttribute(WebConstants.SHOPPING_CART_ITEMS, list);
		}
		return list;
	}
	
	public Coupons getCoupon(){
		return (Coupons) request.getSession().getAttribute(WebConstants.SHOPPING_CART_COUPON);
	}
	
	protected void applyCoupon(){
		Coupons coupon = getCoupon();
		ShoppingCart  shoppingCart = getShoppingCart();
		List<Item> itemList = getShoppingCartItems();
		if(coupon != null && shoppingCart != null && itemList != null){
			couponText = coupon.getCouponsType().getTypeCode().getCoupon().apply(coupon, shoppingCart, itemList, getCurrentCurrencyCode());
			if(couponText.contains("error")){
				request.getSession().removeAttribute(WebConstants.SHOPPING_CART_COUPON);
			}
		}
	}
	
	public String getCouponTextName(){
		Coupons coupon = getCoupon();
		if(coupon != null){
			return TranslationUtils.getTranslation(coupon.getCouponsType().getTranslationByNameTransId(), getCurrentLanguage());
		}
		return "";
	}
	
	public String getCouponTextDescription(){
		Coupons coupon = getCoupon();
		if(coupon != null){
			return TranslationUtils.getTranslation(coupon.getCouponsType().getTranslationByDescriptionTransId(), getCurrentLanguage());
		}
		return "";
	}

	public String getCouponText() {
		return couponText;
	}

	public void setCouponText(String couponText) {
		this.couponText = couponText;
	}

	public String getPageFbTitle() {
		return pageFbTitle;
	}

	public void setPageFbTitle(String pageFbTitle) {
		this.pageFbTitle = pageFbTitle;
	}

	public String getPageFbDescription() {
		return pageFbDescription;
	}

	public void setPageFbDescription(String pageFbDescription) {
		this.pageFbDescription = pageFbDescription;
	}

	public String getPageFbUrl() {
		return pageFbUrl;
	}

	public void setPageFbUrl(String pageFbUrl) {
		this.pageFbUrl = pageFbUrl;
	}

	public String getPageFbImage() {
		return pageFbImage;
	}

	public void setPageFbImage(String pageFbImage) {
		this.pageFbImage = pageFbImage;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setUrlToRedirect(){
		String referer = request.getHeader("referer");
		if (StringUtils.isNotBlank(referer)) {
			setUrl(referer);
		}
	}
	
	public void sendEmailToUser(ShoppingCart shoppingCart) {
		try{
			Receipt.orderSummaryConfirmation( getUserSession(shoppingCart.getUsers()), shoppingCart, shoppingCart.getUsers().getLang(), getCurrentCurrencyCode());
		}
		catch(Exception e){
			managerException(e);
		}
		
	}

	public void inactiveCoupon(Users users) throws ServiceException, ServiceLocatorException {
		Coupons coupon = ServiceLocator.getService(CouponServiceImpl.class).getCouponToUser(users.getLogin());
		if ( coupon != null){
			if ( coupon.getCouponsUsers().size() == coupon.getCouponsType().getMultiTime()){
				coupon.setStatusCode(CouponStatus.INACTIVE);
				coupon.setInactiveDate(new Date());
			}
			else{
				coupon.setStatusCode(CouponStatus.ACTIVE);
			}
			ServiceLocator.getService(CouponServiceImpl.class).updateCoupons(coupon);
			request.getSession().removeAttribute(WebConstants.SHOPPING_CART_COUPON);
		}
	}
}
