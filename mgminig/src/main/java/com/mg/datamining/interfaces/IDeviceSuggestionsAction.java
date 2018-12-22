package com.mg.datamining.interfaces;

import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceSuggestions;

public interface IDeviceSuggestionsAction extends IDeviceAction {
	void applyAction(Device device, Audit audit, DeviceSuggestions item);
}
