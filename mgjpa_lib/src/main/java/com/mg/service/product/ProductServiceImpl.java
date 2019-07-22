package com.mg.service.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.CustomComponentImageDAO;
import com.mg.dao.impl.ImageDAO;
import com.mg.dao.impl.ItemDAO;
import com.mg.dao.impl.PriceDAO;
import com.mg.dao.impl.PriceEntryDAO;
import com.mg.dao.impl.ProductDAO;
import com.mg.dao.impl.ProductImageDAO;
import com.mg.dao.impl.ProductOrderDAO;
import com.mg.enums.CollectionStatus;
import com.mg.enums.ImageType;
import com.mg.enums.ProductType;
import com.mg.exception.CacheException;
import com.mg.exception.DaoException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Collection;
import com.mg.model.CustomComponentImage;
import com.mg.model.CustomComponentText;
import com.mg.model.Image;
import com.mg.model.Item;
import com.mg.model.Price;
import com.mg.model.PriceEntry;
import com.mg.model.Product;
import com.mg.model.ProductImage;
import com.mg.model.ProductOrder;
import com.mg.service.ServiceImpl;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.dto.CustomComponentImageDTO;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ImageDTO;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.currency.CurrencyUtils;
import com.mg.util.translation.TranslationUtils;
import com.mg.util.translation.Translations;

/**
 * Provides all product related logic in the system.
 * 
 * 
 */
@Transactional
public class ProductServiceImpl extends ServiceImpl implements ProductService {

	private static final Logger log = LogManager.getLogger(ProductServiceImpl.class);
	private static final Comparator<Object[]> ITEM_COMPARATOR_COUNT = new Comparator<Object[]>() {
		public int compare(Object[] item1, Object[] item2) {
			return ((Long)item2[2]).compareTo((Long)item1[2]);
		}
	};

