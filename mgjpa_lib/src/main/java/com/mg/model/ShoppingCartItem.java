package com.mg.model;

// Generated 07-may-2015 22:48:42 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mg.enums.ShoppingCartItemStatus;

/**
 * MgShoppingCartItem generated by hbm2java
 */
@Entity
@Table(name = "mg_shopping_cart_item", schema = "bolsos")
public class ShoppingCartItem implements java.io.Serializable {

	private static final long serialVersionUID = 2022199278233613739L;
	private int id;
	private Item item;
	private ShoppingCart shoppingCart;
	private ShoppingCartItemStatus statusCode;
	private BigDecimal price;
	private Integer quantity;
	private String discount;
	private Date creationDate;
	
	public ShoppingCartItem() {
	}

	public ShoppingCartItem(int id, Item item,
			ShoppingCart shoppingCart, ShoppingCartItemStatus statusCode, BigDecimal price) {
		this.id = id;
		this.item = item;
		this.shoppingCart = shoppingCart;
		this.statusCode = statusCode;
		this.price = price;
	}

	public ShoppingCartItem(int id, Item item,
			ShoppingCart shoppingCart, ShoppingCartItemStatus statusCode, BigDecimal price,
			Integer quantity, String discount, Date creationDate) {
		this.id = id;
		this.item = item;
		this.shoppingCart = shoppingCart;
		this.statusCode = statusCode;
		this.price = price;
		this.quantity = quantity;
		this.discount = discount;
		this.creationDate = creationDate;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_shopping_cart_item_id")
	@SequenceGenerator(name="seq_shopping_cart_item_id", sequenceName="bolsos.seq_shopping_cart_item_id", initialValue=1, allocationSize= 1 )
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "item_id", nullable = false)
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shopping_cart_id", nullable = false)
	public ShoppingCart getShoppingCart() {
		return this.shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@Column(name = "status_code", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	public ShoppingCartItemStatus getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(ShoppingCartItemStatus statusCode) {
		this.statusCode = statusCode;
	}

	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "quantity")
	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "discount", length = 15)
	public String getDiscount() {
		return this.discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, length = 29)
	public Date getCreationDate() {
		if(this.creationDate == null){
			return new Date();
		}
		else{
			return this.creationDate;
		}
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
