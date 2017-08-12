package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum CouponStatus implements BasicEnum{
	
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE"),
	USING("USING"),
	SEND("SEND")
	;
	
	
	private String localizationKey = "enum.user.status." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private CouponStatus(String code){
		this.code = code;
	}
	
}
