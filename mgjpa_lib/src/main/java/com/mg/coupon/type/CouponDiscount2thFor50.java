package com.mg.coupon.type;

import java.util.List;

import org.apache.log4j.Logger;

import com.mg.coupon.CouponAbstract;
import com.mg.exception.CurrencyNoExistException;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;
import com.mg.util.currency.CurrencyUtils;

public class CouponDiscount2thFor50 extends CouponAbstract{
	
	private static Logger log = Logger.getLogger(CouponDiscount2thFor50.class);
	
	@Override
	protected void applyCoupon(ShoppingCart shoppingCart, List<Item> itemList, String currencyCode) {
		Item itemLowerPrice = null;
		try {
			//I apply coupon just if there is more that one item
			if(itemList.size() > 1){
				itemLowerPrice = itemList.get(0);
				for (Item item : itemList) {
					if( CurrencyUtils.priceToLocale(item.getProduct().getPrice(), currencyCode).floatValue() <= CurrencyUtils.priceToLocale(itemLowerPrice.getProduct().getPrice(), currencyCode).floatValue()){
						itemLowerPrice = item;
					}	
					item.setDiscountPrice( ZERO );
				}
				itemLowerPrice.setDiscountPrice( CurrencyUtils.priceToLocale(itemLowerPrice.getProduct().getPrice(), currencyCode).multiply(PERCENTAGE50) );
				
			}
			else if(itemList.size() == 1){
				((Item)itemList.get(0)).setDiscountPrice( ZERO );
			}
		} catch (CurrencyNoExistException e) {
			log.error(e.getMessage(), e);
		}
	}

}
