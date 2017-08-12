package com.mg.web.filter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

//import org.apache.coyote.RequestInfo;
import org.apache.log4j.Logger;


public class TimingFilter implements Filter {

	private final static Logger log = Logger.getLogger(TimingFilter.class);

	public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
	
		long start = System.currentTimeMillis();

		chain.doFilter(req, resp);

		long elapsed = System.currentTimeMillis() - start;

		//RequestInfo info = null;
		String url = ((HttpServletRequest)req).getRequestURI().toString();// getRequestURL().toString();
		 String queryString = ((HttpServletRequest)req).getQueryString();
		String requestInfo = String.format("%s|%s|%s|%s", TimeUnit.MILLISECONDS.toSeconds(elapsed), elapsed, url,queryString );

		StringBuffer sb = new StringBuffer(requestInfo);


		if (elapsed < 2000) {
			log.debug(sb.toString());
		} 
		else if (elapsed > 2000 && elapsed < 5000) {
			log.info(sb.toString());
		} 
		else {
			log.warn(sb.toString());
		}
	
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}


}
