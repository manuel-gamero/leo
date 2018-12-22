package com.mg.datamining.helpers;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mg.datamining.interfaces.IDeviceSuggestionsAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceSuggestions;
import com.mg.util.text.StringUtils;

public class DeviceSuggestionHelper {

	private static final Logger log = Logger.getLogger(DeviceSuggestionHelper.class);
	
	public static void create(Device device, Audit audit, IDeviceSuggestionsAction action) throws ServiceException, ServiceLocatorException{
		if( device.getDeviceSuggestions() == null || device.getDeviceSuggestions().size() == 0 ){
			DeviceSuggestions item = createDeviceSuggestion(device, audit);
			action.applyAction(device, audit, item);
			Set<DeviceSuggestions> suggestionsSet = new HashSet<DeviceSuggestions>();
			suggestionsSet.add(item);
			device.setDeviceSuggestions(suggestionsSet);
		}
		else{
			DeviceSuggestions item = getDeviceSuggestions(device, audit );
			if( item == null ){
				item = createDeviceSuggestion(device, audit);
				if(item != null){
					device.getDeviceSuggestions().add(item);
				}
			}
			action.applyAction(device, audit, item);
		}
	}

	private static DeviceSuggestions createDeviceSuggestion(Device device, Audit audit) throws ServiceException, ServiceLocatorException {
		DeviceSuggestions deviceSuggestion = new DeviceSuggestions();
		String message = audit.getMessage();
		String codeSuggestion = StringUtils.getListRegexMatches(message, "suggestion = [a-zA-Z0-9|_|,|.|;|/|:|+|-|'|(|)]*").get(0).split("=")[1].trim();
		codeSuggestion = codeSuggestion.substring(codeSuggestion.lastIndexOf("/")+1, codeSuggestion.length());
		String typeSuggestion = StringUtils.getListRegexMatches(message, "type = [a-zA-Z|-]*").get(0).split("=")[1].trim();
		deviceSuggestion.setDevice(device);
		deviceSuggestion.setTypeSuggestion(typeSuggestion);
		deviceSuggestion.setCodeSuggestion(codeSuggestion);
		deviceSuggestion.setCount(0);
		deviceSuggestion.setCountRight(0);
		deviceSuggestion.setLastModification(new Date());
		deviceSuggestion.setCreationDate(new Date());
		return deviceSuggestion;
	}
	
	private static DeviceSuggestions getDeviceSuggestions(Device device, Audit audit) {
		String message = audit.getMessage();
		String codeSuggestion = StringUtils.getListRegexMatches(message, "suggestion = [a-zA-Z0-9|_|,|.|;|/|:|+|-|'|(|)]*").get(0).split("=")[1].trim();
		final String codeSuggestionSearch = codeSuggestion.substring(codeSuggestion.lastIndexOf("/")+1, codeSuggestion.length());
		Optional<DeviceSuggestions> deviceSuggetion = device.getDeviceSuggestions().stream().filter(s -> s.getCodeSuggestion().equals(codeSuggestionSearch)).findFirst();
		if( deviceSuggetion.isPresent() ){
			return deviceSuggetion.get();
		}
		else{
			return null;
		}
	}
}
