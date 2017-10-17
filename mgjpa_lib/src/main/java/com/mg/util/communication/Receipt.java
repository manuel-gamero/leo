/**
 * Utility class to regroup email manipulation common methods.
 * 
 * @author MJGP
 *
 */

package com.mg.util.communication;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mg.enums.Language;
import com.mg.enums.UserStatus;
import com.mg.enums.UserType;
import com.mg.exception.CurrencyNoExistException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Product;
import com.mg.model.ShoppingCart;
import com.mg.model.UserAddress;
import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.service.user.UserServiceImpl;
import com.mg.util.CommonUtils;
import com.mg.util.currency.CurrencyUtils;
import com.mg.util.date.TimeAndDate;
import com.mg.util.text.StringUtils;
import com.mg.util.translation.TranslationUtils;

public class Receipt {

	private static final Logger log = Logger.getLogger(Receipt.class);
	public static final BigDecimal ZERO = new BigDecimal(0.00).setScale(2, RoundingMode.CEILING);
	private static ConfigService configService;
	static {
		try {
			configService = ServiceLocator.getService(ConfigServiceImpl.class);
		} catch (ServiceLocatorException e) {
			log.error("A problem has happened while looking up the config service.", e);
		}
		
	}
	protected static final DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
	public static final int LANGUAGE_ENGLISH					= 1;
	public static final int LANGUAGE_FRENCH						= 2;
	public static final String REVIEW_TITLE 					= "#REVIEW_TITLE#";
	public static final String REVIEW_BODY 						= "#REVIEW_BODY#";
	public static final String templatePath 					= "/WEB-INF/communication/templates/"; 
	public static final String englishTemplatePath 				= configService.getRootPathWeb() + "WEB-INF/communication/template/en/";
	public static final String frenchTemplatePath 				= configService.getRootPathWeb() + "WEB-INF/communication/template/fr/";
	public static final String passwordRequestTemplate 			= "email_password.html";
	public static final String signUpMembership		 			= "email.html";
	public static final String tellAFriendTemplate 				= "tellAFriend.txt";
	public static final String tellAFriendHTMLTemplate 			= "tellAFriend.html";
	public static final String tellAFriendFrenchTemplate 		= "tellAFriend_fr.txt";
	public static final String tellAFriendFrenchHTMLTemplate 	= "tellAFriend_fr.html";
	
	public static final String orderSumaryConfirmation 	= "customer-order.html";
	public static final String registerConfirmation 	= "customer-register.html";


	public Receipt() {
	}

	private static String toDdMmmYyyy(TimeAndDate date, String lang) {

		String[] enMonths = { "", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October",
				"November", "December" };
		String[] frMonths = { "", "Janvier", "Février", "Mars", "Avril", "Mai",
				"Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre",
				"Décembre" };

		int d, m, y;
		d = date.get(Calendar.DATE);
		m = date.get(Calendar.MONTH) + 1;
		y = date.get(Calendar.YEAR);

		return d + "-"
				+ ((lang.equalsIgnoreCase("fr")) ? frMonths[m] : enMonths[m])
				+ "-" + y;
	}


	public static boolean tellAFriend(String senderName, String customerID,
			String senderEmail, String recipientFirstName,
			String recipientEmail, String lang) {

		String subject;

		Hashtable<String, String> keyValue = new Hashtable<String, String>();
		keyValue.put("#NAME#", recipientFirstName);
		keyValue.put("#REFERAL_ID#", customerID);
		keyValue.put("#SENDER_FIRST_NAME#", senderName);

		// -----------
		// .. CONSTRUCT AND MAIL
		// -----------

		File receiptFile = null;
		File receiptFileHTML = null;

		if (lang.equalsIgnoreCase("fr")) {
			subject = "Message de " + senderName;
			receiptFile = new File(templatePath + tellAFriendFrenchTemplate);
			receiptFileHTML = new File(templatePath
					+ tellAFriendFrenchHTMLTemplate);
		} else {
			subject = "Message from " + senderName;
			receiptFile = new File(templatePath + tellAFriendTemplate);
			receiptFileHTML = new File(templatePath + tellAFriendHTMLTemplate);
		}

		String emailBodyHTML = StringUtils.replaceFileWithHashTable(
				receiptFileHTML, keyValue);
		String emailBody = StringUtils.replaceFileWithHashTable(receiptFile,
				keyValue);

		System.out.println(emailBody);
		// send(String toWhom [], String fromWhom, String subject, String body,
		// File theFile, String htmlString)
		boolean success = Mail.send(new String[] { recipientEmail },
				senderEmail, subject, emailBody, null, emailBodyHTML);

		return (success);
	}


