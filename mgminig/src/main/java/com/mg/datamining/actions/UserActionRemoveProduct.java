package com.mg.datamining.actions;

import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.datamining.helpers.DeviceCustomComponentHelper;
import com.mg.datamining.helpers.DeviceCustomComponentHistHelper;
import com.mg.datamining.helpers.DeviceProductHelper;
import com.mg.datamining.helpers.DeviceProductHistHelper;
import com.mg.datamining.helpers.DeviceSuggestionHelper;
import com.mg.enums.UserActionType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceComponent;
import com.mg.model.DeviceComponentHist;
import com.mg.model.DeviceProduct;
import com.mg.model.DeviceProductHist;
import com.mg.model.DeviceSuggestions;
import com.mg.util.text.StringUtils;

public class UserActionRemoveProduct extends UserActionBasicCount {
	
	private static Logger log = LogManager.getLogger(UserActionRemoveProduct.class);
	
	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException {
		String message = audit.getMessage();
		List<String> stringList =StringUtils.getListRegexMatches(message, "product = [ ]*[0-9]*");
		if (stringList.size() > 0){
			String productId = stringList.get(0).split("=")[1].trim();
			List<String> stringCCCList = StringUtils.getListRegexMatches(message, "customComponentCollection = [0-9|_]*");
			if (stringCCCList.size() > 0){
				String[] customComponentCollectionList = stringCCCList.get(0).split("_");
				if(productId != null && productId != "" && StringUtils.isNumber(productId) ){
					DeviceProductHelper.create(device, audit, this, Integer.valueOf(productId));
					DeviceProductHistHelper.create(device, audit, this, Integer.valueOf(productId));
					//Update Suggestion
					DeviceSuggestionHelper.update(device, audit, this, Integer.valueOf(productId));
				}
				else{
					log.warn(productId + " is NOT a Product number");
				}
				if(customComponentCollectionList != null){
					for (String customComponentId : customComponentCollectionList) {
						if(StringUtils.isNumber(customComponentId) ){
							DeviceCustomComponentHelper.create(device, audit, this, Integer.valueOf(customComponentId));
							DeviceCustomComponentHistHelper.create(device, audit, this, Integer.valueOf(customComponentId));
						}
						else{
							log.warn(customComponentId + " is NOT a customComponet number " + audit);
						}
					}
				}
			}
			else{
				log.warn(" There is NOT customComponentCollection : " + message + audit);
			}
		}
		else{
			log.warn(" There is NOT Product : " + message + audit);
		}
	}
	
	@Override
	public void applyAction(Audit audit, DeviceProduct item) {
		item.setRemoveCount( item.getRemoveCount() + 1 ); 
		item.setLastModification(audit.getCreationDate());
	}

	@Override
	public void applyAction(Audit audit, DeviceComponentHist item) {
		item.setUserAction(UserActionType.REMOVED);	
		item.setActionDate(audit.getCreationDate());
	}

	@Override
	public void applyAction(Audit audit, DeviceProductHist item) {
		item.setUserAction(UserActionType.REMOVED);
		item.setActionDate(audit.getCreationDate());
	}

	@Override
	public void applyAction(Audit audit, DeviceComponent item) {
		item.setRemoveCount( item.getRemoveCount() + 1 ); 
		item.setLastModification(audit.getCreationDate());		
	}

	@Override
	public void applyAction(Device device, Audit audit, DeviceSuggestions item) {
		item.setRemoveCount( item.getRemoveCount() + 1 );
		item.setLastModification(audit.getCreationDate());
	}

}
