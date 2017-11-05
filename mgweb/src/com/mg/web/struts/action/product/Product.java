package com.mg.web.struts.action.product;

import java.util.List;

import org.apache.log4j.Logger;

import com.mg.annotation.Action;
import com.mg.exception.CurrencyNoExistException;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ItemViewDTO;
import com.mg.service.dto.ProductDTO;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.util.currency.CurrencyUtils;
import com.mg.web.RequestAtributeConstants;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class Product extends BasicAction implements Preparable {

	private static Logger log = Logger.getLogger(Product.class);
	private static final long serialVersionUID = 1155604242419622177L;
	//private ProductDTO productDTO;
	private List<ItemViewDTO> itemList;
	private Integer id;
	private String extraPrice;
	private String nameUrlProduct;
	
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
	@Action(value="product = #id ")
	public String execute(){
		log.debug("Product debug execute id " + id);		
		ProductDTO productDTO = null;
		try {
			
			/*if( nameUrlProduct != null ){
				id = ServiceLocator.getService(CacheServiceImpl.class).getUrlcache().get(nameUrlProduct);
			}*/
			if(id != null){
				log.debug("Getting productDTO");
				productDTO = DTOFactory.getProductDTO(id, getCurrentLanguage(),getCurrentCurrencyCode(), true);
				if( productDTO != null ){
					List<ItemViewDTO> listItem = null;
					if ( productDTO.getCustomProduct()){
						log.debug("Getting list product suggestion for custom product");
						listItem = DTOFactory.getItemViewDTOForItemList( ServiceLocator.getService(ProductServiceImpl.class).getListItemByProduct(id), getCurrentLanguage(), getCurrentCurrencyCode(), 3 );
					}
					else{
						log.debug("Getting list similar product for final product");
						listItem = DTOFactory.getItemViewDTOForProductList( ServiceLocator.getService(ProductServiceImpl.class).getProductByCollection( productDTO.getCollection().getId(), false), id, getCurrentLanguage(), getCurrentCurrencyCode(), 3 );
					}
					setItemList( listItem );
					/*if(listItem.size()>=4){
						setItemList( listItem.subList(1, 4));
					}
					else{
						setItemList( listItem );
					}*/
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
			managerExceptionBySupport(ce);
			return ERROR;
		}
		catch (Exception e) {
			managerException(e, "productId: " + id);
			return ERROR;
		}		
		log.debug("Return SUCCESS");
		return SUCCESS;
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


}
