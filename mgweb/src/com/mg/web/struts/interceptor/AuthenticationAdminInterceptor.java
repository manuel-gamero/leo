package com.mg.web.struts.interceptor;

import java.util.Map;

import com.mg.enums.UserType;
import com.mg.service.dto.UserSessionDTO;
import com.mg.web.WebConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
//import com.loyauty.service.core.dto.UserSessionDTO;

@SuppressWarnings("serial")
public class AuthenticationAdminInterceptor implements Interceptor{

	@Override
	public void destroy() {}

	@Override
	public void init() {}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
		
		UserSessionDTO user =(UserSessionDTO) session.get(WebConstants.USER);

        if ( user == null ) {           
        	return "authentification";            
        }
        else {
        	if( !user.getUserType().equals( UserType.ADMIN ) ){
        		session.remove(WebConstants.USER);
        		return "authentification";            		
            }
            else {
            	 return actionInvocation.invoke();
        	} 
        }         
	}
	

}
