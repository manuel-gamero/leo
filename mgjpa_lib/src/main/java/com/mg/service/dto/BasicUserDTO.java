package com.mg.service.dto;


/**
 * Contains user's basic information 
 * 
 * @author MJGP
 *
 */
public class BasicUserDTO extends BasicDTO{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getPrimaryLangId() {
		return primaryLangId;
	}
	public void setPrimaryLangId(int primaryLangId) {
		this.primaryLangId = primaryLangId;
	}
	private String login;
	private String email;
	private int primaryLangId;
	
}
 