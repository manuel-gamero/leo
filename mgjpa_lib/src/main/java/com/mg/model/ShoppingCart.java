package com.mg.model;

// Generated 07-may-2015 22:48:42 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.mg.enums.PaymentMethodType;
import com.mg.enums.ShoppingCartStatus;

/**
 * MgShoppingCart generated by hbm2java
 */
@Entity
@Table(name = "mg_shopping_cart", schema = "bolsos")
public class ShoppingCart implements java.io.Serializable, BasicModel {

	private static final long serialVersionUID = 8168541433819670846L;
	private int id;
	private Users users;
	private MethodShipping methodShipping;
	private Transaction transaction;
	private ShoppingCartStatus statusCode;
	private BigDecimal total;
	private BigDecimal shippingFees;
	private BigDecimal taxes;
	private BigDecimal extras;
	private UserAddress shippingAddressId;
	private String trackNumber;
	private PaymentMethodType paymentMethod;
	private Date purchaseDate;
	private Date sendDate;
	private Date shippingDate;
	private String comment;
	private String commentUser;
	private Date creationDate;
	private Set<ShoppingCartItem> shoppingCartItems = new HashSet<ShoppingCartItem>(0);
	private String currency;
	private String reference;
	private BigDecimal totalShopping;
	private BigDecimal discountTotalShopping;
	private BigDecimal discountTotal;
	private BigDecimal discountShippingFees;
	private BigDecimal discountTaxes;
	private BigDecimal discountExtras;
	private Paypal paypal;
	private String paymentType;
	
	public static final Comparator<ShoppingCart> SHOPPING_CART_COMPARATOR_DATE = new Comparator<ShoppingCart>() {
		public int compare(ShoppingCart item1, ShoppingCart item2) {
			return item2.getCreationDate().compareTo(item1.getCreationDate());
		}
	};

	public ShoppingCart() {
	}

	public ShoppingCart(int id, Users users,
			MethodShipping methodShipping, Transaction transaction,
			ShoppingCartStatus statusCode, BigDecimal total, BigDecimal taxes,
			UserAddress shippingAddressId, PaymentMethodType paymentMethod, Date purchaseDate) {
		this.id = id;
		this.users = users;
		this.methodShipping = methodShipping;
		this.transaction = transaction;
		this.statusCode = statusCode;
		this.total = total;
		this.taxes = taxes;
		this.shippingAddressId = shippingAddressId;
		this.paymentMethod = paymentMethod;
		this.purchaseDate = purchaseDate;
	}

	public ShoppingCart(int id, Users users,
			MethodShipping methodShipping, Transaction transaction,
			ShoppingCartStatus statusCode, BigDecimal total, BigDecimal shippingFees, BigDecimal taxes,
			UserAddress shippingAddressId, String trackNumber, PaymentMethodType paymentMethod,
			Date purchaseDate, Date sendDate, Date shippingDate,
			String comment, String commentUser, Date creationDate,
			Set<ShoppingCartItem> shoppingCartItems) {
		this.id = id;
		this.users = users;
		this.methodShipping = methodShipping;
		this.transaction = transaction;
		this.statusCode = statusCode;
		this.total = total;
		this.shippingFees = shippingFees;
		this.taxes = taxes;
		this.shippingAddressId = shippingAddressId;
		this.trackNumber = trackNumber;
		this.paymentMethod = paymentMethod;
		this.purchaseDate = purchaseDate;
		this.sendDate = sendDate;
		this.shippingDate = shippingDate;
		this.comment = comment;
		this.commentUser = commentUser;
		this.creationDate = creationDate;
		this.shoppingCartItems = shoppingCartItems;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_shopping_cart_id")
	@SequenceGenerator(name="seq_shopping_cart_id", sequenceName="bolsos.seq_shopping_cart_id", initialValue=1, allocationSize= 1 )
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "method_shipping_id", nullable = false)
	public MethodShipping getMethodShipping() {
		return this.methodShipping;
	}

