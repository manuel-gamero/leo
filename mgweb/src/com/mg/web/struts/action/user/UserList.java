package com.mg.web.struts.action.user;

import java.util.List;

import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.user.UserServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class UserList extends BasicAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private List<Users> userList;
	
	
	@Override
	public void prepare() {
		try{
			setUserList(ServiceLocator.getService(UserServiceImpl.class).getAllUsers());
	
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	@Override
	public String execute() {
		return SUCCESS;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	


}