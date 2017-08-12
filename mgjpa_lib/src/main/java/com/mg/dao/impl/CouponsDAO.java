package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.model.Coupons;

public class CouponsDAO extends GenericDaoImpl<Coupons> {
	
	private static final long serialVersionUID = 1L;

	public CouponsDAO() {
		super(Coupons.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	public Coupons find(String couponName) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("couponName", couponName);

		return  findOneResult(" select distinct c " +
							" from Coupons c " +
							" left join fetch c.couponsType ct " +
							" left join fetch c.couponsUsers cu " +
							" where c.couponName = :couponName ", parameters);
	}
	
	public List<Coupons> getAll() {
		return  findResults(" select distinct c " +
							" from Coupons c " +
							" left join fetch c.couponsType ct ");
	}
	
	public Coupons findToUser(String login) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("login", login);

		return  findOneResult(" select distinct c " +
							" from Coupons c " +
							" left join fetch c.couponsUsers cu " +
							" left join fetch cu.users u " +
							" left join fetch c.couponsType ct " +
							" where u.login = :login " +
							" and c.statusCode = 'SEND' ", parameters);
	}
	
}
