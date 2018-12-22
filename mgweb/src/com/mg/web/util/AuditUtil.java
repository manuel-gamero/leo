package com.mg.web.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.mg.model.Audit;
import com.mg.service.dto.UserSessionDTO;
import com.mg.web.WebConstants;
import com.opensymphony.xwork2.ActionInvocation;

import ognl.OgnlException;

public class AuditUtil {
	
	private static final Logger log = Logger.getLogger(AuditUtil.class);

	public static Audit createAudit(ActionInvocation actionInvocation, String pELMessage) throws OgnlException{
		Audit audit = new Audit();

		if(actionInvocation != null){
			audit.setUsername( getUsername(actionInvocation) );
			audit.setActionUser( String.format( "%s.%s", actionInvocation.getProxy().getActionName(), actionInvocation.getProxy().getMethod() ) );
			if( pELMessage != null && pELMessage.length() != 0 ) {
				audit.setMessage( evaluateExpresionOGNL(actionInvocation, pELMessage) );
			}
		}
		else{
			audit.setUsername("system");
			audit.setActionUser("suggestion");
			audit.setMessage(pELMessage);
		}

		String remoteAddr = ServletActionContext.getRequest().getRemoteAddr();
		audit.setLocation( remoteAddr );

		HttpSession session = ServletActionContext.getRequest().getSession();
		if( session != null ) {
			//retrieve JSESSIONID
			audit.setSessionGuid( session.getId() );
		}

		//String requestUUID = (String)Component.getInstance( "requestUUID" );
		//audit.setRequestGuid( requestUUID );

		audit.setCreationDate( new Date() );

		try {
			audit.setServerName( InetAddress.getLocalHost().getHostName() );
		} catch( UnknownHostException e ) {
			audit.setServerName( null );
		}
		
		String requestUserAgent = ServletActionContext.getRequest().getHeader("User-Agent");
		audit.setBrowser( requestUserAgent );
		
		String url = ServletActionContext.getRequest().getHeader("referer");
		audit.setUrl( url );
		
		return audit;
	}
	
	private static String getUsername(ActionInvocation actionInvocation ) {
		UserSessionDTO user =(UserSessionDTO) actionInvocation.getInvocationContext().getSession().get(WebConstants.USER);
		return user == null ? null : user.getLogin();
	}
	
	private static String evaluateExpresionOGNL(ActionInvocation actionInvocation,
			String pELMessage) throws OgnlException {		
		String result = pELMessage;
		Set<String> ognlExpresions = getOgnlExpresions( result );
		for (String string : ognlExpresions) {
			String ognlValue = actionInvocation.getStack().findString(string.replace("#", ""));
			/*if(string.contains(".")){
				OgnlContext ctx = new OgnlContext();
				String expression = Ognl.parseExpression( expressionString );

				Object expr = Ognl.parseExpression(expression);
				Object value = Ognl.getValue(expr, ctx, actionInvocation.getStack().findString(string.replace("#", "")).getClass());
				System.out.println("Value: " + value);
				String valueExpresion =(String) Ognl.getValue(string, actionInvocation.getStack().findString(string.split(".")[0].replace("#", "")).getClass());
				result = result.replace(string, valueExpresion);
			}
			else{
				if(ognlValue != null){
					result = result.replace(string, ognlValue);
				}
				else{
					log.warn("String to replace OGNL expresion is null: " + string );
					result = result.replace(string, "");
				}
			}	*/
			if(ognlValue != null){
				result = result.replace(string, ognlValue);
			}
			else{
				log.warn("String to replace OGNL expresion is null: " + string );
				result = result.replace(string, "");
			}
		}
		return result;
	}
	
	private static Set<String> getOgnlExpresions(String pELMessage) {
		Set<String> result = new HashSet<String>();
		//String patternString1 = "(#)(.+?)\\b";
		String patternString1 = "(#)([\\p{Graph}]+)\\b";
	    Pattern pattern = Pattern.compile(patternString1);
	    Matcher matcher = pattern.matcher(pELMessage);

	    while(matcher.find()) {
	    	String match = matcher.group(1) + matcher.group(2);
	    	result.add(match);
	    }
		return result;
	}
}
