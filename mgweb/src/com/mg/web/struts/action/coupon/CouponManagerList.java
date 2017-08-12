package com.mg.web.struts.action.coupon;

import java.util.List;

import com.mg.model.CouponsType;
import com.mg.service.ServiceLocator;
import com.mg.service.coupon.CouponServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class CouponManagerList extends BasicAction implements Preparable {

	private static final long serialVersionUID = 1155604242419622177L;
	private List<CouponsType> couponsTypeList;
	
	
	@Override
	public void prepare(){
		try{
			setCouponsTypeList(ServiceLocator.getService(CouponServiceImpl.class).getAllCouponsType());
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	@Override
	public String execute() {
		return SUCCESS;
	}

	public List<CouponsType> getCouponsTypeList() {
		return couponsTypeList;
	}

	public void setCouponsTypeList(List<CouponsType> couponsTypeList) {
		this.couponsTypeList = couponsTypeList;
	}

	

}
