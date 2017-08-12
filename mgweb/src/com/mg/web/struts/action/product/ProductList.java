package com.mg.web.struts.action.product;

import java.util.List;

import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.product.ProductServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class ProductList extends BasicAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private List<Product> list;
	
	
	@Override
	public void prepare() {
		try{
		setList(ServiceLocator.getService(ProductServiceImpl.class).getAllProductForAdmin());
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	@Override
	public String execute(){
		return SUCCESS;
	}

	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	


}
