package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum ComponentAttributeType implements BasicEnum{
	
	COLOR("COLOR"),
	PATH("PATH"),
	FONT("FONT"),
	SIZE("SIZE")
	;
	
	
	private String localizationKey = "enum.user.type." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private ComponentAttributeType(String code){
		this.code = code;
	}
	
}
