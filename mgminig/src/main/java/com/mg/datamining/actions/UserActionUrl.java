package com.mg.datamining.actions;

import com.mg.datamining.helpers.DeviceUrlHelper;
import com.mg.datamining.interfaces.IDeviceUrlAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;

public class UserActionUrl extends UserActionBasicUrl implements IDeviceUrlAction {

	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException {
		DeviceUrlHelper.create(device, audit, this, true);
	}
}
