package com.mg.web.struts.action.ajax;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mg.enums.CollectionStatus;
import com.mg.enums.ComponentAttributeType;
import com.mg.enums.Country;
import com.mg.enums.Province;
import com.mg.exception.ServiceException;
import com.mg.model.Collection;
import com.mg.model.CustomComponent;
import com.mg.model.CustomComponentCollection;
import com.mg.model.CustomComponentImage;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.ItemDTO;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.web.struts.action.BasicListActionSupport;




public class AdminPageAjax<T> extends BasicListActionSupport<T> {
	
	private static final long serialVersionUID = 1L;

	private static final String DATE_FORMAT_DD_MMMM_YYYY_KEY = "loyauty.date.format.dd-MM-YYYY";	
	private static String AA_DATA = "aaData";
	private List<ItemDTO> list;
	private int id;
	private String code;
		
	public AdminPageAjax() {		
	}
	
	public String colllectionList(){	
		List<Collection> collectionList;
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();
		try {			
			collectionList = ServiceLocator.getService(CollectionServiceImpl.class).getAllCollection();
			for (Collection item : collectionList) {
				ItemDTO itemDTO = new ItemDTO();
				itemDTO.setId(item.getId());
				itemDTO.setValue(item.getCode());
				itemDTOList.add(itemDTO);
			}
			list = itemDTOList;
		}catch(Exception e){
			managerException(e);			
		}
		return  SUCCESS;
	}
	
	public String customComponentListById() {	
		Collection collection;
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();
		try {			
			collection = ServiceLocator.getService(CollectionServiceImpl.class).getCollection(id, false);
			for (CustomComponentCollection item : collection.getCustomComponentCollections()) {
				if (!itemDTOList.contains(item.getCustomComponent().getCode())){
					CustomComponent customComponent = item.getCustomComponent();
					ItemDTO itemDTO = new ItemDTO();
					itemDTO.setId(customComponent.getId());
					itemDTO.setValue(customComponent.getCode());
					itemDTOList.add(itemDTO);
				}
			}
			list = itemDTOList;
		}catch(Exception e){
			managerException(e);		
		}
		return  SUCCESS;
	}
	
	public String customComponentList() {	
		List<CustomComponent> customComponentList;
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();
		try {			
			customComponentList = ServiceLocator.getService(CollectionServiceImpl.class).getAllCustomComponent();
			for (CustomComponent item : customComponentList) {
				ItemDTO itemDTO = new ItemDTO();
				itemDTO.setId(item.getId());
				itemDTO.setValue(item.getCode());
				itemDTOList.add(itemDTO);
			}
			list = itemDTOList;
		}catch(Exception e){
			managerException(e);			
		}
		return  SUCCESS;
	}
	
	public String componentCollectionList() {	
		Collection collection;
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();
		try {			
			collection = ServiceLocator.getService(CollectionServiceImpl.class).getCollection(id, false);
			for (CustomComponentCollection item : collection.getCustomComponentCollections()) {
				if (item.getTypeCode().equals(ComponentAttributeType.COLOR) || 
					item.getTypeCode().equals(ComponentAttributeType.PATH)){
					ItemDTO itemDTO = new ItemDTO();
					itemDTO.setId(item.getId());
					if(item.getTypeCode().equals(ComponentAttributeType.COLOR)){
						itemDTO.setValue(item.getValue());
					}
					else{
						itemDTO.setValue(item.getImage().getName());
					}
					itemDTO.setKey(item.getTypeCode().name() + "-" + item.getCustomComponent().getCode()+ "-" + item.getCustomComponent().getId() + "-" + item.getId() );
					itemDTOList.add(itemDTO);
				}
			}
			list = itemDTOList;
		}catch(Exception e){
			managerException(e);			
		}
		return  SUCCESS;
	}
	
	
	/**
	 * @return The list of different font that there are associated with the collection
	 * 		   This information comes from CustomComponentCollection table
	 * @throws ServiceException
	 */
	public String colllectionFont() {	
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();
		try {	
			Collection collection = ServiceLocator.getService(CollectionServiceImpl.class).getCollection(id, false);
			
			for (CustomComponentCollection item : collection.getCustomComponentCollections()) {
				if(item.getTypeCode().equals(ComponentAttributeType.FONT)){
					ItemDTO itemDTO = new ItemDTO();
					itemDTO.setKey(item.getValue());
					itemDTO.setValue(item.getValue());
					itemDTOList.add(itemDTO);
				}
			}
			list = itemDTOList;
		}catch(Exception e){
			managerException(e);			
		}
		return  SUCCESS;
	}
	
