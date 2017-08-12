package com.mg.web.struts.action.collection;

import java.util.List;

import com.mg.model.Collection;
import com.mg.service.ServiceLocator;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class CollectionList extends BasicAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private List<Collection> list;

	@Override
	public void prepare() {
		try {
			setList(ServiceLocator.getService(CollectionServiceImpl.class).getAllCollection());
		} catch (Exception e) {
			managerException(e);
		}
	}

	@Override
	public String execute(){
		return SUCCESS;
	}

	public List<Collection> getList() {
		return list;
	}

	public void setList(List<Collection> list) {
		this.list = list;
	}

}
