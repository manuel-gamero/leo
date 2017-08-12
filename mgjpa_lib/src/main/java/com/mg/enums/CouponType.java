package com.mg.enums;

import com.mg.coupon.Coupon;
import com.mg.coupon.type.Coupon1MediumTaxFree;
import com.mg.coupon.type.Coupon1WalletTaxFree;
import com.mg.coupon.type.CouponDiscount2thFor50;
import com.mg.coupon.type.CouponDiscountTaxes;
import com.mg.coupon.type.CouponDiscountTotal25;
import com.mg.coupon.type.CouponDiscountTotal30;
import com.mg.coupon.type.CouponDiscountTotal50;
import com.mg.coupon.type.CouponFree;

/**
 * 
 * @author MJGP
 *
 */
public enum CouponType implements BasicEnum{
	
	DISCOUNTTAXES("DISCOUNTTAXES") {
		@Override
		public Coupon getCoupon() {
			return new CouponDiscountTaxes();
		}
	},
	DISCOUNTTOTAL25("DISCOUNTTOTAL25") {
		@Override
		public Coupon getCoupon() {
			return new CouponDiscountTotal25();
		}
	},
	DISCOUNTTOTAL30("DISCOUNTTOTAL30") {
		@Override
		public Coupon getCoupon() {
			return new CouponDiscountTotal30();
		}
	},
	DISCOUNTTOTAL50("DISCOUNTTOTAL50") {
		@Override
		public Coupon getCoupon() {
			return new CouponDiscountTotal50();
		}
	},
	DISCOUNT2THFOR50("DISCOUNT2THFOR50") {
		@Override
		public Coupon getCoupon() {
			return new CouponDiscount2thFor50();
		}
	},
	FREE("FREE") {
		@Override
		public Coupon getCoupon() {
			return new CouponFree();
		}
	},
	WALLETTAX1FREE("WALLETTAX1FREE") {
		@Override
		public Coupon getCoupon() {
			return new Coupon1WalletTaxFree();
		}
	},
	MEDIUMTAX1FREE("MEDIUMTAX1FREE") {
		@Override
		public Coupon getCoupon() {
			return new Coupon1MediumTaxFree();
		}
	}
	;
	
	
	private String localizationKey = "enum.coupon.type." + name().toLowerCase();
	

	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getCode() {
		return code;
	}


	private String code;
	
	
	private CouponType(String code){
		this.code = code;
	}
	
	public abstract Coupon getCoupon();
}
