package com.mg.util.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.mg.enums.Language;
import com.mg.exception.CurrencyNoExistException;
import com.mg.model.MethodShipping;
import com.mg.model.Price;
import com.mg.model.PriceEntry;
import com.mg.model.Product;
import com.mg.util.text.StringUtils;

public class CurrencyUtils {
	
	private static final String ZERO = "0.00"; 
	public static final String DEFAULT_CURRENCY = "CAD";

	public static void getPrice(ICurrency currencyObj,
			Set<PriceEntry> priceEntries) {
		Price price = null;
		if(priceEntries != null){
			if(currencyObj.getPrice() == null){
				price = new Price();
				if(currencyObj.getClass().isAssignableFrom(Product.class)){
					price.setProduct((Product)currencyObj);
					price.setMethodShipping(null);
				}
				else if (currencyObj.getClass().isAssignableFrom(MethodShipping.class)){
					price.setMethodShipping((MethodShipping)currencyObj);
					price.setProduct(null);
				}
			}
			else{
				price = currencyObj.getPrice();
			}
			price.setPriceEntries(priceEntries);
			currencyObj.setPrice(price);
		}
	}

	public static void getPriceByPriceEntry (ICurrency currencyObj, BigDecimal price, String currencyCode){
		PriceEntry priceEntry = new PriceEntry();
		priceEntry.setCurrency(currencyCode);
		priceEntry.setPriceCurrency(price);

		if(currencyObj.getPrice() == null){
			Price priceItem = new Price();
			priceEntry.setPrice(priceItem);
			Set<PriceEntry> priceEntries = new HashSet<PriceEntry>(); 
			priceEntries.add(priceEntry);
			priceItem.setPriceEntries(priceEntries);
			if(currencyObj.getClass().isAssignableFrom(Product.class)){
				priceItem.setProduct((Product)currencyObj);
				priceItem.setMethodShipping(null);
			}
			else if (currencyObj.getClass().isAssignableFrom(MethodShipping.class)){
				priceItem.setMethodShipping((MethodShipping)currencyObj);
				priceItem.setProduct(null);
			}
			priceItem.setMethodShipping(null);
			currencyObj.setPrice(priceItem);
		}
		else{
			priceEntry.setPrice(currencyObj.getPrice());
			currencyObj.getPrice().getPriceEntries().add(priceEntry);
		}
	}
	
	/**
	 * @param currencyObj
	 * @param currency
	 * @return If the currency exists in the PriceEntry set.
	 */
	public static boolean checkCurrency(ICurrency currencyObj, String currencyCode){
		if(currencyObj.getPrice() == null){
			return false;
		}
		for (PriceEntry item : currencyObj.getPrice().getPriceEntries()) {
			if(item.getCurrency().equals(currencyCode)){
				return true;
			}
		}
		return false;
	}
	
	public static BigDecimal priceToLocale(Price price, String currencyCode) throws CurrencyNoExistException{
		if(price != null){
			Set<PriceEntry> priceSet = price.getPriceEntries();
			for (PriceEntry priceEntry : priceSet) {
				if(priceEntry.getCurrency().equals(currencyCode)){
					if(priceEntry.getDiscount() != null){
						return(priceEntry.getDiscount().setScale(2, RoundingMode.CEILING));
					}
					else{
						return(priceEntry.getPriceCurrency().setScale(2, RoundingMode.CEILING));
					}
				}
			}
			/*for (PriceEntry priceEntry : priceSet) {
				if(priceEntry.getCurrency().equals(DEFAULT_CURRENCY)){
					if(priceEntry.getDiscount() != null){
						return(priceEntry.getDiscount().setScale(2, RoundingMode.CEILING));
					}
					else{
						return(priceEntry.getPriceCurrency().setScale(2, RoundingMode.CEILING));
					}
				}
			}*/
		}
		throw new CurrencyNoExistException("Currency doesn't exist for productId: " + price.getProduct().getId() + " for currency: " + currencyCode);
	}
	
