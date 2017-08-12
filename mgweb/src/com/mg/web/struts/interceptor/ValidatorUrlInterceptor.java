package com.mg.web.struts.interceptor;

import org.apache.struts2.ServletActionContext;

import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigServiceImpl;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
//import com.loyauty.service.core.dto.UserSessionDTO;

@SuppressWarnings("serial")
public class ValidatorUrlInterceptor implements Interceptor{

	@Override
	public void destroy() {}

	@Override
	public void init() {}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		StringBuffer urlReceive = ServletActionContext.getRequest().getRequestURL();
		String[] urlAllow = ServiceLocator.getService(ConfigServiceImpl.class).getUrlAllow();
		for (String string : urlAllow) {
			if( urlReceive.indexOf( string ) > 0 ){
				return actionInvocation.invoke();
			}
		}
		return "errorUrl";	
	}
	

}
