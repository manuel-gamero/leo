package com.mg.dao.impl;

import java.util.List;

import com.mg.model.ProductOrder;

public class ProductOrderDAO extends GenericDaoImpl<ProductOrder> {

	private static final long serialVersionUID = 1L;

	public ProductOrderDAO() {
		super(ProductOrder.class);
	}
	
	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	public List<ProductOrder> getAll(){
		return  findResults(" from ProductOrder po " +
							" join fetch po.product.id " +
							" order by po.order ");
	}
}
