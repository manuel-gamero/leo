package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum PaymentMethodType implements BasicEnum{
	
	NO_METHOD("NO_METHOD"),
	VISA("VI"),
	MASTERCAR("MC"),
	AMERICANEXPRESS("AM"),
	NOTAVAILABLE("na"),
	PAYPAL("PAYPAL"),
	FREE("FREE"),
	PAYPAL_CART("cart"),
	PAYPAL_EXP_CHECKOUT("expresscheckout"),
	DESJARDINS("DESJARDINS")
	;
	
	
	private String localizationKey = "enum.payment.method.type." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private PaymentMethodType(String code){
		this.code = code;
	}
	
	public static PaymentMethodType getEnumByCode(String code){
		for (PaymentMethodType item : PaymentMethodType.values()) {
			if(item.getCode().equals(code)){
				return (item);
			}
		} 
		return (null);
	}
	
}
