package com.mg.datamining.interfaces;

import com.mg.model.Audit;
import com.mg.model.DeviceComponent;

public interface IDeviceComponentAction extends IDeviceAction {
	void applyAction(Audit audit, DeviceComponent item);
}
