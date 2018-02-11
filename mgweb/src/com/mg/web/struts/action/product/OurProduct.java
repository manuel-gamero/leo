package com.mg.web.struts.action.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.mg.annotation.Action;
import com.mg.enums.ProductType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Collection;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ProductDTO;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.util.currency.CurrencyUtils;
import com.mg.util.text.StringUtils;
import com.mg.util.translation.TranslationUtils;
import com.mg.web.RequestAtributeConstants;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class OurProduct extends BasicAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155604242419622177L;
	private static Logger log = Logger.getLogger(OurProduct.class);
	private List<ProductDTO> list;
	private ProductType type;
	private Integer collectionId;
	private String nameUrlCollection;
	private String nameUrlType;
	private String tag;
	private String sortBy;
	private String title;
	private Boolean sale;
	//private String description;
	private String action;
	private Collection collection;
	private Boolean customProduct;
	
	private static final Comparator<ProductDTO> PRODUCT_COMPARATOR_PRICE = new Comparator<ProductDTO>() {
		public int compare(ProductDTO item1, ProductDTO item2) {
			if(item1.getNewProduct() && !item2.getNewProduct()){
				return -1;
			}
			else if(!item1.getNewProduct() && item2.getNewProduct()){
				return 1;
			}
			return item2.getMsrp().compareTo(item1.getMsrp());
		}
	};
	
	private static final Comparator<ProductDTO> PRODUCT_COMPARATOR_NAME = new Comparator<ProductDTO>() {
		public int compare(ProductDTO item1, ProductDTO item2) {
			if(item1.getNewProduct() && !item2.getNewProduct()){
				return -1;
			}
			else if(!item1.getNewProduct() && item2.getNewProduct()){
				return 1;
			}
			return item1.getName().compareTo(item2.getName());
		}
	};
	
	@Override
	public void prepare(){
		try {
			super.prepare();
		} catch (Exception e) {
			managerException(e, "type: " + type + " collectionId: " + collectionId + " tag: " + tag);
		}
	}
	
	private ProductType getProductType(String nameUrlType2) {
		ProductType type = null;
		if(nameUrlType2.equals("large-tote-bag"))
			type = ProductType.BAG;
		else if(nameUrlType2.equals("sac-cabas"))
			type = ProductType.BAG;
		else if(nameUrlType2.equals("large-pouch"))
			type = ProductType.LARGE_POUCH;
		else if(nameUrlType2.equals("trousse-grand-format"))
			type = ProductType.LARGE_POUCH;
		else if(nameUrlType2.equals("medium-pouch"))
			type = ProductType.MEDIUM_POUCH;
		else if(nameUrlType2.equals("pochette-moyenne"))
			type = ProductType.MEDIUM_POUCH;
		else if(nameUrlType2.equals("wallet"))
			type = ProductType.WALLET;
		else if(nameUrlType2.equals("porte-monnaie"))
			type = ProductType.WALLET;
		else if(nameUrlType2.equals("laptop-sleeve"))
			type = ProductType.LAPTOP_POUCH;
		else if(nameUrlType2.equals("housse-ordinateur"))
			type = ProductType.LAPTOP_POUCH;
		else if(nameUrlType2.equals("tablet-sleeve"))
			type = ProductType.TABLET_POUCH;
		else if(nameUrlType2.equals("housse-tablette"))
			type = ProductType.TABLET_POUCH;
		else if(nameUrlType2.equals("kid-purse"))
			type = ProductType.GIRL_PURSE;
		else if(nameUrlType2.equals("sac-enfant"))
			type = ProductType.GIRL_PURSE;
		else if(nameUrlType2.equals("snack-bag"))
			type = ProductType.SNACK_BAG;
		else if(nameUrlType2.equals("sac-a-collation"))
			type = ProductType.SNACK_BAG;
		else if(nameUrlType2.equals("baskets"))
			type = ProductType.BASKETS;
		else if(nameUrlType2.equals("paniers"))
			type = ProductType.BASKETS;
		else if(nameUrlType2.equals("pouches"))
			type = ProductType.POUCHES;
		else if(nameUrlType2.equals("pochettes"))
			type = ProductType.POUCHES;

		return type;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Action(value="nameUrlType = #nameUrlType , nameUrlCollection = #nameUrlCollection ")
	public String execute() {
		log.debug("OurProduct debug execute");
		try {
			action = getRequest().getRequestURI();
			if(nameUrlType != null){
				type = getProductType( nameUrlType );
			}
			else if( nameUrlCollection != null){
				collectionId = ServiceLocator.getService(CacheServiceImpl.class).getUrlcache().get(nameUrlCollection);
				
				//Redirect to error page if collection no found
				if(collectionId == null){
					log.warn("Collection " + nameUrlCollection + " does not exist.");
					return ERRORPAGE;
				}
			}
			
			//Try to get the list from the cache
			String key = OurProduct.class + "_" + type + "_"  + collectionId + "_" + sortBy + "_" + customProduct + "_" + getCurrentLanguage() + "_" + getCurrentCurrencyCode();
			log.debug("Retriving from cache key: " + key);
			list = (List<ProductDTO>) ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().fetch( key );
			
			log.debug("From cache list: " + nameUrlCollection);
			//If there is not any list then get it
			if(list == null){
				List<Product> listProduct = null;
				if(sale){
					listProduct = ServiceLocator.getService(ProductServiceImpl.class).getAllSaleProduct(getCurrentCurrencyCode());
				}
				else if(nameUrlType != null){
					log.debug("Type nameUrlType: " + nameUrlType);
					listProduct = createProductList( ServiceLocator.getService(ProductServiceImpl.class).getProductByType(type, customProduct) );
				}
				else if( nameUrlCollection != null){
					log.debug("Collection nameUrlCollection: " + nameUrlCollection);
					listProduct = createProductList( ServiceLocator.getService(ProductServiceImpl.class).getProductByCollection(collectionId, customProduct) );
				}
					
				log.debug("listProduct: " + listProduct);
				if(listProduct != null){
					log.debug("Size  listProduct: " + listProduct.size());
					list = DTOFactory.getProductDTOList(listProduct, getCurrentLanguage(), getCurrentCurrencyCode(), true) ;
				}	
				
				if("PRICE".equals(sortBy)){
					Collections.sort(list, PRODUCT_COMPARATOR_PRICE);
					setSortBy("PRICE");
				}
				else if("NAME".equals(sortBy)){
					Collections.sort(list, PRODUCT_COMPARATOR_NAME);
					setSortBy("NAME");
				}
				else{
					Collections.sort(list, PRODUCT_COMPARATOR_PRICE);
				}
				
				//Store the list dto product in cache
				if(list!= null && list.size()>0){
					log.debug("Store in cache: " + key);
					ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().store( key, list );
				}
			}
			log.debug("list.size: " + list.size());
			
			if(sale){
				log.debug("Setting metainfo for sales page.");
				setMetaInfoForSale();
			}
			else if(nameUrlType != null){
				log.debug("Setting metainfo for type.");
				setMetaInfoForType();
			}
			else if( nameUrlCollection != null){
				log.debug("Setting metainfo for collection.");
				setMetaInfoForCollection();
			}
		} catch (Exception e) {
			managerException(e, "type: " + type + " collectionId: " + collectionId + " tag: " + tag + "nameUrlCollection : " + nameUrlCollection + " nameUrlType: " + nameUrlType );
			return ERROR;
		}
		log.debug("Return SUCCESS");
		return SUCCESS;
	}

	private List<Product> createProductList(List<Product> productByCollection) throws ServiceException, ServiceLocatorException {
		if(productByCollection.size() > 0 ){
			List<Product> resutList = new ArrayList<Product>();
			for (Product item : productByCollection) {
				Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(item.getId(), true);
				resutList.add(product);
			}
			return resutList;
		}
		return (productByCollection);
	}
	
	private void setMetaInfoForSale() throws ServiceLocatorException {
		setPageTitleKey(TITLE_PAGE_PARAM);
		setPageFbTitle(TITLE_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_TITLE_PARAM_1, getText("bolsos.sales.title") );
		request.setAttribute(RequestAtributeConstants.PAGE_FB_TITLE_PARAM_1, getText("bolsos.sales.title"));
		setPageDescriptionKey(DESCR_PAGE_PARAM);
		setPageFbDescription(DESCR_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_DESCRIPTION_PARAM_1, getText("bolsos.sales.description") );
		request.setAttribute(RequestAtributeConstants.PAGE_FB_DESCRIPTION_PARAM_1, getText("bolsos.sales.description") );
	}


	private void setMetaInfoForType() throws ServiceLocatorException {
		setPageTitleKey(TITLE_PAGE_PARAM);
		setPageFbTitle(TITLE_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_TITLE_PARAM_1, getText("bolsos.pages.type.text") + " - " + StringUtils.cleanUrl(nameUrlType));
		request.setAttribute(RequestAtributeConstants.PAGE_FB_TITLE_PARAM_1, getText("bolsos.pages.type.text") + " - " + StringUtils.cleanUrl(nameUrlType));
		setPageDescriptionKey(DESCR_PAGE_PARAM);
		setPageFbDescription(DESCR_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_DESCRIPTION_PARAM_1, getText( type.getLocalizationDescriptionKey() ) );
		request.setAttribute(RequestAtributeConstants.PAGE_FB_DESCRIPTION_PARAM_1, getText( type.getLocalizationDescriptionKey() ) );
		setPageFbImage(getText("url.web") + ServiceLocator.getService(ConfigServiceImpl.class).getWebImagesLocation() + getText( type.getLocalizationImageKey() ) );
		
	}

	private void setMetaInfoForCollection() throws ServiceLocatorException {
		setPageTitleKey(TITLE_PAGE_PARAM);
		setPageFbTitle(TITLE_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_TITLE_PARAM_1, getText("bolsos.pages.collection.text") + " - " + StringUtils.cleanUrl(nameUrlCollection));
		request.setAttribute(RequestAtributeConstants.PAGE_FB_TITLE_PARAM_1, getText("bolsos.pages.collection.text") + " - " + StringUtils.cleanUrl(nameUrlCollection));
		setPageFbUrl(URL_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_FB_URL_PARAM_1 , getText("url.web") + getText("url.ourproduct.collections")+ "/" + nameUrlCollection);
		setPageDescriptionKey(DESCR_PAGE_PARAM);
		setPageFbDescription(DESCR_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_DESCRIPTION_PARAM_1, getDescriptionSeo() );
		request.setAttribute(RequestAtributeConstants.PAGE_FB_DESCRIPTION_PARAM_1, getDescriptionSeo() );
		if( collection.getImage() != null ){
			setPageFbImage(getText("url.web") + ServiceLocator.getService(ConfigServiceImpl.class).getWebImageCollectionLocation() + collection.getImage().getName());
		}
	}

	public List<ProductDTO> getList() {
		return list;
	}

	public void setList(List<ProductDTO> list) {
		this.list = list;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getTitle(){
		if( title == null){
			if(sale){
				title = getText("bolsos.sales.title");
			}
			else if(type != null){
				title = getText(type.getLocalizationTitleKey());
			}
			else if(collectionId != null && list.size() > 0){
				try {
					title =  TranslationUtils.getTranslation( getCollection().getTranslationByNameTransId(), getCurrentLanguage() );
				} catch (Exception e) {
					managerException(e, "collectionId: " + collectionId + " type : " + type );
					title = "";
				}
			}
		}
		return title;
	}

	public String getDescription() {
		String description = "";
		if(sale){
			description = getText("bolsos.sales.description");
		}
		else if(type != null){
			description = getText(type.getLocalizationDescriptionKey());
		}
		else if(collectionId != null ){
			description  = TranslationUtils.getCleanDescription( getCollection().getTranslationByDescriptionTransId(), getCurrentLanguage() );
		}
		return description; // ;
	}
	
	public String getDescriptionSeo(){
		return StringUtils.removeHtmlTag( getDescription() );
	}
	
	private Collection getCollection(){
		if(collection == null){
			try {
				collection = ServiceLocator.getService(CollectionServiceImpl.class).getCollection(collectionId, true);
			} catch (Exception e) {
				managerException(e, "collectionId: " + collectionId + " type : " + type );
			}
		}
		return collection;
	}

	/*public void setDescription(String description) {
		this.description = description;
	}
*/
	public void setTitle(String title) {
		this.title = title;
	}

	public String getNameUrlCollection() {
		return nameUrlCollection;
	}

	public void setNameUrlCollection(String nameUrlCollection) {
		this.nameUrlCollection = nameUrlCollection;
	}

	public String getNameUrlType() {
		return nameUrlType;
	}

	public void setNameUrlType(String nameUrlType) {
		this.nameUrlType = nameUrlType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getCustomProduct() {
		return customProduct;
	}

	public void setCustomProduct(Boolean customProduct) {
		this.customProduct = customProduct;
	}

	public Boolean getSale() {
		return sale;
	}

	public void setSale(Boolean sale) {
		this.sale = sale;
	}


}
