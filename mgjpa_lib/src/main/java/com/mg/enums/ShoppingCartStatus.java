package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum ShoppingCartStatus implements BasicEnum{
	
	NEW("NEW"),
	CANCEL("CANCEL"),
	PAIMENT("PAIMENT"),
	MAKING("MAKING"),
	SHIPPING("SHIPPING"),
	FINAL("FINAL")	
	;
	
	
	private String localizationKey = "enum.shopping.cart.status." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private ShoppingCartStatus(String code){
		this.code = code;
	}
	
}
