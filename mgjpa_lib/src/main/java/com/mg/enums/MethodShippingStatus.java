package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum MethodShippingStatus implements BasicEnum{
	
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE")	
	;
	
	
	private String localizationKey = "enum.methodshipping.status." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private MethodShippingStatus(String code){
		this.code = code;
	}
	
}
