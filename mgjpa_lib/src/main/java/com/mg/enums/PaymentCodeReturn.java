package com.mg.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum PaymentCodeReturn implements BasicEnum{
	
	TEST("payetest"),
	PAIEMENT("paiement"),
	ANNULATION("Annulation"),
	PAYPAL_NONE("None"),
	PAYPAL_CANCELEDREVERSAL("Canceled-Reversal"),
	PAYPAL_COMPLETED("Completed"),
	PAYPAL_DENIED("Denied"),
	PAYPAL_PENDINGREASON("PendingReason"),
	PAYPAL_EXPIRED("Expired"),
	PAYPAL_FAIL("Failed"),
	PAYPAL_INPROGRES("In-Progress"),
	PAYPAL_PARTIALLYREFUNDED("Partially-Refunded"),
	PAYPAL_PENDING("Pending"),
	PAYPAL_REFUNDED("Refunded"),
	PAYPAL_REVERSED("Reversed"),
	PAYPAL_PROCESSED("Processed"),
	PAYPAL_VOIDED("Voided")
	;
	
	
	private String localizationKey = "enum.payment.code.return." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private PaymentCodeReturn(String code){
		this.code = code;
	}
	
	public static PaymentCodeReturn getPaymentCodeReturn(String codeReturn){
		for (PaymentCodeReturn item : PaymentCodeReturn.values()) {
			if(item.getCode().equals(codeReturn)){
				return (item);
			}
		} 
		return (null);
	}
	
}
