package com.mg.datamining.actions;

import com.mg.datamining.helpers.DeviceProductHelper;
import com.mg.datamining.helpers.DeviceProductHistHelper;
import com.mg.datamining.helpers.DeviceUrlHelper;
import com.mg.datamining.interfaces.IDeviceProductAction;
import com.mg.datamining.interfaces.IDeviceProductHistAction;
import com.mg.enums.UserActionType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceProduct;
import com.mg.model.DeviceProductHist;
import com.mg.util.text.StringUtils;

public class UserActionLookProduct extends UserActionBasicUrl implements IDeviceProductAction, IDeviceProductHistAction{

	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException {
		String message = audit.getMessage();
		String productId = StringUtils.getListRegexMatches(message, "product = [0-9]*").get(0).split("=")[1].trim(); 
		DeviceProductHelper.create(device, audit, this, Integer.valueOf(productId));
		DeviceProductHistHelper.create(device, audit, this, Integer.valueOf(productId));
		DeviceUrlHelper.create(device, audit, this, true);
	}

	@Override
	public void applyAction(Audit audit, DeviceProduct item) {
		item.setCount( item.getCount() + 1 );
		item.setLastModification(audit.getCreationDate());
	}

	@Override
	public void applyAction(Audit audit, DeviceProductHist item) {
		item.setUserAction(UserActionType.LOOKED);
		item.setActionDate(audit.getCreationDate());		
	}

}
