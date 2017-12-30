package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum ProductType implements BasicEnum{
	
	BAG("BAG"),
	WALLET("WALLET"),
	LARGE_POUCH("LARGE_POUCH"),
	MEDIUM_POUCH("MEDIUM_POUCH"),
	LAPTOP_BAG("LAPTOP_BAG"),
	GIRL_PURSE("GIRL_PURSE"),
	LAPTOP_POUCH("LAPTOP_POUCH"),
	TABLET_POUCH("TABLET_POUCH"),
	SNACK_BAG("SNACK_BAG"),
	BASKETS("BASKETS"),
	POUCHES("POUCHES"),
	;
	
	
	private String localizationKey = "enum.user.type." + name().toLowerCase();
	private String localizationTitleKey = "enum.user.type." + name().toLowerCase() + ".title";
	private String localizationDescriptionKey = "enum.user.type." + name().toLowerCase() + ".description";
	private String localizationImageKey = "enum.user.type." + name().toLowerCase() + ".image";
	

	public String getLocalizationKey() {
		return localizationKey;
	}
	
	public String getLocalizationTitleKey() {
		return localizationTitleKey;
	}
	
	public String getLocalizationDescriptionKey() {
		return localizationDescriptionKey;
	}

	public String getLocalizationImageKey() {
		return localizationImageKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private ProductType(String code){
		this.code = code;
	}
	
}
