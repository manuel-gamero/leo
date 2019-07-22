package com.mg.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.exception.ExceptionHandler;

//import org.apache.coyote.RequestInfo;

public class RequestFilter implements Filter {

	private final static Logger log = LogManager.getLogger(RequestFilter.class);

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		// chain.doFilter(req, resp);
		// resp.sendError(HttpServletResponse.SC_FORBIDDEN);

		// RequestInfo info = null;
		
		String path = ((HttpServletRequest) req).getRequestURI();
		ConfigService service;
		log.debug ("RequestFilter path: " + path);
		try {
			service = ServiceLocator.getService(ConfigServiceImpl.class);
			if( !path.contains(".") ){
				log.debug ("RequestFilter no ext");
				chain.doFilter(req, resp);
			}
			else{
				String ext = path.substring(path.lastIndexOf(".") + 1, path.length());
				if( service.getFilterRequestExt().contains(ext) ){
					log.debug ("RequestFilter ext: " + ext);
						chain.doFilter(req, resp);
				}
				else{
					//((HttpServletResponse)resp).setStatus(HttpServletResponse.SC_NOT_FOUND);
					log.debug ("RequestFilter ERROR!!! ");
					req.getRequestDispatcher("/errorUrl.action").forward(req, resp);
				}
			}
		} catch (ServiceLocatorException e) {
			ExceptionHandler.handleException(e, e.getMessage());
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