	public static boolean passwordRequest(Users user) {
		Language languageUser = Language.ENGLISH;
		String templatePath = getTemplatePath(languageUser);

		Hashtable<String,String> keyValue = new Hashtable<String,String>();
		//keyValue.put("#FIRSTNAME#", user.getUserProfile().getRealName());
		keyValue.put("#LOGIN#", user.getLogin());
		keyValue.put("#PASSWORD#", user.getPassword());
		
		String subject = "Password Request";
		if (languageUser.getValue() == LANGUAGE_FRENCH)
			subject = "Demande de mot de passe";

		File receiptFile = new File(templatePath + passwordRequestTemplate);
		String emailBody = StringUtils.replaceFileWithHashTable(receiptFile,
				keyValue);

		System.out.println(emailBody);
		StringBuffer htmlStr = new StringBuffer(emailBody);

		boolean success = Mail.send(user.getLogin(), subject, emailBody, htmlStr);

		return (success);
	}
	
	public static boolean signUpMembership(Users user) {
		
		Language languageUser = Language.ENGLISH;
		
		String templatePath = getTemplatePath(languageUser);

		Hashtable<String,String> keyValue = new Hashtable<String,String>();
		//keyValue.put("#FIRSTNAME#", user.getUserProfile().getRealName());
		keyValue.put("#LOGIN#", user.getEmail());
		keyValue.put("#PASSWORD#", user.getPassword());

		String subject = "SignUp";
		if (languageUser.getValue() == LANGUAGE_FRENCH)
			subject = "Enregistrement";

		File receiptFile = new File(templatePath + signUpMembership);
		String emailBody = StringUtils.replaceFileWithHashTable(receiptFile,
				keyValue);

		System.out.println(emailBody);
		StringBuffer htmlStr = new StringBuffer(emailBody);

		boolean success = Mail.send(user.getEmail(), subject, emailBody, htmlStr);

		return (success);
	}

	public static String getTemplatePath(Language language) {
		if (language.getValue() == LANGUAGE_FRENCH)
			return frenchTemplatePath;
		else
			return englishTemplatePath;
	}
	
	public static boolean sendTemplateFilledToUser(UserSessionDTO user, String language, Hashtable<String,String> keyValue, String subject , String fileTemplate, Map<String, String> inlineImages) {
		Language languageUser = Language.getLanguageByCode(language);
		String templatePath = getTemplatePath(languageUser);
		
		File receiptFile = new File(templatePath + fileTemplate);
		String emailBody = StringUtils.replaceFileWithHashTable(receiptFile,
				keyValue);

		System.out.println(emailBody);
		StringBuffer htmlStr = new StringBuffer(emailBody);

		boolean success = Mail.sendHtml (user.getLogin(), subject, htmlStr.toString(), inlineImages);

		return (success);
	}
	
