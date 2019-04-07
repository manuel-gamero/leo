package com.mg.service.datamining;

import java.util.List;

import com.mg.datamining.clustering.ItemCluster;
import com.mg.datamining.enums.Attribute;
import com.mg.exception.ServiceException;
import com.mg.model.Product;
import com.mg.service.Service;

public interface DataminigService extends Service {
	void saveDataminingProduct(List<ItemCluster<Product,Attribute>> itemClusterList) throws ServiceException;
}
