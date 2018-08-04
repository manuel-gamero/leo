package com.mg.datamining.helpers;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mg.datamining.interfaces.IDeviceComponentAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.CustomComponentCollection;
import com.mg.model.CustomComponentImage;
import com.mg.model.Device;
import com.mg.model.DeviceComponent;
import com.mg.service.ServiceLocator;
import com.mg.service.image.ImageServiceImpl;

public class DeviceCustomComponentHelper {
	
	private static final Logger log = Logger.getLogger(DeviceCustomComponentHelper.class);

	public static void create(Device device, Audit audit, IDeviceComponentAction action, int id) throws ServiceException, ServiceLocatorException{
		if( device.getDeviceComponents() == null || device.getDeviceComponents().size() == 0 ){
			DeviceComponent deviceComponent = createDeviceComponent(device, audit, id);
			if(deviceComponent != null){
				action.applyAction(audit, deviceComponent);
				Set<DeviceComponent> componentSet = new HashSet<DeviceComponent>();
				componentSet.add(deviceComponent);
				device.setDeviceComponents(componentSet);
			}
		}
		else{
			CustomComponentImage customComponentImage = ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImage(id);
			DeviceComponent deviceComponent = getDeviceComponent(device, customComponentImage.getCustomComponentCollection().getId());
			if( deviceComponent == null ){
				deviceComponent = createDeviceComponent(device, audit, id);
				if(deviceComponent != null){
					device.getDeviceComponents().add(deviceComponent);
				}
			}
			action.applyAction(audit, deviceComponent);
		}
	}
	
	private static DeviceComponent getDeviceComponent(Device device, Integer customCompoentCollectionId) {
		Set<DeviceComponent> componentSet = device.getDeviceComponents();
		Iterator<DeviceComponent> iterator = componentSet.iterator();
		boolean found = false;
		DeviceComponent deviceComponent = null;
		while (iterator.hasNext() && !found){
			deviceComponent = iterator.next();
			if(deviceComponent != null && deviceComponent.getCustomComponentCollection() != null){
				if(deviceComponent.getCustomComponentCollection().getId() == customCompoentCollectionId){
					found = true;
				}
			}
		}
		if( found ){
			return deviceComponent;
		}
		else{
			return null;
		}
	}

	private static DeviceComponent createDeviceComponent(Device device, Audit audit, Integer customComponentCollectioId) throws ServiceException, ServiceLocatorException {
		CustomComponentCollection customComponentCollection = null;
		if( customComponentCollectioId > 0){
			CustomComponentImage customComponentImage = ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImage(customComponentCollectioId);
			if(customComponentImage != null){
				customComponentCollection = customComponentImage.getCustomComponentCollection();
			}
			else{
				log.info("CustomComponentImage id not found : " + customComponentCollectioId);
			}
		}
		if(customComponentCollection != null){
			DeviceComponent item = new DeviceComponent();
			item.setDevice(device);
			item.setCustomComponentCollection(customComponentCollection);
			item.setCount(0);
			item.setShareCount(0);
			item.setAddCount(0);
			item.setSellCount(0);
			item.setRemoveCount(0);
			item.setLastModification(audit.getCreationDate());
			item.setCreationDate(new Date());
			return item;
		}
		else{
			log.info("CustomComponentCollection doesn't exist id : " + customComponentCollectioId);
			return null;
		}
	}
}
