package com.mg.datamining.interfaces;

import com.mg.model.Audit;
import com.mg.model.DeviceComponentHist;

public interface IDeviceComponentActionHist extends IDeviceAction {
	void applyAction(Audit audit, DeviceComponentHist item);
}
