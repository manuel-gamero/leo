package com.mg.datamining.clustering;

import java.util.List;

public interface Clusterer<T,E> {
	public List<ItemCluster<T,E>> cluster();
}
