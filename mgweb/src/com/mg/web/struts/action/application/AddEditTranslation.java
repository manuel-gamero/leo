package com.mg.web.struts.action.application;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.enums.Language;
import com.mg.model.Translation;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.translation.TranslationServiceImpl;
import com.mg.util.translation.TranslationUtils;
import com.mg.util.translation.Translations;
import com.mg.web.struts.action.BasicAction;

public class AddEditTranslation extends BasicAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private static final Logger log = LogManager.getLogger(AddEditTranslation.class);
	private Integer id;
	private String textEn;
	private String textFr;
	private String url;
	
	@Override
	public void prepare() throws Exception {
	}
	
	@Override
	public String execute(){
		try{
			if(id != null && id != 0){
				String key = Translation.class + "_" + id;
				Translation translation = ServiceLocator.getService(CacheServiceImpl.class).getTranslationCache().fetch(key);
				setTextEn(TranslationUtils.getTranslation(translation, Language.ENGLISH));
				setTextFr(TranslationUtils.getTranslation(translation, Language.FRENCH));
			}
			return INPUT;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String save(){
		
		try{
			log.debug(" >>> AddEditTranslation save() ");	
			// set the url where this action come from 
			setRefereUrl();
			
			
			if(id != null){
				Translations translationsText = new Translations.StringTranslationBuilder().engString(textEn).frString(textFr).build();
				Translation translation = null; 
				if(id == 0){
					translation = new Translation();
					TranslationUtils.createTranslations(translation, translationsText);
				}
				else{
					String key = Translation.class + "_" + id;
					translation = ServiceLocator.getService(CacheServiceImpl.class).getTranslationCache().fetch(key);
					TranslationUtils.updateTranslations(translation, translationsText);
				}
				ServiceLocator.getService(TranslationServiceImpl.class).updateTranslation(translation);
			}
			log.debug(" <<< AddEditTranslation save() ");
		}
		catch(Exception e){
			managerException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTextEn() {
		return textEn;
	}

	public void setTextEn(String textEn) {
		this.textEn = textEn;
	}

	public String getTextFr() {
		return textFr;
	}

	public void setTextFr(String textFr) {
		this.textFr = textFr;
	}


}
