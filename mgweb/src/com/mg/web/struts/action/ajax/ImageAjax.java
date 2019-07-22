package com.mg.web.struts.action.ajax;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.annotation.Action;
import com.mg.model.CustomComponentImage;
import com.mg.service.ServiceLocator;
import com.mg.service.image.ImageServiceImpl;
import com.mg.util.translation.TranslationUtils;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.BasicListActionSupport;


public class ImageAjax extends BasicListActionSupport<String> {
	
	private static final long serialVersionUID = 1L;
	protected Logger log = LogManager.getLogger(this.getClass());
	private int productId;
    private int componentImageId;
    private String path;
    private String name;
    private String desc;
    
    @Action(value="componentImageId = #componentImageId")
	public String loadImagePath() {	
		path = WebConstants.PRODUCT_BASE_URL;
		try {
			CustomComponentImage customComponentImage = ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImage(componentImageId);
			path = path + productId + "/" + "large" + "/"  + customComponentImage.getImageByImageMaskId().getName();
			name = TranslationUtils.getTranslation(customComponentImage.getCustomComponentCollection().getTranslationByNameTransId(), getCurrentLanguage());
			desc = TranslationUtils.getTranslation(customComponentImage.getCustomComponentCollection().getTranslationByDescriptionTransId(), getCurrentLanguage());
			
		} catch (Exception e) {
			managerException(e, "productId : " + productId + "componentImageId: " + componentImageId);
		}
		return  SUCCESS;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getComponentImageId() {
		return componentImageId;
	}

	public void setComponentImageId(int componentImageId) {
		this.componentImageId = componentImageId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
