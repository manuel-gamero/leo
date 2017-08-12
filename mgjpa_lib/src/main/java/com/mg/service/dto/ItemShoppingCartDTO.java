package com.mg.service.dto;

import java.util.HashSet;
import java.util.Set;

public class ItemShoppingCartDTO extends BasicDTO{
	
	private static final long serialVersionUID = 1L;
	private ProductDTO product;
	private String text;
	private String font;
	private String size;
	private String color;
	private String nameImage;
	private String discount;
	private String total;
	private Set<ItemComponentDTO> itemComponentDTOs = new HashSet<ItemComponentDTO>(0);
	
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Set<ItemComponentDTO> getItemComponentDTOs() {
		return itemComponentDTOs;
	}
	public void setItemComponentDTOs(Set<ItemComponentDTO> itemComponentDTOs) {
		this.itemComponentDTOs = itemComponentDTOs;
	}
	public String getNameImage() {
		return nameImage;
	}
	public void setNameImage(String nameImage) {
		this.nameImage = nameImage;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

}
