package com.mg.service.dto;

import java.util.Date;
import java.util.Set;

import com.mg.model.CustomComponentCollection;
import com.mg.model.CustomComponentText;
import com.mg.model.Image;


public class CustomComponentImageDTO extends BasicDTO {

	private static final long serialVersionUID = 1L;
	private CustomComponentCollection customComponentCollection;
	private Image image;
	private Image imageByImageMaskId;
	private Date creationDate;
	private Set<CustomComponentText> customComponentTexts;
	
	private ImageDTO imageDTO;

	public CustomComponentImageDTO() {
	}

	public CustomComponentImageDTO(int id,
			CustomComponentCollection customComponentCollection, Image image,
			Image imageByImageMaskId) {
		setId(id);
		this.customComponentCollection = customComponentCollection;
		this.image = image;
		this.imageByImageMaskId = imageByImageMaskId;
	}
	
	public CustomComponentCollection getCustomComponentCollection() {
		return customComponentCollection;
	}

	public void setCustomComponentCollection(CustomComponentCollection customComponentCollection) {
		this.customComponentCollection = customComponentCollection;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImageByImageMaskId() {
		return imageByImageMaskId;
	}

	public void setImageByImageMaskId(Image imageByImageMaskId) {
		this.imageByImageMaskId = imageByImageMaskId;
	}

	public Set<CustomComponentText> getCustomComponentTexts() {
		return customComponentTexts;
	}

	public void setCustomComponentTexts(Set<CustomComponentText> customComponentTexts) {
		this.customComponentTexts = customComponentTexts;
	}

	public ImageDTO getImageDTO() {
		return imageDTO;
	}

	public void setImageDTO(ImageDTO imageDTO) {
		this.imageDTO = imageDTO;
	}


}
