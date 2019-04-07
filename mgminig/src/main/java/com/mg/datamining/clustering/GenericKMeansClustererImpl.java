package com.mg.datamining.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.datamining.enums.Attribute;
import com.mg.datamining.product.ProductDataSetCreatorImpl;
import com.mg.model.Product;

public class GenericKMeansClustererImpl<T,E> implements Clusterer<T,E> {

	private List<DataItem<T,E>> dataSet = null;
	private List<ItemCluster<T,E>> clusters = null;
	private int numClusters;

	public GenericKMeansClustererImpl(List<DataItem<T,E>> dataSet, int numClusters) {
		this.dataSet = dataSet;
		this.numClusters = numClusters;
	}

	@Override
	public List<ItemCluster<T,E>> cluster() {
		if (this.dataSet.size() == 0) {
			return Collections.emptyList();
		}
		this.intitializeClusters();
		boolean change = true;
		int count = 0;
		while ((count++ < 100) && (change)) {
			clearClusterItems();
			change = reassignClusters();
			computeClusterCenters();
		}
		return this.clusters;
	}

	private void intitializeClusters() {
		this.clusters = new ArrayList<ItemCluster<T,E>>();
		Map<Integer, Integer> usedIndexes = new HashMap<Integer, Integer>();
		for (int i = 0; i < this.numClusters; i++) {
			ClusterImpl<T,E> cluster = new ClusterImpl<T,E>(i);
			cluster.setCenter(getDataItemAtRandom(usedIndexes)
					.getMagnitudeVector());
			this.clusters.add(cluster);
		}
	}

	private DataItem<T,E> getDataItemAtRandom(Map<Integer, Integer> usedIndexes) {
		boolean found = false;
		while (!found) {
			int index = (int) Math.floor(Math.random() * this.dataSet.size());
			if (!usedIndexes.containsKey(index)) {
				usedIndexes.put(index, index);
				return this.dataSet.get(index);
			}
		}
		return null;
	}

	private boolean reassignClusters() {
		int numChanges = 0;
		for (DataItem <T,E>item : this.dataSet) {
			ItemCluster<T,E> newCluster = getClosestCluster(item);
			if ((item.getClusterId() == null)
					|| (item.getClusterId().intValue() != newCluster
							.getClusterId())) {
				numChanges++;
				item.setClusterId(newCluster.getClusterId());
			}
			newCluster.addDataItem(item);
		}
		return (numChanges > 0);
	}

	private void computeClusterCenters() {
		for (ItemCluster<T,E> cluster : this.clusters) {
			cluster.computeCenter();
		}
	}

	private void clearClusterItems() {
		for (ItemCluster<T,E> cluster : this.clusters) {
			cluster.clearItems();
		}
	}

	private ItemCluster<T,E> getClosestCluster(DataItem<T,E> item) {
		ItemCluster<T,E> closestCluster = null;
		Double hightSimilarity = null;
		for (ItemCluster<T,E> cluster : this.clusters) {
			double similarity = cluster.getCenter().dotProduct(
					item.getMagnitudeVector());
			if ((hightSimilarity == null)
					|| (hightSimilarity.doubleValue() < similarity)) {
				hightSimilarity = similarity;
				closestCluster = cluster;
			}
		}
		return closestCluster;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (ItemCluster<T,E> cluster : clusters) {
			sb.append("\n\n");
			sb.append(cluster.toString());
		}
		return sb.toString();
	}

	public static final void main(String[] args) throws Exception {
		DataSetCreator<Product,Attribute> bc = new ProductDataSetCreatorImpl();
		List<DataItem<Product,Attribute>> productData = bc.createLearningData();
		GenericKMeansClustererImpl<Product,Attribute> clusterer = new GenericKMeansClustererImpl<Product,Attribute>(
				productData, 12);
		clusterer.cluster();
	}

	@Override
	public List<ItemCluster<T, E>> getClusters() {
		return clusters;
	}
}
