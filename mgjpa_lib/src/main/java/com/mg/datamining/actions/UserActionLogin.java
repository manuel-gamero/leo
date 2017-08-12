package com.mg.datamining.actions;

import com.mg.datamining.helpers.DeviceUserHelper;
import com.mg.datamining.interfaces.IDeviceUserAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceUser;
import com.mg.util.text.StringUtils;

public class UserActionLogin extends UserActionBasic implements IDeviceUserAction{

	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException {
		String message = audit.getMessage();
		String userName = StringUtils.getListRegexMatches(message, "username = [a-zA-Z0-9|@|.|-|_]*").get(0).split("=")[1].trim(); 
		DeviceUserHelper.create(device, audit, this, userName);
	}
	
	@Override
	public void applyAction(Audit audit, DeviceUser item) {
		item.setCount( item.getCount() + 1 );
	}
}
