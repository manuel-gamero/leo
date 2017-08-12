package com.mg.enums;



/**
 * 
 * @author MJGP
 *
 */
public enum Country implements BasicEnum{
	
	US("US") /*{
		@Override
		public Currency getCurrency() { return Currency.USD; }}*/,
	CA("CA")/*{
		@Override
		public Currency getCurrency() { return Currency.CAD; }}*/,
	FR("FR")/*{
		@Override
		public Currency getCurrency() { return Currency.EUR; }}*/,
	ES("ES")/*{
		@Override
		public Currency getCurrency() { return Currency.EUR; }}*/	
	;
	
	private String localizationKey = "enum.shopping.cart.country." + name().toLowerCase();
	
	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}

	private String code;	
	
	private Country(String code){
		this.code = code;
	}
	
	//public abstract Currency getCurrency();
	
	public static Country getCountryByCode(String lang){
		for (Country item : Country.values()) {
			if(item.getCode().equals(lang.toUpperCase())){
				return (item);
			}
		} 
		return (null);
	}
	
}
