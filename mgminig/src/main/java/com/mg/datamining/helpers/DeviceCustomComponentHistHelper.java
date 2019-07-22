package com.mg.datamining.helpers;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.datamining.interfaces.IDeviceComponentActionHist;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.CustomComponentCollection;
import com.mg.model.CustomComponentImage;
import com.mg.model.Device;
import com.mg.model.DeviceComponentHist;
import com.mg.service.ServiceLocator;
import com.mg.service.image.ImageServiceImpl;

public class DeviceCustomComponentHistHelper {

	private static Logger log = LogManager.getLogger(DeviceCustomComponentHistHelper.class);
	
	public static void create(Device device, Audit audit, IDeviceComponentActionHist action, int id) throws ServiceException, ServiceLocatorException{
		if(id > 0)
		{
			DeviceComponentHist item = createDeviceItem(device, audit, id);
			if ( item != null ){
				action.applyAction(audit, item);
				if( device.getDeviceComponentHists() == null ){
					Set<DeviceComponentHist> itemSet = new HashSet<DeviceComponentHist>();
					itemSet.add(item);
					device.setDeviceComponentHists(itemSet);
				}
				else{
					device.getDeviceComponentHists().add(item);
				}
			}
			else{
				log.warn(audit.getMessage() + " NO generate customComponentHist row");
			}
		}
	}
	

	private static DeviceComponentHist createDeviceItem(Device device, Audit audit, int id) throws ServiceException, ServiceLocatorException {
		if(id > 0){
			CustomComponentImage customComponentImage = ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImage(id);
			if (customComponentImage != null ){
				CustomComponentCollection customComponentCollection = customComponentImage.getCustomComponentCollection();
				DeviceComponentHist item = new DeviceComponentHist();
				item.setActionDate(audit.getCreationDate());
				item.setCustomComponentCollection(customComponentCollection);
				item.setDevice(device);
				item.setUserAction(null);
				item.setCreationDate(new Date());
				return item;
			}
			else{
				log.warn("No customComponentImage with id : " + id );
				return null;
			}
		}
		else{
			log.warn("No customComponentImage with id : " + id );
			return null;
		}
	}
}
