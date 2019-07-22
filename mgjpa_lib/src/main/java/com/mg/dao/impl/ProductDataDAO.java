package com.mg.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mg.model.ProductData;

public class ProductDataDAO extends GenericDaoImpl<ProductData> {
	
	private static final long serialVersionUID = 1L;

	public ProductDataDAO() {
		super(ProductData.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	public void deleteProductData(){
		updateQuery(" delete from ProductData pd ");
	}
}
