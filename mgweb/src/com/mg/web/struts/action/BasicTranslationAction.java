package com.mg.web.struts.action;

import com.mg.enums.Language;
import com.mg.util.translation.IBasicTranslation;
import com.mg.util.translation.TranslationUtils;

public class BasicTranslationAction extends BasicAction {

	private static final long serialVersionUID = -815933298666032664L;
	protected String nameEn;
	protected String nameFr;
	protected String descEn;
	protected String descFr;

	public void setValueTranslation(IBasicTranslation translation){
		if(translation != null ){
			if (translation.getTranslationByNameTransId() != null ) {
				nameEn = TranslationUtils.getTranslation(translation.getTranslationByNameTransId(), Language.ENGLISH);
				nameFr = TranslationUtils.getTranslation(translation.getTranslationByNameTransId(), Language.FRENCH);
			}
			if (translation.getTranslationByDescriptionTransId() != null ) {
				descEn = TranslationUtils.getTranslation(translation.getTranslationByDescriptionTransId(), Language.ENGLISH);
				descFr = TranslationUtils.getTranslation(translation.getTranslationByDescriptionTransId(), Language.FRENCH);
			}
		}
	}
	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameFr() {
		return nameFr;
	}

	public void setNameFr(String nameFr) {
		this.nameFr = nameFr;
	}

	public String getDescEn() {
		return descEn;
	}

	public void setDescEn(String descEn) {
		this.descEn = descEn;
	}

	public String getDescFr() {
		return descFr;
	}

	public void setDescFr(String descFr) {
		this.descFr = descFr;
	}

}
