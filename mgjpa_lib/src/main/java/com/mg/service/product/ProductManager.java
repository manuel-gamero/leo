package com.mg.service.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Product;
import com.mg.model.ProductOrder;
import com.mg.service.ServiceLocator;

public class ProductManager {
	
	private static final Logger log = Logger.getLogger(ProductManager.class);
	private static List<Product> prodcutSaleList;
	
	public static void initialize() throws ServiceException, ServiceLocatorException{
		log.info("Initializing prodcutSaleList");
		List<ProductOrder> productOrderList = ServiceLocator.getService(ProductServiceImpl.class).getAllProductOrder();
		List<Product> prodcutList = getProductList(productOrderList);
		setProdcutSaleList(prodcutList);
		log.info("prodcutSaleList initialized with " + prodcutList.size() + " products");
	}
	
	public static List<Product> getProductList(List<ProductOrder> productOrderList) throws ServiceException, ServiceLocatorException{
		List<Product> prodcutList = new ArrayList<Product>();
		
		for (ProductOrder productOrder : productOrderList) {
			Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(productOrder.getProduct().getId(), true);
			prodcutList.add(product);			
		}
		return prodcutList;
	}

	public static void reset() throws ServiceException, ServiceLocatorException{
		log.info("Reseting prodcutSaleList");
		initialize();
	}
	
	public synchronized static List<Product> getProdcutSaleList() {
		return prodcutSaleList;
	}

	public synchronized static void setProdcutSaleList(List<Product> prodcutSaleList) {
		ProductManager.prodcutSaleList = prodcutSaleList;
	}
}
