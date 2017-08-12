package com.mg.web.struts.action.user;

import java.util.Collections;
import java.util.List;

import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ShoppingCartDTO;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.mg.web.util.LoginUtils;

public class ShoppingCartUser extends BasicAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private List<ShoppingCartDTO> list;
	
	@Override
	public String execute() {
		try{
			UserSessionDTO userSeeeionDTO = LoginUtils.getUser(request);
			List<ShoppingCart> listSort = ServiceLocator.getService(ShoppingServiceImpl.class).getAllShoppingCartByUser(userSeeeionDTO.getId()); 
			Collections.sort(listSort, ShoppingCart.SHOPPING_CART_COMPARATOR_DATE);
			setList(DTOFactory.getShoppingCartDTOList(listSort, getCurrentLanguage()) );
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public List<ShoppingCartDTO> getList() {
		return list;
	}

	public void setList(List<ShoppingCartDTO> list) {
		this.list = list;
	}

	


}
