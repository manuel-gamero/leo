package com.mg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mg.model.Paypal;

public class PaypalDAO extends GenericDaoImpl<Paypal> {
	
	private static final long serialVersionUID = 1L;

	public PaypalDAO() {
		super(Paypal.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public Paypal getPaypalByToken(String token){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("token", token);
		
		return findOneResult("select distinct p " +
						   " from paypal p " +
						   " where p.token = :token ", parameters);
	}

}
