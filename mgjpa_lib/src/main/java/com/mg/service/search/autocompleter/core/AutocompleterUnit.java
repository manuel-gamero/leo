package com.mg.service.search.autocompleter.core;


/**
 * A simple class used for autocompletion service. It implements
 * {@link Completable} which imposes all implementing units to provide a
 * unique id and a text they expose for the autocompletion service.
 * 
 * @author MJGP
 * 
 */
public class AutocompleterUnit implements Completable<AutocompleterUnit> {

	private long id;

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public String getAutocompletionText() {
		return autocompletionText;
	}

	protected void setAutocompletionText(String autocompletionText) {
		this.autocompletionText = autocompletionText;
	}

	private String autocompletionText;	

	public AutocompleterUnit(long id, String autocompletionText) {
		this.id = id;
		this.autocompletionText = autocompletionText;		
	}

	@Override
	public int compareTo(AutocompleterUnit o) {
		if (this.getId() > o.getId()) {
			return -1;
		} else if (this.getId() < o.getId()) {
			return +1;
		} else {
			return this.getAutocompletionText().compareTo(
					o.getAutocompletionText());
		}
	}

}
