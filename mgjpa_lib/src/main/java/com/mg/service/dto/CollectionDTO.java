package com.mg.service.dto;

import java.util.Set;

import com.mg.enums.CollectionStatus;
import com.mg.model.CustomComponentCollection;
import com.mg.model.Image;

public class CollectionDTO extends BasicDTO{
	
	private static final long serialVersionUID = 6268999732248769088L;
	private String description;
	private String name;
	private Image image;
	private CollectionStatus statusCode;
	private String url;
	private Set<CustomComponentCollection> customComponentCollections;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public CollectionStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(CollectionStatus statusCode) {
		this.statusCode = statusCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Set<CustomComponentCollection> getCustomComponentCollections() {
		return customComponentCollections;
	}
	public void setCustomComponentCollections(
			Set<CustomComponentCollection> customComponentCollections) {
		this.customComponentCollections = customComponentCollections;
	}

}

