package com.mg.coupon.type;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.mg.coupon.CouponAbstract;
import com.mg.enums.ProductType;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;
import com.mg.util.currency.CurrencyUtils;

public class Coupon1WalletTaxFree extends CouponAbstract {

	private static Logger log = Logger.getLogger(Coupon1WalletTaxFree.class);

	@Override
	protected void applyCoupon(ShoppingCart shoppingCart, List<Item> itemList,
			String currencyCode) {
		Item item = null;
		int size = itemList.size();
		int i = 0;
		boolean found = false;
		try {
			while (i != size && !found) {
				if (((Item) itemList.get(i)).getProduct().getTypeCode()
						.equals(ProductType.WALLET)) {
					item = (Item) itemList.get(i);
					found = true;
				}
				i++;
			}
			if (item != null) {
				item.setDiscountPrice(CurrencyUtils.priceToLocale(item.getProduct().getPrice(), currencyCode));
				if(shoppingCart.getShippingAddressId() != null){
					BigDecimal taxes = shoppingCart.getShippingAddressId().getProvince().getTax();
					shoppingCart.setDiscountTaxes(  item.getDiscountPrice().multiply(taxes.divide(new BigDecimal(100))) );
				}
			}
			else{
				shoppingCart.setDiscountTaxes( ZERO );
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
