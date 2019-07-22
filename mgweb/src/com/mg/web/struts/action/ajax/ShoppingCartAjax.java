package com.mg.web.struts.action.ajax;

import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.annotation.Action;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Item;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.BasicAction;

/**
 * The main landing page action on the site.
 * 
 * @author MJGP
 *
 */
public class ShoppingCartAjax extends BasicAction {
		
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(ShoppingCartAjax.class);
	private int id;
	private String resutl;

	public ShoppingCartAjax() throws ServiceLocatorException {
		super();	
	}

	@Action(value="product = #id , shoppingCartList.size = #shoppingCartList.size ")
	public String addProduct() {
		try{
			if(id > 0){
				Item item = new Item();
				log.debug("call ajax addProduct()");
				Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(id, true);
				ServiceLocator.getService(ImageServiceImpl.class).getItemPNG( item, product, null );
				List<Item> itemList = getShoppingCartItems();
				itemList.add(item);
				request.getSession().setAttribute(WebConstants.SHOPPING_CART_ITEMS, itemList);
				log.debug("Item's number in shoppingCart: " + itemList.size());
				setResutl(SUCCESS);
			}
			return SUCCESS;
		} catch (Exception e) {
			managerException(e, "id: " + id);
			return SUCCESS;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResutl() {
		return resutl;
	}

	public void setResutl(String resutl) {
		this.resutl = resutl;
	}
}
