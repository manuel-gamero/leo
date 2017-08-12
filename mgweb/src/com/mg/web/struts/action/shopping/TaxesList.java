package com.mg.web.struts.action.shopping;

import java.util.List;

import com.mg.model.Taxes;
import com.mg.service.ServiceLocator;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class TaxesList extends BasicAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private List<Taxes> taxesList;
	
	
	@Override
	public void prepare(){
		try{
			setTaxesList(ServiceLocator.getService(ShoppingServiceImpl.class).getAllTaxes());
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	@Override
	public String execute() {
		return SUCCESS;
	}

	public List<Taxes> getTaxesList() {
		return taxesList;
	}

	public void setTaxesList(List<Taxes> taxesList) {
		this.taxesList = taxesList;
	}
	


}
