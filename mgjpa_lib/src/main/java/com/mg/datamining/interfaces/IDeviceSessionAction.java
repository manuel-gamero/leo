package com.mg.datamining.interfaces;

import com.mg.model.Audit;
import com.mg.model.DeviceSession;

public interface IDeviceSessionAction extends IDeviceAction {
	void applyAction(Audit audit, DeviceSession item);
}
