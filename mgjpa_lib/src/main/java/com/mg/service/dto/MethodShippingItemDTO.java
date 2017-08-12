package com.mg.service.dto;

import com.mg.enums.Country;
import com.mg.model.Price;

public class MethodShippingItemDTO extends BasicDTO{
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String nameEn;
	private String nameFr;
	private String descEn;
	private String descFr;
	private Price price;
	private Country country;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getNameFr() {
		return nameFr;
	}
	public void setNameFr(String nameFr) {
		this.nameFr = nameFr;
	}
	public String getDescEn() {
		return descEn;
	}
	public void setDescEn(String descEn) {
		this.descEn = descEn;
	}
	public String getDescFr() {
		return descFr;
	}
	public void setDescFr(String descFr) {
		this.descFr = descFr;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}

}
