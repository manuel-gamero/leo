package com.mg.web.struts.interceptor;

import com.mg.web.WebConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
//import com.loyauty.service.core.dto.UserSessionDTO;

@SuppressWarnings("serial")
public class ErrorInjectionInterceptor implements Interceptor{

	@Override
	public void destroy() {}

	@Override
	public void init() {}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		if(ActionContext.getContext().getSession().get(WebConstants.ERRORACTION) != null){
			ActionSupport action = (ActionSupport)actionInvocation.getAction();
			action.addActionError((String)ActionContext.getContext().getSession().get(WebConstants.ERRORACTION));
			ActionContext.getContext().getSession().remove(WebConstants.ERRORACTION);
		}
		return actionInvocation.invoke();
			
	}
	

}
