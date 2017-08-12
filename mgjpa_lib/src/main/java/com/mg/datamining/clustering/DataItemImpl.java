package com.mg.datamining.clustering;

import com.mg.datamining.core.GenericMagnitudeVector;

public class DataItemImpl<T,E> implements DataItem<T,E> {

	private T elem;
	private GenericMagnitudeVector<E> productMagnitudeVector = null;
	private Integer clusterId;

	public DataItemImpl(T elem, GenericMagnitudeVector<E> productMagnitudeVector) {
		super();
		this.elem = elem;
		this.productMagnitudeVector = productMagnitudeVector;
	}

	@Override
	public T getData() {
		return elem;
	}

	@Override
	public GenericMagnitudeVector<E> getMagnitudeVector() {
		return productMagnitudeVector;
	}

	@Override
	public Integer getClusterId() {
		return clusterId;
	}

	@Override
	public void setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
	}

	public double distance(GenericMagnitudeVector<E> other) {
		return this.getMagnitudeVector().dotProduct(other);
	}
}
