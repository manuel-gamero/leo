package com.mg.web.struts.action.shopping.payment.paypal.beans;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.CurrencyNoExistException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Item;
import com.mg.model.Paypal;
import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.util.currency.CurrencyUtils;
import com.mg.util.translation.TranslationUtils;
import com.mg.web.struts.action.shopping.payment.paypal.AbstractPaypalAction;
import com.mg.web.struts.action.shopping.payment.paypal.ShoppingCartPaylPalCheckout;
import com.mg.web.util.ConnectionWebUtils;
import com.opensymphony.xwork2.Action;

public class ExpressCheckout extends AbstractPaypalAction {

	private static Logger log = LogManager.getLogger(ExpressCheckout.class);
	public ExpressCheckout(ShoppingCartPaylPalCheckout shoppingCartAction){
		setShoppingCartAction(shoppingCartAction);
	}
	
	@Override
	public String execute(Map<String, String> params) throws UnsupportedEncodingException, ServiceLocatorException, ServiceException, ParseException {
		String token = (String) params.get("TOKEN");
		String paypalUrl = ServiceLocator.getService(ConfigServiceImpl.class).getPaypalUrlCheckout()+ 
				"&token=" + ConnectionWebUtils.getParameter(token)+ "&useraction=" + ConnectionWebUtils.getParameter("commit");
		log.debug("paypalUrl: " + paypalUrl);
		
		//Register coupon to the user
		getShoppingCartAction().registeCouponToUser( getShoppingCart().getUsers(), getShoppingCartAction().getCoupon() );
		
		//create paypal object and set it to shoppingcart
		createValidPaypal(getShoppingCart(), params);
		ServiceLocator.getService(ShoppingServiceImpl.class).updateShoppingCart(getShoppingCart());
		return paypalUrl;
	}

	@Override
	public String getUrl() throws ServiceLocatorException, UnsupportedEncodingException, CurrencyNoExistException, ServiceException {
		String url = "";
		ShoppingCart shoppingCart = null;
		ConfigService configService = ServiceLocator.getService(ConfigServiceImpl.class);
		List<Item> itemList = getShoppingCartAction().getShoppingCartItems();
		if ( getShoppingCart() == null ) {
			return Action.ERROR;
		} else {
			shoppingCart = getShoppingCart();
		}
		
		url = getBasicParamUrl("SetExpressCheckout");
		url += "&RETURNURL=" + ConnectionWebUtils.getParameter(configService.getPaypalUrlOk());
		url += "&CANCELURL=" + ConnectionWebUtils.getParameter(configService.getPaypalUrlError());
		
		url += "&SHIPTOSTREET=" + ConnectionWebUtils.getParameter(getShoppingCart().getShippingAddressId().getStreet());
		url += "&SHIPTOCITY=" + ConnectionWebUtils.getParameter(getShoppingCart().getShippingAddressId().getCity());
		url += "&SHIPTOSTATE=" + ConnectionWebUtils.getParameter(getShoppingCart().getShippingAddressId().getProvince().getProvice());
		url += "&SHIPTOZIP=" + ConnectionWebUtils.getParameter(getShoppingCart().getShippingAddressId().getPostCode());
		url += "&SHIPTOCOUNTRY=" + ConnectionWebUtils.getParameter(getShoppingCart().getShippingAddressId().getCountry().getCode());
		
		
		url += "&PAYMENTREQUEST_0_INVNUM=" + ConnectionWebUtils.getParameter(getShoppingCart().getReference());
		
		url += "&PAYMENTREQUEST_0_ITEMAMT=" + ConnectionWebUtils.getParameter(getShoppingCartAction().getSubTotal().setScale(2,RoundingMode.CEILING).toString());
		url += "&PAYMENTREQUEST_0_TAXAMT=" + ConnectionWebUtils.getParameter(getShoppingCartAction().getTaxes().setScale(2,RoundingMode.CEILING).toString());
		url += "&PAYMENTREQUEST_0_SHIPPINGAMT=" + ConnectionWebUtils.getParameter(getShoppingCartAction().getShippingPrice().setScale(2,RoundingMode.CEILING).toString());
		url += "&PAYMENTREQUEST_0_HANDLINGAMT=" + ConnectionWebUtils.getParameter(getShoppingCartAction().getExtraTextPrice().setScale(2,RoundingMode.CEILING).toString());
		url += "&PAYMENTREQUEST_0_SHIPDISCAMT=-" + ConnectionWebUtils.getParameter(getShoppingCartAction().getTotalDiscountCoupon().setScale(2,RoundingMode.CEILING).toString());
		url += "&PAYMENTREQUEST_0_INSURANCEAMT=" + ConnectionWebUtils.getParameter("0.00");
		
		
		url += "&PAYMENTREQUEST_0_AMT=" + ConnectionWebUtils.getParameter(getShoppingCartAction().getTotal().setScale(2,RoundingMode.CEILING).toString());

		url += "&PAYMENTREQUEST_0_CURRENCYCODE=" + ConnectionWebUtils.getParameter(shoppingCart.getCurrency());
		url += "&PAYMENTREQUEST_0_CUSTOM=" + ConnectionWebUtils.getParameter("Thank You For business");
		url += "&PAYMENTREQUEST_0_DESC=" + ConnectionWebUtils.getParameter("Product Details");
		url += "&NOSHIPPING=" + ConnectionWebUtils.getParameter("1");
		url += "&useraction=" + ConnectionWebUtils.getParameter("commit");
		url += getItemListParams(itemList);
		return url;
	}

	private String getItemListParams(List<Item> itemList) throws UnsupportedEncodingException, CurrencyNoExistException {
		String itemParam = "";
		int i = 0;
		for (Item item : itemList) {
			itemParam += "&L_PAYMENTREQUEST_0_NAME" + i + "=" + ConnectionWebUtils.getParameter(TranslationUtils.getTranslation(item.getProduct().getTranslationByNameTransId(), getShoppingCartAction().getCurrentLanguage()) );
			//itemParam += "&L_PAYMENTREQUEST_0_DESC" + i + "=" + ConnectionWebUtils.getParameter(TranslationUtils.getTranslation(item.getProduct().getTranslationByDescriptionTransId(), getCurrentLanguage()) );
			itemParam += "&L_PAYMENTREQUEST_0_AMT" + i + "=" + ConnectionWebUtils.getParameter(CurrencyUtils.priceToLocale(item.getProduct().getPrice(), getShoppingCartAction().getCurrentCurrencyCode()).subtract(item.getDiscountPrice()).setScale(2,RoundingMode.CEILING).toString());
			itemParam += "&L_PAYMENTREQUEST_0_QTY" + i + "=" + ConnectionWebUtils.getParameter("1");
			i++;
		}
		return itemParam;
	}
	
	private void createValidPaypal(ShoppingCart shoppingCart, Map<String, String> result) {
		Paypal paypal = new Paypal();
		paypal.setToken((String) result.get("TOKEN"));
		paypal.setCorrelationid((String) result.get("CORRELATIONID"));
		paypal.setAck((String) result.get("ACK"));
		paypal.setUpdateDate(new Date());
		paypal.setCreationDate(new Date());
		paypal.setShoppingCart(shoppingCart);
		shoppingCart.setPaypal(paypal);
	}

}
