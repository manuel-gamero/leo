package com.mg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mg.model.Device;

public class DeviceDAO extends GenericDaoImpl<Device> {
	
	private static final long serialVersionUID = 1L;

	public DeviceDAO() {
		super(Device.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	public Device getDevice(String fingerPrint) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fingerPrint", fingerPrint);

		return  findOneResult(" select d " +
							  " from Device d " +
							  " where d.fingerprint = :fingerPrint ", parameters);
	}
	
	public Device findEntityByFingerPrint(String fingerPrint) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fingerPrint", fingerPrint);

		return  findOneResult(" select d " +
							  " from Device d " +
							  " left join fetch d.deviceProducts dp " +
							//  " left join fetch d.deviceProductHists dph " +
							  " left join fetch d.deviceComponents dcc " +
							//  " left join fetch d.deviceComponentHists dph " +
							  " left join fetch d.deviceUsers dus " +
							//  " left join fetch d.deviceSessions ds " +
							//  " left join fetch d.deviceUrls dur " +
							  " where d.fingerprint = :fingerPrint ", parameters);
	}


}
