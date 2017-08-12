package com.mg.service.dto;

import java.util.List;

import com.mg.enums.ProductStatus;
import com.mg.enums.ProductType;
import com.mg.model.Image;

public class ProductDTO extends BasicDTO{
	
	private static final long serialVersionUID = -7939474478060728313L;
	private String description;
	private String name;
	private Image image;
	private CollectionDTO collection;
	private ProductStatus statusCode;
	private String msrp;
	private String cost;
	private ProductType typeCode;
	private String weight;
	private String width;
	private String height;
	private String depth;
	private List<CustomComponentDTO> customComponentDTOSet;
	private CustomComponentTextDTO customComponentText;
	private String url;
	private List<ProductImageDTO> productImagesSetDTO;
	private Boolean customProduct;
	private Boolean newProduct;
	private Boolean customText;
	private List<String> descriptionList;
	private String urlCustomProduct;
	private String oldPrice;
	private boolean hasDiscount;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public CollectionDTO getCollection() {
		return collection;
	}
	public void setCollection(CollectionDTO collection) {
		this.collection = collection;
	}
	public ProductStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(ProductStatus statusCode) {
		this.statusCode = statusCode;
	}
	public String getMsrp() {
		return msrp;
	}
	public void setMsrp(String msrp) {
		this.msrp = msrp;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public ProductType getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(ProductType typeCode) {
		this.typeCode = typeCode;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}	
	public CustomComponentTextDTO getCustomComponentText() {
		return customComponentText;
	}
	public void setCustomComponentText(CustomComponentTextDTO customComponentText) {
		this.customComponentText = customComponentText;
	}
	public List<CustomComponentDTO> getCustomComponentDTOSet() {
		return customComponentDTOSet;
	}
	public void setCustomComponentDTOSet(List<CustomComponentDTO> customComponentDTOSet) {
		this.customComponentDTOSet = customComponentDTOSet;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<ProductImageDTO> getProductImagesSetDTO() {
		return productImagesSetDTO;
	}
	public void setProductImagesSetDTO(List<ProductImageDTO> productImagesSet) {
		this.productImagesSetDTO = productImagesSet;
	}
	public Boolean getCustomProduct() {
		return customProduct;
	}
	public void setCustomProduct(Boolean customProduct) {
		this.customProduct = customProduct;
	}
	public Boolean getNewProduct() {
		return newProduct;
	}
	public void setNewProduct(Boolean newProduct) {
		this.newProduct = newProduct;
	}
	public Boolean getCustomText() {
		return customText;
	}
	public void setCustomText(Boolean customText) {
		this.customText = customText;
	}
	public List<String> getDescriptionList() {
		return descriptionList;
	}
	public void setDescriptionList(List<String> descriptionList) {
		this.descriptionList = descriptionList;
	}
	public String getUrlCustomProduct() {
		return urlCustomProduct;
	}
	public void setUrlCustomProduct(String urlCustomProduct) {
		this.urlCustomProduct = urlCustomProduct;
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

}

