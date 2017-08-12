package com.mg.web.struts.action.shopping;

import java.util.List;

import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.MethodShippingItemDTO;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class MethodShippingList extends BasicAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private List<MethodShippingItemDTO> methodShippingList;
	
	
	@Override
	public void prepare(){
		try{
			setMethodShippingList(DTOFactory.getMethodShippingItemDTOList(ServiceLocator.getService(ShoppingServiceImpl.class).getAllMethodShipping()));
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	@Override
	public String execute(){
		return SUCCESS;
	}

	public List<MethodShippingItemDTO> getMethodShippingList() {
		return methodShippingList;
	}

	public void setMethodShippingList(List<MethodShippingItemDTO> methodShippingList) {
		this.methodShippingList = methodShippingList;
	}



}

