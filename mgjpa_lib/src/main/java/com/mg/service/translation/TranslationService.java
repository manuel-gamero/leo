package com.mg.service.translation;

import com.mg.exception.ServiceException;
import com.mg.model.Translation;
import com.mg.service.Service;

/**
 * Translations service interface.
 * 
 *
 */
public interface TranslationService extends Service {
	
	int saveTranslation (Translation translation)throws ServiceException;
	
	void updateTranslation (Translation translation)throws ServiceException;
	
}