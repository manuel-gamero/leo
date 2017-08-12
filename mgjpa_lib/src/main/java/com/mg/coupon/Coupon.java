package com.mg.coupon;

import java.util.List;

import com.mg.model.Coupons;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;

public interface Coupon {

	/**
	 * @param coupon the user wants to use
	 * @param shoppingCart instance of shopping cart
	 * @return String localization with ERROR text or text that the coupon is well applied.
	 */
	public String apply(Coupons coupon, ShoppingCart shoppingCart, List<Item> itemList, String country);
	
}
