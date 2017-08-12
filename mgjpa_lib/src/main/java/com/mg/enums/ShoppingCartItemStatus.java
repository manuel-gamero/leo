package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum ShoppingCartItemStatus implements BasicEnum{
	
	NEW("NEW"),
	MAKING("MAKING"),
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
	
	
	private ShoppingCartItemStatus(String code){
		this.code = code;
	}
	
}
