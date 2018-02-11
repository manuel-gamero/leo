package com.mg.web.struts.action.ajax;

import java.util.List;

import org.apache.log4j.Logger;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ItemDTO;
import com.mg.service.search.SearchServiceImpl;
import com.mg.web.struts.action.BasicListActionSupport;


public class AutocompleteAjax extends BasicListActionSupport<List<String>> {
	
	private static final long serialVersionUID = 1L;
	protected Logger log = Logger.getLogger(this.getClass());
	private List<ItemDTO> list;
	private String term;
	
	public AutocompleteAjax(){
		super();
	}
	
    
	public String search() throws ServiceException, ServiceLocatorException{
		List<Product> listProduct = ServiceLocator.getService(SearchServiceImpl.class).searchProduct(term);
		list = DTOFactory.getItemDTO(listProduct, getCurrentLanguage());
		log.debug("size : " + listProduct.size());
		return SUCCESS;
	}


	public String getTerm() {
		return term;
	}


	public void setTerm(String term) {
		this.term = term;
	}


	public List<ItemDTO> getList() {
		return list;
	}


	public void setList(List<ItemDTO> list) {
		this.list = list;
	}
	
}
