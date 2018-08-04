package com.mg.datamining.actions;

import java.util.concurrent.TimeUnit;

import com.mg.datamining.interfaces.IDeviceUrlAction;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceUrl;
import com.mg.util.CommonUtils;

public abstract class UserActionBasicUrl extends UserActionBasic implements IDeviceUrlAction {

	@Override
	public void applyAction(Device device, Audit audit, DeviceUrl item) {
		int size = device.getDeviceUrls().size();
		if( size > 0 ){
			DeviceUrl deviceUrl = device.getDeviceUrls().get(size - 1);
			Long duration = CommonUtils.getDateDiff( deviceUrl.getActionDate(),item.getActionDate(), TimeUnit.MILLISECONDS );
			deviceUrl.setDuration(duration);
		}
		
	}
}
