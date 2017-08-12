package com.mg.datamining.enums;


/**
 * 
 * @author MJGP
 *
 */
public enum Attribute {
	
	VIEWCOUNTER("VIEWCOUNTER"),
	ADDCOUNTER("ADDCOUNTER"),
	SHARECOUNTER("SHARECOUNTER"),
	SELLCOUNTER("SELLCOUNTER"),
	REMOVECOUNTER("REMOVECOUNTER")
	;
	
	public String getCode() {
		return code;
	}

	private String code;
	
	
	private Attribute(String code){
		this.code = code;
	}
	
}
