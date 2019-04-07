package com.mg.dao.impl;

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
}
