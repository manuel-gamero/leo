package com.mg.datamining.clustering.HAC;

import java.io.StringWriter;

import com.mg.datamining.clustering.ClusterImpl;
import com.mg.datamining.clustering.DataItem;

public class HierClusterImpl<T, E> extends ClusterImpl<T, E> implements HierCluster<T, E> {
	private HierCluster<T, E> child1 = null;
	private HierCluster<T, E> child2 = null;
	private double similarity;

	public HierClusterImpl(int clusterId, HierCluster<T, E> child1, HierCluster<T, E> child2, double similarity,
			DataItem<T,E> dataItem) {
		super(clusterId);
		this.child1 = child1;
		this.child2 = child2;
		this.similarity = similarity;
		if (dataItem != null) {
			this.addDataItem(dataItem);
		}
	}

	public HierCluster<T, E> getChild1() {
		return child1;
	}

	public HierCluster<T, E> getChild2() {
		return child2;
	}

	public double getSimilarity() {
		return similarity;
	}

	public double computeSimilarity(HierCluster<T, E> o) {
		return this.getCenter().dotProduct(o.getCenter());
	}

	public String toString() {
		StringWriter sb = new StringWriter();
		String details = getDetails();
		if (details != null) {
			sb.append("Id=" + this.getClusterId() + " " + details);
		} else {
			sb.append("Id=" + this.getClusterId() + " similarity=" + this.similarity);
		}
		if (this.getChild1() != null) {
			sb.append(" C1=" + this.getChild1().getClusterId());
		}
		if (this.getChild2() != null) {
			sb.append(" C2=" + this.getChild2().getClusterId());
		}
		return sb.toString();
	}

	private String getDetails() {
		if ((this.getItems() != null) && (this.getItems().size() > 0)) {
			DataItem<T,E> textDataItem = this.getItems().get(0);
			if (textDataItem != null) {
				T t = (T) textDataItem.getData();
				return t.toString();
			}
		}
		return null;
	}
}
