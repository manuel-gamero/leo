package com.mg.service.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mg.enums.PaymentMethodType;
import com.mg.enums.ShoppingCartStatus;
import com.mg.model.MethodShipping;


public class ShoppingCartDTO extends BasicDTO{

	private static final long serialVersionUID = 8168541433819670846L;
	private int id;
	private String users;
	private MethodShipping methodShipping;
	private String transaction;
	private ShoppingCartStatus statusCode;
	private String total;
	private String subTotal;
	private String shippingFees;
	private String taxes;
	private String shippingAddressId;
	private UserAddressDTO shippingAddress;
	private String trackNumber;
	private PaymentMethodType paymentMethod;
	private Date purchaseDate;
	private Date sendDate;
	private Date shippingDate;
	private String comment;
	private String commentUser;
	private String creationDate;
	private Set<ShoppingCartItemDTO> shoppingCartItems = new HashSet<ShoppingCartItemDTO>(0);
	private String currency;
	private String reference;
	private String extras;
	private String statusLocalization;

	public ShoppingCartDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public MethodShipping getMethodShipping() {
		return methodShipping;
	}

	public void setMethodShipping(MethodShipping methodShipping) {
		this.methodShipping = methodShipping;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public ShoppingCartStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(ShoppingCartStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getShippingFees() {
		return shippingFees;
	}

	public void setShippingFees(String shippingFees) {
		this.shippingFees = shippingFees;
	}

	public String getTaxes() {
		return taxes;
	}

	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}

	public String getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(String shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public String getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}

	public PaymentMethodType getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethodType paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Set<ShoppingCartItemDTO> getShoppingCartItems() {
		return shoppingCartItems;
	}

	public void setShoppingCartItems(Set<ShoppingCartItemDTO> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public UserAddressDTO getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(UserAddressDTO shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}

	public String getStatusLocalization() {
		return statusLocalization;
	}

	public void setStatusLocalization(String statusLocalization) {
		this.statusLocalization = statusLocalization;
	}

	
}
