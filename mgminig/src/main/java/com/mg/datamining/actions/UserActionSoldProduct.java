package com.mg.datamining.actions;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mg.datamining.helpers.DeviceCustomComponentHelper;
import com.mg.datamining.helpers.DeviceCustomComponentHistHelper;
import com.mg.datamining.helpers.DeviceProductHelper;
import com.mg.datamining.helpers.DeviceProductHistHelper;
import com.mg.datamining.helpers.DeviceUrlHelper;
import com.mg.enums.UserActionParamType;
import com.mg.enums.UserActionType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceComponent;
import com.mg.model.DeviceComponentHist;
import com.mg.model.DeviceProduct;
import com.mg.model.DeviceProductHist;
import com.mg.model.DeviceUrl;

public class UserActionSoldProduct extends UserActionBasicCount {
	
	private static Logger log = Logger.getLogger(UserActionSoldProduct.class);
	
	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException {
		boolean addComponent = false;
		if( audit.getOutcome().contains("-post") ){
			for (DeviceProduct element : device.getDeviceProducts()) {
				//If the product is added and not removed then is sold
				if( element.getAddCount() > 0 && element.getRemoveCount() == 0 ){
					if( isInProductRangeTime(device, audit, element) ){
						addComponent = true;
						DeviceProductHelper.create(device, audit, this, element.getProduct().getId());
						DeviceProductHistHelper.create(device, audit, this, element.getProduct().getId());
						
					}
				}
			}
			if( addComponent ){
				Set<DeviceComponent> clone = new HashSet<DeviceComponent>(device.getDeviceComponents());
				for (DeviceComponent element : clone) {
					//If the product is added and not removed then is sold
					if( element.getAddCount() > 0 && element.getRemoveCount() == 0 ){
						if( isInComponentRangeTime(device, audit, element) ){
							DeviceCustomComponentHelper.create(device, audit, this, element.getCustomComponentCollection().getId());
							DeviceCustomComponentHistHelper.create(device, audit, this, element.getCustomComponentCollection().getId());
						}
					}
				}
			}
			DeviceUrlHelper.create(device, audit, this, true);
		}
		else{
			log.warn(audit.getActionUser() + " with outcome: " + audit.getOutcome());
		}
		
	}
	
	/**
	 * @param device
	 * @param audit
	 * @param element
	 * @return True is the product is in the range of the sell.
	 * Just in case that there is more than one sell. the system has to increase the
	 * sell counter of the right product.
	 */
	private boolean isInProductRangeTime(Device device, Audit audit, DeviceProduct element) {
		Date dateInf = getDateLastSell( device );
		log.debug("+++ +++ +++ Date element: " + element.getLastModification() + " element id : " + element.getProduct().getId());
		log.debug("+++ +++ +++ Start sale : " + dateInf.toString());
		if( dateInf != null ){
			Date dateSup = audit.getCreationDate();
			log.debug("+++ +++ +++ End sale : " + dateSup.toString());
			for (DeviceProductHist item : device.getDeviceProductHists()) {
				if( item.getProduct().getId() == element.getProduct().getId() &&
						dateInf.before(item.getActionDate()) &&
						dateSup.after(item.getActionDate()) &&
						item.getUserAction().equals(UserActionType.ADDED)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isInComponentRangeTime(Device device, Audit audit, DeviceComponent element) {
		Date dateInf = getDateLastSell( device );
		if(dateInf != null){
			Date dateSup = audit.getCreationDate();
			for (DeviceComponentHist item : device.getDeviceComponentHists()) {
				if( item.getCustomComponentCollection().getId() == element.getCustomComponentCollection().getId() &&
						dateInf.before(item.getActionDate()) &&
						dateSup.after(item.getActionDate()) &&
						item.getUserAction().equals(UserActionType.ADDED)){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param device
	 * @return the date before the last sell that the client did.
	 * Otherwise return the date that the client reach the page.
	 */
	private Date getDateLastSell(Device device) {
		
		for (DeviceUrl url : device.getDeviceUrls()) {
			if( url.getUrl().contains("ShoppingCartPaymentSend") || 
				url.getUrl().contains("shoppingCartPaypalSend") ){
				return url.getActionDate();
			}
		}
		if(device.getDeviceUrls().size() > 0){
			return device.getDeviceUrls().get(0).getActionDate();
		}
		else{
			log.info("Date not found.");
			return null;
		}
	}
	

	@Override
	public void applyAction(Audit audit, DeviceProduct item) {
		item.setSellCount( item.getSellCount() + 1 ); 
		item.setLastModification(audit.getCreationDate());
	}

	@Override
	public void applyAction(Audit audit, DeviceComponentHist item) {
		item.setUserAction(UserActionType.SOLD);	
		item.setActionDate(audit.getCreationDate());
	}

	@Override
	public void applyAction(Audit audit, DeviceProductHist item) {
		String message = audit.getMessage();
		item.setUserAction(UserActionType.SOLD);
		item.setActionDate(audit.getCreationDate());
		if(message != null){
			getCreateParamValue(item, message, "text = ", "text = [a-zA-Z0-9| ]*", UserActionParamType.TEXT);
			getCreateParamValue(item, message, "color = ", "color = #[a-z|A-Z|0-9]*", UserActionParamType.COLOR);
			getCreateParamValue(item, message, "font = ", "font = [a-z|A-Z|0-9]*", UserActionParamType.FONT);
		}
	}

	@Override
	public void applyAction(Audit audit, DeviceComponent item) {
		item.setSellCount( item.getSellCount() + 1 );
		item.setLastModification(audit.getCreationDate());
		
	}

}
