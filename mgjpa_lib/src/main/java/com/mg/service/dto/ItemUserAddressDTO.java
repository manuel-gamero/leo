package com.mg.service.dto;


/**
 * 
 * @author MJGP
 *
 */
public class ItemUserAddressDTO extends BasicDTO{
	
	private static final long serialVersionUID = -7297414197953404853L;
		 
	private String 	location;
	
	public ItemUserAddressDTO(){
		super();
	}
	
	public ItemUserAddressDTO(int id, String location){
		super();
		setId(id);
		setLocation(location);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
