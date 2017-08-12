package com.mg.service.dto;

import java.util.Date;

import com.mg.enums.ShoppingCartItemStatus;

public class ShoppingCartItemDTO extends BasicDTO {

	private static final long serialVersionUID = 2022199278233613739L;
	private int id;
	private String productName;
	private String text;
	private String font;
	private String size;
	private String color;
	private String nameImage;
	private ShoppingCartItemStatus statusCode;
	private String price;
	private Integer quantity;
	private String discount;
	private Date creationDate;
	private String urlImage;

	public ShoppingCartItemDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getNameImage() {
		return nameImage;
	}

	public void setNameImage(String nameImage) {
		this.nameImage = nameImage;
	}

	public ShoppingCartItemStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(ShoppingCartItemStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	
}
