package com.mg.service.dto;

import java.util.Date;
import java.util.List;

import com.mg.enums.UserStatus;

/**
 * 
 * @author MJGP
 *
 */
public class UserDTO extends BasicDTO{
	
	private static final long serialVersionUID = -7297414197953404853L;
	 
	private String 	login;	
	private String 	password;
	private String 	oldPassword;
	private Integer	groupId;	
	private String  email;	
	private Integer numLogins;
	private Date 	lastLogindate;
	private boolean active;
	private Date 	createdOn;
	private String firstName;
	private String lastName;
	private String phone;
	private UserStatus statusCode;
	private List<UserAddressDTO> userAddressDTOList;
	
	public UserDTO(){
		super();
	}
	
	public UserDTO(int id, String login, String password, int groupId, String email){
		super();
		setId(id);
		setLogin(login);
		setPassword(password);
		setGroupId(groupId);
		setEmail(email);
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getNumLogins() {
		return numLogins;
	}

	public void setNumLogins(Integer numLogins) {
		this.numLogins = numLogins;
	}

	public Date getLastLogindate() {
		return lastLogindate;
	}

	public void setLastLogindate(Date lastLogindate) {
		this.lastLogindate = lastLogindate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(UserStatus statusCode) {
		this.statusCode = statusCode;
	}

	public List<UserAddressDTO> getUserAddressDTOList() {
		return userAddressDTOList;
	}

	public void setUserAddressDTOList(List<UserAddressDTO> userAddressDTOList) {
		this.userAddressDTOList = userAddressDTOList;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


}
