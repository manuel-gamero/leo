package com.mg.coupon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.mg.enums.CouponStatus;
import com.mg.model.Coupons;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;

public abstract class CouponAbstract implements Coupon{

	protected final static BigDecimal ZERO = new BigDecimal(0.00).setScale(2, RoundingMode.CEILING);
	protected final static BigDecimal PERCENTAGE25 = new BigDecimal(0.25).setScale(2, RoundingMode.CEILING);
	protected final static BigDecimal PERCENTAGE30 = new BigDecimal(0.30).setScale(2, RoundingMode.CEILING);
	protected final static BigDecimal PERCENTAGE50 = new BigDecimal(0.50).setScale(2, RoundingMode.CEILING);
	/* (non-Javadoc)
	 * @see com.mg.coupon.Coupon#apply(com.mg.model.Coupons, com.mg.model.ShoppingCart)
	 */
	@Override
	public String apply(Coupons coupon, ShoppingCart shoppingCart, List<Item> itemList, String country ){
		//In order to apply the right discount without have in count old coupon
		//I need to clean all the discount to apply the right discount after.
		cleanDiscount( shoppingCart, itemList );
		//If the coupon is active
		if( coupon.getStatusCode().equals(CouponStatus.USING) || coupon.getStatusCode().equals(CouponStatus.SEND) ){
			//If there are times to use it
			if ( coupon.getCouponsUsers().size() <= coupon.getCouponsType().getMultiTime() ){
				applyCoupon(shoppingCart, itemList, country);
				return "bolsos.coupon.apply";
			}
			return "bolsos.coupon.error.inactive";
		}
		return "bolsos.coupon.error.noexist";
	}
	
	public static void cleanDiscount(ShoppingCart shoppingCart, List<Item> itemList) {
		shoppingCart.setDiscountExtras(ZERO);
		shoppingCart.setDiscountShippingFees(ZERO);
		shoppingCart.setDiscountTaxes(ZERO);
		shoppingCart.setDiscountTotal(ZERO);
		shoppingCart.setDiscountTotalShopping(ZERO);
		for (Item item : itemList) {
			item.setDiscountPrice(ZERO);
		}
	}

	protected abstract void applyCoupon( ShoppingCart shoppingCart, List<Item> itemList, String country );
}
