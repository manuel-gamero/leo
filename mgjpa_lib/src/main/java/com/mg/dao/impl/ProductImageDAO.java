package com.mg.dao.impl;

import com.mg.model.ProductImage;

public class ProductImageDAO extends GenericDaoImpl<ProductImage> {

	private static final long serialVersionUID = 1L;

	public ProductImageDAO() {
		super(ProductImage.class);
	}
	
	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
}
