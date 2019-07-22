package com.mg.web.struts.action.collection;

import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.model.CustomComponentCollection;
import com.mg.service.ServiceLocator;
import com.mg.service.product.CollectionServiceImpl;
import com.mg.util.translation.Translations;
import com.mg.web.struts.action.BasicTranslationAction;
import com.opensymphony.xwork2.Preparable;

public class AddEditCustomComponentCollection extends BasicTranslationAction implements Preparable {

	private static final long serialVersionUID = 1155604242419622177L;
	private static final Logger log = LogManager.getLogger(AddEditCustomComponentCollection.class);
	private Integer id;
	private CustomComponentCollection customComponentCollection;
	private int descrTranslationId;
	private int nameTranslationId;
	private String url;
	
	
	@Override
	public void prepare() throws Exception {
	}
	
	@Override
	public String execute(){
		try{
			if(id != null){
				customComponentCollection = ServiceLocator.getService(CollectionServiceImpl.class).getCustomComponentCollection(id);
				setValueTranslation(customComponentCollection);
				
				return INPUT;
			}else{
				return INPUT;
			}
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String save(){
		
		try{
			log.debug(" >>> AddEditCustomComponentCollection save() ");	
			// set the url where this action come from 
			setRefereUrl();
			Translations translationsName = new Translations.StringTranslationBuilder().engString(nameEn).frString(nameFr).build();
			Translations translationsDesc = new Translations.StringTranslationBuilder().engString(descEn).frString(descFr).build();
			customComponentCollection = ServiceLocator.getService(CollectionServiceImpl.class).getCustomComponentCollection(id);
			
			ServiceLocator.getService(CollectionServiceImpl.class).updateCustomComponentCollection(customComponentCollection, translationsName, translationsDesc);
			
			//Add translation in similar customcomoponetcollection (same customcomponentcollection image)
			List<CustomComponentCollection> listToTranslate = ServiceLocator.getService(CollectionServiceImpl.class).getCustomComponentCollectionForTranslation(customComponentCollection.getImage().getName());
			log.debug(" +++ Number CustomComponentCollection to update: " + listToTranslate.size());
			for (CustomComponentCollection item : listToTranslate) {
				item.setTranslationByNameTransId(customComponentCollection.getTranslationByNameTransId());
				item.setTranslationByDescriptionTransId(customComponentCollection.getTranslationByDescriptionTransId());
				ServiceLocator.getService(CollectionServiceImpl.class).updateCustomComponentCollection(item, translationsName, translationsDesc);
			}
			log.debug(" <<< AddEditCustomComponentCollection save() ");
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

	public int getDescrTranslationId() {
		return descrTranslationId;
	}

	public void setDescrTranslationId(int descrTranslationId) {
		this.descrTranslationId = descrTranslationId;
	}

	public int getNameTranslationId() {
		return nameTranslationId;
	}

	public void setNameTranslationId(int nameTranslationId) {
		this.nameTranslationId = nameTranslationId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
