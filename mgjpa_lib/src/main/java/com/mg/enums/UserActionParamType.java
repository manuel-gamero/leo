package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum UserActionParamType implements BasicEnum{
	
	COLOR("COLOR"),
	FONT("FONT"),
	TEXT("TEXT")
	;
	
	
	private String localizationKey = "enum.user.action.param.type." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private UserActionParamType(String code){
		this.code = code;
	}
	
}
