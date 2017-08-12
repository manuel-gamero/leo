package com.mg.web.struts.action.shopping;

import com.mg.enums.Country;
import com.mg.enums.Province;
import com.mg.model.Taxes;
import com.mg.service.ServiceLocator;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.struts.action.BasicAction;

public class AddEditTaxes extends BasicAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private Integer id;
	private Taxes taxe;
	private String url;
	private Province province;
	private Country country;
	
	@Override
	public String execute() {
		try{
			if(id != null){
				taxe = ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes(id);
				setProvince( Province.valueOf( taxe.getCode() ));
				setCountry( province.getCountry() );
			}
			return INPUT;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String save(){
		try{
			if(id != null){
				taxe.setId(id);
				ServiceLocator.getService(ShoppingServiceImpl.class).updateTaxes(taxe);
			}
			else{
				ServiceLocator.getService(ShoppingServiceImpl.class).saveTaxes(taxe);
			}
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String remove(){
		try{
			if(id != null){
				taxe = ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes(id); 
				ServiceLocator.getService(ShoppingServiceImpl.class).deleteTaxes(taxe);
			}
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

	public Taxes getTaxe() {
		return taxe;
	}

	public void setTaxe(Taxes taxe) {
		this.taxe = taxe;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
