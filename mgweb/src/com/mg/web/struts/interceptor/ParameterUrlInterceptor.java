package com.mg.web.struts.interceptor;

import java.util.Map;

import org.apache.struts2.dispatcher.Parameter;

import com.mg.web.struts.action.shopping.payment.rbc.ShoppingCartPayment;
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
		/*Set<String> keySet = new HashSet<String>(parameters.keySet()) ; 
		for (String key : keySet) {
			if(key.contains("-")){
				parameters.put(key.replace("-", ""), parameters.get(key)) ;
			}
		}*/
		if(actionInvocation.getAction() instanceof ShoppingCartPayment){
			((ShoppingCartPayment)actionInvocation.getAction()).setCoderetour(parameters.get("code-retour").getValue());
			((ShoppingCartPayment)actionInvocation.getAction()).setTextelibre(parameters.get("texte-libre").getValue());
		}
		
		return actionInvocation.invoke();		
	}
	

}
