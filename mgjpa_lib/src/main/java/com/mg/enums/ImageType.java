package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum ImageType implements BasicEnum{
	
	PRODUCT("PRODUCT"),
	LOGO("LOGO"),
	NEGATIVE("NEGATIVE"),
	COLLECTION("COLLECTION"),
	MASK("MASK"),
	PRODGROUP("PROD_GROUP"),
	PROMOTION("PROMOTION")
	;
	
	
	private String localizationKey = "enum.image.type." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private ImageType(String code){
		this.code = code;
	}
	
}
