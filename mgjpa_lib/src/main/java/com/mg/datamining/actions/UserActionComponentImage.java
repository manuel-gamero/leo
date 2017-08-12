package com.mg.datamining.actions;

import com.mg.datamining.helpers.DeviceCustomComponentHelper;
import com.mg.datamining.helpers.DeviceCustomComponentHistHelper;
import com.mg.datamining.helpers.DeviceUrlHelper;
import com.mg.datamining.interfaces.IDeviceComponentAction;
import com.mg.datamining.interfaces.IDeviceComponentActionHist;
import com.mg.enums.UserActionType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceComponent;
import com.mg.model.DeviceComponentHist;

public class UserActionComponentImage extends UserActionBasicUrl implements IDeviceComponentAction, IDeviceComponentActionHist{

	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException {
		String customComponentCollectionId = audit.getMessage().substring( audit.getMessage().indexOf("=") + 1, audit.getMessage().length()).trim();
		DeviceCustomComponentHelper.create(device, audit, this, Integer.valueOf(customComponentCollectionId));
		DeviceCustomComponentHistHelper.create(device, audit, this, Integer.valueOf(customComponentCollectionId));
		DeviceUrlHelper.create(device, audit, this, false);
	}

	@Override
	public void applyAction(Audit audit, DeviceComponent deviceComponent) {
		deviceComponent.setCount( deviceComponent.getCount() + 1 );
		deviceComponent.setLastModification(audit.getCreationDate());		
	}

	@Override
	public void applyAction(Audit audit, DeviceComponentHist item) {
		item.setUserAction(UserActionType.LOOKED);
		item.setActionDate(audit.getCreationDate());
	}

}
