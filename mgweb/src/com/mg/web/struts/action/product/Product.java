package com.mg.web.struts.action.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.mg.annotation.Action;
import com.mg.enums.ProductStatus;
import com.mg.exception.CacheException;
import com.mg.exception.CurrencyNoExistException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.CustomComponentImage;
import com.mg.model.Item;
import com.mg.model.Suggestions;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ItemViewDTO;
import com.mg.service.dto.ProductDTO;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.service.suggestion.SuggestionServiceImpl;
import com.mg.util.currency.CurrencyUtils;
import com.mg.web.RequestAtributeConstants;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.BasicAction;
import com.mg.web.util.AuditUtil;
import com.opensymphony.xwork2.Preparable;

public class Product extends BasicAction implements Preparable {

	private static Logger log = Logger.getLogger(Product.class);
	private static final long serialVersionUID = 1155604242419622177L;
	private List<ItemViewDTO> itemList;
	private Integer id;
	private String extraPrice;
	private String nameUrlProduct;
	private String custom;
	private Integer index;
	
	@Override
	public void prepare() {
		try {
			super.prepare();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			managerException(e, "productId: " + id);
		}
	}

	@Override
	@Action(value="product = #id , custom = #custom ")
	public String execute(){
		log.debug("Product debug execute id " + id);		
		ProductDTO productDTO = null;
		try {
			if(id != null){
				log.debug("Getting productDTO");
				productDTO = DTOFactory.getProductDTO(id, getCurrentLanguage(),getCurrentCurrencyCode(), true);
				if( productDTO != null ){
					List<ItemViewDTO> listItem = null;
					if ( productDTO.getCustomProduct()){
						log.debug("Getting list product suggestion for custom product");
						//listItem = DTOFactory.getItemViewDTOForItemList( ServiceLocator.getService(ProductServiceImpl.class).getListItemByProduct(id), getCurrentLanguage(), getCurrentCurrencyCode(), 3 );
						List<Object[]> suggestionsList = ServiceLocator.getService(SuggestionServiceImpl.class).getSuggestionByCustomProduct(id, ServletActionContext.getRequest().getSession().getId(), getCurrentCurrencyCode());
						listItem = suggestionsList.stream().map( i -> {
							try {
								return DTOFactory.getItemViewDTOForItem((String)i[0],(Integer)i[1],getCurrentLanguage(), getCurrentCurrencyCode());
							} catch (Exception e) {
								managerException(e, "productId: " + id);
								return null;
							}
						}).limit(3).collect(Collectors.toList());
						//listItem = DTOFactory.getItemViewDTOForItemList( suggestionsList.stream().map(i -> (Item)i[0]).collect(Collectors.toList()), getCurrentLanguage(), getCurrentCurrencyCode(), 3 );
						generateAuditSuggestion(suggestionsList, 3);
					}
					else{
						log.debug("Getting list similar product for final product");
						listItem = DTOFactory.getItemViewDTOForProductList( ServiceLocator.getService(ProductServiceImpl.class).getProductByCollection( productDTO.getCollection().getId(), false), id, getCurrentLanguage(), getCurrentCurrencyCode(), 3 );
						//TODO esto lo tiene que hace una clase
						//La clase deberia de hacer la sugerencia y guardar el audit
						//generateAuditSuggestion(listItem);
					}
					setItemList( listItem );
					
					log.debug("Getting Extra-price");
					String extraPrice = ServiceLocator.getService(ConfigServiceImpl.class).getPriceExtraText(getCurrentCurrencyCode());
					setExtraPrice(CurrencyUtils.displayPriceLocale(extraPrice, getCurrentLanguage(), getCurrentCurrencyCode()) );
				}
				else{
					log.warn("Product id " + id + " does not exist.");
					return ERRORPAGE;
				}
			}
			else{
				log.error("Product ID : " + id + " DOESN´T exist ");
				return ERROR;
			}
			
			// page header customization 
			log.debug("Setting meta page information");
			setMetadataPage(productDTO);
		}
		catch(CurrencyNoExistException ce){
			try {
				com.mg.model.Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(id, true);
				if( product.getStatusCode().equals(ProductStatus.ACTIVE) && 
					product.getCollection().getStatusCode().equals(ProductStatus.ACTIVE) ){
						managerExceptionBySupport(ce);
				}
			} catch (Exception e) {
				managerException(e, "productId: " + id);
			}
			return ERRORPAGE;
		}
		catch (Exception e) {
			managerException(e, "productId: " + id);
			return ERROR;
		}		
		log.debug("Return SUCCESS");
		return SUCCESS;
	}

