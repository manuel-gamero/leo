package com.mg.service.dto;

import java.io.Serializable;

import com.mg.service.cache.Cacheable;

/**
 * Basic class for all Data Transfer Objects in the system.
 * 
 * @author MJGP
 */

public class BasicDTO implements Serializable, Cacheable {	
		
	private static final long serialVersionUID = 1L;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String makeKey() {
		StringBuilder builder = new StringBuilder(this.getClass().getName());
		builder.append("-");
		builder.append(getId());
		return builder.toString();
	}

	/**
	 * @param id
	 */
	public BasicDTO(int id) {
		super();
		this.id = id;
	}
	

	/**
	 * @param id
	 */
	public BasicDTO() {}	
	
}
