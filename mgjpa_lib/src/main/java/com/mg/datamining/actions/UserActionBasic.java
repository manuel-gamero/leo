package com.mg.datamining.actions;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mg.datamining.interfaces.ICreation;
import com.mg.enums.UserActionParamType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceProductHist;
import com.mg.model.DeviceUserActionParam;

public abstract class UserActionBasic implements ICreation  {

	@Override
	public abstract void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException;

	protected void createParamValue(DeviceProductHist item,
			UserActionParamType actionParamType, String paramValue) {
		if(item.getUserActioParams() == null){
			Set<DeviceUserActionParam> deviceUserActionParams =  new HashSet<DeviceUserActionParam>();
			item.setUserActioParams(deviceUserActionParams);
		}
		DeviceUserActionParam param = new DeviceUserActionParam();
		param.setCreationDate(new Date());
		param.setDeviceProductHist(item);
		param.setParam(actionParamType);
		param.setParamValue(paramValue);
		
		item.getUserActioParams().add(param);
	}
}
