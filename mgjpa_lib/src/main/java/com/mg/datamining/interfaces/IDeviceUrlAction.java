package com.mg.datamining.interfaces;

import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceUrl;

public interface IDeviceUrlAction extends IDeviceAction {
	void applyAction(Device device, Audit audit, DeviceUrl item);
}
