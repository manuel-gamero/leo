package com.mg.web.struts.interceptor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.dispatcher.Parameter;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
//import com.loyauty.service.core.dto.UserSessionDTO;

@SuppressWarnings("serial")
public class ParameterUrlInterceptor implements Interceptor{

	@Override
	public void destroy() {}

	@Override
	public void init() {}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Parameter> parameters =  actionInvocation.getInvocationContext().getParameters();
		Set<String> keySet = new HashSet<String>(parameters.keySet()) ; 
		for (String key : keySet) {
			if(key.contains("-")){
				parameters.put(key.replace("-", ""), parameters.get(key)) ;
			}
		}
		
		return actionInvocation.invoke();		
	}
	

}
