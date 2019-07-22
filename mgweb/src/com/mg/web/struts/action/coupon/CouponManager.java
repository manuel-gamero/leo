package com.mg.web.struts.action.coupon;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.annotation.Action;
import com.mg.coupon.CouponAbstract;
import com.mg.enums.CouponStatus;
import com.mg.model.Coupons;
import com.mg.service.ServiceLocator;
import com.mg.service.coupon.CouponServiceImpl;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.ActionContext;

public class CouponManager extends BasicAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private static final Logger log = LogManager.getLogger(CouponManager.class);
	private String couponName;
	private Coupons coupon;
	

	public void validate() {
		log.debug("validate user");
		String error = null;
		try {
			if(couponName == null || couponName.length() == 0 ){
				error = getText("bolsos.coupon.error.empty");
			}
			else{
				coupon = ServiceLocator.getService(CouponServiceImpl.class).getCoupon(couponName.trim());
				if( coupon == null){
					error = getText("bolsos.coupon.error.noexist");
				}
				else{
					if( coupon.getStatusCode().equals(CouponStatus.INACTIVE) ){
						error = getText("bolsos.coupon.error.inactive");
					}
					else if ( /*coupon.getStatusCode().equals(CouponStatus.USING) ||*/ coupon.getStatusCode().equals(CouponStatus.SEND)){
						error = getText("bolsos.coupon.error.inuse");
					}
				}
			}
			//Propagate erroraction between actions
			ActionContext.getContext().getSession().put(WebConstants.ERRORACTION, error);
			if(error != null){
				//put the actionError otherwise I execute  execute method
				addActionError(error);
				//If there is an error then remove session coupon and put active again
				//Coupons coupon = getCoupon();
				//coupon.setStatusCode(CouponStatus.ACTIVE);
				//ServiceLocator.getService(CouponServiceImpl.class).updateCoupons(coupon);
				//If there is an error clean up all the old discounts
				CouponAbstract.cleanDiscount(getShoppingCart(), getShoppingCartItems());
				request.getSession().removeAttribute(WebConstants.SHOPPING_CART_COUPON);
			}
		} catch (Exception e) {
			managerException(e, "couponName: " + couponName);
		}
	}
	
	@Override
	@Action(value="couponName = #couponName, status = #coupon.statusCode")
	public String execute() {
		log.debug("CouponManager using coupon: " + couponName);
		try {
			if(couponName != null && couponName.length() > 0){
				//Active again if there is a coupon
				Coupons oldCoupon = getCoupon();
				if( oldCoupon != null && !oldCoupon.getCouponName().equals(coupon.getCouponName()) ){
					oldCoupon.setStatusCode(CouponStatus.ACTIVE);
					ServiceLocator.getService(CouponServiceImpl.class).updateCoupons(oldCoupon);
				}
				if(coupon != null && getShoppingCart() != null){
					coupon.setStatusCode(CouponStatus.USING);
					ServiceLocator.getService(CouponServiceImpl.class).updateCoupons(coupon);
				}
				request.getSession().setAttribute(WebConstants.SHOPPING_CART_COUPON , coupon);
			}
			return SUCCESS;
		} catch (Exception e) {
			managerException(e, "couponName: " + couponName);
			return ERROR;
		}
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}



	

}
