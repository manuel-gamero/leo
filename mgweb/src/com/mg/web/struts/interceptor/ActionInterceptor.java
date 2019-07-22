package com.mg.web.struts.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.annotation.Action;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.exception.ExceptionHandler;
import com.mg.web.struts.action.shopping.payment.paypal.ShoppingCartPaylPalCheckout;
import com.mg.web.struts.action.shopping.payment.rbc.ShoppingCartPaymentSend;
import com.mg.web.util.AuditUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import ognl.OgnlException;

/**
 * Intercepts the outermost component invocations during the struts2
 * INVOKE_APPLICATION phase.
 * 
 * @author mgamero
 * 
 */



public class ActionInterceptor implements Interceptor{

	private static final long serialVersionUID = 1791550069973083781L;
	private static final Logger log = LogManager.getLogger(ActionInterceptor.class);
	
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
	
	private Audit prepareAudit( ActionInvocation actionInvocation, String pELMessage ) throws OgnlException {
		return AuditUtil.createAudit(actionInvocation, pELMessage);
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
