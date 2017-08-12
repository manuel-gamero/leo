package com.mg.service.device;

import java.util.List;

import com.mg.exception.ServiceException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceCollection;
import com.mg.model.DeviceComponent;
import com.mg.model.DeviceProduct;
import com.mg.service.Service;

public interface DeviceService extends Service {
	
	Device getDevice(String fingerPrint) throws ServiceException;
	DeviceProduct getDeviceProduct(Integer deviceId, Integer productId) throws ServiceException;
	DeviceCollection getDeviceCollection(Integer deviceId, Integer collectionId) throws ServiceException;
	DeviceComponent getDeviceComponent(Integer deviceId, Integer componentId) throws ServiceException;
	int saveDevice(final Device device) throws ServiceException;
	int saveAuditHist(final Audit auditHist) throws ServiceException;
	DeviceProduct getDeviceProductGroupByProduc( final Integer productId ) throws ServiceException;
	List<DeviceProduct> getDeviceProductGroupByProduc() throws ServiceException;
	List<DeviceProduct> getDeviceProduct(final Integer productId) throws ServiceException;
	
}
