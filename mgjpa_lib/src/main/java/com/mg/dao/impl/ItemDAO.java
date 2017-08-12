package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.model.Item;

public class ItemDAO extends GenericDaoImpl<Item> {
	
	private static final long serialVersionUID = 1L;

	public ItemDAO() {
		super(Item.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public List<Item> getListItemByProduct(Integer productId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", productId);    

		return  findResults(" select i.product.id, i.nameImage, count(*) " +
							  " from Item i " +
							  " where i.product.id = :id " +
							  " group by i.product.id, i.nameImage ", parameters);
	}


}
