package com.mg.web.struts.action.user;

import com.mg.exception.ServiceLocatorException;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.BasicAction;


/**
 * This class is in charge of logging out a User
 *  @author MJGP
 */
public class Logout extends BasicAction {
	
	private static final long serialVersionUID = 7640068153357889448L;

	public Logout() throws ServiceLocatorException {}	
	
	@Override
	public String execute() {
		//remove the user information in session
		if (getUserSession() != null) {
			request.getSession().removeAttribute(WebConstants.USER);
			request.getSession().removeAttribute(WebConstants.SHOPPING_CART);
			request.getSession().removeAttribute(WebConstants.SHOPPING_CART_ITEMS);
		}
		return getSuccessResult();
	}
	
}
