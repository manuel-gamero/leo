package com.mg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mg.model.DeviceComponent;

public class DeviceComponentDAO extends GenericDaoImpl<DeviceComponent> {
	
	private static final long serialVersionUID = 1L;

	public DeviceComponentDAO() {
		super(DeviceComponent.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public DeviceComponent getDeviceComponent(Integer deviceId, Integer componentId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("componentId", componentId);
		parameters.put("deviceId", deviceId);

		return  findOneResult(" select dc " +
							  " from DeviceComponent dc " +
							  " where dc.customComponentCollection.id = :componentId " +
							  " and dc.device.id = :deviceId ", parameters);
	}


}
