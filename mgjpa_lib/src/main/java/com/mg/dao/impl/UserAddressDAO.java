package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.enums.UserAddressType;
import com.mg.model.UserAddress;

public class UserAddressDAO extends GenericDaoImpl<UserAddress> {

	private static final long serialVersionUID = 1L;

	public UserAddressDAO() {
		super(UserAddress.class);
	}

	public List<UserAddress> getUserAddressList(int userId, UserAddressType type){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", userId);    
		parameters.put("type", type);  
		

		return  findResults(" select ua " +
							" from UserAddress ua " +
							" where ua.users.id = :userId " +
							" and ua.typeCode = :type", parameters);
	}
	
	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public UserAddress findEntityById(Integer id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(" select distinct ua " +
							  " from UserAddress ua " +
							  " join fetch ua.users u " +
							  " where ua.id = :id " +
							  " and ua.typeCode = 'SHIPPING'", parameters);
	}
}
