package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum ComponentType implements BasicEnum{
	
	COLOR("COLOR"),
	IMAGE("IMAGE"),
	LOGO("LOGO"),
	TEXT("TEXT")
	;
	
	
	private String localizationKey = "enum.user.type." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private ComponentType(String code){
		this.code = code;
	}
	
}
