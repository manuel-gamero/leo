package com.mg.datamining.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mg.datamining.core.GenericMagnitude;
import com.mg.datamining.core.GenericMagnitudeVector;
import com.mg.datamining.core.GenericMagnitudeVectorImpl;

public class ClusterImpl<T,E> implements ItemCluster<T,E> {

	private GenericMagnitudeVector<E> center = null;
	private List<DataItem<T,E>> items = null;
	private int clusterId;

	public ClusterImpl(int clusterId) {
		this.clusterId = clusterId;
		this.items = new ArrayList<DataItem<T,E>>();
	}

	@Override
	public void clearItems() {
		items.clear();
	}

	@Override
	public GenericMagnitudeVector<E> getCenter() {
		return center;
	}

	@Override
	public void computeCenter() {
		if (this.items.size() == 0) {
			return;
		}
		List<GenericMagnitudeVector<E>> tmList = new ArrayList<GenericMagnitudeVector<E>>();
		for (DataItem<T,E> item : items) {
			tmList.add(item.getMagnitudeVector());
		}
		List<GenericMagnitude<E>> emptyList = Collections.emptyList();
		GenericMagnitudeVector<E> empty = new GenericMagnitudeVectorImpl<E>(emptyList);
		this.center = empty.add(tmList);
	}

	@Override
	public int getClusterId() {
		return clusterId;
	}

	@Override
	public void addDataItem(DataItem<T,E> item) {
		items.add(item);
	}

	public List<DataItem<T,E>> getItems() {
		return items;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id=" + this.clusterId);
		for (DataItem<T,E> item : items) {
			T t = (T) item.getData();
			sb.append("\nProduct Id=" + t.toString());
		}
		return sb.toString();
	}

	@Override
	public void setCenter(GenericMagnitudeVector<E> center) {
		this.center = center;
	}
}
