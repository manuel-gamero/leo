package com.mg.web.struts.action.user;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.user.UserService;
import com.mg.service.user.UserServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.mg.web.struts.action.CUDAction;
import com.mg.web.util.LoginUtils;

public class UserCUD extends BasicAction implements CUDAction {
	
	private static final long serialVersionUID = 7640068153357889448L;

	private UserService userService;
	private UserSessionDTO userSession;
	/*
	 * Form field
	 */
	private String username;
	private String email;
	private String password;
	private String confirmPassword;
	private long country;
	private int primaryLang;
	
	public UserCUD() throws ServiceLocatorException {
		userService = ServiceLocator.getService(UserServiceImpl.class);
	}	
	
	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String create() throws ServiceException {
		try {
			userSession = userService.createAccount(username, LoginUtils.generateStorngPasswordHash(password), email, country, primaryLang);
			setUserSession(userSession);
		} catch (ServiceException e) {
			addActionError(getText("bolsos.error.common.service"));
		} catch (Exception e) {
			addActionError(getText("bolsos.error.common.service"));
		}		
		return getSuccessResult();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public long getCountry() {
		return country;
	}

	public void setCountry(long country) {
		this.country = country;
	}

	public int getPrimaryLang() {
		return primaryLang;
	}

	public void setPrimaryLang(int primaryLang) {
		this.primaryLang = primaryLang;
	}

			
	
}
