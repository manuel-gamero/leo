package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum PaymentCVX implements BasicEnum{
	
	VERIFICATION("oui"),
	NO_VERIFICATION("non"),
	PAYPAL_NONE("none"),
	PAYPAL_ECHECK("echeck"),
	PAYPAL_INSTANT("instant")
	;
	
	
	private String localizationKey = "enum.payment.cvx." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private PaymentCVX(String code){
		this.code = code;
	}
	
	public static PaymentCVX getEnumByCode(String code){
		for (PaymentCVX item : PaymentCVX.values()) {
			if(item.getCode().equals(code)){
				return (item);
			}
		} 
		return (null);
	}
}
