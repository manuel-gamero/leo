package com.mg.coupon.type;

import java.util.List;

import com.mg.coupon.CouponAbstract;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;

public class CouponFree extends CouponAbstract{

	@Override
	protected void applyCoupon(ShoppingCart shoppingCart, List<Item> itemList, String country) {
		shoppingCart.setDiscountTotalShopping( shoppingCart.getTotalShopping() );
	}

}
