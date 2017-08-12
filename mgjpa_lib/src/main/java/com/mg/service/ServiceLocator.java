package com.mg.service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mg.exception.ServiceLocatorException;


/**
 * J2EE clients interact with service components which provide business 
 * services and persistence capabilities.
 * This class offers a lookup interface for all services available in the system.
 *  
 * TODO:
 * JNDI lookup for remote services!  
 * @author MJGP
 *
 */
public class ServiceLocator {
	
	private static final Logger log = Logger.getLogger(ServiceLocator.class);
	private static Map<Class<?>, Object> services = new HashMap<Class<?>, Object>();
	
	private static final ServiceLocator INSTANCE = new ServiceLocator();
	
	/**
	 * A singleton service locator
	 * 
	 * @return
	 */
	public static ServiceLocator getInsance(){
		return INSTANCE;
	}
	
	private ServiceLocator(){
		if (INSTANCE != null) {
			throw new IllegalStateException(this.getClass()	+ " instance is already created");
		}
	}
	
	public static <T extends Service> T getService(Class<T> clazz) throws ServiceLocatorException {
		T t;
		try {
			log.debug("Get " + clazz);
			if(!services.containsKey(clazz)){
				Constructor[] constructors = clazz.getDeclaredConstructors();
				for (Constructor c : constructors) {
				     System.out.println(c.getParameterTypes().length);
				}
				t = clazz.newInstance();
				log.info("New instantace for " + clazz);
				services.put(clazz, clazz.cast(t));
			}

		} catch (Exception e) {
			throw new ServiceLocatorException(e);
		}
		log.debug("Return " + clazz);
		return clazz.cast(services.get(clazz));
	}
	
	

}
