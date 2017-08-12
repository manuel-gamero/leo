package com.mg.datamining.helpers;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.mg.enums.UserType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Device;
import com.mg.model.DeviceSession;
import com.mg.model.DeviceUser;
import com.mg.util.CommonUtils;

public class DeviceSessionHelper {

	public static void create(Device device) throws ServiceException, ServiceLocatorException{
		DeviceSession item = createDeviceSession(device);
		if( device.getDeviceSessions() == null ){
			Set<DeviceSession> itemSet = new HashSet<DeviceSession>();
			itemSet.add(item);
			device.setDeviceSessions(itemSet);
		}
		else{
			device.getDeviceSessions().add(item);
		}
	}

	private static DeviceSession createDeviceSession(Device device) throws ServiceException, ServiceLocatorException {
		if( device.getDeviceUrls().size()>0 ){
			DeviceSession item = new DeviceSession();
			item.setDevice(device);
			Date iniDate = device.getDeviceUrls().get(0).getActionDate();
			Date lastDate = device.getDeviceUrls().get(device.getDeviceUrls().size()-1).getActionDate();
			item.setActionDate(iniDate);
			item.setDuration(CommonUtils.getDateDiff( iniDate, lastDate, TimeUnit.MILLISECONDS ));
			item.setCreationDate(new Date());
			return item;
		}
		return null;
	}
	
	public static boolean isAdminUser(Device deviceDatabase) {
		if(deviceDatabase.getDeviceUsers() != null){
			for (DeviceUser item : deviceDatabase.getDeviceUsers()) {
				if(item.getUsers() != null && item.getUsers().getTypeCode().equals(UserType.ADMIN)){
					return true;
				}
			}
		}
		return false;
	}
}
