package com.mg.service.dto;

import java.util.Comparator;


/**
 * Contains basic ProductImage information 
 * 
 * @author MJGP
 *
 */
public class ProductImageDTO extends BasicDTO {
		
	private static final long serialVersionUID = 1L;
	private String nameImage;
	private Integer height;
	private Integer width;
	
	public static final Comparator<ProductImageDTO> IMAGE_ORDER_WITDH = new Comparator<ProductImageDTO>() {
		public int compare(ProductImageDTO item1, ProductImageDTO item2) {
			return item2.getWidth().compareTo(item1.getWidth());
		}
	};

	public String getNameImage() {
		return nameImage;
	}

	public void setNameImage(String nameImage) {
		this.nameImage = nameImage;
	}
	
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

}
