package com.mg.enums;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.shopping.ShoppingServiceImpl;


/**
 * 
 * @author MJGP
 *
 */
public enum Province implements BasicEnum{
	
	NY(Country.US, "NY") {
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("NY").getTax();
		}
	},
	LA(Country.US, "LA"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("LA").getTax();
		}
	},
	ON(Country.CA, "ON"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("ON").getTax();
		}
	},
	QC(Country.CA, "QC"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("QC").getTax();
		}
	},
	NS(Country.CA, "NS"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("NS").getTax();
		}
	},
	NB(Country.CA, "NB"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("NB").getTax();
		}
	},
	MB(Country.CA, "MB"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("MB").getTax();
		}
	},
	BC(Country.CA, "BC"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("BC").getTax();
		}
	},
	PE(Country.CA, "PE"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("PE").getTax();
		}
	},
	SK(Country.CA, "SK"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("SK").getTax();
		}
	},
	AB(Country.CA, "AB"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("AB").getTax();
		}
	},
	NL(Country.CA, "NL"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("NL").getTax();
		}
	},
	FR(Country.FR, "FR"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("FR").getTax();
		}
	},
	ES(Country.ES, "ES"){
		@Override
		public BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException {
			return  ServiceLocator.getService(ShoppingServiceImpl.class).getTaxes("ES").getTax();
		}
	}	
	;
	
	private String localizationKey = "enum.shopping.cart.province." + name().toLowerCase();
	private String province;
	private Country country;
	
	public String getLocalizationKey() {
		return localizationKey;
	}

	public String getProvice() {
		return province;
	}
	
	private Province(Country country, String province){
		this.province = province;
		this.country = country;
	}

	public Country getCountry() {
		return country;
	}

	public static List<Province> getListProvinceByCountry( Country country ){
		List<Province> list = new ArrayList<Province>();
		for (Province province : values()) {
			if(province.getCountry().equals(country)){
				list.add(province);
			}
		}
		return(list);
	}
	
	public abstract BigDecimal getTax() throws NumberFormatException, ServiceException, ServiceLocatorException;
}
