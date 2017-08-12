package com.mg.service.dto;

import java.io.File;
import java.util.Date;

import com.mg.enums.ComponentAttributeType;
import com.mg.model.Collection;
import com.mg.model.CustomComponent;
import com.mg.model.Image;


public class CustomComponentCollectionDTO extends BasicDTO {

	private static final long serialVersionUID = 1L;
	private CustomComponent customComponent;
	private Collection collection;
	private ComponentAttributeType typeCode;
	private String value;
	private Date creationDate;
	private Image image;
	private File file;
	private String fileContentType;
	private String fileFileName;
	private int customComponentImageId;
	private String name;
	private String description;

	public CustomComponentCollectionDTO() {
	}

	public CustomComponentCollectionDTO(int id,
			CustomComponent customComponent, Collection collection,
			ComponentAttributeType typeCode, String value) {
		setId(id);
		this.customComponent = customComponent;
		this.collection = collection;
		this.typeCode = typeCode;
		this.value = value;
	}

	public CustomComponentCollectionDTO(int id,
			CustomComponent customComponent, Image image,
			Collection collection, ComponentAttributeType typeCode, String value,
			Date creationDate) {
		setId(id);
		this.customComponent = customComponent;
		this.image =image;
		this.collection = collection;
		this.typeCode = typeCode;
		this.value = value;
		this.creationDate = creationDate;
	}

	public CustomComponent getCustomComponent() {
		return customComponent;
	}

	public void setCustomComponent(CustomComponent customComponent) {
		this.customComponent = customComponent;
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public ComponentAttributeType getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(ComponentAttributeType typeCode) {
		this.typeCode = typeCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public int getCustomComponentImageId() {
		return customComponentImageId;
	}

	public void setCustomComponentImageId(int customComponentImageId) {
		this.customComponentImageId = customComponentImageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
