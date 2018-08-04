package com.mg.datamining.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericMagnitudeVectorImpl<E> implements GenericMagnitudeVector<E> {

	private Map<E, GenericMagnitude<E>> magnitudesMap = null;

	public GenericMagnitudeVectorImpl(List<GenericMagnitude<E>> magnitudes) {
		normalize(magnitudes);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void normalize(List<GenericMagnitude<E>> magnitudes) {
		magnitudesMap = new HashMap<E, GenericMagnitude<E>>();
		if ((magnitudes == null) || (magnitudes.size() == 0)) {
			return;
		}
		double sumSqd = 0.;
		for (GenericMagnitude<E> tm : magnitudes) {
			sumSqd += tm.getMagnitudeSqd();
		}
		if (sumSqd == 0.) {
			sumSqd = 1. / magnitudes.size();
		}
		double normFactor = Math.sqrt(sumSqd);
		for (GenericMagnitude<E> tm : magnitudes) {
			GenericMagnitude<E> otherTm = this.magnitudesMap.get(tm.getObject());
			double magnitude = tm.getMagnitude();
			if (otherTm != null) {
				magnitude = mergeMagnitudes(magnitude, otherTm.getMagnitude()
						* normFactor);
			}
			GenericMagnitude<E> normalizedTm = new GenericMagnitudeImpl(tm.getObject(),
					(magnitude / normFactor));
			this.magnitudesMap.put(tm.getObject(), normalizedTm);
		}
	}

	public List<GenericMagnitude<E>> getMagnitudes() {
		List<GenericMagnitude<E>> sortedTagMagnitudes = new ArrayList<GenericMagnitude<E>>();
		sortedTagMagnitudes.addAll(magnitudesMap.values());
		Collections.sort(sortedTagMagnitudes);
		return sortedTagMagnitudes;
	}

	public Map<E, GenericMagnitude<E>> getMagnitudeMap() {
		return this.magnitudesMap;
	}

	private double mergeMagnitudes(double a, double b) {
		return Math.sqrt(a * a + b * b);
	}
	
	public double dotProduct(GenericMagnitudeVector<E> o) {
		Map<E, GenericMagnitude<E>> otherMap = o.getMagnitudeMap();
		double dotProduct = 0.;
		for (E item : this.magnitudesMap.keySet()) {
			GenericMagnitude<E> otherTm = otherMap.get(item);
			if (otherTm != null) {
				GenericMagnitude<E> tm = this.magnitudesMap.get(item);
				dotProduct += tm.getMagnitude() * otherTm.getMagnitude();
			}
		}
		return dotProduct;
	}

	public GenericMagnitudeVector<E> add(GenericMagnitudeVector<E> o) {
		Map<E, GenericMagnitude<E>> otherMap = o.getMagnitudeMap();
		Map<E, E> uniqueTags = new HashMap<E, E>();
		for (E item : this.magnitudesMap.keySet()) {
			uniqueTags.put(item, item);
		}
		for (E item : otherMap.keySet()) {
			uniqueTags.put(item, item);
		}
		List<GenericMagnitude<E>> tagMagnitudesList = new ArrayList<GenericMagnitude<E>>(
				uniqueTags.size());
		for (E item : uniqueTags.keySet()) {
			GenericMagnitude<E> tm = mergeObjectMagnitudes(
					this.magnitudesMap.get(item), otherMap.get(item));
			tagMagnitudesList.add(tm);
		}
		return new GenericMagnitudeVectorImpl<E>(tagMagnitudesList);
	}

	public GenericMagnitudeVector<E> add(Collection<GenericMagnitudeVector<E>> tmList) {
		Map<E, Double> uniqueTags = new HashMap<E, Double>();
		for (GenericMagnitude<E> tagMagnitude : this.magnitudesMap.values()) {
			uniqueTags.put(tagMagnitude.getObject(),
					new Double(tagMagnitude.getMagnitudeSqd()));
		}
		for (GenericMagnitudeVector<E> tmv : tmList) {
			Map<E, GenericMagnitude<E>> tagMap = tmv.getMagnitudeMap();
			for (GenericMagnitude<E> tm : tagMap.values()) {
				Double sumSqd = uniqueTags.get(tm.getObject());
				if (sumSqd == null) {
					uniqueTags.put(tm.getObject(), tm.getMagnitudeSqd());
				} else {
					sumSqd = new Double(sumSqd.doubleValue()
							+ tm.getMagnitudeSqd());
					uniqueTags.put(tm.getObject(), sumSqd);
				}
			}
		}
		List<GenericMagnitude<E>> newList = new ArrayList<GenericMagnitude<E>>();
		for (E tag : uniqueTags.keySet()) {
			newList.add(new GenericMagnitudeImpl<E>(tag,
					Math.sqrt(uniqueTags.get(tag))));
		}
		return new GenericMagnitudeVectorImpl<E>(newList);
	}

	private GenericMagnitude<E> mergeObjectMagnitudes(GenericMagnitude<E> a, GenericMagnitude<E> b) {
		if (a == null) {
			if (b == null) {
				return null;
			}
			return b;
		} else if (b == null) {
			return a;
		} else {
			double magnitude = mergeMagnitudes(a.getMagnitude(),
					b.getMagnitude());
			return new GenericMagnitudeImpl<E>(a.getObject(), magnitude);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		List<GenericMagnitude<E>> sortedList = getMagnitudes();
		double sumSqd = 0.;
		for (GenericMagnitude<E> tm : sortedList) {
			sb.append(tm);
			sumSqd += tm.getMagnitude() * tm.getMagnitude();
		}
		sb.append("\nSumSqd = " + sumSqd);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		List<GenericMagnitude<String>> tmList = new ArrayList<GenericMagnitude<String>>();
		tmList.add(new GenericMagnitudeImpl<String>("a",1.));
		tmList.add(new GenericMagnitudeImpl<String>("b",2.));
		tmList.add(new GenericMagnitudeImpl<String>("c",1.5));
		tmList.add(new GenericMagnitudeImpl<String>("a",1.));
		
		GenericMagnitudeVector<String> tmVector1 = new GenericMagnitudeVectorImpl<String>(tmList);
		System.out.println(tmVector1);

	}
}
