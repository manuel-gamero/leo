package com.mg.service.search.autocompleter.core;

import java.util.Set;

/**
 * We need a normalization strategy to tell how each autocompleter is supposed
 * to treat the data and generate the output format(JSON).
 * 
 * @author MJGP
 * 
 * @param <K>
 * @param <V>
 */
public class DefaultNormalizerStrategy<K, V extends Completable<V>> implements
		NormalizerStrategy<K, V> {

	@Override
	public String normalize(String input) {
		return input.toLowerCase().replaceAll(" \\s+", " ").trim();
	}

	@Override
	public String convertToJSON(String query,
			Set<AutocompleterElement<K, V>> result) {
		// String Builders are the most efficient (must be thread safe)
		StringBuilder namesBuilder = new StringBuilder("[");
		StringBuilder idsBuilder = new StringBuilder("[");
		boolean isFirst = true;
		for (AutocompleterElement<K, V> element : result) {
			if (!isFirst) {
				namesBuilder.append(",");
				idsBuilder.append(",");
			}
			namesBuilder.append("'");
			namesBuilder.append(element.getValue().getAutocompletionText()
					.replaceAll("'", "\\\\'"));
			namesBuilder.append("'");
			idsBuilder.append("'");
			idsBuilder.append(element.getValue().getId());
			idsBuilder.append("'");

			isFirst = false;
		}
		namesBuilder.append("]");
		idsBuilder.append("]");

		String escapedPrefix = query.replaceAll("'", "\\\\'");
		StringBuilder jsonBuilder = new StringBuilder("{query:'");
		jsonBuilder.append(escapedPrefix);
		jsonBuilder.append("',suggestions:");
		jsonBuilder.append(namesBuilder);
		jsonBuilder.append(",data:");
		jsonBuilder.append(idsBuilder);
		jsonBuilder.append("}");

		return jsonBuilder.toString();
	}

	@Override
	public String getDummyJSON(String query) {
		return "{query:'" + query + "', suggestions:[], data:[]}";
	}

}
