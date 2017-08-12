package com.mg.service.dto;

// Generated 29-mar-2015 3:39:16 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class CustomComponentTextDTO extends BasicDTO {

	private static final long serialVersionUID = 1L;
	private Integer posTop;
	private Integer posLeft;
	private Integer width;
	private Integer height;
	private Integer imageWidth;
	private Integer imageHeight;
	private String align;
	private Integer maximum;
	private Date creationDate;

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public Integer getMaximum() {
		return maximum;
	}

	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public CustomComponentTextDTO() {
	}

	public Integer getPosTop() {
		return posTop;
	}

	public void setPosTop(Integer posTop) {
		this.posTop = posTop;
	}

	public Integer getPosLeft() {
		return posLeft;
	}

	public void setPosLeft(Integer posLeft) {
		this.posLeft = posLeft;
	}


}
