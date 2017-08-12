package com.mg.service.dto;

import java.util.Date;

import com.mg.enums.Country;
import com.mg.enums.Province;
import com.mg.enums.UserAddressType;

public class UserAddressDTO extends BasicDTO{

	private static final long serialVersionUID = -2474711616657527834L;
	
	private UserAddressType typeCode;
	private String street;
	private String apartment;
	private String postCode;
	private Province province;
	private Country country;
	private Date creationDate;
	private String firstName;
	private String lastName;
	private String city;

	public UserAddressDTO() {
	}

	public UserAddressDTO(int id, String firsName, String lastName, UserAddressType typeCode,
			String street, String aparment, String postCode, Province province, Country country,
			String city, Date creationDate) {
		setId(id);
		this.setFirstName(firsName);
		this.setLastName(lastName);
		this.setTypeCode(typeCode);
		this.setStreet(street);
		this.setApartment(aparment);
		this.setPostCode(postCode);
		this.setProvince(province);
		this.setCountry(country);
		this.setCreationDate(creationDate);
		this.setCity(city);
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public UserAddressType getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(UserAddressType typeCode) {
		this.typeCode = typeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
}
