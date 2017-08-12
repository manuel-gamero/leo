package com.mg.web.struts.action.user;

import org.apache.log4j.Logger;

import com.mg.enums.UserAddressType;
import com.mg.model.UserAddress;
import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.user.UserServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.mg.web.util.LoginUtils;

public class AddEditUserAddress extends BasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private static Logger log = Logger.getLogger(AddEditUserAddress.class); 
	
	private Integer id;
	private UserAddress userAddress;
	private UserAddressType type;
	
	@Override
	public String execute() {
		try{
			// set the url where this action come from 
			setRefereUrl();
			if(id != null){
				userAddress = ServiceLocator.getService(UserServiceImpl.class).getUserAddress(id);
			}
			return INPUT;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String save() {
		try{
			UserSessionDTO userSession = getUserSession();
			if(userSession != null){
				userAddress.setUsers( new Users(userSession.getId()));
			}
			userAddress.setTypeCode(type);
			if(id != null){
				userAddress.setId(id);
				ServiceLocator.getService(UserServiceImpl.class).updateUserAddress(userAddress);
			}
			else{
				ServiceLocator.getService(UserServiceImpl.class).saveUserAddress(userAddress);
			}
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}
	
	public String remove() {
		try{
			if(id != null){
				UserAddress address = ServiceLocator.getService(UserServiceImpl.class).getUserAddress(id); 
				UserSessionDTO userSessionDTO = LoginUtils.getUser(getRequest());
				if(userSessionDTO.getId() == address.getUsers().getId()){
					ServiceLocator.getService(UserServiceImpl.class).deleteUserAddress(address);
				}
				else{
					log.error("Try to remove address from other user, userAddressId : " + id);
					return ERROR;
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
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

	public UserAddressType getType() {
		return type;
	}

	public void setType(UserAddressType type) {
		this.type = type;
	}

	public String getTitle(){
		if( id!=null && id != 0){
			return getText("bolsos.shoppingcart.address.panel.button.edit.shipping");
		}
		return getText("bolsos.shoppingcart.address.panel.button.add.shipping");
	}

}
