package com.mg.datamining.interfaces;

import com.mg.model.Audit;
import com.mg.model.DeviceUser;

public interface IDeviceUserAction extends IDeviceAction {
	void applyAction(Audit audit, DeviceUser item);
}
