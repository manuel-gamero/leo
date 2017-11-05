package com.mg.util.reflexion;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.persistence.Id;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

public class ReflectionUtils {

	private final static Logger log = Logger.getLogger(ReflectionUtils.class);
	private final static String COUPON_TYPE_NAMESPACE = "com.mg.coupon.type";

	private static Hashtable<String, PropertyDescriptor> pkPropertyPerClass;
	private static Hashtable<String, List<PropertyDescriptor>> propertiesPerClass;

	static {
		pkPropertyPerClass = new Hashtable<String, PropertyDescriptor>();
		propertiesPerClass = new Hashtable<String, List<PropertyDescriptor>>();
	}

	public static PropertyDescriptor getPkProperty(Class<?> c) {
		PropertyDescriptor property = null;
		if (!pkPropertyPerClass.containsKey(c.getName())) {
			property = getAnnotatedProperty(c, Id.class);
			pkPropertyPerClass.put(c.getName(), property);
		} else {

			property = pkPropertyPerClass.get(c.getName());
			log.trace(String.format(
					"pkProperty for class %s found in cache : %s",
					c.getSimpleName(), property.getName()));
		}
		return property;
	}

	public static PropertyDescriptor getAnnotatedProperty(Class<?> c,
			Class<? extends Annotation> annotationClass) {
		for (PropertyDescriptor pd : getProperties(c)) {

			if (pd.getReadMethod() == null) {
				continue;
			} else if (pd.getReadMethod().isAnnotationPresent(annotationClass)) {
				return pd;
			}
		}
		throw new RuntimeException("No getter annotated with @"
				+ annotationClass.getName() + " on class " + c.getName());

	}

	public static List<PropertyDescriptor> getProperties(Class<?> c) {
		List<PropertyDescriptor> l;

		if (!propertiesPerClass.containsKey(c.getName())) {

			log.debug("building property list for " + c.getName());

			l = new ArrayList<PropertyDescriptor>();

			for (PropertyDescriptor pd : PropertyUtils
					.getPropertyDescriptors(c)) {

				if (pd.getReadMethod() == null) {
					log.trace("unable to find read method for property "
							+ pd.getName());
				} else if (!excludedType(pd.getReadMethod().getReturnType())) {
					l.add(pd);
				}
			}
			propertiesPerClass.put(c.getName(), l);
		} else {
			l = propertiesPerClass.get(c.getName());
			log.trace(String.format("property list for %s found in cache",
					c.getName()));
		}

		return l;
	}

	private static boolean excludedType(Class<?> returnType) {
		return Class.class.isAssignableFrom(returnType);
	}

	public static PropertyDescriptor getProperty(String propertyName,
			Class<?> clazz) {
		for (PropertyDescriptor pd : getProperties(clazz)) {

			if (pd.getReadMethod() == null) {
				continue;
			} else if (pd.getName().equals(propertyName)) {
				return pd;
			}
		}
		throw new RuntimeException("No property named '" + propertyName
				+ "' on class '" + clazz.getName() + "'");
	}

	public static <T> T newInstance(Class<T> clazz) {
		try {
			return (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object invoke(Method method, Object object, Object... args) {
		try {
			return method.invoke(object, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object invokeRead(PropertyDescriptor pd, Object object) {
		try {
			return pd.getReadMethod().invoke(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object invokeWrite(PropertyDescriptor pd, Object object,
			Object value) {

		try {
			return pd.getWriteMethod().invoke(object, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static Class<?>[] getClasses(String packageName)
	        throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
	    List<Class<?>> classes = new ArrayList<Class<?>>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	    }
	    return classes;
	}
	
	
	public static  List<String> getCouponClassType(){
		try {
			return getClassNamesFromPackage(COUPON_TYPE_NAMESPACE);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static ArrayList<String>getClassNamesFromPackage(String packageName) throws IOException, URISyntaxException{
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    URL packageURL;
	    ArrayList<String> names = new ArrayList<String>();;

	    packageName = packageName.replace(".", "/");
	    packageURL = classLoader.getResource(packageName);

	    if(packageURL.getProtocol().equals("jar")){
	        String jarFileName;
	        JarFile jf ;
	        Enumeration<JarEntry> jarEntries;
	        String entryName;

	        // build jar file name, then loop through zipped entries
	        jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
	        jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));
	        System.out.println(">"+jarFileName);
	        jf = new JarFile(jarFileName);
	        jarEntries = jf.entries();
	        while(jarEntries.hasMoreElements()){
	            entryName = jarEntries.nextElement().getName();
	            if(entryName.startsWith(packageName) && entryName.length()>packageName.length()+5){
	                entryName = entryName.substring(packageName.length(),entryName.lastIndexOf('.'));
	                names.add(entryName.replace("/", ""));
	            }
	        }
	        jf.close();

	    // loop through files in classpath
	    }else{
	    URI uri = new URI(packageURL.toString());
	    File folder = new File(uri.getPath());
	        // won't work with path which contains blank (%20)
	        // File folder = new File(packageURL.getFile()); 
	        File[] contenuti = folder.listFiles();
	        String entryName;
	        for(File actual: contenuti){
	            entryName = actual.getName();
	            entryName = entryName.substring(0, entryName.lastIndexOf('.'));
	            names.add(entryName.replace("/", ""));
	        }
	    }
	    return names;
	}
}