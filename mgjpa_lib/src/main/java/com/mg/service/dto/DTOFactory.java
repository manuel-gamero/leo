package com.mg.service.dto;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mg.enums.CollectionStatus;
import com.mg.enums.ImageType;
import com.mg.enums.Language;
import com.mg.enums.ProductStatus;
import com.mg.exception.CacheException;
import com.mg.exception.CurrencyNoExistException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Collection;
import com.mg.model.CustomComponent;
import com.mg.model.CustomComponentCollection;
import com.mg.model.CustomComponentImage;
import com.mg.model.CustomComponentText;
import com.mg.model.Image;
import com.mg.model.Item;
import com.mg.model.ItemComponent;
import com.mg.model.MethodShipping;
import com.mg.model.Product;
import com.mg.model.ProductImage;
import com.mg.model.ShoppingCart;
import com.mg.model.ShoppingCartItem;
import com.mg.model.UserAddress;
import com.mg.model.Users;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.image.ImageService;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.util.currency.CurrencyUtils;
import com.mg.util.exception.ExceptionHandler;
import com.mg.util.text.StringUtils;
import com.mg.util.translation.TranslationUtils;

/**
 * Creates, converts, and manages all DTOs in the system.
 *  
 *
 */
public final class DTOFactory {

	private static Logger log = Logger.getLogger(DTOFactory.class);
	protected static final DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));

	private DTOFactory(){}

	/**
	 * Creates  UserSessionDTO from a User model.
	 * After authenticating a user, a useful object is returned to 
	 * the calling client who can use it for session managment. 
	 *
	 * @param user
	 * @return
	 */
	public static UserSessionDTO getUserSessionDTO(Users user){
		return UserSessionDTO.valueOf(user.getId(), user.getLogin(), Language.ENGLISH.getValue(), Language.ENGLISH.getValue(), 10, user.getEmail(), user.getTypeCode(), user.getStatusCode(), user.getUserAddresses());
	}	
	
	private static String format(double value, String pattern, String language, String country) {
		Locale loc = new Locale(language, country);
		NumberFormat nf = NumberFormat.getInstance(loc);
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern(pattern);
		return nf.format(value);
	}
	
	private static String format(double value, String pattern) {
		return format(value, pattern, "en", "US");
	}

	public static CustomComponentCollection createCustomComponentCollection(
			CustomComponentCollectionDTO item) {
		CustomComponentCollection c = new CustomComponentCollection();
		c.setCollection(item.getCollection());
		c.setCreationDate(item.getCreationDate());
		c.setCustomComponent(item.getCustomComponent());
		c.setId(item.getId());
		c.setImage(item.getImage());
		c.setTypeCode(item.getTypeCode());
		c.setValue(item.getValue());
		return c;
	}
	
	public static CustomComponentImage createCustomComponentImage(
			CustomComponentImageDTO item) {
		CustomComponentImage c = new CustomComponentImage();
		c.setId(item.getId());
		c.setCreationDate(item.getCreationDate());
		c.setCustomComponentCollection(item.getCustomComponentCollection());
		c.setImageByImageId(item.getImage());
		c.setImageByImageMaskId(item.getImageByImageMaskId());
		return c;
	}

	public static List<CustomComponentCollection> createCustomComponentCollectionList(
			List<CustomComponentCollectionDTO> customComponentCollections, Collection collection) {
		List<CustomComponentCollection> customComponentCollectionList = null;
		try {
			ImageService imagenService = ServiceLocator.getService(ImageServiceImpl.class);
		
		if(customComponentCollections != null){
			customComponentCollectionList = new ArrayList<CustomComponentCollection>();
			for (CustomComponentCollectionDTO item : customComponentCollections) {
				if(item!=null){
					CustomComponentCollection customComponentCollection = DTOFactory.createCustomComponentCollection(item);
					customComponentCollection.setCollection(collection);
					if(item.getImageDTO() != null && item.getImageDTO().getFileFileName() != null){
						Image image = imagenService.getImage(item.getImageDTO().getFile(),item.getImageDTO().getFileFileName(), ImageType.COLLECTION);
						customComponentCollection.setImage(image);
						item.setImage(image);
					}
					customComponentCollectionList.add(customComponentCollection);
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return customComponentCollectionList;
	}
	
	public static List<ProductDTO> getProductDTOList( List<Product> listProduct, String lang, String country, boolean large){
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		for (Product product : listProduct) {
			try{
				if( product != null ){
					list.add(DTOFactory.getProductDTO(product.getId(), lang, country, large));
				}
			}
			catch(CurrencyNoExistException ce){
				if( product.getStatusCode().equals(ProductStatus.ACTIVE) && 
					product.getCollection().getStatusCode().equals(ProductStatus.ACTIVE) ){
					ExceptionHandler.handleExceptionBySupport(ce);
				}
			}
			catch(Exception e){
				ExceptionHandler.handleException(e, null, null);
			}
		}
		return list;
	}

	public static ProductDTO getProductDTO (int id, String lang, String country, boolean large) throws CacheException, ServiceLocatorException, CurrencyNoExistException, ServiceException{
		String key = ProductDTO.class + "_" + id + "_" + lang + "_" + country + "_" + large;
		ProductDTO productDTO = (ProductDTO) ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().fetch(key);
		if( productDTO == null ){
			productDTO = DTOFactory.getProductDTO(ServiceLocator.getService(ProductServiceImpl.class).getProduct(id, true), lang ,country , large);
			if( productDTO != null ){
				ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().store(key, productDTO);
			}
		}
		return productDTO;
	}
	public static ProductDTO getProductDTO(Product product, String lang, String currencyCode, boolean large) throws CurrencyNoExistException, ServiceException, ServiceLocatorException, CacheException{
		if(product != null){
			ProductDTO productViewDTO = new ProductDTO();
			//productViewDTO.setCollection( getCollectionDTO(product.getCollection(), lang) );
			Collection collection = ServiceLocator.getService(CollectionServiceImpl.class).getCollection(product.getCollection().getId(), true);
			productViewDTO.setCollection( getCollectionDTO(collection , lang) );
			productViewDTO.setCost(product.getCost());
			productViewDTO.setDepth(product.getDepth());
			productViewDTO.setDescription(TranslationUtils.getTranslation(product.getTranslationByDescriptionTransId(), lang ));
			productViewDTO.setHeight(product.getHeight());
			productViewDTO.setId(product.getId());
			productViewDTO.setImage(product.getImage());
			productViewDTO.setMsrp(CurrencyUtils.displayPriceLocale(product.getPrice(), lang, currencyCode));
			productViewDTO.setOldPrice(CurrencyUtils.displayPriceWithoutDiscountLocale(product.getPrice(), lang, currencyCode));
			productViewDTO.setHasDiscount(CurrencyUtils.hasDiscount(product.getPrice(), currencyCode));
			productViewDTO.setName(TranslationUtils.getTranslation(product.getTranslationByNameTransId(), lang ));
			productViewDTO.setStatusCode(product.getStatusCode());
			productViewDTO.setTypeCode(product.getTypeCode());
			productViewDTO.setWeight(product.getWeight());
			productViewDTO.setWidth(product.getWidth());
			productViewDTO.setCustomProduct(product.getCustomProduct());
			productViewDTO.setCustomText(product.getCustomText());
			productViewDTO.setNewProduct(product.getNewProduct());
			if(large){
				List<CustomComponentDTO> customComponentList = getCustomCompoentSet(ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImages(product.getImage().getId()), collection, lang);
				java.util.Collections.sort( customComponentList, CustomComponentDTO.CUSTOM_COMPONENT_COMPARATOR_CODE);
				productViewDTO.setCustomComponentDTOSet(customComponentList);
				if(product.getImage().getCustomComponentTexts().iterator().hasNext()){
					productViewDTO.setCustomComponentText( getCustomComponentTextDTO( product.getImage().getCustomComponentTexts().iterator().next() ));
				}
				//productViewDTO.getCollection().setCustomComponentCollections( product.getCollection().getCustomComponentCollections() );
				productViewDTO.setDescriptionList(getDescriptionList(ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImages(product.getImage().getId()), lang));
				productViewDTO.setProductImagesSetDTO(getProductImagesSet( product.getProductImages() ));
				java.util.Collections.sort( productViewDTO.getProductImagesSetDTO(), ProductImageDTO.IMAGE_ORDER_WITDH);
				if(!product.getCustomProduct() && product.getCollection().getStatusCode().equals(CollectionStatus.ACTIVE)){
					productViewDTO.setUrlCustomProduct( getUrlCustomProduct(product, lang) );
				}
			}
			productViewDTO.setUrl( productViewDTO.getId() + "/" + StringUtils.generateUrl( productViewDTO.getName() ) );
			return productViewDTO;
		}
		return null;
	}

	private static CustomComponentTextDTO getCustomComponentTextDTO(
			CustomComponentText entity) {
		CustomComponentTextDTO item = new CustomComponentTextDTO();
		item.setAlign(entity.getAlign());
		item.setCreationDate(entity.getCreationDate());
		item.setHeight(entity.getHeight());
		item.setId(entity.getId());
		if(entity.getImageHeight() != null){
			item.setImageHeight(entity.getImageHeight());
		}
		else{
			item.setImageHeight(1);
		}
		if(entity.getImageWidth() != null){
			item.setImageWidth(entity.getImageWidth());
		}
		else{
			item.setImageWidth(1);
		}
		if(entity.getMaximum() != null){
			item.setMaximum(entity.getMaximum());
		}
		else{
			item.setMaximum(0);
		}
		item.setPosLeft(entity.getPosLeft());
		item.setPosTop(entity.getPosTop());
		item.setWidth(entity.getWidth());
		return item;
	}

	private static String getUrlCustomProduct(Product product, String lang) throws ServiceException, ServiceLocatorException {
		Product customProduct = null;
		if(product.getCustomLink() != null && product.getCustomLink() > 0){
			customProduct = ServiceLocator.getService(ProductServiceImpl.class).getProduct(product.getCustomLink(), true);
		}
		else{
			customProduct = ServiceLocator.getService(ProductServiceImpl.class).getCustomProduct(product.getCollection().getId(), product.getTypeCode());
		}
		if(customProduct != null){
			String customProductName = TranslationUtils.getTranslation(customProduct.getTranslationByNameTransId(), lang );
			return customProduct.getId() + "/" + StringUtils.generateUrl( customProductName ) ;
		}
		return null;
	}

	private static List<String> getDescriptionList(
			Set<CustomComponentImage> customComponentImageSet,
			String lang) {
		List<String> descriptionList = new ArrayList<String>();
		if(customComponentImageSet != null && customComponentImageSet.size() > 0 ){
			for (CustomComponentImage item : customComponentImageSet) {
				descriptionList.add(TranslationUtils.getTranslation(item.getCustomComponentCollection().getTranslationByDescriptionTransId(), lang));
			}
		}
		return descriptionList;
	}

	private static List<ProductImageDTO> getProductImagesSet(
			Set<ProductImage> productImages) {
		List<ProductImageDTO> set = new ArrayList<ProductImageDTO>();
		for (ProductImage productImage : productImages) {
			ProductImageDTO dto = new ProductImageDTO();
			dto.setNameImage(productImage.getImage().getName());
			dto.setHeight(productImage.getImage().getHeight());
			dto.setWidth(productImage.getImage().getWidth());
			set.add(dto);
		}
		
		return set;
	}

	private static List<CustomComponentDTO> getCustomCompoentSet(
			Set<CustomComponentImage> customoComponentImageSet, Collection collection, String lang) {
		Map<CustomComponent, CustomComponentDTO> customComponentMap = new HashMap<CustomComponent, CustomComponentDTO>();
		if(customoComponentImageSet!= null && customoComponentImageSet.size() > 0){
			for (CustomComponentImage item : customoComponentImageSet){
				if(!customComponentMap.containsKey(item.getCustomComponentCollection().getCustomComponent())){
					CustomComponentDTO customComponent = new CustomComponentDTO();	
					customComponent.setId(item.getCustomComponentCollection().getCustomComponent().getId());
					CustomComponentCollectionDTO customComponentCollectionDTO = getCustomComponentCollectionDTO(item.getCustomComponentCollection(), collection, lang);
					customComponentCollectionDTO.setCustomComponentImageId(item.getId());
					customComponent.getCustomComponentCollections().add(customComponentCollectionDTO);
					customComponent.setName(item.getCustomComponentCollection().getCustomComponent().getName());
					customComponentMap.put(item.getCustomComponentCollection().getCustomComponent(), customComponent);
				}
				else{
					CustomComponentCollectionDTO customComponentCollectionDTO = getCustomComponentCollectionDTO(item.getCustomComponentCollection(), collection, lang);
					customComponentCollectionDTO.setCustomComponentImageId(item.getId());
					customComponentMap.get(item.getCustomComponentCollection().getCustomComponent()).getCustomComponentCollections().add(customComponentCollectionDTO);
				}
			}
		}
		return new ArrayList<CustomComponentDTO>(customComponentMap.values());
	}
	
	public static CustomComponentCollectionDTO getCustomComponentCollectionDTO(CustomComponentCollection customComponentCollection, Collection collection, String lang){
		CustomComponentCollectionDTO customComponentCollectionDTO = new CustomComponentCollectionDTO();
		customComponentCollectionDTO.setCollection(customComponentCollection.getCollection());
		customComponentCollectionDTO.setCreationDate(customComponentCollection.getCreationDate());
		customComponentCollectionDTO.setCustomComponent(customComponentCollection.getCustomComponent());
		customComponentCollectionDTO.setId(customComponentCollection.getId());
		customComponentCollectionDTO.setImage( getImage(customComponentCollection.getImage(), collection.getCustomComponentCollections()) );
		customComponentCollectionDTO.setTypeCode(customComponentCollection.getTypeCode());
		customComponentCollectionDTO.setValue(customComponentCollection.getValue());
		customComponentCollectionDTO.setName(TranslationUtils.getTranslation(customComponentCollection.getTranslationByNameTransId(), lang ));
		customComponentCollectionDTO.setDescription(TranslationUtils.getTranslation(customComponentCollection.getTranslationByDescriptionTransId(), lang ));
		return customComponentCollectionDTO;
	}

	private static Image getImage(Image image, Set<CustomComponentCollection> customComponentCollections) {
		for (CustomComponentCollection item : customComponentCollections) {
			if( item.getImage().getId() == image.getId() ){
				return item.getImage();
			}
		}
		return null;
	}

	public static ItemShoppingCartDTO getItemShoppingCartDTO(Item item, String lang) throws ServiceException, ServiceLocatorException {
		ItemShoppingCartDTO itemShoppingCartDTO = new ItemShoppingCartDTO();
		itemShoppingCartDTO.setId(item.getId());
		itemShoppingCartDTO.setFont(item.getFont());
		itemShoppingCartDTO.setSize(item.getSize());
		itemShoppingCartDTO.setText(item.getText());
		itemShoppingCartDTO.setColor(item.getColor());
		//This is the way to know if it custom product
		if(item.getItemComponents() == null || item.getItemComponents().size() == 0){
			//in the case that is not a custom product to access the image
			//I have to put the product id before
			itemShoppingCartDTO.setNameImage(item.getProduct().getId() + "/" + item.getNameImage());
		}
		else{
			itemShoppingCartDTO.setNameImage(item.getNameImage());
		}
		if (item.getItemComponents() != null){
			itemShoppingCartDTO.setItemComponentDTOs(getItemComponentDTO(item, lang));
		}
		
		return itemShoppingCartDTO;
	}

	private static Set<ItemComponentDTO> getItemComponentDTO(
			Item item, String lang) throws ServiceException, ServiceLocatorException {
		Set<ItemComponentDTO> set = new HashSet<ItemComponentDTO>();
		for (ItemComponent itemComponent : item.getItemComponents()) {
			if(itemComponent != null){
				ItemComponentDTO itemComponentDTO = new ItemComponentDTO();
				Collection collection = ServiceLocator.getService(CollectionServiceImpl.class).getCollection(item.getProduct().getCollection().getId(), true);
				itemComponentDTO.setCustomComponentCollectionDTO(getCustomComponentCollectionDTO(itemComponent.getCustomComponentCollection(),collection , lang));
				itemComponentDTO.setCustomComponentImageDTO(getCustomComponentImageDTO(itemComponent.getCustomComponentImage()));
				
				set.add(itemComponentDTO);
			}
		}
		return set;
	}

	private static CustomComponentImageDTO getCustomComponentImageDTO(
			CustomComponentImage customComponentImage) {
		CustomComponentImageDTO customComponentImageDTO = new CustomComponentImageDTO();
		customComponentImageDTO.setCustomComponentCollection(customComponentImage.getCustomComponentCollection());
		customComponentImageDTO.setId(customComponentImage.getId());
		customComponentImageDTO.setImage(customComponentImage.getImageByImageId());
		customComponentImageDTO.setImageByImageMaskId(customComponentImage.getImageByImageMaskId());
		return customComponentImageDTO;
	}


	public static List<MethodShippingItemDTO> getMethodShippingItemDTOList(List<MethodShipping> listMethodShipping){
	
		List<MethodShippingItemDTO> list = new ArrayList<MethodShippingItemDTO>();
		for (MethodShipping item : listMethodShipping) {
			list.add(getMethodShippingItem(item));
		}
		return(list);
	}

	private static MethodShippingItemDTO getMethodShippingItem(
			MethodShipping item) {
		MethodShippingItemDTO methodShippingItemDTO = new MethodShippingItemDTO();

		methodShippingItemDTO.setId(item.getId());
		methodShippingItemDTO.setCode(item.getCode());
		methodShippingItemDTO.setDescEn(TranslationUtils.getTranslation(item.getTranslationByDescriptionTransId(), Language.ENGLISH));
		methodShippingItemDTO.setDescFr(TranslationUtils.getTranslation(item.getTranslationByDescriptionTransId(), Language.FRENCH));
		methodShippingItemDTO.setNameEn(TranslationUtils.getTranslation(item.getTranslationByNameTransId(), Language.ENGLISH));
		methodShippingItemDTO.setNameFr(TranslationUtils.getTranslation(item.getTranslationByNameTransId(), Language.FRENCH));
		methodShippingItemDTO.setPrice(item.getPrice());
		methodShippingItemDTO.setCountry(item.getCountry());
			
		return methodShippingItemDTO;
	}

	public static List<MethodShippingDTO> getMethodShippingDTOList(
			List<MethodShipping> allMethodShipping, String currentLanguage, String country) {
		List<MethodShippingDTO> list = new ArrayList<MethodShippingDTO>();
		for (MethodShipping methodShipping : allMethodShipping) {
			list.add( getMethodShippingDTO(methodShipping, currentLanguage, country));
		}
		
		return list;
	}

	private static MethodShippingDTO getMethodShippingDTO(
			MethodShipping methodShipping, String currentLanguage, String country) {
		MethodShippingDTO item = new MethodShippingDTO();
		try{
			item.setCode( methodShipping.getCode() );
			item.setDesc( TranslationUtils.getTranslation( methodShipping.getTranslationByDescriptionTransId(), currentLanguage ) );
			item.setName( TranslationUtils.getTranslation( methodShipping.getTranslationByNameTransId(), currentLanguage ) );
			item.setPrice( CurrencyUtils.displayPriceLocale(methodShipping.getPrice(), currentLanguage, country) );
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}	
		return item;
	}

	public static Users getUser(UserSessionDTO userSession) {
		Users user = new Users();
		user.setEmail(userSession.getEmail());
		user.setId(userSession.getId());
		user.setLogin(userSession.getLogin());
		user.setStatusCode(userSession.getUserStatus());
		user.setTypeCode(userSession.getUserType());
		return user;
	}
	
	public static List<ShoppingCartDTO> getShoppingCartDTOList(List<ShoppingCart> shoppingcartList, String lang) throws CurrencyNoExistException, ServiceLocatorException, ServiceException{
		List<ShoppingCartDTO> list = new ArrayList<ShoppingCartDTO>();
		for (ShoppingCart item : shoppingcartList) {
			list.add( getShoppingCartDTO(item, lang, false));
		}
		return list;
	}
	
	public static ShoppingCartDTO getShoppingCartDTO(ShoppingCart shoppingCart, String lang, boolean detail) throws CurrencyNoExistException, ServiceLocatorException, ServiceException{
		ShoppingCartDTO dto = new ShoppingCartDTO();
		dto.setComment(shoppingCart.getComment());
		dto.setCommentUser(shoppingCart.getCommentUser());
		dto.setCurrency(shoppingCart.getCurrency());
		dto.setCreationDate( StringUtils.getDisplayOrderDate(shoppingCart.getCreationDate()));
		dto.setId(shoppingCart.getId());
		dto.setMethodShipping(shoppingCart.getMethodShipping());
		dto.setPaymentMethod(shoppingCart.getPaymentMethod());
		dto.setPurchaseDate(shoppingCart.getPurchaseDate());
		dto.setReference(shoppingCart.getReference());
		dto.setSendDate(shoppingCart.getSendDate());
		dto.setShippingAddressId(shoppingCart.getShippingAddressId().getStreet() + " " + shoppingCart.getShippingAddressId().getApartment() + " " + shoppingCart.getShippingAddressId().getPostCode() + " " + shoppingCart.getShippingAddressId().getProvince() + " " + shoppingCart.getShippingAddressId().getCountry());
		dto.setShippingAddress(getUserAddressDTO(shoppingCart.getShippingAddressId()));
		dto.setShippingDate(shoppingCart.getSendDate());
		dto.setShippingFees( CurrencyUtils.displayPriceLocale(shoppingCart.getShippingFees(), lang, dto.getCurrency()) );
		dto.setStatusCode(shoppingCart.getStatusCode());
		dto.setTaxes( CurrencyUtils.displayPriceLocale(shoppingCart.getTaxes(), lang, dto.getCurrency()) );
		dto.setTotal( CurrencyUtils.displayPriceLocale(shoppingCart.getTotalShopping(), lang, dto.getCurrency()) );
		dto.setSubTotal( CurrencyUtils.displayPriceLocale(shoppingCart.getTotal(), lang, dto.getCurrency()) );
		dto.setTrackNumber(shoppingCart.getTrackNumber());
		dto.setUsers(shoppingCart.getUsers().getLogin());
		dto.setUserName(shoppingCart.getUsers().getFirstName() + " " + shoppingCart.getUsers().getLastName());
		dto.setUserPhone(shoppingCart.getUsers().getPhone());
		dto.setExtras( CurrencyUtils.displayPriceLocale(shoppingCart.getExtras(), lang, dto.getCurrency()) );
		dto.setStatusLocalization(shoppingCart.getStatusCode().getLocalizationKey());
		if(detail){
			if(shoppingCart.getTransaction()!= null){
				dto.setTransaction(shoppingCart.getTransaction().getMac());
			}
			dto.setShoppingCartItems(getShoppingCartItemDTOSet (shoppingCart.getShoppingCartItems()));
		}
		return dto;
	}

	public static List<ItemShoppingCartDTO> getItemShoppingCartDTOList(List<Item> itemList, String lang, String country) throws CurrencyNoExistException, CacheException, ServiceLocatorException, ServiceException{
		List<ItemShoppingCartDTO> shoppingCartList = new ArrayList<ItemShoppingCartDTO>();
		for (Item item : itemList) {
			ItemShoppingCartDTO itemShoppingCart = DTOFactory.getItemShoppingCartDTO(item, lang);
			itemShoppingCart.setProduct(DTOFactory.getProductDTO( item.getProduct().getId(), lang, country, true));
			BigDecimal productPrice = CurrencyUtils.priceToLocale(item.getProduct().getPrice(), country);
			if(item.getDiscountPrice() == null){
				itemShoppingCart.setDiscount( CurrencyUtils.displayPriceLocale("0.00", lang, country) );
				itemShoppingCart.setTotal( CurrencyUtils.displayPriceLocale(productPrice, lang, country) );
			}
			else{
				itemShoppingCart.setDiscount( CurrencyUtils.displayPriceLocale(item.getDiscountPrice(), lang, country) );
				itemShoppingCart.setTotal( CurrencyUtils.displayPriceLocale(productPrice.subtract(item.getDiscountPrice()), lang, country) );
			}
			shoppingCartList.add(itemShoppingCart);
		}
		return shoppingCartList;
	}

	private static Set<ShoppingCartItemDTO> getShoppingCartItemDTOSet(
			Set<ShoppingCartItem> shoppingCartItems) throws ServiceLocatorException, ServiceException {
		Set<ShoppingCartItemDTO> dto = new HashSet<ShoppingCartItemDTO>();
		for (ShoppingCartItem item : shoppingCartItems) {
			dto.add(getShoppingCartItemDTO(item));
		}
		return dto;
	}

	private static ShoppingCartItemDTO getShoppingCartItemDTO(ShoppingCartItem item) throws ServiceLocatorException, ServiceException {
		ShoppingCartItemDTO dto = new ShoppingCartItemDTO();
		dto.setCreationDate(item.getCreationDate());
		dto.setDiscount(item.getDiscount());
		dto.setFont(item.getItem().getFont());
		dto.setId(item.getId());
		dto.setNameImage(item.getItem().getNameImage());
		dto.setPrice(item.getPrice().toString());
		dto.setProductName(TranslationUtils.getTranslation(item.getItem().getProduct().getTranslationByNameTransId(), Language.ENGLISH));
		dto.setQuantity(item.getQuantity());
		dto.setSize(item.getItem().getSize());
		dto.setStatusCode(item.getStatusCode());
		dto.setText(item.getItem().getText());
		dto.setColor(item.getItem().getColor());
		String urlImage;
		if(item.getItem().getProduct().getCustomProduct()){
			urlImage =  ServiceLocator.getService(ConfigServiceImpl.class).getWebImageTmp();
			urlImage = urlImage + item.getItem().getNameImage();
		}
		else{
			urlImage = ServiceLocator.getService(ConfigServiceImpl.class).getWebImageProdcutLocation();
			String idProduct = String.valueOf(item.getItem().getProduct().getId());
			Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(item.getItem().getProduct().getId(), true);
			urlImage = urlImage + idProduct + "/" + product.getImage().getName();
		}
		
		dto.setUrlImage(urlImage);
		
		return dto;
	}

	public static UserDTO getUserDTO(Users user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setActive(user.getActive());
		userDTO.setCreatedOn(user.getCreationDate());
		userDTO.setEmail(user.getEmail());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setId(user.getId());
		userDTO.setLastLogindate(user.getLastLoginDate());
		userDTO.setLastName(user.getLastName());
		userDTO.setLogin(user.getLogin());
		userDTO.setPhone(user.getPhone());
		userDTO.setStatusCode(user.getStatusCode());
		userDTO.setUserAddressDTOList(getUserAddressDTOList(user.getUserAddresses()));
		return userDTO;
	}

	private static List<UserAddressDTO> getUserAddressDTOList(
			Set<UserAddress> userAddresses) {
		List<UserAddressDTO> list = new ArrayList<UserAddressDTO>();
		for (UserAddress userAddress : userAddresses) {
			list.add(getUserAddressDTO(userAddress));
		}
		return list;
	}
	
	private static UserAddressDTO getUserAddressDTO(UserAddress userAddress){
		return(new UserAddressDTO(userAddress.getId(), userAddress.getFirstName(), userAddress.getLastName(), userAddress.getTypeCode(), userAddress.getStreet(), userAddress.getApartment(), userAddress.getPostCode(), userAddress.getProvince(), userAddress.getCountry(), userAddress.getCity(), userAddress.getCreationDate()));
	}
	
	public static CollectionDTO getCollectionDTO(Collection collection, String lang){
		CollectionDTO collectionDTO = new CollectionDTO();
		collectionDTO.setId(collection.getId());
		collectionDTO.setName(TranslationUtils.getTranslation(collection.getTranslationByNameTransId(), lang ));
		collectionDTO.setDescription( TranslationUtils.getCleanDescription( collection.getTranslationByDescriptionTransId(), lang ) );
		collectionDTO.setImage(collection.getImage());
		collectionDTO.setStatusCode(collection.getStatusCode());
		collectionDTO.setUrl( StringUtils.generateUrl( collectionDTO.getName() ) );
		collectionDTO.setCustomComponentCollections(collection.getCustomComponentCollections());
		return collectionDTO;
	}
	
	public static List<CollectionDTO> getCollectionDTOList(List<Collection> collectionList, String lang){
		List<CollectionDTO> list = new ArrayList<CollectionDTO>();
		for (Collection item : collectionList) {
			list.add(getCollectionDTO(item, lang));
			
		}
		return list;
	}

	public static List<ItemViewDTO> getItemViewDTOForItemList(List<Item> listItemByProduct, String lang, String country, Integer size) throws CurrencyNoExistException, ServiceException, ServiceLocatorException, CacheException {
		List<ItemViewDTO> listItemViewDTO = new ArrayList<ItemViewDTO>();
		int i = 0;
		boolean reachSize = false;
		while (i != listItemByProduct.size() && !reachSize){
			Item item = listItemByProduct.get(i);
			listItemViewDTO.add( getItemViewDTOForItem(item, lang, country));
			i++;
			if( size != null && i >= size){
				reachSize = true;
			}
		}
		return listItemViewDTO;
	}

	private static ItemViewDTO getItemViewDTOForItem(Item item, String lang, String country) throws CurrencyNoExistException, ServiceException, ServiceLocatorException, CacheException {
		ItemViewDTO itemViewDTO = new ItemViewDTO();
		ProductDTO productDTO = getProductDTO(item.getProduct().getId(), lang, country, false);
		itemViewDTO.setId(productDTO.getId());
		String pathImage = ServiceLocator.getService(ConfigServiceImpl.class).getWebImageTmp();
		pathImage = pathImage + item.getNameImage();
		itemViewDTO.setItemCustomUrl(item.getNameImage().replace(".png", ""));
		itemViewDTO.setNameImage(pathImage);
		itemViewDTO.setItemName(productDTO.getName());
		itemViewDTO.setPrice(productDTO.getMsrp());
		return itemViewDTO;
	}
	
	public static ItemViewDTO getItemViewDTOForItem(String nameImage, int productId, String lang, String country) throws CurrencyNoExistException, ServiceException, ServiceLocatorException, CacheException {
		ItemViewDTO itemViewDTO = new ItemViewDTO();
		ProductDTO productDTO = getProductDTO(productId, lang, country, false);
		itemViewDTO.setId(productDTO.getId());
		String pathImage = ServiceLocator.getService(ConfigServiceImpl.class).getWebImageTmp();
		pathImage = pathImage + nameImage;
		itemViewDTO.setItemCustomUrl(productDTO.getUrl()+ "/" + nameImage.replace(".png", ""));
		itemViewDTO.setNameImage(pathImage);
		itemViewDTO.setItemName(productDTO.getName());
		itemViewDTO.setPrice(productDTO.getMsrp());
		return itemViewDTO;
	}

	public static List<ItemViewDTO> getItemViewDTOForProductList( List<Product> productByCollection, Integer id, String lang, String country, Integer size) throws ServiceException, ServiceLocatorException, CurrencyNoExistException, CacheException {
		List<ItemViewDTO> listItemViewDTO = new ArrayList<ItemViewDTO>();
		int i = 0;
		boolean reachSize = false;
		while (i != productByCollection.size() && !reachSize){
			Product item = productByCollection.get(i);
			if( item.getId() != id ){
				try{
					ProductDTO productDTO = getProductDTO(item.getId(), lang, country, false);
					listItemViewDTO.add( getItemViewDTOForProduct(productDTO, lang, country));
				}
				catch(Exception e){
					log.error(e.getMessage(), e);
				}
			}
			i++;
			if( size != null && i >= size){
				reachSize = true;
			}
		}
		return listItemViewDTO;
	}

	private static ItemViewDTO getItemViewDTOForProduct(ProductDTO productDTO, String lang, String country) throws ServiceException, ServiceLocatorException, CurrencyNoExistException {
		ItemViewDTO itemViewDTO = new ItemViewDTO();
		itemViewDTO.setId(productDTO.getId());
		String pathImage = ServiceLocator.getService(ConfigServiceImpl.class).getWebImageProdcutLocation();
		pathImage = pathImage + productDTO.getId() + "/" + productDTO.getImage().getName();
		itemViewDTO.setNameImage(pathImage);
		itemViewDTO.setItemName(productDTO.getName());
		itemViewDTO.setPrice(productDTO.getMsrp());
		itemViewDTO.setUrl(productDTO.getId() + "/" + StringUtils.generateUrl( productDTO.getName() ));
		itemViewDTO.setNewProduct(productDTO.getNewProduct());
		itemViewDTO.setOldPrice(productDTO.getOldPrice());
		itemViewDTO.setHasDiscount(productDTO.isHasDiscount());
		return itemViewDTO;
	}

	public static List<ItemDTO> getItemDTO(List<Product> listProduct, String lang) {
		List<ItemDTO> listDTO = new ArrayList<ItemDTO>();
		for (Product product : listProduct) {
			ItemDTO item = new ItemDTO();
			item.setKey( String.valueOf(product.getId()) );
			item.setValue( TranslationUtils.getTranslation(product.getTranslationByNameTransId(), lang ));
			listDTO.add(item);
		}
		return listDTO;
	}
	
}
