package com.mg.web.struts.action.ajax;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.user.UserService;
import com.mg.service.user.UserServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.mg.web.util.LoginUtils;

public class LostUserOrPwdAjax extends BasicAction{
	
	private static final long serialVersionUID = 7640068153357889448L;
	private static Logger log = Logger.getLogger(LostUserOrPwdAjax.class);
	
	private String email;
	private String result;
	
	public String resetPassword() {
		try {
			UserService userService = ServiceLocator.getService(UserServiceImpl.class);
			Users user = userService.getUser(email);
			if( user != null ){
				String newPassword = RandomStringUtils.randomAscii(16);
				user.setPassword( LoginUtils.generateStorngPasswordHash(newPassword));
				userService.sendUserPassword(user, newPassword);
				log.info("User: " + email + " request change password.");
			}
			else{
				log.error("User: " + email + " not exist.");
				setResult( getText("bolsos.signin.error.label.user") );
				return ERROR;
			}
		} catch (Exception e) {
			managerException(e);
			setResult(ERROR);
			return ERROR;
		}
		setResult(SUCCESS);
		return SUCCESS;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}	
}
