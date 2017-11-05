package com.mg.datamining.product;

import java.util.ArrayList;
import java.util.List;

import com.mg.datamining.clustering.DataItem;
import com.mg.datamining.clustering.DataItemImpl;
import com.mg.datamining.clustering.DataSetCreator;
import com.mg.datamining.core.GenericMagnitudeVector;
import com.mg.datamining.enums.Attribute;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.DeviceProduct;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.device.DeviceServiceImpl;

public class ProductDataSetCreatorImpl implements DataSetCreator<Product,Attribute> {

	@Override
	public List<DataItem<Product,Attribute>> createLearningData() throws Exception {
		return getProductMagnitudeVectors();
	}

	private List<DataItem<Product,Attribute>> getProductMagnitudeVectors() throws ServiceException, ServiceLocatorException {
		List<DataItem<Product,Attribute>> result = new ArrayList<DataItem<Product,Attribute>>();
		List<DeviceProduct> dpList = ServiceLocator.getService(DeviceServiceImpl.class).getDeviceProductGroupByProduc();
		//List<Product> productList = ServiceLocator.getService(ProductServiceImpl.class).getAllProduct();
		ProductAnalyzer productAnalyzer = new ProductAnalyzerImpl();
		for (DeviceProduct item : dpList) {
			GenericMagnitudeVector<Attribute> pmv = productAnalyzer.createMagnitudeVector(item);
			result.add( new DataItemImpl<Product, Attribute>(item.getProduct(), pmv) );
		}
		return result;
	}

}
