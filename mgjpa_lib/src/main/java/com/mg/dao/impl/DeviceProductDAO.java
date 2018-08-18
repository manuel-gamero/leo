package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.model.DeviceProduct;

public class DeviceProductDAO extends GenericDaoImpl<DeviceProduct> {
	
	private static final long serialVersionUID = 1L;

	public DeviceProductDAO() {
		super(DeviceProduct.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public DeviceProduct getDeviceProduct(Integer deviceId, Integer productId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);
		parameters.put("deviceId", deviceId);

		return  findOneResult(" select dp " +
							  " from DeviceProduct dp " +
							  " where dp.product.id = :productId " +
							  " and dp.device.id = :deviceId ", parameters);
	}
	
	public List<DeviceProduct> getDeviceProduct(Integer productId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);

		return  findResults(" select dp " +
							  " from DeviceProduct dp " +
							  " where dp.product.id = :productId ", parameters);
	}
	
	public DeviceProduct getDeviceProductGroupByProduct(Integer productId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);

		return  findOneResult(" select dp.product.id, sum(dp.count) as count, " +
							  " sum(dp.shareCount) as shareCount, " +
							  " sum(dp.addCount) as addCount, " +
							  " sum(dp.sellCount) as sellCount, " +
							  " sum(dp.removeCount) as removeCount " +
							  " from DeviceProduct dp " +
							  " where dp.product.id = :productId " +
							  " group by dp.product.id ", parameters);
	}
	
	public List<DeviceProduct> getDeviceProductGroupByProduct() {
		
		return  findResults(" select dp.product.id, sum(dp.count) as count, " +
							  " sum(dp.shareCount) as shareCount, " +
							  " sum(dp.addCount) as addCount, " +
							  " sum(dp.sellCount) as sellCount, " +
							  " sum(dp.removeCount) as removeCount " +
							  " from DeviceProduct dp " +
							  " group by dp.product.id  " );
	}


}
