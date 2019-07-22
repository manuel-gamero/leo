package com.mg.datamining.helpers;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.datamining.interfaces.IDeviceSuggestionsAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceSuggestions;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.suggestion.SuggestionService;
import com.mg.service.suggestion.SuggestionServiceImpl;
import com.mg.util.text.StringUtils;

public class DeviceSuggestionHelper {

	private static final Logger log = LogManager.getLogger(DeviceSuggestionHelper.class);
	
	public static void create(Device device, Audit audit, IDeviceSuggestionsAction action, String suggestion) throws ServiceException, ServiceLocatorException{
		if( device.getDeviceSuggestions() == null || device.getDeviceSuggestions().size() == 0 ){
			DeviceSuggestions item = createDeviceSuggestion(device, audit, suggestion);
			if(item != null){
				action.applyAction(device, audit, item);
				Set<DeviceSuggestions> suggestionsSet = new HashSet<DeviceSuggestions>();
				suggestionsSet.add(item);
				device.setDeviceSuggestions(suggestionsSet);
			}
		}
		else{
			DeviceSuggestions item = getDeviceSuggestions(device, audit, suggestion );
			if( item == null ){
				item = createDeviceSuggestion(device, audit, suggestion);
				if(item != null){
					device.getDeviceSuggestions().add(item);
				}
			}
			action.applyAction(device, audit, item);
		}
	}
	
	public static void update (Device device, Audit audit, IDeviceSuggestionsAction action, Integer productId) throws ServiceLocatorException{
		DeviceSuggestions item = getSuggestedProduct(device, productId);
		if(item != null){
			action.applyAction(device, audit, item);
		}
	}

	private static DeviceSuggestions getSuggestedProduct(Device device, Integer productId) throws ServiceLocatorException {
		SuggestionService service = ServiceLocator.getService(SuggestionServiceImpl.class);
		Optional<DeviceSuggestions> deviceSuggetion = device.getDeviceSuggestions().stream().filter(s -> {
			try {
				Product product = service.getProductBySuggestion(s.getCodeSuggestion());
				if (product != null){
					return product.getId() == productId;
				}
				else{
					return false;
				}
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			return false;
		}).findFirst();
		if(deviceSuggetion.isPresent()){
			return deviceSuggetion.get();
		}
		else{
			return null;
		}
	}

	private static DeviceSuggestions createDeviceSuggestion(Device device, Audit audit, String suggestion) throws ServiceException, ServiceLocatorException {
		DeviceSuggestions deviceSuggestion = new DeviceSuggestions();
		String message = audit.getMessage();
		//String codeSuggestion = StringUtils.getListRegexMatches(message, "suggestion = [a-zA-Z0-9|_|,|.|;|/|:|+|-|'|(|)]*").get(0).split("=")[1].trim();
		//codeSuggestion = codeSuggestion.substring(codeSuggestion.lastIndexOf("/")+1, codeSuggestion.length());
		List<String> typeList = StringUtils.getListRegexMatches(message, "type = [a-zA-Z|-|_]*");
		if( suggestion != null && typeList.size() > 0 && typeList.get(0).split("=")[1] != null ){
			String typeSuggestion = typeList.get(0).split("=")[1].trim();
			deviceSuggestion.setDevice(device);
			deviceSuggestion.setTypeSuggestion(typeSuggestion);
			deviceSuggestion.setCodeSuggestion(suggestion);
			deviceSuggestion.setCount(0);
			deviceSuggestion.setSellCount(0);
			deviceSuggestion.setRemoveCount(0);
			deviceSuggestion.setAddCount(0);
			deviceSuggestion.setShareCount(0);
			deviceSuggestion.setCountSuggested(0);
			deviceSuggestion.setLastModification(new Date());
			deviceSuggestion.setCreationDate(new Date());
			return deviceSuggestion;
		}
		return null;
	}
	
	private static DeviceSuggestions getDeviceSuggestions(Device device, Audit audit, final String suggestion) {
		if(suggestion != null && device.getDeviceSuggestions() != null){
			Optional<DeviceSuggestions> deviceSuggetion = device.getDeviceSuggestions().stream().filter(s -> suggestion.equals(s.getCodeSuggestion())).findFirst();
			if( deviceSuggetion.isPresent() ){
				return deviceSuggetion.get();
			}
		}
		return null;
	}
}
