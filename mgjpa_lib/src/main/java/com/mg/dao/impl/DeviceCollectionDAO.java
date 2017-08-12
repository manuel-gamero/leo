package com.mg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mg.model.DeviceCollection;

public class DeviceCollectionDAO extends GenericDaoImpl<DeviceCollection> {
	
	private static final long serialVersionUID = 1L;

	public DeviceCollectionDAO() {
		super(DeviceCollection.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public DeviceCollection getDeviceCollection(Integer deviceId, Integer collectionId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("collectionId", collectionId);
		parameters.put("deviceId", deviceId);

		return  findOneResult(" select dc " +
							  " from DeviceCollection dc " +
							  " where dc.collection.id = :collectionId " +
							  " and dc.device.id = :deviceId ", parameters);
	}



}
