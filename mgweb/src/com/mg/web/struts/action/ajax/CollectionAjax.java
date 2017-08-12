package com.mg.web.struts.action.ajax;

import com.mg.model.CustomComponentCollection;
import com.mg.service.ServiceLocator;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.web.struts.action.BasicListActionSupport;

public class CollectionAjax extends BasicListActionSupport {

	private static final long serialVersionUID = 1L;

	private int ccId;
	private String ccCode;
	private String ccName;
	private String ccTypeCode;
	private int cccId;
	private String cccTypeCode;
	private String cccValue;
	private String imageName;

	public CollectionAjax() {
	}

	public String retrieveCustomComponentCollection() {
		CustomComponentCollection ccc = null;
		try {
			ccc = ServiceLocator.getService(CollectionServiceImpl.class).getCustomComponentCollection(imageName, ccId);
			if(ccc != null){
				setCcId(ccc.getCustomComponent().getId());
				setCcCode(ccc.getCustomComponent().getCode());
				setCcTypeCode(ccc.getCustomComponent().getTypeCode().name());
				setCccId(ccc.getId());
				setCccValue(ccc.getValue());
				setCccTypeCode(ccc.getTypeCode().name());
				setImageName(ccc.getImage().getName());
			}
		} catch (Exception e) {
			managerException(e);
		}
		return SUCCESS;
	}

	public int getCcId() {
		return ccId;
	}

	public void setCcId(int ccId) {
		this.ccId = ccId;
	}

	public String getCcCode() {
		return ccCode;
	}

	public void setCcCode(String ccCode) {
		this.ccCode = ccCode;
	}

	public String getCcName() {
		return ccName;
	}

	public void setCcName(String ccName) {
		this.ccName = ccName;
	}

	public String getCcTypeCode() {
		return ccTypeCode;
	}

	public void setCcTypeCode(String ccTypeCode) {
		this.ccTypeCode = ccTypeCode;
	}

	public int getCccId() {
		return cccId;
	}

	public void setCccId(int cccId) {
		this.cccId = cccId;
	}

	public String getCccTypeCode() {
		return cccTypeCode;
	}

	public void setCccTypeCode(String cccTypeCode) {
		this.cccTypeCode = cccTypeCode;
	}

	public String getCccValue() {
		return cccValue;
	}

	public void setCccValue(String cccValue) {
		this.cccValue = cccValue;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
