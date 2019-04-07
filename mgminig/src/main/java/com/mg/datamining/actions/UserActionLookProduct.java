package com.mg.datamining.actions;

import java.util.List;
import java.util.Optional;

import com.mg.datamining.helpers.DeviceProductHelper;
import com.mg.datamining.helpers.DeviceProductHistHelper;
import com.mg.datamining.helpers.DeviceSuggestionHelper;
import com.mg.datamining.helpers.DeviceUrlHelper;
import com.mg.datamining.interfaces.IDeviceProductAction;
import com.mg.datamining.interfaces.IDeviceProductHistAction;
import com.mg.datamining.interfaces.IDeviceSuggestionsAction;
import com.mg.enums.UserActionType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceProduct;
import com.mg.model.DeviceProductHist;
import com.mg.model.DeviceSuggestions;
import com.mg.util.text.StringUtils;

public class UserActionLookProduct extends UserActionBasicUrl implements IDeviceProductAction, IDeviceProductHistAction, IDeviceSuggestionsAction{

	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException {
		String message = audit.getMessage();
		String productId = StringUtils.getListRegexMatches(message, "product = [0-9]*").get(0).split("=")[1].trim(); 
		DeviceProductHelper.create(device, audit, this, Integer.valueOf(productId));
		DeviceProductHistHelper.create(device, audit, this, Integer.valueOf(productId));
		DeviceUrlHelper.create(device, audit, this, true);
		List<String> productList = StringUtils.getListRegexMatches(message, "product = [0-9]* , custom = [0-9|_]*");
		if( productList.size() > 0 && productList.get(0).split("=") != null ){
			String[] listParser = productList.get(0).split("=");
			if( listParser.length > 2 && listParser[2].length() > 1){ //That means that the user chose the suggestion
				String suggestion = listParser[2].trim(); //When the use click the product suggested the custom contains the value suggestion
				DeviceSuggestionHelper.create(device, audit, this, suggestion + ".png");
			}
		}
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
	
	@Override
	public void applyAction(Device device, Audit audit, DeviceSuggestions item) {
		if(item.getCodeSuggestion() != null ){
			Optional<DeviceProduct> deviceProduct = device.getDeviceProducts().stream().filter(p -> p.getProduct().getId() == Integer.valueOf(item.getCodeSuggestion().split("_")[0])).findFirst();
			if( deviceProduct.isPresent() ){
				if( deviceProduct.get().getCount() > 0 ){//That means that the suggestion works because the user add it to the shopping cart
					item.setCount( item.getCount() + 1 );
					item.setLastModification(audit.getCreationDate());
				}
			}
		}
	}

}