	public static BigDecimal PriceWithoutDiscountToLocale(Price price, String currencyCode) throws CurrencyNoExistException{
		if(price != null){
			Set<PriceEntry> priceSet = price.getPriceEntries();
			for (PriceEntry priceEntry : priceSet) {
				if(priceEntry.getCurrency().equals(currencyCode)){
					return(priceEntry.getPriceCurrency().setScale(2, RoundingMode.CEILING));
				}
			}
			/*for (PriceEntry priceEntry : priceSet) {
				if(priceEntry.getCurrency().equals(DEFAULT_CURRENCY)){
					return(priceEntry.getPriceCurrency());
				}
			}*/
		}
		throw new CurrencyNoExistException("Currency doesn't exist for productId: " + price.getProduct().getId() + " for currency: " + currencyCode);
	}
	
	public static String displayPriceLocale(Price price, String lang, String country) throws CurrencyNoExistException{
		return displayPriceLocale(priceToLocale(price, country) , lang, country);
	}
	
	public static String displayPriceWithoutDiscountLocale(Price price, String lang, String country) throws CurrencyNoExistException{
		return displayNewPriceLocale(PriceWithoutDiscountToLocale(price, country) , lang, country);
	}
	
	private static String displayNewPriceLocale(BigDecimal newPriceToLocale,
			String lang, String country) throws CurrencyNoExistException {
		if(newPriceToLocale != null){
			return displayPriceLocale( newPriceToLocale, lang, country );
		}
		else{
			return null;
		}
	}
	public static String displayPriceLocale(Float price, String lang, String country) throws CurrencyNoExistException{
		return displayPriceLocale(String.valueOf(price), lang, country);
	}
	
	//Se puede conseguir el caracter de euro con StringUtils.convertToUTF8("\u20AC") o "\u20AC"
	public static String displayPriceLocale(String price, String lang, String currencyCode) throws CurrencyNoExistException{
		if(price != null){
			price = price.replace(",", ".");
			Language languageEnum = Language.getLanguageByCode(lang);
			Currency currency = Currency.getInstance(currencyCode);
			if("EUR".equals(currencyCode)){
				return price + StringUtils.convertToUTF8("\u20AC"); //StringUtils.convertToUTF8("€");
			}
			else if ("USD".equals(currencyCode) || "CAD".equals(currencyCode)){
				if(Language.ENGLISH.equals(languageEnum)){
					return  "$" + price;
				}
				else{
					return price + "$";
				}
			}
			else{
				if(Language.ENGLISH.equals(languageEnum)){
					return currency.getSymbol() + price;
				}
				else{
					return price + currency.getSymbol();
				}
			}
		}
		return null;
	}

	public static String displayPriceLocale(String price, Locale locale) throws CurrencyNoExistException{
		String numberFormat = DecimalFormat.getCurrencyInstance(locale).format(price);
		
		Currency currency = Currency.getInstance(locale);
		NumberFormat format = NumberFormat.getCurrencyInstance(locale);
		format.setCurrency(currency);
		format.setMaximumFractionDigits(currency.getDefaultFractionDigits());
		String formatPrice = format.format(price);
				
		return numberFormat;
	}
	
	/*private static String getCountry(Currency currency) {
		if(Currency.CAD.code.equals("CAD")){
			return Country.CA.getCode();
		}
		else if(Currency.USD.code.equals("USD")){
			return Country.US.getCode();
		}
		else if(Currency.EUR.code.equals("EUR")){
			return Country.FR.getCode();
		}
		return Country.CA.getCode();
	}
*/
	public static String displayPriceLocale(BigDecimal totalShopping,
			String lang, String currencyCode) throws CurrencyNoExistException {
		return displayPriceLocale( getPrice(totalShopping), lang, currencyCode );
	}

	private static String getPrice(BigDecimal totalShopping) {
		if(totalShopping != null){
			return totalShopping.toString();
		}
		else{
			return ZERO;
		}
	}
	
	public static boolean hasDiscount(Price price, String currencyCode) {
		boolean hasDiscount = false;
		if(price != null){
			Set<PriceEntry> priceSet = price.getPriceEntries();
			for (PriceEntry priceEntry : priceSet) {
				if(priceEntry.getCurrency().equals(currencyCode)){
					return(priceEntry.getDiscount() != null);
				}
			}
			for (PriceEntry priceEntry : priceSet) {
				if(priceEntry.getCurrency().equals(DEFAULT_CURRENCY)){
					return(priceEntry.getDiscount() != null);
				}
			}
		}
		return hasDiscount;
	}
}
