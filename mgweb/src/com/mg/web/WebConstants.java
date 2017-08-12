package com.mg.web;

import org.apache.log4j.Logger;

import com.mg.enums.ComponentAttributeType;
import com.mg.enums.ComponentType;
import com.mg.enums.Language;
import com.mg.enums.MethodShippingStatus;
import com.mg.enums.ProductStatus;
import com.mg.enums.ProductType;
import com.mg.enums.ShoppingCartStatus;
import com.mg.enums.UserAddressType;
import com.mg.enums.UserStatus;
import com.mg.enums.UserType;
import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;

/**
 * All web constants go in this class.
 * 
 * @author MJGP
 *
 */
public final class WebConstants {

	private static final Logger LOG = Logger.getLogger(WebConstants.class);
	private static ConfigService configService;
	static {
		try {
			configService = ServiceLocator.getService(ConfigServiceImpl.class);
		} catch (ServiceLocatorException e) {
			LOG.error("A problem has happened while looking up the config service.", e);
		}
	}
	
	// Constants class, no instantiation
	private WebConstants() {};	

	/* Country */
	public static final String COUNTRY_CANADA = "CA";
	public static final String CURRENCY_CANADA = "CAD";
	public static final String CURRENCY_USA = "USD";
	public static final String CURRENCY_SESSION = "CURRENCY_SESSION";
	
	/* Language */
	public static final String ENGLISH_LANGUAGE = "en";
	public static final String FRENCH_LANGUAGE 	= "fr";
	public static final String SPANISH_LANGUAGE = "es";
	
	public static final int ENGLISH_LANGUAGE_ID = 1;
	public static final int FRENCH_LANGUAGE_ID 	= 2;
	public static final int SPANISH_LANGUAGE_ID = 3;
	
	/* Struts*/
	public static final String STRUTS_LOCALE 			= "WW_TRANS_I18N_LOCALE";
	public static final String STRUTS_LOCALE_REQUEST 	= "request_locale";
	
	/**
	 * <p>
	 * The session scope attribute under which the User object for the currently
	 * logged in user is stored.
	 * </p>
	 */
	public static final String USER 						= "user";
	public static final String NEXTSTEP 					= "nextStep";  // we need it later
	
	/**	 
	 * User's Cookies keys 
	 */
	public static final String USER_LANGUAGE_COOKIE 			= "language";
	public static final String USER_COUNTRY_COOKIE 				= "country";
	public static final String USER_SEARCH_RESULTS_COOKIE 		= "searchResults";
	
	/* Cookies */	
	public static final int SECONDS_PER_YEAR 					= 60*60*24*365;
	
	/** regex value for game name comparaison **/
	public static final String regex						    = "[,:-_()'\"\\/|]";
	
	public static UserType[] ALL_USER_TYPE = UserType.values();
	public static Language[] ALL_LANGUAGE = Language.values();
	public static UserStatus[] ALL_USER_STATUS = UserStatus.values();
	public static UserAddressType[] ALL_USER_ADDRESS_TYPE = UserAddressType.values();
	public static ComponentType[] ALL_COMPONENT_TYPE = ComponentType.values();
	public static ComponentAttributeType[] ALL_COMPONENT_ATTRIBUTE_TYPE = ComponentAttributeType.values();
	public static ProductType[] ALL_PRODUCT_TYPE = ProductType.values();
	public static ProductStatus[] ALL_PRODUCT_STATUS = ProductStatus.values();
	public static MethodShippingStatus[] ALL_METHOD_SHIPPING_STATUS = MethodShippingStatus.values();
	public static String[] ALL_CURRENCIES = {"CAD","EUR","USD"};
	public static ShoppingCartStatus[] ALL_SHOPPINGCART_STATUS = ShoppingCartStatus.values();
	
	public static final String SHOPPING_CART_ITEMS = "shopping_cart_items";
	public static final String SHOPPING_CART = "shopping_cart";
	public static final String SHOPPING_CART_COUPON = "shopping_cart_coupon";
	public final static boolean CURRENCY_CHOICE = configService.isCurrencyChoice();
	
	//Constant use to propagate erroractions between action when redirect from an action to another
	public static final String ERRORACTION = "errorAction";
	
	static public int getLanguageId(String lang) {
		int res = 0;
		if(lang.equals(ENGLISH_LANGUAGE)) {
			res = ENGLISH_LANGUAGE_ID;
		} 
		else if (lang.equals(FRENCH_LANGUAGE)) {
			res =FRENCH_LANGUAGE_ID;
		}
		else if (lang.equals(SPANISH_LANGUAGE)) {
			res =SPANISH_LANGUAGE_ID;
		}
		return res;
	}
	
	static public String getLanguageStr(int id) {
		String res = null;
		if(id == ENGLISH_LANGUAGE_ID) {
			res = ENGLISH_LANGUAGE;
		} 
		else if (id == FRENCH_LANGUAGE_ID) {
			res =FRENCH_LANGUAGE;
		}
		else if (id == SPANISH_LANGUAGE_ID) {
			res =SPANISH_LANGUAGE;
		}
		return res;
	}
	
	//mail constant support
	public static final String MAIL_SUPPORTGAMEJAB_KEY 	    = "support";
	public static final String MAIL_MEDIA_INQUIRY_KEY	    = "media";
	public static final String MAIL_MARKETING_KEY		    = "marketing";
	public static final String MAIL_OTHER_KEY			    = "other";
	
	
	/**
	 * <p>
	 * Static configuration to locate js, css and images content of the application.
	 * These constants will be referenced in all jsps. By this way we can move this content in another domain.
	 * The loading of these constants will be processed at the application start up from a properties file.
	 * </p>
	 */
	public final static String SCRIPTS_BASE_URL 			= configService.getWebScriptsLocation();
	public final static String CSS_BASE_URL 				= configService.getWebCssLocation();
	public final static String IMAGES_BASE_URL 				= configService.getWebImagesLocation();
	public final static String MEDIA_BASE_URL 				= configService.getWebMediaLocation();
	public final static String COLLECTION_BASE_URL 			= configService.getWebImageCollectionLocation();
	public final static String PRODUCT_BASE_URL 			= configService.getWebImageProdcutLocation();
	public final static String TMP_BASE_URL					= configService.getWebImageTmp();
	
	
	// File size allowed in bytes
	public static final int PRODUCT_SIZE_ALLOWED 			= 2048000;
	public static final int WIDTH_PRODUCT_MIN				= 500; //px
	
}
