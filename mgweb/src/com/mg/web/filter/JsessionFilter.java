package com.mg.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

/**
 * When no cookies allowed on client browser (disabled or unsupported), servers
 * use url rewriting to keep the session(JSESSIONID).
 * This filter can truncate JSESSIONID from the URL when the
 * response is rendered by overriding the HttpServletResponseWrapper
 * redirecting methods.
 * 
 * <H2>Warning:</H2> when this filter is configured to be enabled, clients with
 * no cookies allowed will be not able to use any session functionality to
 * communicate with server.
 *  
 * @author MJGP
 *
 */
public class JsessionFilter implements Filter{

	private static Logger log = LogManager.getLogger(JsessionFilter.class);
	
	protected boolean truncateJsession;
	
	protected FilterConfig filterConfig = null;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if(log.isInfoEnabled()){
			log.info("Initializing Jsession filter ...");
		}
		
		this.filterConfig = filterConfig;
		String truncateJsession = this.filterConfig.getInitParameter("truncateJsession");		
		
		if(truncateJsession != null &&	truncateJsession.trim().equalsIgnoreCase("true")){
			this.truncateJsession = true;
		}
		
		if(log.isInfoEnabled()){
			log.info("truncateJsession has been set to: " + this.truncateJsession);
		}
	}

	@Override
	public void destroy() {
		this.filterConfig = null;
		
		if(log.isInfoEnabled()){
			log.info("Destroying Jsession filter ...");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain filterChain ) throws IOException, ServletException{
			
		if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
			HttpServletResponse wrappedResponse = wrapResponse((HttpServletResponse) response);
			filterChain.doFilter((HttpServletRequest) request, wrappedResponse);			
		}else{
			filterChain.doFilter(request, response);
		}
			
		
	}	
	
	/**
	 * Wraps the response and override the methods that encode
	 * the JSESSIONID.
	 * @param response
	 * @return
	 */
	private HttpServletResponse wrapResponse(HttpServletResponse response){
		if(truncateJsession){
			return new HttpServletResponseWrapper(response){
				public String encodeRedirectUrl(String url){return url;}
				public String encodeRedirectURL(String url){return url;}
				public String encodeUrl(String url){return url;}
				public String encodeURL(String url){return url;}
			};
		}else{
			return response;
		}
		
	}

}
