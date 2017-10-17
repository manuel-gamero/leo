package com.mg.service.dto;


/**
 * Contains basic item information 
 * 
 * @author MJGP
 *
 */
public class InstagramDTO extends BasicDTO {
		
	private static final long serialVersionUID = 1L;
	
	private String link;
	private String image;
	
	public InstagramDTO(String link, String image){
		this.link = link;
		this.image = image;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	
}
 