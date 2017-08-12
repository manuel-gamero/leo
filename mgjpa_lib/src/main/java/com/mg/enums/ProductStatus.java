package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum ProductStatus implements BasicEnum{
	
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
	
	
	private ProductStatus(String code){
		this.code = code;
	}
	
}
