package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum CollectionStatus implements BasicEnum{
	
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE")	
	;
	
	
	private String localizationKey = "enum.collection.status." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private CollectionStatus(String code){
		this.code = code;
	}
	
}
