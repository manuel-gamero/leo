package com.mg.web.struts.action.shopping;

import java.util.List;

import org.apache.log4j.Logger;

import com.mg.annotation.Action;
import com.mg.exception.CurrencyNoExistException;
import com.mg.model.Item;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ItemShoppingCartDTO;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.web.WebConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class ShoppingCart extends BasicShoppingCart implements Preparable {

	private static Logger log = Logger.getLogger(ShoppingCart.class);
	private static final long serialVersionUID = 1155604242419622177L;
	private Product product;
	private List<String> customComponentCollection;
	private Item item;
	private int id;
	private Integer index;
	
	@Override
	public void prepare() {
		try{
			super.prepare();
		} catch (Exception e) {
			managerException(e, "id: " + id);
		}
	}

	@Override
	public String execute() {
		log.debug("ShoppingCart debug execute");
		return SUCCESS;
	}

	@Action(value="product = #id , shoppingCartList.size = #shoppingCartList.size, customComponentCollection = #customComponentCollection, text = #item.text, color = #item.color, font = #item.font")
	public String addProduct() {
		try{
			log.debug("call addProduct()");
			setUrlToRedirect();
			if(id > 0){
				setProduct(ServiceLocator.getService(ProductServiceImpl.class).getProduct(id, true));
			}
			if(item == null){
				item = new Item();
			}
			ServiceLocator.getService(ImageServiceImpl.class).getItemPNG( item, product, customComponentCollection );
			if(product.getCustomProduct()){
				ServiceLocator.getService(ImageServiceImpl.class).generatePNGImage( item, false);
			}
			List<Item> itemList = getShoppingCartItems();
			if(index == null){//That means a new product
				itemList.add(item);
			}
			else{ //Modify a custom product
				itemList.set(index, item); 
			}
			request.getSession().setAttribute(WebConstants.SHOPPING_CART_ITEMS, itemList);
			log.debug("Item's number in shoppingCart: " + itemList.size());
			return SUCCESS;
		} catch (Exception e) {
			managerException(e, "customComponentCollection: " + customComponentCollection);
			return ERROR;
		}
	}

	public String showShoppingCart(){
		try{
			log.debug("call showShoppingCart()");
			//retrieve errroraction that cames from another action
			if(ActionContext.getContext().getSession().get(WebConstants.ERRORACTION) != null){
				addActionError((String)ActionContext.getContext().getSession().get(WebConstants.ERRORACTION));
				ActionContext.getContext().getSession().remove(WebConstants.ERRORACTION);
			}
			calculateShopingCart();

			//If the process is already done, then go directly to summary
			if( getShoppingCart().getMethodShipping() != null &&
			   getShoppingCart().getMethodShipping() != null ){
				return SUMMARY;
			}
			return SUCCESS;
		} 
		catch(CurrencyNoExistException ce){
			managerExceptionBySupport(ce);
			return ERROR;
		}
		catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	@Action(value="product = #shoppingCartList[index].product.id customComponentCollection = #shoppingCartList[index].nameImage ")
	public String remove(){
		List<Item> itemList = getShoppingCartItems();
		itemList.remove(index.intValue());
		return SUCCESS;
	}
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<String> getCustomComponentCollection() {
		return customComponentCollection;
	}

	public void setCustomComponentCollection(
			List<String> customComponentCollection) {
		this.customComponentCollection = customComponentCollection;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<ItemShoppingCartDTO> getShoppingCartList() {
		if(getShoppingCartItems() != null){
			try {
				return DTOFactory.getItemShoppingCartDTOList(getShoppingCartItems(), getCurrentLanguage(), getCurrentCurrencyCode());
			} 
			catch(CurrencyNoExistException ce){
				managerExceptionBySupport(ce);
			}
			catch (Exception e) {
				managerException(e, "productId: " + id);
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
