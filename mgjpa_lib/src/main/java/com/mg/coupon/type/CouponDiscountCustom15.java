package com.mg.coupon.type;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.coupon.CouponAbstract;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.currency.CurrencyUtils;

public class CouponDiscountCustom15 extends CouponAbstract{
	private static Logger log = LogManager.getLogger(CouponDiscountCustom15.class);

	@Override
	protected void applyCoupon(ShoppingCart shoppingCart, List<Item> itemList, String currencyCode) {
		try {
			Item item = null;
			int size = itemList.size();
			String extraPrice = ServiceLocator.getService(ConfigServiceImpl.class).getPriceExtraText(currencyCode);
			BigDecimal partialTotalExtra = ZERO;
			int i = 0;
			
			while (i != size ) {
				if (((Item) itemList.get(i)).getProduct().getCustomProduct()) {
					item = ((Item) itemList.get(i));
					item.setDiscountPrice(CurrencyUtils.priceToLocale(item.getProduct().getPrice(), currencyCode).multiply(PERCENTAGE15));
					if(item.getText() != null && item.getText().trim().length() > 0){
						partialTotalExtra = partialTotalExtra.add( new BigDecimal(extraPrice) );	
					}
				}
				i++;
			}
			shoppingCart.setDiscountExtras(partialTotalExtra.multiply(PERCENTAGE15));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
