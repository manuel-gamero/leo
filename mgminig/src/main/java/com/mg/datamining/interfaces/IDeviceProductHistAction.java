package com.mg.datamining.interfaces;

import com.mg.model.Audit;
import com.mg.model.DeviceProductHist;

public interface IDeviceProductHistAction extends IDeviceAction {
	void applyAction(Audit audit, DeviceProductHist item);
}
