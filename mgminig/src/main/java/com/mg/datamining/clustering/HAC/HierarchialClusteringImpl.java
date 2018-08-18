package com.mg.datamining.clustering.HAC;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.datamining.clustering.Clusterer;
import com.mg.datamining.clustering.DataItem;
import com.mg.datamining.clustering.ItemCluster;

public class HierarchialClusteringImpl<T,E> implements Clusterer<T,E> {
	private Map<Integer, HierCluster<T,E>> availableClusters = null;
	private List<DataItem<T,E>> textDataSet = null;
	private HierCluster<T,E> rootCluster;
	private int idCount = 0;
	private Map<HierDistance<T,E>, HierDistance<T,E>> allDistance = null;

	public HierarchialClusteringImpl(List<DataItem<T,E>> textDataSet) {
		this.textDataSet = textDataSet;
		this.availableClusters = new HashMap<Integer, HierCluster<T,E>>();
		this.allDistance = new HashMap<HierDistance<T,E>, HierDistance<T,E>>();
	}

	public List<ItemCluster<T,E>> cluster() {
		createInitialClusters();
		while (allDistance.size() > 0) {
			addNextCluster();
		}
		List<ItemCluster<T,E>> clusters = new ArrayList<ItemCluster<T,E>>();
		clusters.add(this.rootCluster);
		return clusters;
	}

	private void createInitialClusters() {
		createInitialSingleItemClusters();
		computeInitialDistances();
	}

	private void createInitialSingleItemClusters() {
		for (DataItem<T,E> dataItem : this.textDataSet) {
			HierClusterImpl<T,E> cluster = new HierClusterImpl<T,E>(this.idCount++, null, null, 1., dataItem);
			cluster.setCenter(dataItem.getMagnitudeVector());
			this.availableClusters.put(cluster.getClusterId(), cluster);
		}
	}

	private void computeInitialDistances() {
		for (HierCluster<T,E> cluster : this.availableClusters.values()) {
			for (HierCluster<T,E> otherCluster : this.availableClusters.values()) {
				if (cluster.getClusterId() != otherCluster.getClusterId()) {
					HierDistance<T,E> hd = new HierDistance<T,E>(cluster, otherCluster);
					if (!this.allDistance.containsKey(hd)) {
						double similarity = cluster.computeSimilarity(otherCluster);
						hd.setSimilarity(similarity);
						this.allDistance.put(hd, hd);
					}
				}
			}
		}
	}

	private void addNextCluster() {
		List<HierDistance<T,E>> sortDist = new ArrayList<HierDistance<T,E>>();
		sortDist.addAll(this.allDistance.keySet());
		Collections.sort(sortDist);
		HierDistance<T,E> hd = sortDist.get(0);
		this.allDistance.remove(hd);
		HierCluster<T,E> cluster = createNewCluster(hd);
		pruneDistances(hd.getC1(), hd.getC2(), sortDist);
		addNewClusterDistances(cluster);
		if (this.allDistance.size() == 0) {
			this.rootCluster = cluster;
		}
	}

	private HierCluster<T,E> createNewCluster(HierDistance<T,E> hd) {
		HierClusterImpl<T,E> cluster = new HierClusterImpl<T,E>(this.idCount++, hd.getC1(), hd.getC2(), hd.getSimilarity(), null);
		cluster.setCenter(hd.getC1().getCenter().add(hd.getC2().getCenter()));
		this.availableClusters.put(cluster.getClusterId(), cluster);
		this.availableClusters.remove(hd.getC1().getClusterId());
		this.availableClusters.remove(hd.getC2().getClusterId());
		return cluster;
	}

	private void pruneDistances(HierCluster<T,E> c1, HierCluster<T,E> c2, List<HierDistance<T,E>> sortDist) {
		for (HierDistance<T,E> hierDistance : sortDist) {
			if ((hierDistance.containsCluster(c1)) || (hierDistance.containsCluster(c2))) {
				this.allDistance.remove(hierDistance);
			}
		}
	}

	private void addNewClusterDistances(HierCluster<T,E> cluster) {
		for (HierCluster<T,E> hc : this.availableClusters.values()) {
			if (hc.getClusterId() != cluster.getClusterId()) {
				HierDistance<T,E> hierDistance = new HierDistance<T,E>(cluster, hc);
				double similarity = cluster.getCenter().dotProduct(hc.getCenter());
				hierDistance.setSimilarity(similarity);
				this.allDistance.put(hierDistance, hierDistance);
			}
		}
	}

	public String toString() {
		StringWriter sb = new StringWriter();
		sb.append("Num of clusters = " + this.idCount + "\n");
		sb.append(printClusterDetails(this.rootCluster, ""));
		return sb.toString();
	}

	private String printClusterDetails(HierCluster<T,E> cluster, String append) {
		StringWriter sb = new StringWriter();
		if (cluster != null) {
			sb.append(cluster.toString());
			String tab = "\t" + append;
			if (cluster.getChild1() != null) {
				sb.append("\n" + tab + "C1=" + printClusterDetails(cluster.getChild1(), tab));
			}
			if (cluster.getChild2() != null) {
				sb.append("\n" + tab + "C2=" + printClusterDetails(cluster.getChild2(), tab));
			}
		}
		return sb.toString();
	}
}