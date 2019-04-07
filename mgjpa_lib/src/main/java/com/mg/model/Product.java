package com.mg.model;

// Generated 31-ene-2015 21:26:16 by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.UniqueConstraint;

import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import com.mg.enums.ProductStatus;
import com.mg.enums.ProductType;
import com.mg.util.currency.ICurrency;
import com.mg.util.translation.IBasicTranslation;

/**
 * MgProduct generated by hbm2java
 */
@Entity
@Table(name = "mg_product", schema = "bolsos", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
@Indexed
@AnalyzerDefs({
		@AnalyzerDef(name = "englishAnalyzer", tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class), filters = {
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
					@Parameter(name = "language", value = "English") }),
				@TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
						@Parameter(name = "minGramSize", value = "3"),
						@Parameter(name = "maxGramSize", value = "50") }) }),
		@AnalyzerDef(name = "frenchAnalyzer", tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class), filters = {
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
					@Parameter(name = "language", value = "French") }),
				@TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
						@Parameter(name = "minGramSize", value = "3"),
						@Parameter(name = "maxGramSize", value = "50") }) }) })

public class Product implements java.io.Serializable, BasicModel, ICurrency, IBasicTranslation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3267259182551003960L;
	private int id;
	private Translation translationByDescriptionTransId;
	private Translation translationByNameTransId;
	private Image image;
	private Collection collection;
	private ProductStatus statusCode;
	private String cost;
	private ProductType typeCode;
	private String weight;
	private String width;
	private String height;
	private String depth;
	private Date creationDate;
	private Set<Item> items = new HashSet<Item>(0);
	private Set<ProductTag> productTags = new HashSet<ProductTag>(0);
	private Price price;
	private Set<ProductImage> productImages = new HashSet<ProductImage>(0);
	private Boolean customProduct;
	private Boolean newProduct;
	private Boolean customText;
	private Integer customLink;
	private Set<DeviceProduct> deviceProducts = new HashSet<DeviceProduct>(0);

	public Product() {
	}

	public Product(Integer id) {
		this.id = id;
	}
	
	public Product(int id, ProductStatus statusCode, ProductType typeCode, Date creationDate) {
		this.id = id;
		this.statusCode = statusCode;
		this.typeCode = typeCode;
		this.creationDate = creationDate;
	}

	public Product(int id, Translation translationByDescriptionTransId, Translation translationByNameTransId,
			Image image, Collection collection, ProductStatus statusCode, Price price, String cost,
			ProductType typeCode, String weight, Date creationDate) {
		this.id = id;
		this.translationByDescriptionTransId = translationByDescriptionTransId;
		this.translationByNameTransId = translationByNameTransId;
		this.image = image;
		this.collection = collection;
		this.statusCode = statusCode;
		this.price = price;
		this.cost = cost;
		this.typeCode = typeCode;
		this.weight = weight;
		this.creationDate = creationDate;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product_id")
	@SequenceGenerator(name = "seq_product_id", sequenceName = "bolsos.seq_product_id", initialValue = 1, allocationSize = 1)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "description_trans_id")
	public Translation getTranslationByDescriptionTransId() {
		return this.translationByDescriptionTransId;
	}

	public void setTranslationByDescriptionTransId(Translation translationByDescriptionTransId) {
		this.translationByDescriptionTransId = translationByDescriptionTransId;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "name_trans_id")
	@IndexedEmbedded(includeEmbeddedObjectId = true)
	public Translation getTranslationByNameTransId() {
		return this.translationByNameTransId;
	}

	public void setTranslationByNameTransId(Translation translationByNameTransId) {
		this.translationByNameTransId = translationByNameTransId;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collection_id")
	public Collection getCollection() {
		return this.collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	@Column(name = "status_code", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	public ProductStatus getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(ProductStatus statusCode) {
		this.statusCode = statusCode;
	}

	@Column(name = "cost", length = 15)
	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@Column(name = "type_code", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	public ProductType getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(ProductType typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "weight", length = 10)
	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, length = 29)
	public Date getCreationDate() {
		if (creationDate == null) {
			return (new Date());
		} else {
			return this.creationDate;
		}
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<ProductTag> getProductTags() {
		return this.productTags;
	}

	public void setProductTags(Set<ProductTag> productTags) {
		this.productTags = productTags;
	}

	@Column(name = "width", length = 10)
	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	@Column(name = "height", length = 10)
	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Column(name = "depth", length = 10)
	public String getDepth() {
		return this.depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "msrp")
	public Price getPrice() {
		return this.price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	public Set<ProductImage> getProductImages() {
		return this.productImages;
	}

	public void setProductImages(Set<ProductImage> productImages) {
		this.productImages = productImages;
	}

	@Column(name = "custom_product")
	public Boolean getCustomProduct() {
		return this.customProduct;
	}

	public void setCustomProduct(Boolean customProduct) {
		this.customProduct = customProduct;
	}

	@Column(name = "new_product")
	public Boolean getNewProduct() {
		return this.newProduct;
	}

	public void setNewProduct(Boolean newProduct) {
		this.newProduct = newProduct;
	}

	@Column(name = "custom_text")
	public Boolean getCustomText() {
		return this.customText;
	}

	public void setCustomText(Boolean customText) {
		this.customText = customText;
	}

	@Override
	public String toString() {
		return ("[ " + id + ", " + collection.getId() + ", " + typeCode + "]");
	}

	@Column(name = "custom_link_id")
	public Integer getCustomLink() {
		return customLink;
	}

	public void setCustomLink(Integer customLink) {
		this.customLink = customLink;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<DeviceProduct> getDeviceProducts() {
		return this.deviceProducts;
	}

	public void setDeviceProducts(Set<DeviceProduct> deviceProducts) {
		this.deviceProducts = deviceProducts;
	}
}
