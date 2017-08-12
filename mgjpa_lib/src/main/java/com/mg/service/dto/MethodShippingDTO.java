package com.mg.service.dto;

public class MethodShippingDTO extends BasicDTO{
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String name;
	private String desc;
	private String price;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
