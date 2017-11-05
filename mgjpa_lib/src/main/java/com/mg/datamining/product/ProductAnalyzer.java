package com.mg.datamining.product;

import com.mg.datamining.core.GenericMagnitudeVector;
import com.mg.datamining.enums.Attribute;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.DeviceProduct;

public interface ProductAnalyzer {
	public GenericMagnitudeVector<Attribute> createMagnitudeVector(Integer productId) throws ServiceException, ServiceLocatorException;
	public GenericMagnitudeVector<Attribute> createMagnitudeVector(DeviceProduct deviceProduct) throws ServiceException, ServiceLocatorException;
}
