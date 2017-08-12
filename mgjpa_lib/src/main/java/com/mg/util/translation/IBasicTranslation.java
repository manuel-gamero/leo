package com.mg.util.translation;

import com.mg.model.Translation;

public interface IBasicTranslation {
	
	public Translation getTranslationByDescriptionTransId();
	public void setTranslationByDescriptionTransId(Translation translationByDescriptionTransId);
	
	public Translation getTranslationByNameTransId();
	public void setTranslationByNameTransId(Translation translationByNameTransId);

}
