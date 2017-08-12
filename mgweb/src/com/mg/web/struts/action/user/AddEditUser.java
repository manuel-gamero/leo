package com.mg.web.struts.action.user;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mg.annotation.Action;
import com.mg.enums.UserAddressType;
import com.mg.enums.UserStatus;
import com.mg.enums.UserType;
import com.mg.model.UserAddress;
import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.user.UserServiceImpl;
import com.mg.util.communication.Receipt;
import com.mg.web.struts.action.BasicAction;
import com.mg.web.util.LoginUtils;
import com.opensymphony.xwork2.ModelDriven;

public class AddEditUser extends BasicAction implements ModelDriven<Users>{

	private static final long serialVersionUID = 1155604242419622177L;
	private static Logger log = Logger.getLogger(AddEditUser.class);
	
	private Users user = new Users();
	private Integer id;
	private UserAddress userAddress;
	private String site;
	
	@Override
	public String execute(){
		try{
			if(id != null){
				UserSessionDTO userSession = getUserSession();
				if(userSession != null && ( userSession.getUserType().equals(UserType.ADMIN) || userSession.getId() == id)){
					user = ServiceLocator.getService(UserServiceImpl.class).getUser(id);
					return INPUT;
				}else{
					return ERROR;
				}
			}else{
				return INPUT;
			}
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	@Override
	public Users getModel() {
		return user;
	}
	
	@Action(value="username = #user.email , site = #site")
	public String save(){
		try{
			log.debug("Saving user");
			String passwordToVerify = user.getPassword();
			if(user != null && user.getId() == 0){
				log.info("Creating user " + user.getEmail());
				if(user.getTypeCode() == null){
					user.setActive(true);
					user.setTypeCode(UserType.USER);
					user.setStatusCode(UserStatus.ACTIVE);
					user.setLogin(user.getEmail());
					user.setLang(getCurrentLanguage());
					
					if(userAddress!= null){
						userAddress.setUsers(user);
						userAddress.setFirstName(user.getFirstName());
						userAddress.setLastName(user.getLastName());
						Set<UserAddress> userAddresses = new HashSet<UserAddress>();
						userAddresses.add(UserAddress.createUserAddress(userAddress, UserAddressType.BILLING));
						userAddresses.add(UserAddress.createUserAddress(userAddress, UserAddressType.SHIPPING));
						user.setUserAddresses(userAddresses);
					}
				}
				user.setPassword(LoginUtils.generateStorngPasswordHash(user.getPassword()));
				ServiceLocator.getService(UserServiceImpl.class).createUser(user);
			}
			else {
				log.info("Updating user " + user.getEmail());
				ServiceLocator.getService(UserServiceImpl.class).updateUser(user);
			}
			
			//Put in session the user if comes the shopping process
			if( "SHOPPING".equals(site) || "WEB".equals(site) ){
				log.info("User " + user.getEmail() + " in session");
				UserSessionDTO userSession = ServiceLocator.getService(UserServiceImpl.class).authenticate(user.getLogin(), passwordToVerify);
				setUserSession(userSession);
				Receipt.registrationConfirmation(userSession, getCurrentLanguage(), getCurrentCurrencyCode());
				return getResultAction(site);
			}
			else{
				return SUCCESS;
			}
		}
		catch(Exception e){
			managerException(e);
			return ERROR;
		}
	}

	private String getResultAction(String site) {
		if( "SHOPPING".equals(site)){
			return SHOPPING;
		}
		else if ( "WEB".equals(site) ){
			return SUCCESS;
		}
		else{
			return SUCCESS;
		}
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}


}
