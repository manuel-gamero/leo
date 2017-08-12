package com.mg.datamining.core;

public interface GenericMagnitude<T> extends Comparable<GenericMagnitude<T>> {
	public double getMagnitude();
	public double getMagnitudeSqd();
	public T getObject();
}
