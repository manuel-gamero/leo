package com.mg.web.struts.action.collection;

import com.mg.model.CustomComponent;
import com.mg.service.ServiceLocator;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class AddEditCustomComponent extends BasicAction implements
		ModelDriven<CustomComponent>, Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private CustomComponent customComponent = new CustomComponent();
	private Integer id;

	@Override
	public void prepare() throws Exception {
	}

	@Override
	public String execute() {
		try {
			if (id != null) {
				customComponent = ServiceLocator.getService(
						CollectionServiceImpl.class).getCustomComponent(id);
				return INPUT;
			} else {
				return INPUT;
			}
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	@Override
	public CustomComponent getModel() {
		return customComponent;
	}

	public String save(){
		try {
			if (customComponent != null && customComponent.getId() == 0) {
				ServiceLocator.getService(CollectionServiceImpl.class)
						.saveCustomComponent(customComponent);
			} else {
				ServiceLocator.getService(CollectionServiceImpl.class)
						.updateCustomComponent(customComponent);
			}
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public CustomComponent getCustomComponent() {
		return customComponent;
	}

	public void setCustomComponent(CustomComponent customComponent) {
		this.customComponent = customComponent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
