package com.mg.service.dto;


public class ItemComponentDTO extends BasicDTO {
	
	private static final long serialVersionUID = 1L;
	private CustomComponentImageDTO customComponentImageDTO;
	private CustomComponentCollectionDTO customComponentCollectionDTO;
	
	public CustomComponentImageDTO getCustomComponentImageDTO() {
		return customComponentImageDTO;
	}
	public void setCustomComponentImageDTO(CustomComponentImageDTO customComponentImageDTO) {
		this.customComponentImageDTO = customComponentImageDTO;
	}
	public CustomComponentCollectionDTO getCustomComponentCollectionDTO() {
		return customComponentCollectionDTO;
	}
	public void setCustomComponentCollectionDTO(
			CustomComponentCollectionDTO customComponentCollectionDTO) {
		this.customComponentCollectionDTO = customComponentCollectionDTO;
	}

}
