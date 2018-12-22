package com.mg.datamining.actions;

import com.mg.datamining.helpers.DeviceSuggestionHelper;
import com.mg.datamining.interfaces.IDeviceSuggestionsAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceSuggestions;

public class UserActionSuggestion extends UserActionBasicUrl implements IDeviceSuggestionsAction{

	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException {
		DeviceSuggestionHelper.create(device, audit, this);
	}

	@Override
	public void applyAction(Device device, Audit audit, DeviceSuggestions item) {
		item.setCount( item.getCount() + 1 );
		item.setLastModification(audit.getCreationDate());
	}
}
