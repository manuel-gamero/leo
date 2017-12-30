package com.mg.web.struts.action.product;

import java.util.List;
import java.util.Set;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.mg.model.Collection;
import com.mg.model.CustomComponentCollection;
import com.mg.model.CustomComponentText;
import com.mg.model.PriceEntry;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.CustomComponentImageDTO;
import com.mg.service.dto.ImageDTO;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.util.translation.Translations;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.BasicTranslationAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class AddEditProduct extends BasicTranslationAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private Product product;
	private Integer id;
	private List<CustomComponentImageDTO> customComponentImageList;
	private Set<PriceEntry> priceEntrySet;
	private String fileContentType;
	private String fileFileName;
	private CustomComponentText customText;
	private String status;
	private ImageDTO imageDTO;
	private String productCustom;

	private List<Collection> collectionList;

	@Override
	public void validate() {
		String error = null;
		if(fileFileName != null && fileFileName.length() > 50){
			error = getText("bolsos.product.error.image.name");
			addActionError(error);
		}
		if( (customComponentImageList == null || customComponentImageList.size() == 0) && product.getId() == 0 ){
			error = getText("bolsos.product.error.collection");
			addActionError(error);
		}
		if( product.getId() != 0 && product.getCustomProduct() && imageDTO != null && imageDTO.getFile() != null && product.getImage().getCustomComponentImagesForImageId() != null  ){
			error = "NO it´s possible change image for a custom product";
			addActionError(error);
		}
		if(error != null){
			ActionContext.getContext().getSession().put(WebConstants.ERRORACTION, error);
		}
	}
	
	@Override
	public void prepare(){
		try{
		collectionList = ServiceLocator.getService(CollectionServiceImpl.class).getAllCollection();
		} catch (Exception e) {
			managerException(e);
		}
	}

	@Override
	@SkipValidation
	public String execute(){
		try{
			if (id != null) {
				try{
					product = ServiceLocator.getService(ProductServiceImpl.class)
							.getProduct(id, false);
					setValueTranslation(product);
					
					if(product.getImage().getCustomComponentTexts().iterator().hasNext()){
						customText = product.getImage().getCustomComponentTexts().iterator().next();
					}
					if(product.getPrice()!= null){
						setPriceEntrySet(product.getPrice().getPriceEntries());
					}
				}
				catch (Exception e) {
					log.error(e.getMessage());
				}
				return INPUT;
			} else {
				return INPUT;
			}
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String save() {
		try {
			Translations translationsName = new Translations.StringTranslationBuilder().engString(nameEn).frString(nameFr).build();
			Translations translationsDesc = new Translations.StringTranslationBuilder().engString(descEn).frString(descFr).build();
			ServiceLocator.getService(ProductServiceImpl.class).saveProduct(customComponentImageList, product, imageDTO, translationsName, translationsDesc, customText, priceEntrySet);
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String delete(){
		try {
			ServiceLocator.getService(CollectionServiceImpl.class)
					.deleteCustomComponentImage(id);
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Collection> getCollectionList() {
		return collectionList;
	}

	public void setCollectionList(List<Collection> collectionList) {
		this.collectionList = collectionList;
	}

	public Set<CustomComponentCollection> getCustomComponentCollection(
			String code) {
		for (Collection item : collectionList) {
			if (item.equals(code)) {
				return item.getCustomComponentCollections();
			}
		}
		return null;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<CustomComponentImageDTO> getCustomComponentImageList() {
		return customComponentImageList;
	}

	public void setCustomComponentImageList(
			List<CustomComponentImageDTO> customComponentImageList) {
		this.customComponentImageList = customComponentImageList;
	}

	public CustomComponentText getCustomText() {
		return customText;
	}

	public void setCustomText(CustomComponentText customText) {
		this.customText = customText;
	}

	public Set<PriceEntry> getPriceEntrySet() {
		return priceEntrySet;
	}

	public void setPriceEntrySet(Set<PriceEntry> priceEntrySet) {
		this.priceEntrySet = priceEntrySet;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ImageDTO getImageDTO() {
		return imageDTO;
	}

	public void setImageDTO(ImageDTO imageDTO) {
		this.imageDTO = imageDTO;
	}

	public String getProductCustom() {
		return productCustom;
	}

	public void setProductCustom(String productCustom) {
		this.productCustom = productCustom;
	}

}
