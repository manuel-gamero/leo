package com.mg.datamining.actions;

import java.util.List;

import org.apache.log4j.Logger;

import com.mg.datamining.helpers.DeviceCustomComponentHelper;
import com.mg.datamining.helpers.DeviceCustomComponentHistHelper;
import com.mg.datamining.helpers.DeviceProductHelper;
import com.mg.datamining.helpers.DeviceProductHistHelper;
import com.mg.datamining.interfaces.IDeviceComponentAction;
import com.mg.datamining.interfaces.IDeviceComponentActionHist;
import com.mg.datamining.interfaces.IDeviceProductAction;
import com.mg.datamining.interfaces.IDeviceProductHistAction;
import com.mg.enums.UserActionParamType;
import com.mg.exception.CacheException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.CustomComponentImage;
import com.mg.model.Device;
import com.mg.model.DeviceComponent;
import com.mg.model.DeviceComponentHist;
import com.mg.model.DeviceProduct;
import com.mg.model.DeviceProductHist;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.util.text.StringUtils;

public abstract class UserActionBasicCount extends UserActionBasicUrl implements  IDeviceComponentAction, IDeviceComponentActionHist, IDeviceProductAction, IDeviceProductHistAction {

	private static final Logger log = Logger.getLogger(UserActionBasicCount.class);
	
	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException, CacheException {
		String message = audit.getMessage();
		List<String> listImags = StringUtils.getListRegexMatches(message, "imag[0-9|_]*");
		//That should take care that add and share cases
		if (listImags != null && listImags.size() > 0){
			String productId = listImags.get(0).split("_")[1];
			for (String image : listImags) {
				String customComponentImageId = image.split("_")[3];
				//CustomComponentImage customComponentImage = ServiceLocator.getService(CollectionServiceImpl.class).getCustomComponentImage(Integer.valueOf(customComponentImageId));
				//Integer customComponentCollectionId = customComponentImage.getCustomComponentCollection().getId();
				DeviceCustomComponentHelper.create(device, audit, this, Integer.valueOf(customComponentImageId));
				DeviceCustomComponentHistHelper.create(device, audit, this, Integer.valueOf(customComponentImageId));	
			}
			DeviceProductHelper.create(device, audit, this, Integer.valueOf(productId));
			
			DeviceProductHistHelper.create(device, audit, this, Integer.valueOf(productId));
		}
		else{
			//In the case to add product to shopping cart the product id is at the beginning of message
			if(StringUtils.getListRegexMatches(message, "product = [0-9]*").size() > 0){
				String productId = StringUtils.getListRegexMatches(message, "product = [0-9]*").get(0).split("=")[1].trim();
				if(productId != null){
					DeviceProductHelper.create(device, audit, this, Integer.valueOf(productId));
					DeviceProductHistHelper.create(device, audit, this, Integer.valueOf(productId));
					//If the product is a finish product add their component
					Product product = ServiceLocator.getService(ProductServiceImpl.class).getProduct(Integer.valueOf(productId), true);
					if(product != null){
						if(!product.getCustomProduct()){
							for (CustomComponentImage customComponentImage : ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImages(product.getImage().getId()) ) {
								DeviceCustomComponentHelper.create(device, audit, this, customComponentImage.getId());
								DeviceCustomComponentHistHelper.create(device, audit, this,  customComponentImage.getId());
							}
						}
					}
				}
			}
			else{
				log.info("Product not found, message: " + message);
			}
		}
	}
	
	@Override
	public abstract void applyAction(Audit audit, DeviceProduct item);

	@Override
	public abstract void applyAction(Audit audit, DeviceComponentHist item) ;

	@Override
	public abstract void applyAction(Audit audit, DeviceProductHist item) ;

	@Override
	public abstract void applyAction(Audit audit, DeviceComponent item) ;
	
	protected void getCreateParamValue(DeviceProductHist item, String message, String text,
			String pattern, UserActionParamType paramType) {
		if ( message.toLowerCase().contains(text) ){
			List<String> stringList = StringUtils.getListRegexMatches(message, pattern);
			if (stringList.size() > 0){
				String value = stringList.get(0).split("=")[1].trim();
				createParamValue(item, paramType, value);
			}
		}
	}

}
