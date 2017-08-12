package com.mg.web.struts.action.image;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Image;
import com.mg.service.ServiceLocator;
import com.mg.service.image.ImageServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class AddEditImage extends BasicAction implements ModelDriven<Image>,
		Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private Image image = new Image();
	private Integer id;

	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		try {
			if (id != null) {
				image = ServiceLocator.getService(ImageServiceImpl.class).getImage(id);
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
	public Image getModel() {
		if (id != null) {
			try {
				image = ServiceLocator.getService(ImageServiceImpl.class)
						.getImage(id);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceLocatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
