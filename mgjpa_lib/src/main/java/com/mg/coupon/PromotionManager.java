package com.mg.coupon;

import java.util.Date;
import java.util.List;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Coupons;
import com.mg.service.ServiceLocator;
import com.mg.service.coupon.CouponServiceImpl;

public class PromotionManager {
	
	private static List<Coupons> promotions;
	
	public static void initialize() throws ServiceException, ServiceLocatorException{
		if( getPromotions() == null){
			setPromotion( ServiceLocator.getService(CouponServiceImpl.class).getAllCouponPromotion());
		}
	}

	public static void reset() throws ServiceException, ServiceLocatorException{
		setPromotion( ServiceLocator.getService(CouponServiceImpl.class).getAllCouponPromotion());
	}
	
	private static Coupons getCouponsPromotion(Date date){
		if( promotions != null && promotions.size() > 0 ){
			for (Coupons coupon : promotions) {
				if(coupon.getCouponsType().getPromotion() && 
				   coupon.getCouponsType().getPromotionStart().before(date) &&
				   coupon.getCouponsType().getPromotionEnd().after(date)){
					return coupon;
				}	
			}
		}
		return null;
	}
	
	public static Coupons getPromotion(){
		return getCouponsPromotion(new Date());
	}
	
	public static boolean isPromotion(){
		return getCouponsPromotion(new Date()) != null ;
	}
	public synchronized static List<Coupons> getPromotions() {
		return promotions;
	}

	public synchronized static void setPromotion(List<Coupons> promotions) {
		PromotionManager.promotions = promotions;
	}

}
