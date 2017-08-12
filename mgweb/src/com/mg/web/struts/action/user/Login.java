/**
 * 
 */
package com.mg.web.struts.action.user;


import com.mg.annotation.Action;
import com.mg.enums.UserType;
import com.mg.exception.InvalidUserException;
import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.user.UserService;
import com.mg.service.user.UserServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

/**
 * This class is in charge of logging a User
 * @author MJGP
 */
@Validations(requiredStrings = {
					@RequiredStringValidator(fieldName = "username", type = ValidatorType.FIELD, message = "Username is required"),
					@RequiredStringValidator(fieldName = "password", type = ValidatorType.FIELD, message = "Password is required")
		  		 }
)

public class Login extends BasicAction {
	
	private static final long serialVersionUID = 7640068153357889448L;

	private UserService userService;
	private UserSessionDTO userSession;
	private String username;
	private String password;
	private String site;
	
	
	public Login() throws ServiceLocatorException {
		userService = ServiceLocator.getService(UserServiceImpl.class);
	}	
	
	@Override
	public void validate() {
		// the authentication is done in a validate method cause if a error is raised from an execute method it won't be handle by Struts JSON validation. 
		try {
			
			log.debug("validate user");
			userSession = userService.authenticate(username, password);
			log.debug("user : " + userSession.getLogin());
			
		} catch (InvalidUserException iue) {
			addActionError("bad login / password. Try again.");
		} catch (Exception e) {
			log.error("an error occured while user authentification",e);
			addActionError("bad login / password. Try again.");
		}
	}
	
	@Action(value="username = #username ")
	@Override
	public String execute() {
		log.debug("dans login");
		setRefereUrl();
		
		setUserSession(userSession);
		if(site != null){
			if(site.equals("ADMIN") && userSession.getUserType().equals(UserType.ADMIN)){
				return SUCESSADMIN;
			}
			else if(site.equals("WEB")){
				return HOME;
			}
			else if(site.equals("SHOPPING")){
				if(getShoppingCart() != null){
					return SHOPPING;
				}
				else{
					return SUCCESS;
				}
			}
			else{
				return ERROR;
			}
		}
		else {
			return ERROR;
		}
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
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	
}
