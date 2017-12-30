package com.mg.service.dto;


/**
 * Contains basic item information 
 * 
 * @author MJGP
 *
 */
public class ItemDTO extends BasicDTO {
		
	private static final long serialVersionUID = 1L;
	
	private String key;
	private String value;
	
	public ItemDTO(){
		super();
	}
	
	public ItemDTO(String key, String value){
		super();
		this.key=key;
		this.value=value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
 