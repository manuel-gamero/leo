package com.mg.web.struts.action.shopping;

import java.util.Set;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.enums.Country;
import com.mg.enums.MethodShippingStatus;
import com.mg.model.MethodShipping;
import com.mg.model.PriceEntry;
import com.mg.service.ServiceLocator;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.util.currency.CurrencyUtils;
import com.mg.util.translation.Translations;
import com.mg.web.struts.action.BasicTranslationAction;

public class AddEditMethodShipping extends BasicTranslationAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private static final Logger log = LogManager.getLogger(AddEditMethodShipping.class);
	private Integer id;
	private MethodShipping methodShipping;
	private int descrTranslationId;
	private int nameTranslationId;
	private String url;
	private Country country;
	private Set<PriceEntry> priceEntrySet;
	private MethodShippingStatus statusCode;
	
	@Override
	public String execute(){
		try{
			if(id != null){
				methodShipping = ServiceLocator.getService(ShoppingServiceImpl.class).getMethodShipping(id);
				setValueTranslation(methodShipping);
				setStatusCode(methodShipping.getStatusCode());
				if(methodShipping.getPrice()!= null){
					setPriceEntrySet(methodShipping.getPrice().getPriceEntries());
				}
			}
			return INPUT;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String save(){
		
		try{
			Translations translationsName = new Translations.StringTranslationBuilder().engString(nameEn).frString(nameFr).build();
			Translations translationsDesc = new Translations.StringTranslationBuilder().engString(descEn).frString(descFr).build();
			if(id != null){
				methodShipping = ServiceLocator.getService(ShoppingServiceImpl.class).getMethodShipping(id);
			}
			methodShipping.setCountry(country);
			methodShipping.setStatusCode(statusCode);
			CurrencyUtils.getPrice(methodShipping, priceEntrySet);
			
			ServiceLocator.getService(ShoppingServiceImpl.class).saveMethodShipping(methodShipping, translationsName, translationsDesc);
	
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}
	
	public String remove(){
		try{
			if(id != null){
				methodShipping = ServiceLocator.getService(ShoppingServiceImpl.class).getMethodShipping(id);
				ServiceLocator.getService(ShoppingServiceImpl.class).deleteMethodShipping(methodShipping);
			}
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getDescrTranslationId() {
		return descrTranslationId;
	}

	public void setDescrTranslationId(int descrTranslationId) {
		this.descrTranslationId = descrTranslationId;
	}

	public int getNameTranslationId() {
		return nameTranslationId;
	}

	public void setNameTranslationId(int nameTranslationId) {
		this.nameTranslationId = nameTranslationId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MethodShipping getMethodShipping() {
		return methodShipping;
	}

	public void setMethodShipping(MethodShipping methodShipping) {
		this.methodShipping = methodShipping;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Set<PriceEntry> getPriceEntrySet() {
		return priceEntrySet;
	}

	public void setPriceEntrySet(Set<PriceEntry> priceEntrySet) {
		this.priceEntrySet = priceEntrySet;
	}

	public MethodShippingStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(MethodShippingStatus statusCode) {
		this.statusCode = statusCode;
	}


}
