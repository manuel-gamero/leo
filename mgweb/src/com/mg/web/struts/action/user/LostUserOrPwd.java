package com.mg.web.struts.action.user;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.user.UserService;
import com.mg.service.user.UserServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

/**
 * This class is in charge of send an email to the user
 */
@Validations(	
		requiredStrings = {
				@RequiredStringValidator(fieldName = "email", type = ValidatorType.FIELD, message = "Email is required")
		},
		customValidators = {
				@CustomValidator(fieldName = "email",  type="existEmailField",  key = "gamejab.error.common.email.notFound")
		}					
)


public class LostUserOrPwd extends BasicAction {
	
	private static final long serialVersionUID = 7640068153357889448L;

	private UserService userService;
	private UserSessionDTO userSession;
	private String email;
	
	
	public LostUserOrPwd() throws ServiceLocatorException {
		userService = ServiceLocator.getService(UserServiceImpl.class);
	}	
	
	
	@Override
	public String execute() {
		
		try {
			if( userService.usernameExists(email) ){
				userService.sendUserPassword(email);
				System.out.println("email exists...");
			}
			else System.out.println("PAS email exists...");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return getSuccessResult();
		
	}


	public UserSessionDTO getUserSession() {
		return userSession;
	}


	public void setUserSession(UserSessionDTO userSession) {
		this.userSession = userSession;
	}	
}
