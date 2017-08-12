package com.mg.service.product;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.CollectionDAO;
import com.mg.dao.impl.CustomComponentCollectionDAO;
import com.mg.dao.impl.CustomComponentDAO;
import com.mg.dao.impl.CustomComponentImageDAO;
import com.mg.dao.impl.ImageDAO;
import com.mg.dao.impl.TranslationDAO;
import com.mg.enums.CollectionStatus;
import com.mg.enums.ImageType;
import com.mg.exception.DaoException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Collection;
import com.mg.model.CustomComponent;
import com.mg.model.CustomComponentCollection;
import com.mg.model.CustomComponentImage;
import com.mg.model.Image;
import com.mg.model.Translation;
import com.mg.service.ServiceImpl;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.dto.CustomComponentCollectionDTO;
import com.mg.service.dto.DTOFactory;
import com.mg.service.image.ImageService;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.translation.TranslationUtils;
import com.mg.util.translation.Translations;


/**
 * Provides all collection logic in the system.
 * 
 *
 */
public class CollectionServiceImpl extends ServiceImpl implements CollectionService {
 
	private static final Logger log = Logger.getLogger(CollectionServiceImpl.class);
	private static final Comparator<Object[]> COLLECTION_COMPARATOR_COUNT = new Comparator<Object[]>() {
		public int compare(Object[] item1, Object[] item2) {
			return ((Long)item2[1]).compareTo((Long)item1[1]);
		}
	};
	
	public CollectionServiceImpl() {
		super();
	}

	@Override
	public int saveCustomComponent(final CustomComponent customComponent)
			throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(CustomComponentDAO.class, em).save(customComponent);
					return(customComponent.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;		
	}
	
