package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum UserStatus implements BasicEnum{
	
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE")	
	;
	
	
	private String localizationKey = "enum.user.status." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private UserStatus(String code){
		this.code = code;
	}
	
}
