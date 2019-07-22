package com.mg.coupon.type;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.coupon.CouponAbstract;
import com.mg.enums.ProductType;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.currency.CurrencyUtils;

public class Coupon1MediumTaxFree extends CouponAbstract {

	private static Logger log = LogManager.getLogger(Coupon1MediumTaxFree.class);

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
						.equals(ProductType.MEDIUM_POUCH)) {
					item = (Item) itemList.get(i);
					if(item.getText() != null && item.getText().trim().length() > 0){
						String extraPrice = ServiceLocator.getService(ConfigServiceImpl.class).getPriceExtraText(currencyCode);
						shoppingCart.setDiscountExtras( BigDecimal.valueOf( Long.parseLong(extraPrice)) );
						if(shoppingCart.getShippingAddressId() != null){
							BigDecimal taxes = shoppingCart.getShippingAddressId().getProvince().getTax();
							shoppingCart.setDiscountTaxes(  BigDecimal.valueOf( Long.parseLong(extraPrice)).multiply(taxes.divide(new BigDecimal(100))) );
						}
					}
					found = true;
				}
				i++;
			}
			if (item != null) {
				item.setDiscountPrice(CurrencyUtils.priceToLocale(item.getProduct().getPrice(), currencyCode));
				//if(shoppingCart.getShippingAddressId() != null){
					//BigDecimal taxes = shoppingCart.getShippingAddressId().getProvince().getTax();
					//shoppingCart.setDiscountTaxes(  item.getDiscountPrice().multiply(taxes.divide(new BigDecimal(100))) );
				//}
			}
			else{
				shoppingCart.setDiscountTaxes( ZERO );
				shoppingCart.setDiscountExtras( ZERO );
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
