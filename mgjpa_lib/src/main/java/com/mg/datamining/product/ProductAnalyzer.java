package com.mg.datamining.product;

import com.mg.datamining.core.GenericMagnitudeVector;
import com.mg.datamining.enums.Attribute;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;

public interface ProductAnalyzer {
	public GenericMagnitudeVector<Attribute> createMagnitudeVector(Integer productId) throws ServiceException, ServiceLocatorException;
}
