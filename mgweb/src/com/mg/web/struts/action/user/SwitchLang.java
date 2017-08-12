/**
 * 
 */
package com.mg.web.struts.action.user;

import org.apache.commons.lang.StringUtils;

import com.mg.exception.ServiceLocatorException;
import com.mg.web.struts.action.BasicAction;

/**
 * This class is in charge of Switching language
 *  * @author MJGP     
 */
public class SwitchLang extends BasicAction  {
	
	private static final long serialVersionUID = 7640068153357889448L;

	public SwitchLang() throws ServiceLocatorException {}
	
	private String lang;
	
	@Override
	public String execute() {
		try{
			// set a new language for this session 
			setCurrentLanguage(lang);
			
			// set the url where this action come from 
			String referer = request.getHeader("referer");
			if (referer == null){
				return HOME;
			}
			referer = referer.replaceAll("/[a-zA-Z][a-zA-Z]/", "/" + lang + "/");
			if (StringUtils.isNotBlank(referer)) {
				setUrl(referer);
				return getSuccessResult();
			} else {
				// request there is no referer in this request send user toward homepage
				// this case won't appear but we don't know
				return HOME;
			}
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	
}
