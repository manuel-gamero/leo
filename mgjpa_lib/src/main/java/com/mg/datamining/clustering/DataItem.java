package com.mg.datamining.clustering;

import com.mg.datamining.core.GenericMagnitudeVector;

public interface DataItem<T, E> {
	public T getData();
	public GenericMagnitudeVector<E> getMagnitudeVector();
	public Integer getClusterId();
	public void setClusterId(Integer clusterId);
}
