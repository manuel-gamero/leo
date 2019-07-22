package com.mg.web.struts.action.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.enums.UserAddressType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.ShoppingCart;
import com.mg.model.UserAddress;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.ItemUserAddressDTO;
import com.mg.service.dto.UserSessionDTO;
import com.mg.service.user.UserServiceImpl;
import com.opensymphony.xwork2.Preparable;

public class ShoppingCartAddress extends BasicShoppingCart implements Preparable {

	private static Logger log = LogManager.getLogger(ShoppingCartAddress.class);
	private static final long serialVersionUID = 1155604242419622177L;
	private List<ItemUserAddressDTO> shippingList;
	private List<ItemUserAddressDTO> billingList;
	private Integer shippingSelect;
	
	
	@Override
	public void prepare() {
		try{
		super.prepare();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			managerException(e);
		}
	}

	private List<ItemUserAddressDTO> getBillingList(Set<UserAddress> addresses) throws ServiceException, ServiceLocatorException {
		return getShippingList(addresses, UserAddressType.BILLING);
	}

	private List<ItemUserAddressDTO> getShippingList(Set<UserAddress> addresses) throws ServiceException, ServiceLocatorException {
		return getShippingList(addresses, UserAddressType.SHIPPING);
	}

	private List<ItemUserAddressDTO> getShippingList(Set<UserAddress> addresses,
			UserAddressType type) throws ServiceException, ServiceLocatorException {
		List<ItemUserAddressDTO> list = new ArrayList<ItemUserAddressDTO>();
		UserSessionDTO userSession = getUserSession();
		List<UserAddress> listUserAddress = null;
		
		if(userSession != null){
			listUserAddress = ServiceLocator.getService(UserServiceImpl.class).getUserAddressList(userSession.getId(), type) ;
		}
		//for (UserAddress userAddress : addresses) {
		for (UserAddress userAddress : listUserAddress) {
			String location = userAddress.getFirstName() + ", " +
							  userAddress.getLastName() + " - " +
							  userAddress.getStreet() + ", " + 
							  userAddress.getApartment() + ", " +
							  userAddress.getCity() + ", " +
							  userAddress.getPostCode() + ", " +
							  userAddress.getProvince() + ", " +
							  getText(userAddress.getCountry().getLocalizationKey());
			ItemUserAddressDTO item = new ItemUserAddressDTO(userAddress.getId(), location);
			if(userAddress.getTypeCode().equals(type)){
				list.add(item);
			}
		}
		return list;
	}

	@Override
	public String execute(){
		log.debug("ShoppingCartAddress debug execute");
		try{
			if(shippingList == null && billingList == null){
				UserSessionDTO userSession = getUserSession();
				if(userSession != null){
					shippingList = getShippingList(userSession.getAddresses());
					billingList = getBillingList(userSession.getAddresses());
				}
			}
			ShoppingCart shoppingCart = getShoppingCart();
			if(shoppingCart != null && shoppingCart.getShippingAddressId() != null){
				setShippingSelect(shoppingCart.getShippingAddressId().getId());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			managerException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public List<ItemUserAddressDTO> getShippingList() {
		return shippingList;
	}

	public void setShippingList(List<ItemUserAddressDTO> shippingList) {
		this.shippingList = shippingList;
	}

	public List<ItemUserAddressDTO> getBillingList() {
		return billingList;
	}

	public void setBillingList(List<ItemUserAddressDTO> billingList) {
		this.billingList = billingList;
	}

	public Integer getShippingSelect() {
		return shippingSelect;
	}

	public void setShippingSelect(Integer shippingSelect) {
		this.shippingSelect = shippingSelect;
	}
	
	

}
