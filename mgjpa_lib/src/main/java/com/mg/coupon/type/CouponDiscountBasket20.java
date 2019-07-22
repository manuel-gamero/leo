package com.mg.coupon.type;

import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.coupon.CouponAbstract;
import com.mg.enums.ProductType;
import com.mg.exception.CurrencyNoExistException;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;
import com.mg.util.currency.CurrencyUtils;

public class CouponDiscountBasket20 extends CouponAbstract{
	private static Logger log = LogManager.getLogger(CouponDiscountBasket20.class);

	@Override
	protected void applyCoupon(ShoppingCart shoppingCart, List<Item> itemList, String currencyCode) {
		itemList.stream().forEach(item -> {
			if ( item.getProduct().getTypeCode().equals(ProductType.BASKETS)) {
				try {
					item.setDiscountPrice(CurrencyUtils.priceToLocale(item.getProduct().getPrice(), currencyCode).multiply(CouponAbstract.PERCENTAGE20));
				} catch (CurrencyNoExistException e) {
					log.error(e.getMessage(), e);
				}
			}
		});
	}

}
