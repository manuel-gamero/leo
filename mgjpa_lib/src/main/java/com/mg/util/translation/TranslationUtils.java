package com.mg.util.translation;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.hibernate.LazyInitializationException;

import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.TranslationDAO;
import com.mg.enums.Language;
import com.mg.model.Translation;
import com.mg.model.TranslationEntry;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.init.ConfigServiceImpl;

public class TranslationUtils {
	
	private static final Logger log = Logger.getLogger(TranslationUtils.class);
	
	/**
	 * @param translationEntrySet
	 *            Set with all the translation.
	 * @param Language
	 *            Language that we want to get.
	 * @return The translation for the language argument.
	 */
	
	public static String getTranslation(Translation translation, Language lang) {
		if (lang == null) {
			lang = Language.FRENCH;
		}
		try{
			if (translation != null && translation.getTranslationEntries() != null
					&& translation.getTranslationEntries().size() > 0) {
				for (TranslationEntry item : translation.getTranslationEntries()) {
					if (item.getLangCode().equals(lang.getCode())) {
						return item.getText();
					}
	
				}
			}
		}
		catch(LazyInitializationException le){
			if(translation != null){
				try {
					Translation entry = ServiceLocator.getService(CacheServiceImpl.class).getTranslationCache().fetch(Translation.class + "_" + translation.getId());
					if( entry == null){
						entry = ServiceLocator.getService(ConfigServiceImpl.class).getTranslation( translation.getId() );
						ServiceLocator.getService(CacheServiceImpl.class).getTranslationCache().store(Translation.class + "_" + entry.getId(), entry);
					}
					for (TranslationEntry item : entry.getTranslationEntries()) {
						if (item.getLangCode().equals(lang.getCode())) {
							return item.getText();
						}
	
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					return null;
				}
			}
		}
		return null;
	}

	public static String getTranslation(Translation translation, String lang) {
		return getTranslation(translation, Language.getLanguageByCode(lang));
	}

	/**
	 * @param translationEntrySet
	 *            Set with all the translation and update the right language.
	 * @param Language
	 *            Language that we want to get.
	 * @return The translation for the language argument.
	 */
	public static void updateTranslation(
			Set<TranslationEntry> translationEntrySet, String Language,
			String translation) {
		if (Language == null) {
			Language = Locale.FRANCE.getLanguage();
		}
		if (translationEntrySet != null) {
			for (TranslationEntry item : translationEntrySet) {
				if (item.getLangCode().equals(Language)) {
					item.setText(translation);
				}

			}
		}
	}
	
	public static void addTranslation(
			Translation translation, String language,
			String text) {
		
		TranslationEntry entry = new TranslationEntry();
		entry.setLangCode( language );
		entry.setText( text );
		entry.setTranslation(translation);
		translation.getTranslationEntries().add( entry );
	}

	public static void updateTranslations(Translation translation,
			Translations translations) {
		for (ItemTranslation item : translations.getTranslations()) {
			updateTranslation(translation.getTranslationEntries(), item
					.getLang().getCode(), item.getTranslation());
		}
		try {
			if (ServiceLocator.getService(CacheServiceImpl.class).getTranslationCache().fetch(Translation.class + "_" + translation.getId()) != null){
				ServiceLocator.getService(CacheServiceImpl.class).getTranslationCache().remove(Translation.class + "_" + translation.getId());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	
	public static void createTranslations(Translation translation,
			Translations translations) {
		for (ItemTranslation item : translations.getTranslations()) {
			addTranslation(translation, item
					.getLang().getCode(), item.getTranslation());
		}
	}
	
	/**
	 * @param translations
	 *            : Translations Object to manage the translations.
	 * @return Return Translation model object
	 */
	public static Translation createTranslation(Translations translations) {

		Translation translation = new Translation();
		Set<TranslationEntry> translationSet = new HashSet<TranslationEntry>();

		for (ItemTranslation item : translations.getTranslations()) {
			TranslationEntry entry = new TranslationEntry(0, translation, item
					.getLang().getCode(), item.getTranslation(), new Date());
			translationSet.add(entry);
		}
		translation.setTranslationEntries(translationSet);
		return (translation);
	}

	private Locale getLocate(Language lang) {
		return (new Locale(lang.getCode()));
	}

	private Language getLanguage(Locale locale) {
		return (Language.valueOf(locale.getLanguage()));
	}

	public static void updateBaicTranslaction(EntityManager em,
			IBasicTranslation basicTranslation, Translations newNameTranslations,
			Translations newDescTranslations) {
		Translation transName = null;
		Translation transDesc = null;

		if (basicTranslation.getTranslationByNameTransId() != null
				&& basicTranslation.getTranslationByNameTransId().getId() > 0) {
			transName = DaoFactory.getDAO(TranslationDAO.class, em).find(basicTranslation.getTranslationByNameTransId().getId());
			updateTranslations(transName, newNameTranslations);
		} else {
			transName = TranslationUtils.createTranslation(newNameTranslations);
		}

		if (basicTranslation.getTranslationByDescriptionTransId() != null
				&& basicTranslation.getTranslationByDescriptionTransId()
						.getId() > 0) {
			transDesc = DaoFactory.getDAO(TranslationDAO.class, em).find(basicTranslation.getTranslationByDescriptionTransId().getId());
			updateTranslations(transDesc, newDescTranslations);
		} else {
			transDesc = TranslationUtils.createTranslation(newDescTranslations);
		}

		basicTranslation.setTranslationByNameTransId(transName);
		basicTranslation.setTranslationByDescriptionTransId(transDesc);

	}
	
	public static Translation updateTranslation(EntityManager em, Translation oldTranslation, Translations newTranslations){
		Translation translation = null;
		if (oldTranslation != null	&& oldTranslation.getId() > 0) {
			translation = DaoFactory.getDAO(TranslationDAO.class, em).find(oldTranslation.getId());
			updateTranslations(translation, newTranslations);
		} else {
			translation = TranslationUtils.createTranslation(newTranslations);
		}
		return translation;
	}
	
	public static String getCleanDescription(Translation translation, String lang){
		String description = "";
		String descriptionTotal = (TranslationUtils.getTranslation( translation, lang ));
		if ( descriptionTotal.indexOf("<span") != -1){
			description = descriptionTotal.substring(descriptionTotal.indexOf("<span"), descriptionTotal.length());
		}
		else if( descriptionTotal.indexOf("<p") != -1){
			description = descriptionTotal.substring(descriptionTotal.indexOf("<p"), descriptionTotal.length());
		}	
		return description;
	}
	
}
