package com.mg.datamining.clustering;

import com.mg.datamining.core.GenericMagnitudeVector;

public interface ItemCluster<T,E> {
	
	public void clearItems();
	public GenericMagnitudeVector<E> getCenter();
	public void computeCenter();
	public int getClusterId();
	public void addDataItem(DataItem<T,E> item);
	public void setCenter(GenericMagnitudeVector<E> center);
}
