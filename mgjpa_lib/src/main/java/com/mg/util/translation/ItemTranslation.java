package com.mg.util.translation;

import com.mg.enums.Language;

public class ItemTranslation {
	
	private final String translation;
	private final Language lang;
	
	public ItemTranslation(String translation, Language lang){
		this.translation = translation;
		this.lang = lang;
	}

	public String getTranslation() {
		return translation;
	}

	public Language getLang() {
		return lang;
	}
	
	

}