	public static boolean orderSummaryConfirmation(UserSessionDTO user, ShoppingCart shoppingCart, String lang, String currencyCode) throws CurrencyNoExistException {
		try {
			Users userData = ServiceLocator.getService(UserServiceImpl.class).getUser(user.getId());
			Hashtable<String,String> keyValue = new Hashtable<String,String>();
			//Map<String, String> inlineImages = new Hashtable<String, String>();
			//keyValue.put("#FIRSTNAME#", user.getUserProfile().getRealName());
			
			//String total = getTotalShippingCart(shoppingCart);
			
			keyValue.put("#FIRSTNAME#", userData.getFirstName());
			keyValue.put("#LASTNAME#", userData.getLastName());
			keyValue.put("#SUBTOTAL#", CurrencyUtils.displayPriceLocale(shoppingCart.getTotal(), lang, currencyCode));
			keyValue.put("#SHIPPING#", CurrencyUtils.displayPriceLocale(shoppingCart.getShippingFees(), lang, currencyCode));
			keyValue.put("#TAXES#", CurrencyUtils.displayPriceLocale(shoppingCart.getTaxes(), lang, currencyCode));
			keyValue.put("#TOTAL#", CurrencyUtils.displayPriceLocale(shoppingCart.getTotalShopping(), lang, currencyCode));
			keyValue.put("#REFERENCE#", shoppingCart.getReference());
			keyValue.put("#EMBROIDERY#", CurrencyUtils.displayPriceLocale(shoppingCart.getExtras(), lang, currencyCode));
			keyValue.put("#METHODSHIPPING#", TranslationUtils.getTranslation( shoppingCart.getMethodShipping().getTranslationByNameTransId(), lang) );
			keyValue.put("#CREATEDATE#", StringUtils.getDisplayOrderDate(new Date()) );
			keyValue.put("#STATUSORDER#", shoppingCart.getStatusCode().name());
			keyValue.put("#COUPONDISCOUNTSECTION#", getDiscountCoupon( shoppingCart, lang, currencyCode ));
			
			keyValue.put("#ITEMLIST#", createItemList(shoppingCart, lang, currencyCode));
			keyValue.put("#SHIIPINGADDRESS#", createShippingAddress(shoppingCart.getShippingAddressId()));
			
			String subject = "";
			if(Language.getLanguageByCode(lang).equals(Language.FRENCH)){
				subject =  "L'atelier de Leo - Confirmation de Resume de Commande";
			}
			else{
				subject =  "L'atelier de Leo - Order Summary Confirmation";
			}

			boolean success = sendTemplateFilledToUser(user, lang, keyValue, subject, orderSumaryConfirmation, null);

			return (success);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		} 
		
		
	}
	
	private static String getDiscountCoupon(ShoppingCart shoppingCart, String lang, String country) throws CurrencyNoExistException {
		String discount = "";
		BigDecimal dicountCoupon = getDiscount(shoppingCart.getDiscountExtras()).add( getDiscount(shoppingCart.getDiscountShippingFees()) ).add( getDiscount(shoppingCart.getDiscountTaxes()) ).add( getDiscount(shoppingCart.getDiscountTotal()) ).add( getDiscount(shoppingCart.getDiscountTotalShopping()) );
		if (dicountCoupon.floatValue() > 0 ){
			discount = "<tr><th colspan='5' class='text-right'>Taxes</th>" +
					   "<th> -" + CurrencyUtils.displayPriceLocale( dicountCoupon, lang, country) + "</th>";
		}
		
		return discount;
	}

	private static BigDecimal getDiscount(BigDecimal discount) {
		if(discount == null){
			return ZERO;
		}
		else{
			return discount;
		}
	}

	public static boolean registrationConfirmation(UserSessionDTO user, String lang, String country) throws ServiceException, ServiceLocatorException{

		Hashtable<String,String> keyValue = new Hashtable<String,String>();
		Map<String, String> inlineImages = new Hashtable<String, String>();
		Users userData = ServiceLocator.getService(UserServiceImpl.class).getUser(user.getId());
		
		keyValue.put("#LOGIN#", user.getLogin());
		keyValue.put("#FIRSTNAME#", userData.getFirstName());
		keyValue.put("#LASTNAME#", userData.getLastName());
		keyValue.put("#EMAIL#", userData.getEmail());
		keyValue.put("#PHONE#", userData.getPhone());
		keyValue.put("#SHIIPINGADDRESS#", createShippingAddress(userData.getUserAddresses().iterator().next() ));
		String subject = "";
		if(Language.getLanguageByCode(lang).equals(Language.FRENCH)){
			subject = "Bienvenue à L'atelier de LEO - Confirmation d'inscription";
		}
		else{
			subject = "Welcome to L'atelier de Leo - Registration Confirmation";
		}

		boolean success = sendTemplateFilledToUser(user, lang, keyValue, subject, registerConfirmation, inlineImages);

		return (success);
	}
	
