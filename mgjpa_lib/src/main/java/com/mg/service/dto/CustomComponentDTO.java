package com.mg.service.dto;


import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mg.enums.ComponentType;

public class CustomComponentDTO extends BasicDTO {

	private static final long serialVersionUID = -3361880719522427630L;
	private ComponentType typeCode;
	private String code;
	private String name;
	private SortedSet<CustomComponentCollectionDTO> customComponentCollections = new TreeSet<CustomComponentCollectionDTO>(CUSTOM_COMPONENT_COLLECTION_COMPARATOR_CODE);
	
	public static final Comparator<CustomComponentDTO> CUSTOM_COMPONENT_COMPARATOR_CODE = new Comparator<CustomComponentDTO>() {
		public int compare(CustomComponentDTO item1, CustomComponentDTO item2) {
			/*int result = 1;
			if(item1.getName().compareTo(item2.getId()) < ){
				result = -1;
			}*/
			return item1.getName().compareTo(item2.getName());
		}
	};
	
	public static final Comparator<CustomComponentCollectionDTO> CUSTOM_COMPONENT_COLLECTION_COMPARATOR_CODE = new Comparator<CustomComponentCollectionDTO>() {
		public int compare(CustomComponentCollectionDTO item1, CustomComponentCollectionDTO item2) {
			if(item1.getId() > item2.getId()){
				return 1;
			}
			else if(item1.getId() < item2.getId()){
				return -1;
			}
			else{
				return 0;
			}
		}
	};
	
	public CustomComponentDTO() {
	}

	public CustomComponentDTO(int id, ComponentType typeCode, String code, String name) {
		setId(id);
		this.typeCode = typeCode;
		this.code = code;
		this.name = name;
	}

	public CustomComponentDTO(int id, ComponentType typeCode, String code, String name,
			Date creationDate,
			SortedSet<CustomComponentCollectionDTO> customComponentCollections) {
		setId(id);
		this.typeCode = typeCode;
		this.code = code;
		this.name = name;
		this.customComponentCollections = customComponentCollections;
	}
	
	public ComponentType getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(ComponentType typeCode) {
		this.typeCode = typeCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SortedSet<CustomComponentCollectionDTO> getCustomComponentCollections() {
		return customComponentCollections;
	}

	public void setCustomComponentCollections(
			SortedSet<CustomComponentCollectionDTO> customComponentCollections) {
		this.customComponentCollections = customComponentCollections;
	}

	
}
