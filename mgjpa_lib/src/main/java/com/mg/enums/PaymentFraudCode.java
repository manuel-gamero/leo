package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum PaymentFraudCode implements BasicEnum{
	
	IP("1"),
	CARD_NUMBER("2"),
	BIN("3"),
	COUNTRY_CARD("4"),
	COUNTRY_IP("5"),
	DIF_COUNTRY_IP("6"),
	EMAIL_BLACK("7"),
	AMOUNT_LIMIT_PERIOD("8"),
	TRANSACTION_LIMIT_CREDIR("9"),
	TRNSACTION_LIMIT_ALIAS("11"),
	AMOUNT_LIMIT_ALIAS("12"),
	AMOUNT_LIMIT_IP("13"),
	NUMBER_LIMIT_IP("14"),
	CARD_TEST("15"),
	LIMIT_NUMBER_ALIAS("16")
	;
	
	
	private String localizationKey = "enum.payment.fraud.code." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private PaymentFraudCode(String code){
		this.code = code;
	}
	
	public static PaymentFraudCode getEnumByCode(String code){
		for (PaymentFraudCode item : PaymentFraudCode.values()) {
			if(item.getCode().equals(code)){
				return (item);
			}
		} 
		return (null);
	}
	
}
