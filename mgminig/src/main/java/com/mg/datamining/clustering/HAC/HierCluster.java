package com.mg.datamining.clustering.HAC;

import com.mg.datamining.clustering.ItemCluster;

public interface HierCluster<T,E> extends ItemCluster<T,E> {
    
	public HierCluster<T,E> getChild1() ;
    public HierCluster<T,E> getChild2();
    public double getSimilarity() ;
    public double computeSimilarity(HierCluster<T,E> o);

}
