package com.mg.web.struts.action.ajax;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.mg.exception.CacheException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.MethodShipping;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.util.currency.CurrencyUtils;
import com.mg.web.struts.action.BasicListActionSupport;


public class PriceAjax extends BasicListActionSupport<String> {
	
	private static final long serialVersionUID = 1L;
	protected Logger log = Logger.getLogger(this.getClass());
	private Integer objId;
    private String price;
    private String currency;
    private Integer id;
    private BigDecimal newPrice;
    private Integer productId;
    	
	public String addProductPrice() {	
		try {
			if( objId != null && objId > 0 ){
				Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(objId, false); // new Product();
				if(product != null && product.getId() >0 && !CurrencyUtils.checkCurrency(product, currency) ){
					CurrencyUtils.getPriceByPriceEntry(product, new BigDecimal(price), currency);
					//id = ServiceLocator.getService(ProductServiceImpl.class).savePrice(priceItem);
					id = ServiceLocator.getService(ProductServiceImpl.class).saveProduct(product);
					removeProductFromCache();
				}
			}
		} catch (Exception e) {
			managerException(e);
		}
		return  SUCCESS;
	}
	
	public String addMethodShippingPrice() {	
		try {
			if( objId != null && objId > 0 ){
				MethodShipping methodShipping = ServiceLocator.getService(ShoppingServiceImpl.class).getMethodShipping(objId); // new Product();
				if( methodShipping != null && methodShipping.getId() > 0 && !CurrencyUtils.checkCurrency(methodShipping, currency)){
					CurrencyUtils.getPriceByPriceEntry(methodShipping, new BigDecimal(price), currency);
					ServiceLocator.getService(ShoppingServiceImpl.class).updateMethodShipping(methodShipping);
				}
			}
		} catch (Exception e) {
			managerException(e);	
		}
		return  SUCCESS;
	}

	public String deletePriceEntry(){
		try {
			if(id != null && id > 0){
				ServiceLocator.getService(ProductServiceImpl.class).deletePriceEntity(id);
				removeProductFromCache();
			}
		}
		catch (Exception e) {
			managerException(e);	
		}
		return  SUCCESS;
	}
	
	public String savePriceEntry(){
		try {
			if(id != null && id > 0){
				ServiceLocator.getService(ProductServiceImpl.class).saveNewPrice(id, newPrice);
				removeProductFromCache();
			}
		}
		catch (Exception e) {
			managerException(e);	
		}
		return  SUCCESS;
	}

	private void removeProductFromCache() throws CacheException, ServiceLocatorException{
		if( productId != null && productId > 0){
			ServiceLocator.getService(CacheServiceImpl.class).getProductCache().remove(Product.class + "_" + productId);
			ServiceLocator.getService(CacheServiceImpl.class).removeProductDTO(productId);
		}
	}
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public BigDecimal getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	
	
}
