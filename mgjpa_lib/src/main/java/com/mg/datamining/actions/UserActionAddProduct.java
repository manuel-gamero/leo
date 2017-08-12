package com.mg.datamining.actions;

import com.mg.enums.UserActionParamType;
import com.mg.enums.UserActionType;
import com.mg.model.Audit;
import com.mg.model.DeviceComponent;
import com.mg.model.DeviceComponentHist;
import com.mg.model.DeviceProduct;
import com.mg.model.DeviceProductHist;

public class UserActionAddProduct extends UserActionBasicCount {
	
	@Override
	public void applyAction(Audit audit, DeviceProduct item) {
		item.setAddCount( item.getAddCount() + 1 ); 
		item.setLastModification(audit.getCreationDate());
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

}
