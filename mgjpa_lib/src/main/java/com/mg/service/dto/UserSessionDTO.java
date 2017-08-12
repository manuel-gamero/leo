package com.mg.service.dto;

import java.util.Set;

import com.mg.enums.UserStatus;
import com.mg.enums.UserType;
import com.mg.model.UserAddress;


/**
 * Contains user data we want to put in the session once
 * the user logs in to the system successfully.
 * 
 * @author MJGP
 *
 */

public class UserSessionDTO extends BasicUserDTO{
		
	private static final long serialVersionUID = 7975322068331229026L;

	public int getSiteLangId() {
		return siteLangId;
	}

	public void setSiteLangId(int siteLangId) {
		this.siteLangId = siteLangId;
	}

	public int getNumberOfResults() {
		return numberOfResults;
	}

	public void setNumberOfResults(int numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

	/*public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}*/

	private int siteLangId;	// Site language Id
	private int numberOfResults; // How many search results per page he wants	
	private UserType userType;
	private UserStatus userStatus;
	private Set<UserAddress> addresses;
	//private String imageName;
	// other
	
	public static UserSessionDTO valueOf(int userId, String login, int userLangId, int siteLangId, int numberOfResults, String email, UserType userType, UserStatus userStatus, Set<UserAddress> addresses){
		
		return new UserSessionDTO(userId, login, userLangId, siteLangId, numberOfResults,email, userType, userStatus, addresses);
	}
	
	private UserSessionDTO(int userId, String login, int userLangId, int siteLangId, int numberOfResults, String email, UserType userType, UserStatus userStatus, Set<UserAddress> addresses){
		setId(userId);
		setLogin(login);
		setPrimaryLangId(userLangId);
		setSiteLangId(siteLangId);
		setNumberOfResults(numberOfResults);
		setEmail(email);
		setUserStatus(userStatus);
		setUserType(userType);
		setAddresses(addresses);
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public Set<UserAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<UserAddress> addresses) {
		this.addresses = addresses;
	}
}
