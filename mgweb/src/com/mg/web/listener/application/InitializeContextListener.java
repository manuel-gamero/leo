package com.mg.web.listener.application;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.mg.coupon.PromotionManager;
import com.mg.exception.InitializationException;
import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.product.ProductManager;
import com.mg.service.search.SearchServiceImpl;

/**
 * Initializing project's configuration.
 *    
 * @author MJGP
 *
 */
public class InitializeContextListener implements ServletContextListener{

	private static final String A_PROBLEM_LOOKING = "A problem has happened while looking up the config service.";
	private static final String A_PROBLEM_ACCESSING = "A problem has happened while accessing the config service.";
	private static Logger log = Logger.getLogger(InitializeContextListener.class);
	@Override
	public void contextInitialized(ServletContextEvent arg0) {		
		try {
			if(log.isInfoEnabled()){
				log.info("Calling config service...");
			}			
			ConfigService configService = ServiceLocator.getService(ConfigServiceImpl.class);
			configService.initialize();
			
			log.info("Initialize PromotionManager.");
			PromotionManager.initialize();
			log.info("Initialize ProductManager for product sale list.");
			ProductManager.initialize();
			log.info("Reseting lucene indexing.");
			ServiceLocator.getService(SearchServiceImpl.class).restart();
			
		} catch (InitializationException ie) {
			log.error(A_PROBLEM_ACCESSING, ie);
			// The application shouldn't continue if something goes wrong
			System.exit(1);			
		}catch (Exception sle) {
			log.error(A_PROBLEM_LOOKING, sle);
			// The application shouldn't continue if something goes wrong
			//System.exit(1);
		}		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try{
			if(log.isInfoEnabled()){
				log.info("Destroying context listener...");
			}
			
			ConfigService configService = ServiceLocator.getService(ConfigServiceImpl.class);
			configService.shutdown();
			
			//Unregistering JDBC driver.
			Enumeration<java .sql.Driver> drivers = java.sql.DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
			    java.sql.Driver driver = drivers.nextElement();
			    try {
			       java.sql.DriverManager.deregisterDriver(driver);
			    } catch (Exception e) {
			    	log.error(e);
			    }
			}
		}catch (ServiceLocatorException sle) {
			log.error(A_PROBLEM_LOOKING, sle);			
		}	
	}

}
