package com.mg.web.struts.action.collection;

import java.util.List;

import com.mg.model.CustomComponent;
import com.mg.service.ServiceLocator;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class CustomComponentList extends BasicAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private List<CustomComponent> list;
	
	
	@Override
	public void prepare(){
		try{
			setList(ServiceLocator.getService(CollectionServiceImpl.class).getAllCustomComponent());
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	@Override
	public String execute(){
		return SUCCESS;
	}

	public List<CustomComponent> getList() {
		return list;
	}

	public void setList(List<CustomComponent> list) {
		this.list = list;
	}

	


}
