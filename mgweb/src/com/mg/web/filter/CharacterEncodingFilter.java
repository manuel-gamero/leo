package com.mg.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

/**
 * This filter sets character encoding for all incoming requests.
 * If you define the encoding in web.xml then the filter's default
 * encoding will be overridden.
 * 
 * @author MJGP
 *
 */
public class CharacterEncodingFilter implements Filter {
	
	private static Logger log = LogManager.getLogger(CharacterEncodingFilter.class); 
	
	protected String encoding = "UTF-8";
	protected FilterConfig filterConfig = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		if(log.isInfoEnabled()){
			log.info("Initializing character encoding filter ...");
		}
		
		this.filterConfig = filterConfig;
		String definedEncoding = this.filterConfig.getInitParameter("encoding");		
		if(definedEncoding != null &&	!definedEncoding.trim().equals("")){
			this.encoding = definedEncoding;
		}
		
		if(log.isInfoEnabled()){
			log.info("Character encoding has been set to: " + this.encoding);
		}		
	}
	
	public void destroy() {		
		this.filterConfig = null;
		
		if(log.isInfoEnabled()){
			log.info("Destroying character encoding filter ...");
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// Set the character encoding to be used
		request.setCharacterEncoding(this.encoding);

		// Continue to the next filter
		chain.doFilter(request, response);
	}
}
