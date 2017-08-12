package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum UserAddressType implements BasicEnum{
	
	BILLING("BILLING"),
	SHIPPING("SHIPPING")	
	;
	
	
	private String localizationKey = "enum.user.address.type." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private UserAddressType(String code){
		this.code = code;
	}
	
}
