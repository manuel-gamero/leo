package com.mg.web.struts.action.user;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.enums.Language;
import com.mg.model.UserAddress;
import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.UserDTO;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.user.UserServiceImpl;
import com.mg.util.text.StringUtils;
import com.mg.web.struts.action.BasicAction;
import com.mg.web.util.LoginUtils;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class ModifyUser extends BasicAction implements ModelDriven<UserDTO>, Preparable{

	private static final long serialVersionUID = 1155604242419622177L;

	private static Logger log = LogManager.getLogger(ModifyUser.class);
	private UserDTO user;
	private String urlRedirect;
	private Integer id;
	
	@Override
	public void prepare() throws Exception {
		try{
			super.prepare();
			UserSessionDTO userSession = LoginUtils.getUser(getRequest());
			user = DTOFactory.getUserDTO( ServiceLocator.getService(UserServiceImpl.class).getUser(userSession.getId()) );
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	@Override
	public String execute() {
		return INPUT;
	}

	@Override
	public UserDTO getModel() {
		return user;
	}
	
	public String save() {
		UserSessionDTO userSession = LoginUtils.getUser(getRequest());
		try {
			Users item = ServiceLocator.getService(UserServiceImpl.class).getUser(userSession.getId());
			if(StringUtils.isNoNullNoEmpty(user.getEmail()) ){
				item.setEmail(user.getEmail());
			}
			if(StringUtils.isNoNullNoEmpty(user.getFirstName())){
				item.setFirstName(user.getFirstName());
			}
			if(StringUtils.isNoNullNoEmpty(user.getLastName())){
				item.setLastName(user.getLastName());
			}
			if(StringUtils.isNoNullNoEmpty(user.getPhone())){
				item.setPhone(user.getPhone());
			}
			ServiceLocator.getService(UserServiceImpl.class).updateUser(item);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return ERROR;
		}
		return getRedirectAction();
	}
	
	private String getRedirectAction() {
		if(Language.FRENCH.getCode().equals(getCurrentLanguage())){
			setUrlRedirect("fr/utilisateur/compte");
		}
		else{
			setUrlRedirect("en/user/account");
		}
		return SUCCESS;
	}

	public String savePassword() {
		UserSessionDTO userSession = LoginUtils.getUser(getRequest());
		try {
			Users item = ServiceLocator.getService(UserServiceImpl.class).getUser(userSession.getId());
			UserSessionDTO userSessionNewPassword = ServiceLocator.getService(UserServiceImpl.class).authenticate(item.getLogin(),user.getOldPassword()); 
			if ( userSessionNewPassword != null ){
				if(StringUtils.isNoNullNoEmpty(user.getPassword())){
					item.setPassword(LoginUtils.generateStorngPasswordHash(user.getPassword()));
				}
				ServiceLocator.getService(UserServiceImpl.class).updateUser(item);
			}			
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return ERROR;
		}
		return getRedirectAction();
	}

	public String getUrlRedirect() {
		return urlRedirect;
	}

	public void setUrlRedirect(String urlRedirect) {
		this.urlRedirect = urlRedirect;
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
			return getRedirectAction();
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
}
