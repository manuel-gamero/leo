package com.mg.datamining.actions;

import org.apache.log4j.Logger;

import com.mg.enums.UserActionParamType;
import com.mg.enums.UserActionType;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceComponent;
import com.mg.model.DeviceComponentHist;
import com.mg.model.DeviceProduct;
import com.mg.model.DeviceProductHist;
import com.mg.model.DeviceSuggestions;

public class UserActionAddProduct extends UserActionBasicCount{

	private static final Logger log = Logger.getLogger(UserActionBasicCount.class);
	
	@Override
	public void applyAction(Audit audit, DeviceProduct item) {
		if(item != null){
			item.setAddCount( item.getAddCount() + 1 ); 
			item.setLastModification(audit.getCreationDate());
		}
		else{
			log.warn(audit);
		}
	}

	@Override
	public void applyAction(Audit audit, DeviceComponentHist item) {
		item.setUserAction(UserActionType.ADDED);	
		item.setActionDate(audit.getCreationDate());
	}

	@Override
	public void applyAction(Audit audit, DeviceProductHist item) {
		String message = audit.getMessage();
		item.setUserAction(UserActionType.ADDED);
		item.setActionDate(audit.getCreationDate());
		getCreateParamValue(item, message, "text = ", "text = [a-zA-Z0-9| ]*", UserActionParamType.TEXT);
		getCreateParamValue(item, message, "color = ", "color = #[a-z|A-Z|0-9]*", UserActionParamType.COLOR);
		getCreateParamValue(item, message, "font = ", "font = [a-z|A-Z|0-9]*", UserActionParamType.FONT);
	}

	@Override
	public void applyAction(Audit audit, DeviceComponent item) {
		item.setAddCount( item.getAddCount() + 1 );
		item.setLastModification(audit.getCreationDate());
		
	}

	@Override
	public void applyAction(Device device, Audit audit, DeviceSuggestions item) {
		item.setAddCount( item.getAddCount() + 1 );
		item.setLastModification(audit.getCreationDate());
	}
}
