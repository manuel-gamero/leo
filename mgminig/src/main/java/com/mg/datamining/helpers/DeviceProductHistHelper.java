package com.mg.datamining.helpers;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mg.datamining.actions.UserActionBasicCount;
import com.mg.datamining.interfaces.IDeviceProductHistAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceProductHist;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.product.ProductServiceImpl;

public class DeviceProductHistHelper {
	
	private static final Logger log = Logger.getLogger(DeviceProductHistHelper.class);

	public static void create(Device device, Audit audit, IDeviceProductHistAction action, int id) throws ServiceException, ServiceLocatorException{
		if( id > 0){
			DeviceProductHist item = createDeviceItem(device, audit, id);
			if(item.getProduct() != null){
				action.applyAction(audit, item);
				if( device.getDeviceProductHists() == null ){
					Set<DeviceProductHist> itemSet = new HashSet<DeviceProductHist>();
					itemSet.add(item);
					device.setDeviceProductHists(itemSet);
				}
				else{
					device.getDeviceProductHists().add(item);
				}
			}
			else{
				log.warn("Product null " + audit);
			}
		}
	}
	

	private static DeviceProductHist createDeviceItem(Device device, Audit audit, int id) throws ServiceException, ServiceLocatorException {
		Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(id, true);
		DeviceProductHist item = new DeviceProductHist();
		item.setActionDate(audit.getCreationDate());
		item.setProduct(product);
		item.setDevice(device);
		//item.setActionDate(null);
		item.setCreationDate(new Date());
		return item;
	}
}