	public ProductServiceImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Product> getAllProduct() throws ServiceException {
		List<Product> list = null;
		try {
			daoManager.setCommitTransaction(true);
			list = (List<Product>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(ProductDAO.class, em)
									.getAll();
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}
	
	@SuppressWarnings({ "unchecked"})
	@Override
	public List<Product> getAllSaleProduct(final String currencyCode) throws ServiceException, CacheException, ServiceLocatorException {
		List<Product> listSales = new ArrayList<Product>();
		/*List<String> keys = ServiceLocator.getService(CacheServiceImpl.class).getProductCache().getKeys();
		for (String item : keys) {
			com.mg.model.Product product = ServiceLocator.getService(CacheServiceImpl.class).getProductCache().fetch(item);
			if( isSale(product, currencyCode) && !product.getCustomProduct() && product.getStatusCode().equals(ProductStatus.ACTIVE) ){
				list.add(product);
			}		
		}*/
		
		try {
			daoManager.setCommitTransaction(true);
			List<Product> list = (List<Product>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(ProductDAO.class, em)
									.getAllDiscount(currencyCode);
						}
					});
			
			for (Product product : list) {
				listSales.add( ServiceLocator.getService(ProductServiceImpl.class).getProduct(product.getId(), true) );
			}
			
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return listSales;
	}
	
	private boolean isSale(Product product, String currencyCode) {
		if( CurrencyUtils.hasDiscount(product.getPrice(), currencyCode) ){
			return true;
		}
		return false;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ProductOrder> getAllProductOrder() throws ServiceException {
		List<ProductOrder> list = null;
		try {
			daoManager.setCommitTransaction(true);
			list = (List<ProductOrder>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(ProductOrderDAO.class, em).findAll();
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Product> getAllProductForAdmin() throws ServiceException {
		List<Product> list = null;
		try {
			daoManager.setCommitTransaction(true);
			list = (List<Product>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(ProductDAO.class, em)
									.getAllForAdmin();
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override 
	public Product getProductForAdmin(Integer id) throws ServiceException, ServiceLocatorException, CacheException{
		Product product = ServiceLocator.getService(ProductServiceImpl.class)
				.getProduct(id, false);
		Collection collection = ServiceLocator.getService(CollectionServiceImpl.class).getCollection(product.getCollection().getId(), true);
		product.setCollection(collection);
		//Map<Integer, Set<CustomComponentImage>> mapImages = (Map<Integer, Set<CustomComponentImage>>) ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().fetch(Hashtable.class + "_Product_Images");
		product.getImage().setCustomComponentImagesForImageId(new HashSet<CustomComponentImage>(ServiceLocator.getService(ImageServiceImpl.class).getAllCustomComponentImageByImageId(product.getImage().getId())));
		return product;
	}
	
	@Override
	public Product getProduct(final Integer id, boolean useCache) throws ServiceException {
		Product result = null;
		try {
			if(useCache){
				result = ServiceLocator.getService(CacheServiceImpl.class).getProductCache().fetch(Product.class + "_" + id);
			}
			if(result == null){
				daoManager.setCommitTransaction(true);
				result = (Product) daoManager.executeAndHandle(new DaoCommand() {
					@Override
					public Object execute(EntityManager em) throws DaoException {
						//return DaoFactory.getDAO(ProductDAO.class, em).find(id);
						return DaoFactory.getDAO(ProductDAO.class, em).findProductById(id);
					}
				});
				
				ServiceLocator.getService(CacheServiceImpl.class).getProductCache().store(Product.class + "_" + id, result);
			}
		} catch (Exception e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	@Override
	public int saveProduct(final Product product) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					try {
						return DaoFactory.getDAO(ProductDAO.class, em)
								.update(product).getId();
					} catch (Exception e) {
						e.printStackTrace();
						throw new DaoException(e);
					}
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}

	@Override
	public int saveProduct(final List<CustomComponentImageDTO> customComponentImageList,
						   final Product product, final ImageDTO imageDTO, final Translations translationsName,
						   final Translations translationsDesc, 
						   final CustomComponentText customText,
						   final Set<PriceEntry> priceEntrySet) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					int id;
					try {
						String path = ServiceLocator.getService(ConfigServiceImpl.class).getWebImageProdcutLocation();
						Image image = ServiceLocator.getService(ImageServiceImpl.class).getImageForObject(em, imageDTO, ImageType.PRODUCT, getImagenId (product));
						//If there is id product then I set the path to image
						if(product.getId() > 0){
							image.setRealName(path + product.getId() + "/");
						}
						List<CustomComponentImage> list = new ArrayList<CustomComponentImage>();
						if (customComponentImageList != null) {
							for (CustomComponentImageDTO item : customComponentImageList) {
								if (item != null) {
									CustomComponentImage customComponentImage = DTOFactory
										.createCustomComponentImage(item);
									if (item.getImageDTO() != null && item.getImageDTO().getFileFileName() != null) {
										Image imageMask = ServiceLocator.getService(ImageServiceImpl.class).getImageForObject(em, item.getImageDTO(), ImageType.MASK, null );
										customComponentImage.setImageByImageMaskId(imageMask);
										//It is used at save disk level
										item.setImage(imageMask);
									}
									//link the product image all the time
									customComponentImage.setImageByImageId(image);
									//customComponentImage.getCustomComponentCollection().setCollection(product.getCollection());
									if(customComponentImage.getStatusCode() == null){
										customComponentImage.setStatusCode(CollectionStatus.ACTIVE);
									}
									list.add(customComponentImage);
								}
							}
						}
						//Allow to change image just a product NO custom
						//To change image in real product I change just the name of the image
						//later the system will save in disk the new image
						if( product.getId() != 0 && !product.getCustomProduct() && imageDTO != null && imageDTO.getFile() != null){
							Image imageProduct = DaoFactory.getDAO(ImageDAO.class, em).find(product.getImage().getId());
							if(imageProduct != null){
								imageProduct.setName(image.getName());
								product.setImage(imageProduct);
							}
						}
						else{
							product.setImage(image);
							image.setCustomComponentImagesForImageId(new HashSet<CustomComponentImage>(list));
						}
						
						TranslationUtils.updateBaicTranslaction(em, product, translationsName, translationsDesc);

						customText.setImage(image);
						if(product.getImage().getCustomComponentTexts() != null && product.getImage().getCustomComponentTexts().size() > 0){
							updateCustomComponentText(product.getImage().getCustomComponentTexts().iterator().next(), customText);
						}
						else {
							Set<CustomComponentText> customTextSet = new HashSet<CustomComponentText>();
							customTextSet.add(customText);
							product.getImage().setCustomComponentTexts(customTextSet);
						}
						
						//Set the currencies set
						//productPrice.setProduct(product);
						CurrencyUtils.getPrice(product, priceEntrySet);
						product.getPrice().setMethodShipping(null);
						product.getPrice().setProduct(product);
						
						if(product.getId() == 0){
							DaoFactory.getDAO(ProductDAO.class, em).save(product);
							id = product.getId();
						}
						else{
							id = DaoFactory.getDAO(ProductDAO.class, em).update(product).getId();
						}
						
						path = path + id + "/";
						
						//Save the location in image realname
						product.getImage().setRealName(path);
						//Change name of customComponentImage and realnem
						for (CustomComponentImage item : product.getImage().getCustomComponentImagesForImageId()) {
							if(item.getImageByImageMaskId() != null){
								item.getImageByImageMaskId().setRealName(path);
							}
						}
						
						//In case to save a product I have to save the right path for the image to do that I need the product´s id
						if(imageDTO != null && imageDTO.getFile() != null){
							ServiceLocator.getService(ImageServiceImpl.class).saveImage(imageDTO.getFile(), path, image.getName(), false);
						}
						if (id != 0) {
							if (customComponentImageList != null) {
								for (CustomComponentImageDTO item : customComponentImageList) {
									if (item != null && item.getImageDTO() != null && item.getImageDTO().getFile() != null) {
										if(item.getImage().getName().length() > 0){
											ServiceLocator.getService(ImageServiceImpl.class).saveImage(item.getImageDTO().getFile(), path, getName(em, product,item.getImage().getName()), true);
										}
									}
								}
							}
						}
						//Remove product from caches
						ServiceLocator.getService(CacheServiceImpl.class).getProductCache().remove(Product.class + "_" + id);
						ServiceLocator.getService(CacheServiceImpl.class).removeProductDTO(id);
					} catch (Exception e) {
						throw new DaoException(e);
					}
					
					return id;
				}

			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;

	}
	
	private int getImagenId(Product product) {
		if(product != null && product.getImage() != null){
			return product.getImage().getId();
		}
		return 0;
	}
	
	protected String getName(EntityManager em, Product product, String name) throws ServiceException, CacheException, ServiceLocatorException {
		String newName = name;
		//for (CustomComponentImage item : ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImages(product.getImage().getId())) {
		for (CustomComponentImage item : DaoFactory.getDAO(CustomComponentImageDAO.class, em).getAllByImageId(product.getImage().getId())) {
			if(name.equals(item.getImageByImageMaskId().getName())){
				newName = product.getId() + "_" + item.getCustomComponentCollection().getId() + "_" + item.getImageByImageMaskId().getId() + ".png";
				item.getImageByImageMaskId().setName(newName);
				return(newName);
			}
		}
		return(name);
	}

	private void updateCustomComponentText(CustomComponentText next,
			CustomComponentText customText) {
		next.setAlign(customText.getAlign());
		next.setHeight(customText.getHeight());
		next.setMaximum(customText.getMaximum());
		next.setPosLeft(customText.getPosLeft());
		next.setPosTop(customText.getPosTop());
		next.setWidth(customText.getWidth());	
		next.setImageHeight(customText.getImageHeight());
		next.setImageWidth(customText.getImageWidth());
		next.setImage(customText.getImage());
	}

	@SuppressWarnings("unchecked")
	public List<Product> getProductByType(final ProductType type, final Boolean customProduct) throws ServiceException {
		List<Product> list = null;
		try {
			daoManager.setCommitTransaction(true);
			list = (List<Product>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							if( !customProduct ){
								return DaoFactory.getDAO(ProductDAO.class, em).findProductByType(type, customProduct);
							}
							else{
								return DaoFactory.getDAO(ProductDAO.class, em).findCustomProductByType(type, customProduct);
							}
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Product> getProductByCollection(final int collectionId, final Boolean customProduct) throws ServiceException {
		List<Product> list = null;
		try {
			daoManager.setCommitTransaction(true);
			list = (List<Product>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(ProductDAO.class, em)
									.findProductByCollection(collectionId, customProduct);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@Override
	public int savePrice(final Price price)
			throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					price.setCreationDate(new Date());
					DaoFactory.getDAO(PriceDAO.class, em).save(price);
					return(price.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}
	
	@Override
	public int saveProductOrder(final ProductOrder productOrder)
			throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					productOrder.setCreationDate(new Date());
					DaoFactory.getDAO(ProductOrderDAO.class, em).save(productOrder);
					return(productOrder.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}

	@Override
	public void deletePrice(final Price price)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(PriceDAO.class, em).delete(price);
					return(price);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}	
		
	}
	
	@Override
	public void deleteProductOrder(final ProductOrder productOrder)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(ProductOrderDAO.class, em).delete(productOrder);
					return(productOrder);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}	
		
	}

	@Override
	public void deletePriceEntity(final Integer id)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					PriceEntry priceEntry = DaoFactory.getDAO(PriceEntryDAO.class, em).find(id);
					DaoFactory.getDAO(PriceEntryDAO.class, em).delete(priceEntry);
					return(priceEntry);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}	
		
	}
	
	@Override
	public void saveNewPrice(final Integer id, final BigDecimal newPrice)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					PriceEntry priceEntry = DaoFactory.getDAO(PriceEntryDAO.class, em).find(id);
					priceEntry.setDiscount(newPrice);
					DaoFactory.getDAO(PriceEntryDAO.class, em).update(priceEntry);
					return(priceEntry);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}	
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getListItemByProduct(final int productId)
			throws ServiceException {
		List<Object[]> list = null;
		try {
			daoManager.setCommitTransaction(true);
			list = (List<Object[]>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(ItemDAO.class, em)
									.getListItemByProduct(productId);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return getItemList(list);
	}
	
	private List<Item> getItemList(List<Object[]> list) {
		List<Item> itemList = new ArrayList<Item>();
		if( list != null && list.size() > 0){
			Collections.sort(list, ITEM_COMPARATOR_COUNT);
			for (Object[] item : list) {
				Item object = new Item();
				Product product = new Product();
				product.setId((Integer)item[0]);
				object.setNameImage((String)item[1]);
				object.setProduct(product);
				itemList.add(object);
			}
		}
		return itemList;
	}

	@Override
	public int saveProductImage(final ImageDTO imageDTO, final Integer id)
			throws ServiceException {
		ProductImage productImage;
		try {
			daoManager.setCommitTransaction(true);
			productImage = (ProductImage) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					try {
						//Get product
						Product product = DaoFactory.getDAO(ProductDAO.class, em).findProductById(id);
						//Create Image object
						Image image = ServiceLocator.getService(ImageServiceImpl.class).getImageForObject(em, imageDTO, ImageType.PRODGROUP, null);
						//Set the same path for the images group than the product image
						image.setRealName(product.getImage().getRealName());
						//Create productImage object
						ProductImage productImage = new ProductImage();
						productImage.setImage(image);
						productImage.setProduct(product);
						if(product.getProductImages() != null ){
							product.setProductImages(new HashSet<ProductImage>());
						}
						product.getProductImages().add(productImage);
						//Store productImage in databse udpating product object
						DaoFactory.getDAO(ProductDAO.class, em).update(product);
						return productImage;
					} catch (Exception e) {
						throw new DaoException(e);
					}
				}
			});
			String path = ServiceLocator.getService(ConfigServiceImpl.class).getWebImageProdcutLocation();
			path = path + id + "/";
			//Save image in disk
			ServiceLocator.getService(ImageServiceImpl.class).saveImage(imageDTO.getFile(), path, productImage.getImage().getName(), false);
			//Invalidate cache for this product object
			ServiceLocator.getService(CacheServiceImpl.class).getProductCache().remove(Product.class + "_" + id);
			ServiceLocator.getService(CacheServiceImpl.class).removeProductDTO(id);
		} catch (Exception de) {
			throw new ServiceException(de);
		}
		return productImage.getId();
	}

	@Override
	public void deleteProductImage(final ProductImage entity)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(ProductImageDAO.class, em).delete(entity);
					return(entity);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}	
	}

	@Override
	public void deleteProductImageEntity(final Integer id)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					ProductImage entity = DaoFactory.getDAO(ProductImageDAO.class, em).find(id);
					DaoFactory.getDAO(ProductImageDAO.class, em).delete(entity);
					return(entity);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}	
		
	}

	@Override
	public Product getCustomProduct(final Integer collectionId, final ProductType type) throws ServiceException {
		Product result = null;
		try {
			daoManager.setCommitTransaction(true);
			result = (Product) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					//return DaoFactory.getDAO(ProductDAO.class, em).find(id);
					return DaoFactory.getDAO(ProductDAO.class, em).findCustomProductForProduct(collectionId, type);
				}
			});
		} catch (Exception e) {
			throw (new ServiceException(e));
		}
		return result;
	}
}