	private void generateAuditSuggestion(List<Object[]> listItem, int size) {
		listItem.subList(0, size).stream().forEach( p -> {
			try {
				Audit audit = AuditUtil.createAudit(null, "suggestion = " + ((String)p[0]) + " type = CUSTOM_PRODUCT ");
				ServiceLocator.getService(ConfigServiceImpl.class).saveAudit(audit);
				ServiceLocator.getService(SuggestionServiceImpl.class).saveSuggestion(null, ServletActionContext.getRequest().getSession().getId(), (Integer)p[1], ((String)p[0]), p[2]!=null?(Integer)p[2]:null, p[3]!=null?(Integer)p[3]:null);
			} catch (Exception e) {
				log.error(e);
			}} );
	}

	private void setMetadataPage(ProductDTO productDTO) {
		setPageTitleKey(TITLE_PAGE_PARAM);
		setPageFbTitle(TITLE_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_TITLE_PARAM_1, productDTO.getName());
		request.setAttribute(RequestAtributeConstants.PAGE_FB_TITLE_PARAM_1, productDTO.getName());
		setPageDescriptionKey(DESCR_PAGE_PARAM);
		setPageFbDescription(DESCR_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_DESCRIPTION_PARAM_1 , productDTO.getDescription());
		request.setAttribute(RequestAtributeConstants.PAGE_FB_DESCRIPTION_PARAM_1, productDTO.getDescription());
		String url = getText("url.web") + getText("url.product") + "/" + productDTO.getUrl();
		setPageFbUrl(URL_PAGE_PARAM);
		request.setAttribute(RequestAtributeConstants.PAGE_FB_URL_PARAM_1, url);
		setPageFbImage(getText("url.web") + WebConstants.PRODUCT_BASE_URL +
						productDTO.getId() + "/large/" + productDTO.getImage().getName());
	}

	public ProductDTO getProductDTO() {
		try {
			return DTOFactory.getProductDTO(id, getCurrentLanguage(),getCurrentCurrencyCode(), true);
		} catch (Exception e) {
			managerException(e, "productId: " + id);
			return null;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<ItemViewDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemViewDTO> itemList) {
		this.itemList = itemList;
	}

	public String getExtraPrice() {
		return extraPrice;
	}

	public void setExtraPrice(String extraPrice) {
		this.extraPrice = extraPrice;
	}

	public String getNameUrlProduct() {
		return nameUrlProduct;
	}

	public void setNameUrlProduct(String nameUrlProduct) {
		this.nameUrlProduct = nameUrlProduct;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	/**
	 * @return The list all the custom component images with its product and custom collection.
	 * 
	 * This method will be called for the frontend and use it for set all the component for the product.
	 * In a custom product there are many component that form the product image.
	 * 
	 * The custom property will have the following pattern: productId_CCIId1_CCIId2_...
	 * 
	 * And the list that I will produce will have the following pattern:
	 * 
	 * productId1_CCId1_CCIId1_CCId1, productId2_CCId2_CCIId2_CCId2, ...
	 * @throws ServiceLocatorException 
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public List<String> getCustomComponentForProduct() throws NumberFormatException, ServiceException, ServiceLocatorException{
		List<String> listCustomComponents = new ArrayList<String>();
		if(custom != null && custom.length()>0){
			int i = 0;
			String components[] = custom.split("_");
			String productId = components[0];
			for (i = 1; i < components.length; i++) {
				String component = getComponent(productId, components[i]);
				component = "'" + component + "'";
				listCustomComponents.add(component);
			}
		}
		return listCustomComponents;
	}

	private String getComponent(String productId, String cciid) throws NumberFormatException, ServiceException, ServiceLocatorException {
		CustomComponentImage cci = ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImage(Integer.valueOf(cciid));
		String id = productId + "_" + cci.getCustomComponentCollection().getCustomComponent().getId();
		id = id + "_" + cci.getId() + "_" + cci.getCustomComponentCollection().getCustomComponent().getId();
		return id;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public boolean getUpdate(){
		return false;
	}
}
