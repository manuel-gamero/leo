package com.mg.util.translation;

import java.util.HashSet;
import java.util.Set;

import com.mg.enums.Language;

public class Translations {
	
	private final Set<ItemTranslation> translations;
	
	private Translations(StringTranslationBuilder builder){
		this.translations = builder.translations;
	}

	public Set<ItemTranslation> getTranslations() {
		return translations;
	}

	public static class StringTranslationBuilder {
		private Set<ItemTranslation> translations = new HashSet<ItemTranslation>();
		
		public StringTranslationBuilder engString(String engString) {
			ItemTranslation item = new ItemTranslation(engString, Language.ENGLISH);
			this.translations.add(item);
			return(this);
		}

		public StringTranslationBuilder frString(String frString) {
			ItemTranslation item = new ItemTranslation(frString, Language.FRENCH);
			this.translations.add(item);
			return(this);
		}
		
		public Translations build() {
			return new Translations(this);
		}

	}

}
