package com.mg.service.product;

import java.io.File;
import java.util.List;

import com.mg.exception.ServiceException;
import com.mg.model.Collection;
import com.mg.model.CustomComponent;
import com.mg.model.CustomComponentCollection;
import com.mg.model.CustomComponentImage;
import com.mg.service.Service;
import com.mg.service.dto.CustomComponentCollectionDTO;
import com.mg.util.translation.Translations;


/**
 * Collection service interface.
 * 
 *
 */
public interface CollectionService extends Service {
	
	
	/**
	 * Save custom component information.  
	 * @param CustomComponet
	 * @return The new custom component identification
	 * @throws ServiceException
	 */
	int saveCustomComponent(CustomComponent customComponent) throws ServiceException;
	
	void updateCustomComponent(CustomComponent customComponent) throws ServiceException;
	
	/**
	 * @return Get the list of all Custom Component
	 * @throws ServiceException
	 */
	List<CustomComponent> getAllCustomComponent()throws ServiceException;
	
	CustomComponent getCustomComponent (final Integer id) throws ServiceException ;
		
	/**
	 * Save collection information.  
	 * @param Collection
	 * @return The new collection identification
	 * @throws ServiceException
	 */
	int saveCollection(final Collection collection, final List<CustomComponentCollectionDTO> customComponentCollections, 
			  final File file, final String fileContentType, final String fileFileName, 
			  final Translations translationsName, final Translations translationsDesc) throws ServiceException;
	
	/**
	 * @return Get the list of all collections
	 * @throws ServiceException
	 */
	List<Collection> getAllCollection()throws ServiceException;
	
	Collection getCollection (final Integer id, boolean useCache) throws ServiceException;
	
	Collection getCollection (final String code) throws ServiceException;
	
	/**
	 * Save custom component collection information.  
	 * @param CustomComponentCollection
	 * @return The new collection identification
	 * @throws ServiceException
	 */
	int saveCustomComponentCollection(CustomComponentCollection customComponentCollection) throws ServiceException;

	boolean deleteCustomComponentCollection(int id)throws ServiceException;
	
	boolean deleteCustomComponentImage(final int id) throws ServiceException;
	
	void updateCustomComponentCollection(CustomComponentCollection customComponentCollection)throws ServiceException;
	
	void updateCustomComponentImage(CustomComponentImage customComponentImage) throws ServiceException;
	
	CustomComponentCollection getCustomComponentCollection (final Integer id) throws ServiceException ;
	
	CustomComponentCollection getCustomComponentCollection(final String imageName, final Integer customComponentId) throws ServiceException;
	
	void updateCustomComponentCollection(final CustomComponentCollection customComponentCollection, Translations translationsName, Translations translationsDesc) throws ServiceException;
	
	List<Collection> getAllCollectionCountGroup() throws ServiceException; 
	
	public List<CustomComponentCollection> getCustomComponentCollectionForTranslation(final String imageName) throws ServiceException;
	
	public CustomComponentCollection getTranslationForCustomComponentCollection(final String imageName)	throws ServiceException;
	
	CustomComponentImage getCustomComponentImage (final Integer id) throws ServiceException ;
	
	public List<Collection> getAllCollectionEntities() throws ServiceException ;
	
	public List<Collection> getAllActiveCollectionEntities() throws ServiceException ;
	
}