	/**
	 * @return The list of different size that there are associated with the collection
	 * 		   This information comes from CustomComponentCollection table
	 * @throws ServiceException
	 */
	public String colllectionSize() {	
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();
		try {	
			Collection collection = ServiceLocator.getService(CollectionServiceImpl.class).getCollection(id, false);
			
			for (CustomComponentCollection item : collection.getCustomComponentCollections()) {
				if(item.getTypeCode().equals(ComponentAttributeType.SIZE)){
					ItemDTO itemDTO = new ItemDTO();
					itemDTO.setKey(item.getValue());
					itemDTO.setValue(item.getValue());
					itemDTOList.add(itemDTO);
				}
			}
			list = itemDTOList;
		}catch(Exception e){
			managerException(e);			
		}
		return  SUCCESS;
	}
	
	public String countryList() {	
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();			
		for (Country item : Country.values()) {
			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setKey(item.getCode());
			itemDTO.setValue( getText(item.getLocalizationKey()));
			itemDTOList.add(itemDTO);
		}
		list = itemDTOList;
		return  SUCCESS;
	}
	
	public String provinceList() {	
		List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();	
		for ( Province item : Province.getListProvinceByCountry( Country.valueOf(code) ) ) {
			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setKey(item.getProvice());
			if(item.equals(Province.ES) || item.equals(Province.FR) ){
				itemDTO.setValue( getText(item.getLocalizationKey()));
			}
			else{
				itemDTO.setValue(item.getProvice());
			}
			
			itemDTOList.add(itemDTO);
		}
		list = itemDTOList;
		return  SUCCESS;
	}
	
	public String changeStatusCCI(){
		try {
			CollectionStatus statusCode = null;
			CustomComponentImage customComponentImage = ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImage(id);
			if(code.equals("ACTIVE")){
				statusCode = CollectionStatus.INACTIVE;
			}
			else{
				statusCode = CollectionStatus.ACTIVE;
			}
			customComponentImage.setStatusCode(statusCode);
			ServiceLocator.getService(CollectionServiceImpl.class).updateCustomComponentImage(customComponentImage);
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String changeStatusCCC(){
		try {
			CollectionStatus statusCode = null;
			CustomComponentCollection customComponentCollection = ServiceLocator.getService(CollectionServiceImpl.class).getCustomComponentCollection(id);
			if(code.equals("ACTIVE")){
				statusCode = CollectionStatus.INACTIVE;
			}
			else{
				statusCode = CollectionStatus.ACTIVE;
			}
			customComponentCollection.setStatusCode(statusCode);
			ServiceLocator.getService(CollectionServiceImpl.class).updateCustomComponentCollection(customComponentCollection);
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	/*
	@SuppressWarnings("unchecked")
	public String brandList() throws LoyautyAjaxActionException, ServiceException {	
				
		List<BrandDTO> datasLinks = new ArrayList<BrandDTO>();
		try {			
			brandService = this.getServiceLocator().getBrandService();
			datasLinks = brandService.getAllBrand();
			for(BrandDTO dto : datasLinks){				
				dto.setLocalizedCreationDate(DateFormatUtils.format(dto.getCreationDate(), getText(LOYALTY_DATE_FORMAT_DD_MMMM_YYYY_KEY)));
			}
		}catch(ServiceLocatorException sle){
			log.debug("Service Locator Exception pb", sle);			
		}
		//serialize result
		Map<String, T> map = new HashMap<String, T>();
		map.put(AA_DATA, (T) datasLinks);
		setResult(map);
		
		return  SUCCESS;
		
	}
	
	@SuppressWarnings("unchecked")
	public String categoryList() throws LoyautyAjaxActionException, ServiceException {	
				
		List<CategoryDTO> datasLinks = new ArrayList<CategoryDTO>();
		try {			
			categoryService = this.getServiceLocator().getCategoryService();
			datasLinks = categoryService.getAllCategory();
		}catch(ServiceLocatorException sle){
			log.debug("Service Locator Exception pb", sle);			
		}
		//serialize result
		Map<String, T> map = new HashMap<String, T>();
		map.put(AA_DATA, (T) datasLinks);
		setResult(map);
		
		return  SUCCESS;		
	}	*/
	
	
	private String format(double value, String pattern, String language, String country) {
		Locale loc = new Locale(language, country);
		NumberFormat nf = NumberFormat.getInstance(loc);
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern(pattern);
		return nf.format(value);
	}


	public List<ItemDTO> getList() {
		return list;
	}


	public void setList(List<ItemDTO> list) {
		this.list = list;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
}
