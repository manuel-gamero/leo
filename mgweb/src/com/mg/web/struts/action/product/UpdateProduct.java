package com.mg.web.struts.action.product;

import java.util.List;

import org.apache.log4j.Logger;

import com.mg.model.Item;

public class UpdateProduct extends Product{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UpdateProduct.class);
	private Item item;
	private Integer index;
	
	@Override
	public void prepare() {
		try {
			super.prepare();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			managerException(e, "productId: " + getId());
		}
	}
	
	@Override
	public String execute(){
		List<Item> itemList = getShoppingCartItems();
		item = itemList.get(index.intValue());
		setId(item.getProduct().getId());
		String customImage = item.getNameImage();
		//Set custom with file name without ext.
		setCustom(customImage.replace(".png", ""));
		return super.execute();
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public boolean getUpdate(){
		return true;
	}

}