	private static String getTotalShippingCart(ShoppingCart shoppingCart) {
		BigDecimal subtotal = shoppingCart.getTotal();
		BigDecimal shipping = shoppingCart.getShippingFees();
		BigDecimal taxes = shoppingCart.getTaxes();
		BigDecimal extras = shoppingCart.getExtras();
		
		return CommonUtils.formatPrice(subtotal.add(shipping).add(taxes).add(extras));
	}

	private static String createShippingAddress(UserAddress shippingAddressId) {
		StringBuffer address = new StringBuffer(" ");
		address.append(shippingAddressId.getFirstName()+ " " + shippingAddressId.getLastName());
		address.append("<br>");
		address.append(shippingAddressId.getStreet() + " " + shippingAddressId.getApartment());
		address.append("<br>");
		address.append(shippingAddressId.getPostCode());
		address.append("<br>");
		address.append(shippingAddressId.getProvince());
		address.append("<br>");
		address.append(shippingAddressId.getCountry());
		return address.toString();
	}

	private static String createItemList(ShoppingCart shoppingCart, String lang, String currencyCode) throws ServiceLocatorException, CurrencyNoExistException, ServiceException {
		StringBuffer row = new StringBuffer(" ");
		StringBuffer table = new StringBuffer(" ");
		
		for (com.mg.model.ShoppingCartItem item : shoppingCart.getShoppingCartItems()) {
			
			String urlServe = ServiceLocator.getService(ConfigServiceImpl.class).getUrlServer(); 
			String urlImage = null;
			if(item.getItem().getProduct().getCustomProduct()){
				urlImage = urlServe +  ServiceLocator.getService(ConfigServiceImpl.class).getWebImageTmp();
				urlImage = urlImage + item.getItem().getNameImage();
			}
			else{
				urlImage = urlServe + ServiceLocator.getService(ConfigServiceImpl.class).getWebImageProdcutLocation();
				String idProduct = String.valueOf(item.getItem().getProduct().getId());
				Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(item.getItem().getProduct().getId(), true);
				urlImage = urlImage + idProduct + "/" + product.getImage().getName();
			}
			
			row.append(" <tr> <td> <a href='#'>") ;
			row.append("<img src=" + urlImage + "  />");
			row.append("</a> </td> <td><a class = 'link product-name' href='#'>" + TranslationUtils.getTranslation(item.getItem().getProduct().getTranslationByNameTransId(), lang) ); 
			row.append("</a> </td> <td class='text-center' > " + item.getQuantity() + " </td> <td class='text-center'> " + CurrencyUtils.displayPriceLocale(item.getPrice(), lang, currencyCode) ); 
			row.append(" </td> <td class='text-center'> " + CurrencyUtils.displayPriceLocale(item.getDiscount(), lang, currencyCode) ); 
			row.append(" </td> <td class='text-center'> " + CurrencyUtils.displayPriceLocale(item.getPrice(), lang, currencyCode) ); 
			row.append(" </td> </tr>" );
			
			table.append( row );
			row = new StringBuffer(" ");
		}
		return table.toString();
	}

	private static String createItemListWithImagenFile(ShoppingCart shoppingCart, Map<String, String> inlineImages , String lang, String country) throws ServiceLocatorException, CurrencyNoExistException, ServiceException {
		StringBuffer row = new StringBuffer(" ");
		StringBuffer table = new StringBuffer(" ");
		
		int i=1;
		
		for (com.mg.model.ShoppingCartItem item : shoppingCart.getShoppingCartItems()) {
			row.append(" <tr> <td> <a href='#'>") ;
			row.append("<img src=\"cid:image" + i + "\"  />");
			row.append("</a> </td> <td><a class = 'link' href='#'>" + TranslationUtils.getTranslation(item.getItem().getProduct().getTranslationByNameTransId(), lang) ); 
			row.append("</a> </td> <td> " + item.getQuantity() + " </td> <td> " + CurrencyUtils.displayPriceLocale(item.getPrice(), lang, country) ); 
			row.append(" </td> <td> " + CurrencyUtils.displayPriceLocale(item.getDiscount(), lang, country) ); 
			row.append(" </td> <td> " + CurrencyUtils.displayPriceLocale(item.getPrice(), lang, country) ); 
			row.append(" </td> </tr>" );
			
			String pathImage = null ;
			if(item.getItem().getProduct().getCustomProduct()){
				String path = ServiceLocator.getService(ConfigServiceImpl.class).getRootPathWeb() + ServiceLocator.getService(ConfigServiceImpl.class).getWebImageTmp();
				path = path.replace("//", "/");
				pathImage = path + item.getItem().getNameImage();
			}
			else{
				String path = ServiceLocator.getService(ConfigServiceImpl.class).getRootPathWeb() + ServiceLocator.getService(ConfigServiceImpl.class).getWebImageProdcutLocation();
				path = path.replace("//", "/");
				String idProduct = String.valueOf(item.getItem().getProduct().getId());
				Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(item.getItem().getProduct().getId(), true);
				pathImage = path + idProduct + "/" + product.getImage().getName();
			}
			inlineImages.put("image" + i , pathImage);
			i++;
			table.append( row );
			row = new StringBuffer(" ");
		}
		return table.toString();
	}

