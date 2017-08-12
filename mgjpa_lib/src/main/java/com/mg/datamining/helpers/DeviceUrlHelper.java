package com.mg.datamining.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mg.datamining.interfaces.IDeviceUrlAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceUrl;

public class DeviceUrlHelper {

	public static void create(Device device, Audit audit, IDeviceUrlAction action, boolean insert) throws ServiceException, ServiceLocatorException{
		DeviceUrl item = createDeviceItem(device, audit);
		//This action will calculate the duration
		//But that applies the item before in the url list
		action.applyAction(device, audit, item);
		if( device.getDeviceUrls() == null ){
			List<DeviceUrl> itemSet = new ArrayList<DeviceUrl>();
			device.setDeviceUrls(itemSet);
		}
		//Because there is url that modify the duration but I don´t want to save them in this table
		if(insert){
			device.getDeviceUrls().add(device.getDeviceUrls().size(), item);
		}
	}
	

	private static DeviceUrl createDeviceItem(Device device, Audit audit) throws ServiceException, ServiceLocatorException {
		DeviceUrl item = new DeviceUrl();
		item.setDevice(device);
		item.setActionDate(audit.getCreationDate());
		item.setCreationDate(new Date());
		if(audit.getUrl() != null){
			if(audit.getUrl().length() > 200){
				item.setUrlFrom(audit.getUrl().substring(0, 199));
			}
			else{
				item.setUrlFrom(audit.getUrl());
			}
		}
		String url;
		if(audit.getMessage() != null){
			url = audit.getActionUser().replace(".execute", "") + " " + audit.getMessage();
		}
		else{
			url = audit.getActionUser().replace(".execute", "");
		}
		if(url.length() > 200){
			url = url.substring(0, 199);
		}
		item.setUrl(url);
		item.setAjax(isAjaxCall(audit));
		item.setDuration(0L);
		return item;
	}


	private static Boolean isAjaxCall(Audit audit) {
		if(audit.getActionUser().toLowerCase().contains("ajax")){
			return true;
		}
		return false;
	}
}
