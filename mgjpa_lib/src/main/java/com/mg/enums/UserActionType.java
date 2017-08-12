package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum UserActionType implements BasicEnum{
	
	LOOKED("LOOKED"),
	ADDED("ADDED"),
	SHARED("SHARED"),
	SOLD("SOLD"),
	REMOVED("REMOVED")
	;
	
	
	private String localizationKey = "enum.user.action.type." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private UserActionType(String code){
		this.code = code;
	}
	
}
