package com.mg.web.struts.action.ajax;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.annotation.Action;
import com.mg.model.Item;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.util.translation.TranslationUtils;
import com.mg.web.struts.action.BasicListActionSupport;


public class SocialImageAjax extends BasicListActionSupport<String> {
	
	private static final long serialVersionUID = 1L;
	protected Logger log = LogManager.getLogger(this.getClass());
	private int id;
    private Float xpos;
    private Float ypos;
    private String customComponentCollection;
    private String color;
    private String text;
    private String size;
    private String font;
    private String name;
    private String picture;
    private String caption;
    private String description;
    
    @Action(value="customComponentCollection = #customComponentCollection , text = #text, color = #color, font = #font ")
	public String saveSocialImage() {	
		try {
			Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(id, true);
			List<String> cccList = getLsit(customComponentCollection);
			Item item = new Item();
			ServiceLocator.getService(ImageServiceImpl.class).getItemPNG( item, product, cccList );
			item.setColor(color);
			item.setFont(font);
			item.setSize(size);
			item.setText(text);
			ServiceLocator.getService(ImageServiceImpl.class).generatePNGImage( item, true);
			name = "L'atelier de Leo";
			picture = getText("url.web") + ServiceLocator.getService(ConfigServiceImpl.class).getWebImageSocial() + item.getNameImage();
			caption = TranslationUtils.getTranslation(product.getTranslationByNameTransId(), getCurrentLanguage());
			description = TranslationUtils.getTranslation(product.getTranslationByDescriptionTransId(), getCurrentLanguage());
			log.info(product.getId());
		} catch (Exception e) {
			managerException(e, "productId : " + id + "customComponentCollection: " + 
								 customComponentCollection + " color: " + color + " text: " + text +
								 " size: " + size + " font: " + font);
		}
		return  SUCCESS;
	}

	private List<String> getLsit(String customComponentCollection) {
		return Arrays.asList(customComponentCollection.split(","));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getCustomComponentCollection() {
		return customComponentCollection;
	}

	public void setCustomComponentCollection(
			String customComponentCollection) {
		this.customComponentCollection = customComponentCollection;
	}

	public Float getXpos() {
		return xpos;
	}

	public void setXpos(Float xpos) {
		this.xpos = xpos;
	}

	public Float getYpos() {
		return ypos;
	}

	public void setYpos(Float ypos) {
		this.ypos = ypos;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
