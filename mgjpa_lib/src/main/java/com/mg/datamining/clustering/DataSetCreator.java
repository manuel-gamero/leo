package com.mg.datamining.clustering;

import java.util.List;

public interface DataSetCreator<T,E> {
	 public List<DataItem<T,E>> createLearningData() throws Exception ;
}
