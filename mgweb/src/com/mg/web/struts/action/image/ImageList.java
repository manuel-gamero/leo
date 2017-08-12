package com.mg.web.struts.action.image;

import java.util.List;

import com.mg.model.Image;
import com.mg.service.ServiceLocator;
import com.mg.service.image.ImageServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class ImageList extends BasicAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private List<Image> list;

	@Override
	public void prepare() {
		try {
			setList(ServiceLocator.getService(ImageServiceImpl.class).getAllImage());
		} catch (Exception e) {
			managerException(e);
		}
	}

	@Override
	public String execute() {
		return SUCCESS;
	}

	public List<Image> getList() {
		return list;
	}

	public void setList(List<Image> list) {
		this.list = list;
	}

}
