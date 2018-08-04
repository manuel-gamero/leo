package com.mg.datamining.product;

import java.util.ArrayList;
import java.util.List;

import com.mg.datamining.core.GenericMagnitude;
import com.mg.datamining.core.GenericMagnitudeImpl;
import com.mg.datamining.core.GenericMagnitudeVector;
import com.mg.datamining.core.GenericMagnitudeVectorImpl;
import com.mg.datamining.enums.Attribute;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.DeviceProduct;
import com.mg.service.ServiceLocator;
import com.mg.service.device.DeviceServiceImpl;

public class ProductAnalyzerImpl implements ProductAnalyzer{

	@Override
	public GenericMagnitudeVector<Attribute> createMagnitudeVector(Integer productId) throws ServiceException, ServiceLocatorException {
		List<GenericMagnitude<Attribute>> productMagnitudes = new ArrayList<GenericMagnitude<Attribute>>();
		List<DeviceProduct> dpList = ServiceLocator.getService(DeviceServiceImpl.class).getDeviceProduct(productId);
		for (DeviceProduct item : dpList) {
			productMagnitudes.add(new GenericMagnitudeImpl<Attribute>(Attribute.VIEWCOUNTER, item.getCount()));
			productMagnitudes.add(new GenericMagnitudeImpl<Attribute>(Attribute.ADDCOUNTER, item.getAddCount()));
			productMagnitudes.add(new GenericMagnitudeImpl<Attribute>(Attribute.REMOVECOUNTER, item.getRemoveCount()));
			productMagnitudes.add(new GenericMagnitudeImpl<Attribute>(Attribute.SELLCOUNTER, item.getSellCount()));
			productMagnitudes.add(new GenericMagnitudeImpl<Attribute>(Attribute.SHARECOUNTER, item.getShareCount()));
		}
		GenericMagnitudeVector<Attribute> gmv = new GenericMagnitudeVectorImpl<Attribute>(productMagnitudes);
		return gmv;
	}
	
	@Override
	public GenericMagnitudeVector<Attribute> createMagnitudeVector(DeviceProduct deviceProduct) throws ServiceException, ServiceLocatorException {
		List<GenericMagnitude<Attribute>> productMagnitudes = new ArrayList<GenericMagnitude<Attribute>>();
		productMagnitudes.add(new GenericMagnitudeImpl<Attribute>(Attribute.VIEWCOUNTER, deviceProduct.getCount()));
		productMagnitudes.add(new GenericMagnitudeImpl<Attribute>(Attribute.ADDCOUNTER, deviceProduct.getAddCount()));
		productMagnitudes.add(new GenericMagnitudeImpl<Attribute>(Attribute.REMOVECOUNTER, deviceProduct.getRemoveCount()));
		productMagnitudes.add(new GenericMagnitudeImpl<Attribute>(Attribute.SELLCOUNTER, deviceProduct.getSellCount()));
		productMagnitudes.add(new GenericMagnitudeImpl<Attribute>(Attribute.SHARECOUNTER, deviceProduct.getShareCount()));
		GenericMagnitudeVector<Attribute> gmv = new GenericMagnitudeVectorImpl<Attribute>(productMagnitudes);
		return gmv;
	}

	

}
