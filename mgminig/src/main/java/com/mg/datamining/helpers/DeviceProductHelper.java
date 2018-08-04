package com.mg.datamining.helpers;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mg.datamining.interfaces.IDeviceProductAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceProduct;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.product.ProductServiceImpl;

public class DeviceProductHelper {

	private static final Logger log = Logger.getLogger(DeviceProductHelper.class);
	
	public static void create(Device device, Audit audit, IDeviceProductAction action, int id) throws ServiceException, ServiceLocatorException{
		if( device.getDeviceProducts() == null || device.getDeviceProducts().size() == 0 ){
			DeviceProduct item = createDeviceProduct(device, audit, id);
			if(item != null){
				action.applyAction(audit, item);
				Set<DeviceProduct> productSet = new HashSet<DeviceProduct>();
				productSet.add(item);
				device.setDeviceProducts(productSet);
			}
		}
		else{
			DeviceProduct item = getDeviceProduct(device, id );
			if( item == null ){
				item = createDeviceProduct(device, audit, id);
				if(item != null){
					device.getDeviceProducts().add(item);
				}
			}
			action.applyAction(audit, item);
		}
	}
	
	private static DeviceProduct getDeviceProduct(Device device, Integer id) {
		Set<DeviceProduct> componentSet = device.getDeviceProducts();
		Iterator<DeviceProduct> iterator = componentSet.iterator();
		boolean found = false;
		DeviceProduct item = null;
		while (iterator.hasNext() && !found){
			item = iterator.next();
			if(item.getProduct().getId() == id){
				found = true;
			}
		}
		if( found ){
			return item;
		}
		else{
			return null;
		}
	}

	private static DeviceProduct createDeviceProduct(Device device, Audit audit, Integer id) throws ServiceException, ServiceLocatorException {
		if( id > 0){
			Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(id, true);
			if(product != null){
				DeviceProduct item = new DeviceProduct();
				item.setProduct(product);
				item.setDevice(device);
				item.setAddCount(0);
				item.setCount(0);
				item.setSellCount(0);
				item.setShareCount(0);
				item.setRemoveCount(0);
				item.setLastModification(audit.getCreationDate());
				item.setCreationDate(new Date());
				return item;
			}
			else{
				log.info("Product doesn't exist id: " + id);
				return null;
			}
		}
		else{
			log.info("Product doesn't exist id: " + id);
			return null;
		}
	}
}
