package com.mg.service.dto;


/**
 * Contains basic item information 
 * 
 * @author MJGP
 *
 */
public class ItemViewDTO extends BasicDTO {
		
	private static final long serialVersionUID = 1L;
	
	private String nameImage;
	private String itemName;
	private String price;
	private String url;
	private Boolean newProduct;
	public Boolean getNewProduct() {
		return newProduct;
	}
	public void setNewProduct(Boolean newProduct) {
		this.newProduct = newProduct;
	}
	public String getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}
	public boolean isHasDiscount() {
		return hasDiscount;
	}
	public void setHasDiscount(boolean hasDiscount) {
		this.hasDiscount = hasDiscount;
	}
	private String oldPrice;
	private boolean hasDiscount;
	
	public String getNameImage() {
		return nameImage;
	}
	public void setNameImage(String nameImage) {
		this.nameImage = nameImage;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
 