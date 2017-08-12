package com.mg.web.struts.action.shopping;

import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ShoppingCartDTO;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class AddEditShoppingCart extends BasicAction implements ModelDriven<ShoppingCartDTO>, Preparable {
	
	private static final long serialVersionUID = 4715983239493644096L;
	private ShoppingCartDTO shoppingCartDTO;
	private Integer id;
	
	
	@Override
	public void prepare(){
		try{
			if(id != null){
				shoppingCartDTO = DTOFactory.getShoppingCartDTO(ServiceLocator.getService(ShoppingServiceImpl.class).getShoppingCartById(id), getCurrentLanguage(), true);
			}
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	@Override
	public String execute(){
		return INPUT;
	}

	@Override
	public ShoppingCartDTO getModel() {
		return shoppingCartDTO;
	}
	
	public String save(){
		try{
			com.mg.model.ShoppingCart shoppingCart = ServiceLocator.getService(ShoppingServiceImpl.class).getShoppingCartById(id);
			shoppingCart.setComment(shoppingCartDTO.getComment());
			shoppingCart.setShippingDate(shoppingCartDTO.getShippingDate());
			shoppingCart.setTrackNumber(shoppingCartDTO.getTrackNumber());
			shoppingCart.setStatusCode(shoppingCartDTO.getStatusCode());
			ServiceLocator.getService(ShoppingServiceImpl.class).updateShoppingCart(shoppingCart);
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
