package com.mg.datamining.core;

public class GenericMagnitudeImpl<T> implements GenericMagnitude<T> {

	private T obj = null;
	private double magnitude;

	public GenericMagnitudeImpl(T obj, double magnitude) {
		this.obj = obj;
		this.magnitude = magnitude;
	}

	@Override
	public String toString() {
		return "[" + this.obj.toString() + ", " + this.getMagnitude() + "]";
	}

	@Override
	public int compareTo(GenericMagnitude<T> o) {
		double diff = this.magnitude - o.getMagnitude();
		if (diff > 0) {
			return -1;
		} else if (diff < 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public double getMagnitude() {
		return this.magnitude;
	}

	@Override
	public double getMagnitudeSqd() {
		return this.magnitude * this.magnitude;
	}

	@Override
	public T getObject() {
		return (T) this.obj;
	}

}
