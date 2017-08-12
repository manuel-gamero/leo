package com.mg.util.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * A simple wrapper on Properties that encapsulate the file loading and the
 * access to properties.
 * 
 * @author MJGP
 * 
 */
public class PropertiesFileWrapper {
	
	private Properties properties = new Properties();

	public PropertiesFileWrapper(String resourceFile) throws Exception{
		load(resourceFile);
	}

	public PropertiesFileWrapper(Properties properties) {
		this.properties = properties;
	}

	private boolean isValidSource(String resourceFile){
		return (resourceFile != null && !resourceFile.equals("")
						&& resourceFile.endsWith(".properties"));
	}
	
	private void load(String resourceFile) throws Exception{
		if (!isValidSource(resourceFile)) {
			throw new Exception("Can't load properties file: " + resourceFile);			
		}
		try {
			InputStream is = getClass().getResourceAsStream(resourceFile);
			if (is != null) {
				properties.load(is);
			} else {
				throw new Exception(resourceFile + " was not found.");
			}
			is.close();
		} catch (FileNotFoundException fnfe) {
			throw new Exception("Could not load the file: " + resourceFile, fnfe);			
		} catch (IOException ioe) {
			throw new Exception("Could not load properties resource: "+ resourceFile, ioe);
		}
	}

	/**
	 * Returns the wrapped properties
	 * 
	 * @return
	 */
	public Properties getProperties() {
		return (Properties) properties.clone();
	}

	/**
	 * Checks if a property is present
	 * 
	 * @param name
	 * @return
	 */
	public boolean PropertyExists(String name) {
		return properties.containsKey(name);
	}

	/**
	 * Check whether a valid properties or no
	 * 
	 * @return
	 */
	public boolean isValidProperties() {
		return (properties.size() > 0 ? true : false);
	}

	/**
	 * Returns property as a trimmed String
	 * 
	 * @param name
	 * @return
	 */
	private String getProperty(String name) {
		String s = properties.getProperty(name);
		if (s != null) {
			s = s.trim();
		}

		return s;
	}

	/**
	 * Returns property as String
	 * 
	 * @param name
	 * @return
	 */
	public String getPropertyAsString(String name) {
		return getProperty(name);
	}

	/**
	 * Returns property as String Array
	 * 
	 * @param name
	 * @return a String Array of properties
	 */
	public String[] getPropertyAsStringArray(String name) {
		List<String> list = null;
		String stringToSplit = getProperty(name);
		if (stringToSplit != null) {
			list = Arrays.asList(stringToSplit.split(","));
			for (int i = 0; i < list.size(); i++) {
				list.set(i, list.get(i).trim());
			}
		}
		if (list != null) {
			return list.toArray(new String[list.size()]);
		} else {
			return new String[0];
		}

	}

	/**
	 * Parses a property as Integer
	 * 
	 * @param name
	 * @return
	 */
	public Integer getPropertyAsInteger(String name) {
		Integer result = null;
		try {
			if (getProperty(name) != null) {
				result = Integer.parseInt(getProperty(name));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Parses a property as double
	 * 
	 * @param name
	 * @return
	 */
	public Double getPropertyAsDouble(String name) {
		Double result = null;
		try {
			if (getProperty(name) != null) {
				result = Double.parseDouble(getProperty(name));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Parses a property as Boolean
	 * 
	 * @param name
	 * @return
	 */
	public Boolean getPropertyAsBoolean(String name) {
		Boolean result = null;
		if (getProperty(name) != null) {
			result = Boolean.parseBoolean(getProperty(name));
		}

		return result;
	}
}
