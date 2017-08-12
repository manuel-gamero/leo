package com.mg.service.coupon;

import java.util.List;

import com.mg.exception.ServiceException;
import com.mg.model.Coupons;
import com.mg.model.CouponsType;
import com.mg.service.Service;
import com.mg.util.translation.Translations;

/**
 * Coupon service interface.
 * 
 *
 */
public interface CouponService extends Service {
	
	CouponsType getCouponsType (final Integer id) throws ServiceException;
	CouponsType getCouponsType (final String typeCode) throws ServiceException;
	List<CouponsType> getAllCouponsType()throws ServiceException;
	int saveCouponsType(final CouponsType couponsType, Translations translationsName, Translations translationsDesc) throws ServiceException;
	void deleteCouponsType(CouponsType couponsType) throws ServiceException;
	
	List<Coupons> getAllCoupons() throws ServiceException;
	void createCouponList(List<Coupons> couponList) throws ServiceException;
	Coupons getCoupon(final String couponName) throws ServiceException;
	void updateCoupons(final Coupons coupon) throws ServiceException;
	Coupons getCouponToUser(final String login) throws ServiceException;
}