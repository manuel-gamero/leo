package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum UserType implements BasicEnum{
	
	ADMIN("ADMIN"),
	USER("USER")	
	;
	
	
	private String localizationKey = "enum.user.type." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private UserType(String code){
		this.code = code;
	}
	
}
