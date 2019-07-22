package com.mg.web.struts.action.product;

import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ProductDTO;
import com.mg.util.currency.CurrencyUtils;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class ProductInSale extends BasicAction implements Preparable {

	private static Logger log = LogManager.getLogger(ProductInSale.class);
	private static final long serialVersionUID = 1155604242419622177L;
	private List<ProductDTO> list;
	
	@Override
	public void prepare() {
		try {
			super.prepare();
			List<String> keys = ServiceLocator.getService(CacheServiceImpl.class).getProductCache().getKeys();
			for (String key : keys) {
				com.mg.model.Product product = ServiceLocator.getService(CacheServiceImpl.class).getProductCache().fetch(key);
				if( isSale(product) ){
					list.add(DTOFactory.getProductDTO(product, getCurrentLanguage(), getCurrentCurrencyCode(), false));
				}		
			}
		} catch (Exception e) {
			managerException(e);
		}
	}

	private boolean isSale(Product product) {
		if( CurrencyUtils.hasDiscount(product.getPrice(), getCurrentCurrencyCode()) ){
			return true;
		}
		return false;
	}

	@Override
	public String execute(){
		return SUCCESS;
	}

	public List<ProductDTO> getList() {
		return list;
	}

	public void setList(List<ProductDTO> list) {
		this.list = list;
	}

	

}
