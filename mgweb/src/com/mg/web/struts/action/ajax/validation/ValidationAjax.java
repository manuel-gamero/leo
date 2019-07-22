package com.mg.web.struts.action.ajax.validation;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.InvalidUserException;
import com.mg.exception.ServiceException;
import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.user.UserServiceImpl;
import com.mg.web.struts.action.BasicListActionSupport;

public class ValidationAjax extends BasicListActionSupport {
	
	private static final long serialVersionUID = 1L;
	protected transient Logger log = LogManager.getLogger(this.getClass());
	private transient Users user;
	private String username;
	private String password;
	private String resutl;
	private String email;
		
	public ValidationAjax() {		
	}
	
	
	public String validateEmail() {	
		
		try {
			if(user!= null){
				if(ServiceLocator.getService(UserServiceImpl.class).usernameExists(user.getEmail())){
					return  ERROR;
				}
				else{
					return SUCCESS;
				}
			}
			else{
				if(ServiceLocator.getService(UserServiceImpl.class).usernameExists(getEmail())){
					return  ERROR;
				}
				else{
					return SUCCESS;
				}
			}
		} catch (Exception e) {
			log.error("Error validating email");
			log.warn(e.getMessage(), e);
		}
		return ERROR;
	}
	
	public String validateLogin() {	
		
		try {
			if(username != null && username.length() > 0){
				if(ServiceLocator.getService(UserServiceImpl.class).usernameExists(username)){
					return  SUCCESS;
				}
				else{
					return ERROR;
				}
			}
			else{
				return ERROR;
			}
		} catch (Exception e) {
			log.error("Error validating username: " + username);
			log.warn(e.getMessage(), e);
		}
		return ERROR;
	}
	
	public String validatePassword() throws ServiceException {		
		log.debug("username: " + username + " password: " + password);
		try {
			if(username != null && username.length() > 0 && password != null && password.length() > 0){
				log.debug("validate user");
				UserSessionDTO userSession = ServiceLocator.getService(UserServiceImpl.class).authenticate(username, password);
				log.debug("user : " + userSession.getLogin());
				setResutl("SUCCESS");
			}
			else{
				setResutl("ERROR");
			}
		} catch (InvalidUserException iue) {
			log.warn("bad login / password. Try again.", iue);
			setResutl("ERROR");
		} catch (Exception e) {
			log.warn("an error occured while user authentification",e);
			setResutl("ERROR");
			managerException(e);
		}
		return SUCCESS;
	}

	public Users getUser() {
		return user;
	}


	public void setUser(Users user) {
		this.user = user;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getResutl() {
		return resutl;
	}


	public void setResutl(String resutl) {
		this.resutl = resutl;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
