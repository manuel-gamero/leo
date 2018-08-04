package com.mg.datamining.interfaces;

import com.mg.model.Audit;
import com.mg.model.DeviceProduct;

public interface IDeviceProductAction extends IDeviceAction {
	void applyAction(Audit audit, DeviceProduct item);
}
