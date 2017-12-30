package com.mg.web.struts.action.ajax;

import java.util.ArrayList;
import com.mg.service.dto.ItemDTO;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.mg.web.struts.action.BasicListActionSupport;


public class AutocompleteAjax extends BasicListActionSupport<List<String>> {
	
	private static final long serialVersionUID = 1L;
	protected Logger log = Logger.getLogger(this.getClass());
	private List<ItemDTO> list;
	private String term;
	
	public AutocompleteAjax(){
		super();
	}
	
    
	public String search(){
		list = new ArrayList<ItemDTO>();
		list.add(new ItemDTO("1", "sup1"));
		list.add(new ItemDTO("2", "sup2"));
		list.add(new ItemDTO("3", "sup3"));
		list.add(new ItemDTO("4", "sup4"));
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
