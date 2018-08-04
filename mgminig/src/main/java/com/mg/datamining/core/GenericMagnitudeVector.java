package com.mg.datamining.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GenericMagnitudeVector <E> {
	
	public List<GenericMagnitude<E>> getMagnitudes();
	public Map<E, GenericMagnitude<E>> getMagnitudeMap();
	public double dotProduct(GenericMagnitudeVector<E> o);
	public GenericMagnitudeVector<E> add(GenericMagnitudeVector<E> o);
	public GenericMagnitudeVector<E> add(Collection<GenericMagnitudeVector<E>> tmList);
	
}
