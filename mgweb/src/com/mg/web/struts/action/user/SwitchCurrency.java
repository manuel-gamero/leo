/**
 * 
 */
package com.mg.web.struts.action.user;

import java.util.Currency;

import org.apache.commons.lang.StringUtils;

import com.mg.exception.ServiceLocatorException;
import com.mg.util.currency.CurrencyUtils;
import com.mg.web.struts.action.BasicAction;
import com.mg.web.util.LocaleUtils;

/**
 * This class is in charge of Switching language
 *  * @author MJGP     
 */
public class SwitchCurrency extends BasicAction  {
	
	private static final long serialVersionUID = 7640068153357889448L;

	public SwitchCurrency() throws ServiceLocatorException {}
	
	private String url;
	private String currency;
	
	@Override
	public String execute() {
		try{
			// set a new language for this session 
			
			//if there is not currency, I set up one by default.
			if(currency == null){
				currency = CurrencyUtils.DEFAULT_CURRENCY;
				log.warn("Trying to set a null language for request: " + getRequest().getRequestURI());
			}
			LocaleUtils.setSessionCurrency(request, Currency.getInstance(currency));
			
			// set the url where this action come from 
			setRefereUrl();
			if (StringUtils.isNotBlank(getReferer())) {
				return getSuccessResult();
			} else {
				// request there is no referer in this request send user toward homepage
				// this case won't appear but we don't know
				return "homePage";
			}
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


	
}
