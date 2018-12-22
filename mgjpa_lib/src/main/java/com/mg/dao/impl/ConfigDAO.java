package com.mg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mg.model.Config;

public class ConfigDAO extends GenericDaoImpl<Config> {
	
	private static final long serialVersionUID = 1L;

	public ConfigDAO() {
		super(Config.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public Config getValue(String code){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", code);
		
		return findOneResult( "select distinct a " +
							  " from Config a " +
							  " where a.code=:code " , parameters);
	}

}