	@Override
	public void updateCustomComponent(final CustomComponent customComponent)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(CustomComponentDAO.class, em).update(customComponent);
					return(customComponent);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}
	
	@Override
	public void updateCustomComponentCollection(final CustomComponentCollection customComponentCollection, final Translations translationsName, final Translations translationsDesc)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					TranslationUtils.updateBaicTranslaction(em, customComponentCollection, translationsName, translationsDesc);
					if(customComponentCollection.getId() > 0){
						log.debug("Update CustomComponentCollection id : " + customComponentCollection.getId());
						DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).update(customComponentCollection);
					}
					else{
						log.debug("Save CustomComponentCollection id : " + customComponentCollection.getId());
						DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).save(customComponentCollection);
					}
					return(customComponentCollection);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}
	
	protected void persistTranslation(
			Translation translation, EntityManager em) {
		if(translation != null){
			if (translation.getId() == 0){
				DaoFactory.getDAO(TranslationDAO.class, em).save(translation);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<CustomComponent> getAllCustomComponent()
			throws ServiceException {	
		List<CustomComponent> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<CustomComponent>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(CustomComponentDAO.class, em).getAll();				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}	
	
	@Override
	public CustomComponent getCustomComponent (final Integer id) throws ServiceException {
		CustomComponent result;
		try {
			daoManager.setCommitTransaction(true);
			result = (CustomComponent) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CustomComponentDAO.class, em).findEntityById(id);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}	
	
	@Override
	public Collection getCollection (final String code) throws ServiceException {
		Collection result;
		try {
			daoManager.setCommitTransaction(true);
			result = (Collection) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CollectionDAO.class, em).findEntity(code);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}	
	
	@Override
	public int saveCollection(final Collection collection, final List<CustomComponentCollectionDTO> customComponentCollections, 
							  final File file, final String fileContentType, final String fileFileName, 
							  final Translations translationsName, final Translations translationsDesc)
			throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {
					int id = 0;
					try{
						String path = ServiceLocator.getService(ConfigServiceImpl.class).getWebImageCollectionLocation();
						Image image = ServiceLocator.getService(ImageServiceImpl.class).getImageForObject(em, file, fileFileName, ImageType.COLLECTION, getCollectionImageId ( collection ));
						image.setRealName(path);
						if(customComponentCollections != null){		
							collection.setCustomComponentCollections(getCustomComponentCollections(em, customComponentCollections));		
						}
						collection.setImage(image);
						TranslationUtils.updateBaicTranslaction(em, collection, translationsName, translationsDesc);
						id = DaoFactory.getDAO(CollectionDAO.class, em).update(collection).getId();
						
						if(id != 0){
							//Save Collection image after save collection in database in case the any error.
							if(file != null){
								ServiceLocator.getService(ImageServiceImpl.class)
										.saveImage(file, path, image.getName());
							}
							
							//Save CustomComponentCollections images
							if(customComponentCollections != null){
								for (CustomComponentCollectionDTO item : customComponentCollections) {
									if( item != null && item.getFile() != null){
										ServiceLocator.getService(ImageServiceImpl.class).saveImage(item.getFile(), path, item.getImage().getName());
									}
								}
							}
						}
						//Invalidate collection in cache
						ServiceLocator.getService(CacheServiceImpl.class).getCollectionCache().remove(Collection.class + "_" + id);
					}
					catch(Exception e){
						throw new DaoException(e);
					}
					return id;
				}

				private int getCollectionImageId(Collection collection) {
					if (collection != null && collection.getImage() != null){
						return collection.getImage().getId();
					}
					return 0;
				}

				private Set<CustomComponentCollection> getCustomComponentCollections(EntityManager em, List<CustomComponentCollectionDTO> customComponentCollections) throws ServiceLocatorException, ServiceException {
					ImageService imagenService = ServiceLocator.getService(ImageServiceImpl.class);
					String path = ServiceLocator.getService(ConfigServiceImpl.class).getWebImageCollectionLocation();
					Set<CustomComponentCollection> customComponentCollectionList = new HashSet<CustomComponentCollection>();
					for (CustomComponentCollectionDTO item : customComponentCollections) {
						if(item!=null){
							CustomComponentCollection customComponentCollection = DTOFactory.createCustomComponentCollection(item);
							customComponentCollection.setCollection(collection);
							customComponentCollection.setStatusCode(CollectionStatus.ACTIVE);
							//If there is not id, that means that is a new ccc then looking for a translation name and description
							//I put this logic here for issue using a transaction inside another
							//Reuse translation for the same image.name for the customComponentCollection
							//At the creation time
							if(item.getId() == 0){
								try {
									
									CustomComponentCollection ccc =  DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).findTranslateForCustomCompoentCollection(item.getFileFileName());
									if(ccc != null){
										customComponentCollection.setTranslationByNameTransId(ccc.getTranslationByNameTransId());
										customComponentCollection.setTranslationByDescriptionTransId(ccc.getTranslationByDescriptionTransId());
									}
								} catch (Exception e) {
									log.error(e.getMessage(), e);
								}
							}
							if(item.getFileFileName() != null){
								Image duplicate = DaoFactory.getDAO(ImageDAO.class, em).findEntity(item.getFileFileName(), ImageType.COLLECTION);
								Image image;
								if(duplicate == null){
									image = imagenService.getImageForObject(em, item.getFile(),item.getFileFileName(), ImageType.COLLECTION, null);
									image.setRealName(path);
								}
								else {
									image = duplicate;
									item.setFile(null);
									item.setFileFileName(null);
									item.setFileContentType(null);
								}
								customComponentCollection.setImage(image);
								item.setImage(image);
							}
							
							customComponentCollectionList.add(customComponentCollection);
						}
					}
					return customComponentCollectionList;
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Collection> getAllCollection()
			throws ServiceException {	
		List<Collection> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Collection>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(CollectionDAO.class, em).getAll();				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Collection> getAllCollectionEntities()
			throws ServiceException {	
		List<Collection> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Collection>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(CollectionDAO.class, em).getAllEntities();				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Collection> getAllActiveCollectionEntities()
			throws ServiceException {	
		List<Collection> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Collection>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(CollectionDAO.class, em).getAllActiveEntities();				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}	
	
	@Override
	public Collection getCollection (final Integer id, boolean useCache) throws ServiceException {
		Collection result = null;
		try {
			if(useCache){
				result = ServiceLocator.getService(CacheServiceImpl.class).getCollectionCache().fetch(Collection.class + "_" + id);
			}
			if(result == null){
				daoManager.setCommitTransaction(true);
				result = (Collection) daoManager.executeAndHandle(new DaoCommand() {
					@Override
					public Object execute(EntityManager em) throws DaoException {
						return DaoFactory.getDAO(CollectionDAO.class, em).findEntityById(id);
					}
				});
				ServiceLocator.getService(CacheServiceImpl.class).getCollectionCache().store(Collection.class + "_" + id, result);
			}
		} catch (Exception e) {
			throw (new ServiceException(e));
		}
		return result;
	}	
	
	@Override
	public int saveCustomComponentCollection(final CustomComponentCollection customComponentCollection)
			throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).update(customComponentCollection);
					return(customComponentCollection.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
		
	}

	@Override
	public boolean deleteCustomComponentCollection(final int id) throws ServiceException {
		try {
			Image file = null;
			boolean result = false;
			daoManager.setCommitTransaction(true);
			file =  (Image) daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					CustomComponentCollection customComponentCollection = DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).findEntityById(id);
					//Collection collection = DaoFactory.getDAO(CollectionDAO.class, em).find(customComponentCollection.getCollection());
					if(customComponentCollection.getCustomComponentImages().size() == 0){
						Collection collection =  customComponentCollection.getCollection();
						collection.getCustomComponentCollections().remove(customComponentCollection);
						//just when the translation is not used by anothe component we can deleted
						//Otherwise it'll throw an exception for foreign key
						if( customComponentCollection.getTranslationByNameTransId().getCustomComponentCollectionsForNameTransId().size() > 1){
							customComponentCollection.setTranslationByDescriptionTransId(null);
						}
						if( customComponentCollection.getTranslationByDescriptionTransId().getCustomComponentCollectionsForDescriptionTransId().size() > 1 ){
							customComponentCollection.setTranslationByNameTransId(null);
						}
						customComponentCollection.setCollection(null);
						DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).delete(customComponentCollection) ;
						return (customComponentCollection.getImage());
					}
					
					//if I can´t delete the componet at least disable it
					customComponentCollection.setStatusCode(CollectionStatus.INACTIVE);
					DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).update(customComponentCollection);
					return null;
					}
				});
			if( file != null ){
				try {
					result = ServiceLocator.getService(ImageServiceImpl.class).deleteImage(file);
				} catch (Exception e) {
					throw new DaoException(e);
				}
			}
			return result;
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
		
	}
	
	@Override
	public boolean deleteCustomComponentImage(final int id) throws ServiceException {
		try {
			Image file = null;
			boolean result = false;
			daoManager.setCommitTransaction(true);
			file = (Image)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					CustomComponentImage customComponentImage= DaoFactory.getDAO(CustomComponentImageDAO.class, em).find(id);
					if( customComponentImage.getItemComponents().size() == 0 ){
						customComponentImage.setCustomComponentCollection(null);
						customComponentImage.setImageByImageId(null);
						DaoFactory.getDAO(CustomComponentImageDAO.class, em).delete(customComponentImage) ;
						return (customComponentImage.getImageByImageMaskId());
					}
					
					//if I can´t delete the componet at least disable it
					customComponentImage.setStatusCode(CollectionStatus.INACTIVE);
					DaoFactory.getDAO(CustomComponentImageDAO.class, em).update(customComponentImage);
					return null;
				}
			});
			
			if( file != null ){
				try {
					result = ServiceLocator.getService(ImageServiceImpl.class).deleteImage(file);
				} catch (Exception e) {
					throw new DaoException(e);
				}
			}
			return result;
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
		
	}

	@Override
	public CustomComponentCollection getCustomComponentCollection(final Integer id)
			throws ServiceException {
		CustomComponentCollection result;
		try {
			daoManager.setCommitTransaction(true);
			result = (CustomComponentCollection) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).findEntityById(id);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
	@Override
	public CustomComponentCollection getCustomComponentCollection(final String imageName, final Integer customComponentId)
			throws ServiceException {
		CustomComponentCollection result;
		try {
			daoManager.setCommitTransaction(true);
			result = (CustomComponentCollection) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).findEntityByImage(imageName,  customComponentId);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	/**
	 * @param imageName
	 * @return CustomComponentCollection list without translation. In order to provide a translation for 
	 * these that don´t have it.
	 * That happens at add/edit translation CustomComponentCollection time.Once I change the translation
	 * in thre translation's popup
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomComponentCollection> getCustomComponentCollectionForTranslation(final String imageName)
			throws ServiceException {
		List<CustomComponentCollection> result;
		try {
			daoManager.setCommitTransaction(true);
			result = (List<CustomComponentCollection>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).findEntityByImageForTranslate(imageName);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
	/**
	 * @param imageName
	 * @return CustomComponentCollection to reuse its translations.
	 * Searching image with same name and get its translation to be reused.
	 */
	@Override
	public CustomComponentCollection getTranslationForCustomComponentCollection(final String imageName)
			throws ServiceException {
		CustomComponentCollection result;
		try {
			daoManager.setCommitTransaction(true);
			result = (CustomComponentCollection) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).findTranslateForCustomCompoentCollection(imageName);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Collection> getAllCollectionCountGroup()
			throws ServiceException {
		List<Object[]> list = null;
		List<Collection> collectionList = null;
		try {			
			collectionList =  (List<Collection>) ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().fetch("CollectionCountGroup") ;
			if( collectionList == null ){
				daoManager.setCommitTransaction(true);
				list = (List<Object[]>) daoManager.executeAndHandle(new DaoCommand() {
					@Override
					public Object execute(EntityManager em) throws DaoException {	
						return DaoFactory.getDAO(CollectionDAO.class, em).getAllCollectionCount();				
					}
				}) ;
				collectionList = getCollectionList(list);
				ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().store("CollectionCountGroup", collectionList);
			}
		}catch (Exception de) {
			throw (new ServiceException(de));
		}
		return collectionList;
	}

	private List<Collection> getCollectionList(List<Object[]> list) {
		List<Collection> collectionList = new ArrayList<Collection>();
		Collections.sort(list, COLLECTION_COMPARATOR_COUNT);
		for (Object[] item : list) {
			if(!collectionList.contains((Collection) item[0])){
				collectionList.add((Collection) item[0]);
			}
		}
		return collectionList;
	}

	@Override
	public void updateCustomComponentCollection(
			final CustomComponentCollection customComponentCollection)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(CustomComponentCollectionDAO.class, em).update(customComponentCollection);
					return(customComponentCollection.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}

	@Override
	public void updateCustomComponentImage(
			final CustomComponentImage customComponentImage) throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(CustomComponentImageDAO.class, em).update(customComponentImage);
					return(customComponentImage.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}	
	}

	@Override
	public CustomComponentImage getCustomComponentImage(final Integer id)
			throws ServiceException {
		CustomComponentImage result;
		try {
			result = (CustomComponentImage) ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().fetch(CustomComponentImage.class + "_" + id);
			if( result == null ){
				daoManager.setCommitTransaction(true);
				result = (CustomComponentImage) daoManager.executeAndHandle(new DaoCommand() {
					@Override
					public Object execute(EntityManager em) throws DaoException {
						return DaoFactory.getDAO(CustomComponentImageDAO.class, em).find(id);
					}
				});
				ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().store(CustomComponentImage.class + "_" + id, result);
			}
		} catch (Exception e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
}
