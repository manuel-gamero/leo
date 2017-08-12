package com.mg.datamining.helpers;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mg.datamining.interfaces.IDeviceUserAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceUser;
import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.user.UserServiceImpl;

public class DeviceUserHelper {

	public static void create(Device device, Audit audit, IDeviceUserAction action, String userName) throws ServiceException, ServiceLocatorException{
		if( device.getDeviceUsers() == null || device.getDeviceUsers().size() == 0 ){
			DeviceUser item = createDeviceUser(device, audit, userName);
			if(item != null){
				action.applyAction(audit, item);
				Set<DeviceUser> itemSet = new HashSet<DeviceUser>();
				itemSet.add(item);
				device.setDeviceUsers(itemSet);
			}
		}
		else{
			DeviceUser item = getDeviceUser(device, userName );
			if( item == null ){
				item = createDeviceUser(device, audit, userName);
				if(item != null){
					device.getDeviceUsers().add(item);
				}
			}
			action.applyAction(audit, item);
		}
	}
	
	private static DeviceUser getDeviceUser(Device device, String userName) {
		Set<DeviceUser> itemSet = device.getDeviceUsers();
		Iterator<DeviceUser> iterator = itemSet.iterator();
		boolean found = false;
		DeviceUser item = null;
		while (iterator.hasNext() && !found){
			item = iterator.next();
			if(item.getUsers().getLogin().equals(userName)){
				found = true;
			}
		}
		if( found ){
			return item;
		}
		else{
			return null;
		}
	}

	private static DeviceUser createDeviceUser(Device device, Audit audit, String userName) throws ServiceException, ServiceLocatorException {
		if(userName != null && userName.trim().length() > 0 ){
			Users user = ServiceLocator.getService(UserServiceImpl.class).getUser(userName);
			if(user != null){
				DeviceUser item = new DeviceUser();
				item.setUsers(user);
				item.setDevice(device);
				item.setCount(0);
				item.setCreationDate(new Date());
				return item;
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
}
