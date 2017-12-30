package com.mg.coupon.type;

import java.util.List;

import com.mg.coupon.CouponAbstract;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;

public class PromotionBanner extends CouponAbstract{

	@Override
	protected void applyCoupon(ShoppingCart shoppingCart, List<Item> itemList, String country) {
		//Do not do anything. Use promotion logic just to show a banner image information for
		//collections, products or types.
	}

}