	public void setMethodShipping(MethodShipping methodShipping) {
		this.methodShipping = methodShipping;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id")
	public Transaction getTransaction() {
		return this.transaction;
	}

	public void setTransaction(Transaction transactionId) {
		this.transaction = transactionId;
	}

	@Column(name = "status_code", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	public ShoppingCartStatus getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(ShoppingCartStatus statusCode) {
		this.statusCode = statusCode;
	}

	@Column(name = "total", nullable = false, precision = 10, scale = 2)
	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Column(name = "shipping_fees", nullable = false, precision = 10, scale = 2)
	public BigDecimal getShippingFees() {
		return this.shippingFees;
	}

	public void setShippingFees(BigDecimal shippingFees) {
		this.shippingFees = shippingFees;
	}

	@Column(name = "taxes", nullable = false, precision = 10, scale = 2)
	public BigDecimal getTaxes() {
		return this.taxes;
	}

	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}

	//@Column(name = "shipping_address_id", nullable = false)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipping_address_id", nullable = false)
	public UserAddress getShippingAddressId() {
		return this.shippingAddressId;
	}

	public void setShippingAddressId(UserAddress shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	@Column(name = "track_number", length = 80)
	public String getTrackNumber() {
		return this.trackNumber;
	}

	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}

	@Column(name = "payment_method", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	public PaymentMethodType getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(PaymentMethodType paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "purchase_date", nullable = false, length = 29)
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "send_date", length = 29)
	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "shipping_date", length = 29)
	public Date getShippingDate() {
		return this.shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	@Column(name = "comment")
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "comment_user")
	public String getCommentUser() {
		return this.commentUser;
	}

	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingCart", cascade=CascadeType.ALL)
	public Set<ShoppingCartItem> getShoppingCartItems() {
		return this.shoppingCartItems;
	}

	public void setShoppingCartItems(
			Set<ShoppingCartItem> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}

	@Column(name = "currency", length = 3)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "reference", length = 20)
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Column(name = "extras", nullable = false, precision = 10, scale = 2)
	public BigDecimal getExtras() {
		return extras;
	}

	public void setExtras(BigDecimal extras) {
		this.extras = extras;
	}
	
	@Column(name = "total_shopping", precision = 10, scale = 2)
	public BigDecimal getTotalShopping() {
		return this.totalShopping;
	}

	public void setTotalShopping(BigDecimal totalShopping) {
		this.totalShopping = totalShopping;
	}

	@Column(name = "discount_total_shopping", precision = 10, scale = 2)
	public BigDecimal getDiscountTotalShopping() {
		return discountTotalShopping;
	}

	public void setDiscountTotalShopping(BigDecimal discountTotalShopping) {
		this.discountTotalShopping = discountTotalShopping;
	}

	@Column(name = "discount_total", precision = 10, scale = 2)
	public BigDecimal getDiscountTotal() {
		return discountTotal;
	}

	public void setDiscountTotal(BigDecimal discountTotal) {
		this.discountTotal = discountTotal;
	}

	@Column(name = "discount_shipping_fees", precision = 10, scale = 2)
	public BigDecimal getDiscountShippingFees() {
		return discountShippingFees;
	}

	public void setDiscountShippingFees(BigDecimal discountShippingFees) {
		this.discountShippingFees = discountShippingFees;
	}

	@Column(name = "discount_taxes", precision = 10, scale = 2)
	public BigDecimal getDiscountTaxes() {
		return discountTaxes;
	}

	public void setDiscountTaxes(BigDecimal discountTaxes) {
		this.discountTaxes = discountTaxes;
	}

	@Column(name = "discount_extras", precision = 10, scale = 2)
	public BigDecimal getDiscountExtras() {
		return discountExtras;
	}

	public void setDiscountExtras(BigDecimal discountExtras) {
		this.discountExtras = discountExtras;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "paypal_id")
	public Paypal getPaypal() {
		return this.paypal;
	}

	public void setPaypal(Paypal paypal) {
		this.paypal = paypal;
	}
	
	@Transient
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
}
