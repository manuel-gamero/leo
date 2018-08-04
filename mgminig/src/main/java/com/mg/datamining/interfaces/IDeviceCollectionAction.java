package com.mg.datamining.interfaces;

import com.mg.model.Audit;
import com.mg.model.DeviceCollection;

public interface IDeviceCollectionAction extends IDeviceAction {
	void applyAction(Audit audit, DeviceCollection item);
}
