package com.mg.web.struts.action.shopping;

import java.util.List;

import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.struts.action.BasicAction;

public class ShoppingCartList extends BasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private List<ShoppingCart> list;
	
	@Override
	public String execute() {
		try{
			setList(ServiceLocator.getService(ShoppingServiceImpl.class).getAllShoppingCart());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			managerException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public List<ShoppingCart> getList() {
		return list;
	}

	public void setList(List<ShoppingCart> list) {
		this.list = list;
	}

	


}
