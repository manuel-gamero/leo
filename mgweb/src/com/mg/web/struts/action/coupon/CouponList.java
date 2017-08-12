package com.mg.web.struts.action.coupon;

import java.util.List;

import com.mg.model.Coupons;
import com.mg.service.ServiceLocator;
import com.mg.service.coupon.CouponServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class CouponList extends BasicAction implements Preparable {

	private static final long serialVersionUID = 1155604242419622177L;
	private List<Coupons> couponsList;
	
	
	@Override
	public void prepare(){
		try{
			setCouponsList(ServiceLocator.getService(CouponServiceImpl.class).getAllCoupons());
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	@Override
	public String execute() {
		return SUCCESS;
	}

	public List<Coupons> getCouponsList() {
		return couponsList;
	}

	public void setCouponsList(List<Coupons> couponsList) {
		this.couponsList = couponsList;
	}


	

}
