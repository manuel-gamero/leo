package com.mg.service.product;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.mg.enums.ProductType;
import com.mg.exception.ServiceException;
import com.mg.model.CustomComponentText;
import com.mg.model.Item;
import com.mg.model.Price;
import com.mg.model.PriceEntry;
import com.mg.model.Product;
import com.mg.model.ProductImage;
import com.mg.service.Service;
import com.mg.service.dto.CustomComponentImageDTO;
import com.mg.util.translation.Translations;

/**
 * Product service interface.
 * 
 * 
 */
public interface ProductService extends Service {

	/**
	 * Save custom component information.
	 * 
	 * @param ProductComponet
	 * @return The new product identification
	 * @throws ServiceException
	 */
	int saveProduct(Product product) throws ServiceException;

	/**
	 * @return Get the list of all product
	 * @throws ServiceException
	 */
	List<Product> getAllProduct() throws ServiceException;

	Product getProduct(Integer id, boolean useCache) throws ServiceException;

	public int saveProduct(final List<CustomComponentImageDTO> customComponentImageList,
			   final Product product, final File file, final String fileContentType,
			   final String fileFileName, final Translations translationsName,
			   final Translations translationsDesc, 
			   final CustomComponentText customText,
			   final Set<PriceEntry> priceEntrySet) throws ServiceException;

	public List<Product> getProductByType(ProductType type, Boolean customProduct)
			throws ServiceException;
	
	public List<Product> getProductByCollection(int collectionId, Boolean customProduct)
			throws ServiceException;
	
	public int savePrice(Price productPrice) throws ServiceException;
	
	public void deletePrice(Price productPrice) throws ServiceException;
	
	public void deletePriceEntity(Integer id) throws ServiceException;
	
	public List<Item> getListItemByProduct(int productId) throws ServiceException;
	
	public List<Product> getAllProductForAdmin() throws ServiceException;
	
	public int saveProductImage(File file, String fileName, Integer id) throws ServiceException;
	
	public void deleteProductImage(ProductImage entity) throws ServiceException;
	
	public void deleteProductImageEntity(final Integer id) throws ServiceException;
	
	/**
	 * @param collectionId
	 * @param type
	 * @return Get the custom product for a certain collection and type.
	 * @throws ServiceException
	 */
	public Product getCustomProduct(final Integer collectionId, final ProductType type) throws ServiceException ;
	
	/**
	 * Save a new price for a product. This new price will be like a discount.
	 * and that make that in fronted appear the two price the old and the new one.
	 * If it´s null then I just display the old price.
	 * @param id
	 * @param newPrice
	 * @throws ServiceException
	 */
	void saveNewPrice(final Integer id, final BigDecimal newPrice) throws ServiceException;
}
