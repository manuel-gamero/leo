package com.mg.web.util;

import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.mg.enums.Language;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.exception.ExceptionHandler;
import com.mg.web.WebConstants;

/**
 * Utils to handle user's Locale.
 *  
 * @author MJGP
 *
 */
public final class LocaleUtils {
	
	private static final Logger log = Logger.getLogger(LocaleUtils.class);
	
	// Utility class, no instantiation
	private LocaleUtils(){};
	
	public static void handleLocale(HttpServletRequest request, boolean detectUserBrowser){
		handleLocale(request, detectUserBrowser, null);
	}
	
	public static void handleLocale(HttpServletRequest request, boolean detectUserBrowser, String language){
		/*
		 *  We check if the session has already been set, thus
		 *  we set the locale one time per session (better performance)
		 */	
		if(language != null){
			// We are changing the language manually
			setSessionLocale(request, language);
			
		}else if(getSessionLocale(request) == null){
			setSessionLocale(request, getDefaultLanguage(request, detectUserBrowser));
		}		
		
		if( getSessionCurrency(request) == null){
			if(request.getLocale() != null && request.getLocale().getCountry().length() == 2){
				setSessionCurrency(request, Currency.getInstance(request.getLocale()));
			}
			else{
				setSessionCurrency(request, Currency.getInstance(WebConstants.CURRENCY_CANADA));
			}
		}
	}
	
	/**
	 * Returns the session locale.
	 * @param request
	 * @return
	 */
	private static Locale getSessionLocale(HttpServletRequest request){		
		return (Locale) request.getSession().getAttribute(WebConstants.STRUTS_LOCALE);
	}
	
	/**
	 * Returns the session language.
	 * @param request
	 * @return
	 */
	public static String getSessionLanguage(HttpServletRequest request){
		Locale locale = getSessionLocale(request);
		return (locale != null)?locale.getLanguage():WebConstants.ENGLISH_LANGUAGE;
	}
	
	/**
	 * Returns the session country.
	 * @param request
	 * @return
	 */
	public static Currency getSessionCurrency(HttpServletRequest request){
		return (Currency)request.getSession().getAttribute(WebConstants.CURRENCY_SESSION);
	}
	
	/**
	 * Gets the language to put in session.
	 * First we check if the user is logged in and has a preference for
	 * site language. If not logged in user, we check if we had set any
	 * cookies and set the session accordingly. Finally, if no
	 * cookies are set, we detect user's browser language.
	 
	 * @param request
	 * @return
	 */
	public static String getDefaultLanguage(HttpServletRequest request, boolean detectUserBrowser){
		String userLanguage = null;
		
		// first try to get language from user profile 
		userLanguage = LoginUtils.getUserLanguage(request);
		
		// Then try to get it from cookie 
		if(userLanguage == null){
			userLanguage = CookieUtils.getCookieValue(request, WebConstants.USER_LANGUAGE_COOKIE);
		}		

		// finally try to get it from browser
		if(userLanguage == null && detectUserBrowser){
			userLanguage = getBrowserLanguage(request);
		}			
				
		return (userLanguage != null) ? userLanguage : WebConstants.ENGLISH_LANGUAGE;	
	}

	/**
	 * Checks the Accept-Language header to see language
	 * preference in user's browser.
	 * @param request
	 * @return
	 */
	public static String getBrowserLanguage(HttpServletRequest request){
		String acceptLanguage = request.getHeader("Accept-Language");
		String language = null;
		if(acceptLanguage != null){
			StringTokenizer tokenizer = new StringTokenizer(acceptLanguage, ",");
		    while (tokenizer.hasMoreTokens()) {
		        // Get the next acceptable language.
		        // (The language can look something like "en; qvalue=0.91")
		        language = tokenizer.nextToken();
		        break;
		      }
		}

		return language;
	}
	
	public static void setSessionLocale(HttpServletRequest request, String language){
		if(log.isDebugEnabled()){
			log.debug("We are setting request_locale to: " + language);		
		}
		
		//request.setAttribute(WebConstants.STRUTS_LOCALE, language);
		// update user in session if we are loged
		if (LoginUtils.isLogged(request)) {
			UserSessionDTO user = LoginUtils.getUser(request);
			user.setSiteLangId(WebConstants.getLanguageId(language));
		}
		if(language == null){
			language = Language.FRENCH.getCode();
			log.warn("Trying to set a null language.");
		}
		request.getSession().setAttribute(WebConstants.STRUTS_LOCALE, new Locale(language));
	}
	
	public static void setSessionCurrency(HttpServletRequest request, Currency currency){
		if(log.isDebugEnabled()){
			log.debug("We are setting currency to: " + currency);		
		}
		ConfigService configService;
		try {
			configService = ServiceLocator.getService(ConfigServiceImpl.class);
			//Set CA to not allow multy currencies
			if(configService.isAllowMultyCurrency()){
				if(currency.getCurrencyCode().equals("CAD") || currency.getCurrencyCode().equals("USD")
				   || currency.getCurrencyCode().equals("EUR")){
					request.getSession().setAttribute(WebConstants.CURRENCY_SESSION, currency);
				}
				else{
					request.getSession().setAttribute(WebConstants.CURRENCY_SESSION,Currency.getInstance(WebConstants.CURRENCY_USA) );
				}
			}
			else{
				request.getSession().setAttribute(WebConstants.CURRENCY_SESSION, Currency.getInstance(WebConstants.CURRENCY_CANADA));
			}
		} catch (Exception e) {			
			request.getSession().setAttribute(WebConstants.CURRENCY_SESSION, currency);
			ExceptionHandler.handleException(e, null, null);
		}
		
	}
	
	public static String formatDate(Date date, String formatPattern, HttpServletRequest request) {
		return DateFormatUtils.format(date, formatPattern, (Locale)request.getSession().getAttribute(WebConstants.STRUTS_LOCALE));
	}
}
