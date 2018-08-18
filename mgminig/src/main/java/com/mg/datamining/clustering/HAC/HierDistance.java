package com.mg.datamining.clustering.HAC;

public class HierDistance<T, E> implements Comparable<HierDistance<T, E>> {
	private HierCluster<T, E> c1 = null;
	private HierCluster<T, E> c2 = null;
	private double similarity;
	private int hashCode;

	public HierDistance(HierCluster<T, E> c1, HierCluster<T, E> c2) {
		this.c1 = c1;
		this.c2 = c2;
		hashCode = ("" + c1.getClusterId()).hashCode() + ("" + c2.getClusterId()).hashCode();
	}

	public boolean equals(Object obj) {
		return (this.hashCode() == obj.hashCode());
	}

	public int hashCode() {
		return this.hashCode;
	}

	public HierCluster<T, E> getC1() {
		return c1;
	}

	public HierCluster<T, E> getC2() {
		return c2;
	}

	public double getSimilarity() {
		return this.similarity;
	}

	public boolean containsCluster(HierCluster<T, E> hci) {
		if ((this.getC1() == null) || (this.getC2() == null)) {
			return false;
		}
		if (hci.getClusterId() == this.getC1().getClusterId()) {
			return true;
		}
		if (hci.getClusterId() == this.getC2().getClusterId()) {
			return true;
		}
		return false;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public int compareTo(HierDistance<T, E> o) {
		double diff = o.getSimilarity() - this.similarity;
		if (diff > 0) {
			return 1;
		} else if (diff < 0) {
			return -1;
		}
		return 0;
	}
}