	public static void main(String args[]) {
		
		 UserSessionDTO user = UserSessionDTO.valueOf(0, "manuel.gamero@hotmail.com", 1, 1, 10, "manuel.gamero@hotmail.com", UserType.USER, UserStatus.ACTIVE, null);
		 user.setLogin("manuel.gamero@hotmail.com");
		 
		 ShoppingCart shoppingCart = new ShoppingCart();
		 shoppingCart.setTotal(new BigDecimal("10.00"));
		 shoppingCart.setTaxes(new BigDecimal("10.00"));
		 shoppingCart.setShippingFees(new BigDecimal("10.00"));
		 
		 //orderSummaryConfirmation(user,shoppingCart);
		 
		/* User user = new User();
		 user.setLogin("manuel");
		 user.setPassword("a123456");
		 user.setEmail("manuel@gameaccess.ca");
		 UserProfile userProfile =  new UserProfile();
		 userProfile.setRealName("a a");
		 userProfile.setPrimaryLanguage(Language.ENGLISH);
		 user.setUserProfile(userProfile);
		 
		 Receipt.signUpMembership(user);*/
		 
		// theCustomer.selectFromID(1);
		// sendReceipt(theCustomer, theSubscription);
		// Mail.send("jerry@inteligan.com", "Registration Details:Page 1 Sign up
		// Page 1 Validation Success : From Jerry", "Test");
		// theCustomer.setFirstName("Clyde");
		// theCustomer.setLastName("Jones");

		/*
		 * 
		 */
		// theCustomer.setEmail("sandeep.mys@gmail.com");
		// theSubscription.setID(777);
		// Receipt.cardDeclineNotification(theCustomer);
		// Receipt theReceipt = new Receipt();
		// theReceipt.sendReceipt(theCustomer, theSubscription);
		// Mail.send("prince.nishchal@gmail.com", "Test Mail", new
		// File(frenchTemplatePath+monthlyPurchaseReceipt));
		// Mail.send("frank@GameJab", "Test Mail", new
		// File(frenchTemplatePath+standardPurchaseReceipt));
		// Mail.send("karl@GameJab", "Test Mail", new
		// File(frenchTemplatePath+standardPurchaseReceipt));
		// Mail.send("prince.nishchal@gmail.com", "Test Mail", new
		// File(frenchTemplatePath+standardPurchaseReceipt));
		// Mail.send("frank@GameJab", "Test - Free Trial Mail", new
		// File(englishTemplatePath+standardPurchaseReceipt));
		// Mail.send("karl@GameJab", "Test - Free Trial Mail", new
		// File(englishTemplatePath+standardPurchaseReceipt));
		// Mail.send("prince.nishchal@gmail.com", "Test - Free Trial Mail", new
		// File(englishTemplatePath+standardPurchaseReceipt));
		// Mail.send("frank@GameJab", "Test - Monthly Mail", new
		// File(englishTemplatePath+monthlyPurchaseReceipt));
		// Mail.send("karl@GameJab", "Test - Monthly Mail", new
		// File(englishTemplatePath+monthlyPurchaseReceipt));
		// Mail.send("prince.nishchal@gmail.com", "Test - Monthly Mail", new
		// File(englishTemplatePath+monthlyPurchaseReceipt));
		// 
	}
}
