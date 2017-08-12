package com.mg.service.search.autocompleter.core;

/**
 * A core element for the autocompleter
 * 
 * @author MJGP
 * 
 * @param <K>
 * @param <V>
 */
public class AutocompleterElement<K, V extends Completable<V>> implements
		Comparable<AutocompleterElement<K, V>> {

	private K key;
	private V value;
	private double score;
	private String normalizedAutocompletionText;

	public AutocompleterElement(K key, V value) {
		this(key, value, 0.0);
	}

	public AutocompleterElement(K key, V value, double score) {
		this.key = key;
		this.value = value;
		this.score = score;
		normalizedAutocompletionText = "";
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getNormalizedAutocompletionText() {
		return normalizedAutocompletionText;
	}

	public void setNormalizedAutocompletionText(
			String normalizedAutocompletionText) {
		this.normalizedAutocompletionText = normalizedAutocompletionText;
	}

	@Override
	public int compareTo(AutocompleterElement<K, V> anotherObj) {
		if (this == anotherObj)
			return 0;
		return this.normalizedAutocompletionText
				.compareTo(anotherObj.normalizedAutocompletionText);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AutocompleterElement) {
			AutocompleterElement<K, V> anotherObj = (AutocompleterElement<K, V>) obj;
			return (this.compareTo(anotherObj) == 0);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "Key=" + key + ", value=" + value.toString() + ", score="
				+ score + ", normalizedAutocompletionText="
				+ normalizedAutocompletionText;
	}

}
