package com.mg.listener;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.mg.coupon.PromotionManager;

public class CouponListener {
	@PrePersist void onPrePersist(Object entity ) {
		try {
			PromotionManager.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@PostPersist void onPostPersist(Object entity) {}
	@PostLoad void onPostLoad(Object entity) {}
	@PreUpdate void onPreUpdate(Object entity) {
		try {
			PromotionManager.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@PostUpdate void onPostUpdate(Object entity) {}
	@PreRemove void onPreRemove(Object entity) {}
	@PostRemove void onPostRemove(Object entity) {}
}
