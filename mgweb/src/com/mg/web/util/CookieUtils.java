package com.mg.web.util;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.mg.web.WebConstants;

/**
 * Utility class to handle cookies issues.
 * 
 * @author MJGP
 *
 */
public final class CookieUtils {

	// Utility class, no instantiation
	private CookieUtils(){};
	
	public static String getCookieValue(HttpServletRequest request, String cookieName){
		return getCookieValue(request, cookieName, false);
	}
	
	public static String getCookieValue(HttpServletRequest request, String cookieName, boolean parseFromHeader){
		return (parseFromHeader)? getCookieValueFromHeader(request, cookieName) 
						   : getCookieValue(request.getCookies(), cookieName);
	}
	
	/**
	 * Tomcat, starting with the release 6.0.14, doesn't allows special characters in cookies.
	 * We have an equal sign "=" in our cookies, one solution could be to change Tomcat configuration
	 * and set STRICT_SERVLET_COMPLIANCE=false; however, this violates Servlet specification.
	 * Another workaround is to parse cookies directly from the header.
	 *    
	 * Call this method only if your cookies contain special characters.
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	private static String getCookieValueFromHeader(HttpServletRequest request, String cookieName){
		String returnVal = null;

		Enumeration cookiesEnumeration = request.getHeaders("Cookie");
		if(cookiesEnumeration != null){

			while (cookiesEnumeration.hasMoreElements()) {
				String cookieRaw = (String) cookiesEnumeration.nextElement();

				for(String part : cookieRaw.split("; *")) {
					String key = part.substring(0, part.indexOf('='));
					if(key.equals(cookieName)){
						returnVal = part.substring(part.indexOf('=')+1);
						break;
					}
				}
			}    		
		}

		return returnVal;
	}
	
	public static String getCookieValue(Cookie[] cookies, String cookieName) {
		String returnVal = null;
		Cookie theCookie = getCookie(cookies, cookieName);
		if(theCookie != null){
			returnVal = theCookie.getValue();
		}
		
		return returnVal;
	}

	public static Cookie getCookie(Cookie[] cookies, String cookieName) {
		if(cookies != null){
			for(int i=0; i<cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName()))
					return(cookie);
			}
		}
		
		return null;
	}

	public static Cookie createLongLivedCookie(String name, String value) {
		return createCookie(name, value, WebConstants.SECONDS_PER_YEAR);
	}

	public static Cookie createCookie(String name, String value, int timeInSeconds){
		Cookie newCookie = new Cookie(name, value);       	       	
		newCookie.setMaxAge(timeInSeconds);
		return(newCookie);
	}

	/**
	 ** This method will delete the cookie given by the name specified.
	 ** The way to delete a cookie is to set the max age to zero.
	 **/
	public static Cookie removeCookie(String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		return(cookie);
	}
}
