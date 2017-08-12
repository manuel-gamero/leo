package com.mg.web.struts.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import ognl.OgnlException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.mg.annotation.Action;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.exception.ExceptionHandler;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.shopping.payment.paypal.ShoppingCartPaylPalCheckout;
import com.mg.web.struts.action.shopping.payment.rbc.ShoppingCartPaymentSend;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Intercepts the outermost component invocations during the struts2
 * INVOKE_APPLICATION phase.
 * 
 * @author mgamero
 * 
 */



public class ActionInterceptor implements Interceptor{

	private static final long serialVersionUID = 1791550069973083781L;
	private static final Logger log = Logger.getLogger(ActionInterceptor.class);
	
	@Override
	public void destroy() {}

	@Override
	public void init() {}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		String actionmethod = actionInvocation.getProxy().getMethod();
		Method method = actionInvocation.getProxy().getAction().getClass().getDeclaredMethod(actionmethod);
		Action curAction = method.getAnnotation(Action.class);
		if( curAction != null ) {
			return interceptCall( actionInvocation );
		}
		return actionInvocation.invoke();
	}

	private String interceptCall( ActionInvocation actionInvocation ) throws Exception {

		CallContext interceptionContext = preCall( actionInvocation );

		long start = System.currentTimeMillis();

		try {
			String result = actionInvocation.invoke();
			interceptionContext.result = result;
			interceptionContext.isCallSuccessful = true;
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		} finally {
			if (interceptionContext.hasAudit()) {
				interceptionContext.audit.setCallDuration( (int) (System.currentTimeMillis() - start) );
			}
			postCall( interceptionContext );
			
			//In the case that shoppingCartPaymentSend I audit before and after to keep trace
			//the information that I receive and the back url that I call with all the parameters
			if(actionInvocation.getProxy().getAction().getClass().isAssignableFrom(ShoppingCartPaymentSend.class) || 
			   actionInvocation.getProxy().getAction().getClass().isAssignableFrom(ShoppingCartPaylPalCheckout.class)	){
				CallContext interceptionContextPost = preCall( actionInvocation );
				interceptionContextPost.result = interceptionContext.result + "-post";
				interceptionContextPost.isCallSuccessful = true;
				postCall(interceptionContextPost);
			}
			
		}
		return interceptionContext.result;

	}

	private CallContext preCall( ActionInvocation actionInvocation ) {
		
		CallContext context = new CallContext( actionInvocation.getInvocationContext() );
		
		try{
			String actionmethod = actionInvocation.getProxy().getMethod();
			Method method = actionInvocation.getProxy().getAction().getClass().getDeclaredMethod(actionmethod);
			Action curAction = method.getAnnotation(Action.class);
			if( curAction == null ) {
				warnForMethod( actionInvocation, "Action method should be annotated with @Action." );
			}
	
			Annotation[] annotations = method.getAnnotations();
			if( ( curAction == null || curAction.restricted() ) && !hasSecurityAttributes( annotations ) ) {
				warnForMethod( actionInvocation, "Action method should explicitly define its permissions (@Restrict, or set restricted = false)" );
			}
	
			if( curAction == null || curAction.audited() ) {
				context.audit = prepareAudit( actionInvocation, ( curAction == null ) ? null : curAction.value() );
			}
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return context;
	}

	private void postCall( CallContext pContext ) {

		if( pContext.hasAudit() ) {
			persistAudit( pContext );
		}

	}

	private String getUsername(ActionInvocation actionInvocation ) {
		UserSessionDTO user =(UserSessionDTO) actionInvocation.getInvocationContext().getSession().get(WebConstants.USER);
		return user == null ? null : user.getLogin();
	}

	private Audit prepareAudit( ActionInvocation actionInvocation, String pELMessage ) throws OgnlException {

		Audit audit = new Audit();

		audit.setUsername( getUsername(actionInvocation) );

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

		audit.setActionUser( String.format( "%s.%s", actionInvocation.getProxy().getActionName(), actionInvocation.getProxy().getMethod() ) );
		try {
			audit.setServerName( InetAddress.getLocalHost().getHostName() );
		} catch( UnknownHostException e ) {
			audit.setServerName( null );
		}

		if( pELMessage != null && pELMessage.length() != 0 ) {
			audit.setMessage( evaluateExpresionOGNL(actionInvocation, pELMessage) );
		}
		
		String requestUserAgent = ServletActionContext.getRequest().getHeader("User-Agent");
		audit.setBrowser( requestUserAgent );
		
		String url = ServletActionContext.getRequest().getHeader("referer");
		audit.setUrl( url );
		
		return audit;
	}

	private String evaluateExpresionOGNL(ActionInvocation actionInvocation,
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

	private Set<String> getOgnlExpresions(String pELMessage) {
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

	private void persistAudit( CallContext pContext ) {

		try{
			if( pContext.isCallSuccessful ) {
				pContext.audit.setOutcome( ( pContext.result == null ) ? ""
						: StringUtils.left( pContext.result.toString(), 100 ) );
			} else {
				pContext.audit.setOutcome( "[Error]" );
			}
			
			if( log.isTraceEnabled() ) {
				traceAudit( pContext.audit );
			}
		
			ServiceLocator.getService(ConfigServiceImpl.class).saveAudit(pContext.audit);
		}
		catch(Exception e){
			try {
				ExceptionHandler.handleException(e, null, "audit: " + pContext.audit);
			} catch (Exception ex) {
				log.error(e.getMessage());
			}
		}
	}

	private void traceAudit( Audit pAudit ) {

		StringBuffer buf = new StringBuffer();
		buf.append( "AUDIT ENTRY:\n" );

		buf.append( "   username: " + pAudit.getUsername() + "\n" );
		buf.append( "   location: " + pAudit.getLocation() + "\n" );
		buf.append( "sessionguid: " + pAudit.getSessionGuid() + "\n" );
		buf.append( "requestguid: " + pAudit.getRequestGuid() + "\n" );
		buf.append( "       time: " + pAudit.getCreationDate() + "\n" );
		buf.append( "     action: " + pAudit.getActionUser() + "\n" );
		buf.append( "    outcome: " + pAudit.getOutcome() + "\n" );
		buf.append( "    message: " + pAudit.getMessage() + "\n" );
		buf.append( "     server: " + pAudit.getServerName() + "\n" );
		buf.append( "    browser: " + pAudit.getBrowser() + "\n" );
		buf.append( "        url: " + pAudit.getUrl() + "\n" );

		log.trace( buf.toString() );

	}

	private boolean isProperty( Method pMethod ) {

		String methodName = pMethod.getName();
		int pos;
		if( methodName.startsWith( "get" ) || methodName.startsWith( "set" ) ) {
			pos = 3;
		} else if( methodName.startsWith( "is" ) ) {
			pos = 2;
		} else {
			return false;
		}

		return methodName.length() > pos && Character.isUpperCase( methodName.charAt( pos ) );

	}

	private boolean hasSecurityAttributes( Annotation[] pAnnotations ) {

		/*for( Annotation annotation : pAnnotations ) {
			if( annotation.annotationType().equals( Restrict.class )
					|| annotation.annotationType().isAnnotationPresent( PermissionCheck.class )
					|| annotation.annotationType().isAnnotationPresent( RoleCheck.class ) ) {
				return true;
			}
		}*/

		return false;
	}

	private void warnForMethod( ActionInvocation actionInvocation, String pMessage ) {

		log.warn( String.format( "%s : [%s]", pMessage, actionInvocation.getProxy().getMethod() ) );

	}

	private class CallContext {

		@SuppressWarnings( "unused" )
		public ActionContext context;

		public boolean isCallSuccessful = false;

		public String result;

		public Audit audit = null;
		
		public CallContext( ActionContext pContext ) {
			context = pContext;
		}
		public boolean hasAudit() {
			return (audit != null && auditAction()) ;
		}
		private boolean auditAction() {
			try {
				ConfigService configService = ServiceLocator.getService(ConfigServiceImpl.class);
				String[] listFilter = configService.getAuditFilter();
				for (String item : listFilter) {
					if(audit.getBrowser() != null && audit.getBrowser().contains(item)){
						return false;
					}
				}
			} catch (ServiceLocatorException e) {
				log.error(e.getCause());
			}
			return true;
		}
	}

	public static void main(String[] args) {

		System.out.println("Start");
		String text    =
                "username = #username, password = {#password} product = #product.id )"
              ;

      String patternString1 = "(#)([\\p{Graph}]+)\\b";

      Pattern pattern = Pattern.compile(patternString1);
      Matcher matcher = pattern.matcher(text);

      while(matcher.find()) {
    	  System.out.println("found: " + matcher.group(1) +
                  " "       + matcher.group(2));
      }
      System.out.println("End");
    }
}
