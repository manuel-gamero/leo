package com.mg.enums;

/**
 * All enumerations in the project should implement this interface.
 * It defines a method to retrieve the integer value of the enum. Thus,
 * we can deal with enums on the medel level and then persist their integer
 * value in the DB.
 *  
 * @author MJGP
 *
 */
public interface BasicEnum {	
	
	/**
	 * returns the id to be used for inserts
	 * @return
	 */
	//Integer getValue();
	
	/**
	 * Returns the key which clients use for localizing
	 * the enum text.
	 * @return
	 */
	String getLocalizationKey();
}
