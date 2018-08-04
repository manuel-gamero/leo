package com.mg.web.struts.action.collection;

import java.util.List;

import com.mg.exception.ServiceException;
import com.mg.model.Collection;
import com.mg.model.CustomComponentCollection;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.CustomComponentCollectionDTO;
import com.mg.service.dto.ImageDTO;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.util.translation.Translations;
import com.mg.web.struts.action.BasicTranslationAction;
import com.opensymphony.xwork2.Preparable;

public class AddEditCollection extends BasicTranslationAction implements  Preparable {

	private static final long serialVersionUID = 1155604242419622177L;
	private Collection collection;
	private Integer id;
	private List<CustomComponentCollectionDTO> customComponentCollections;
	private String fileContentType;
	private String fileFileName;
	private ImageDTO imageDTO;
	private String status;
	
	@Override
	public void prepare() throws Exception {
	}
	
	@Override
	public String execute(){
		try {
			if(id != null){
				collection = ServiceLocator.getService(CollectionServiceImpl.class).getCollection(id, false);
				setValueTranslation(collection);
				return INPUT;
			}else{
				return INPUT;
			}
		} catch (Exception e) {
			managerException(e);
			return  ERROR;
		}
	}

	@Override
	public void validate() {
		try {		
			if(collection != null && collection.getId() == 0){
				Collection collectionSearched = ServiceLocator.getService(CollectionServiceImpl.class).getCollection(collection.getCode());
				if( collectionSearched !=null && collection.getId() != collectionSearched.getId() ){
					addFieldError("collection.code","Code collection already exists");
				}
			}
			int i = 0;
			boolean error = false;
			if(customComponentCollections != null){
				while ( i!= customComponentCollections.size() && !error ) {
					if(customComponentCollections.get(i)!= null){
						if(customComponentCollections.get(i).getImageDTO() != null && customComponentCollections.get(i).getImageDTO().getFileFileName() == null || customComponentCollections.get(i).getImageDTO().getFileFileName().trim().equals("")){
							error = true;
							addFieldError("collection.image","A custom component collections have not image");
						}
					}
					i++;
				}
			}
		} catch (Exception e) {
			managerException(e);
		}
	}
	
	
	public String save(){
		try {
			Translations translationsName = new Translations.StringTranslationBuilder().engString(nameEn).frString(nameFr).build();
			Translations translationsDesc = new Translations.StringTranslationBuilder().engString(descEn).frString(descFr).build();
			ServiceLocator.getService(CollectionServiceImpl.class).saveCollection(collection, customComponentCollections, imageDTO, translationsName, translationsDesc);
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String delete() throws ServiceException {
		try {
			ServiceLocator.getService(CollectionServiceImpl.class).deleteCustomComponentCollection(id);
		} catch (Exception e) {
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

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public List<CustomComponentCollectionDTO> getCustomComponentCollections() {
		return customComponentCollections;
	}

	public void setCustomComponentCollections(
			List<CustomComponentCollectionDTO> customComponentCollections) {
		this.customComponentCollections = customComponentCollections;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ImageDTO getImageDTO() {
		return imageDTO;
	}

	public void setImageDTO(ImageDTO imageDTO) {
		this.imageDTO = imageDTO;
	}

}
