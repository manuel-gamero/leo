package com.mg.datamining.actions;

import com.mg.datamining.helpers.DeviceSuggestionHelper;
import com.mg.datamining.interfaces.IDeviceSuggestionsAction;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceSuggestions;
import com.mg.util.text.StringUtils;

public class UserActionSuggestion extends UserActionBasicUrl implements IDeviceSuggestionsAction{

	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException {
		String message = audit.getMessage();
		String codeSuggestion = StringUtils.getListRegexMatches(message, "suggestion = [a-zA-Z0-9|_|,|.|;|/|:|+|-|'|(|)]*").get(0).split("=")[1].trim();
		String codeSuggestionSearch = codeSuggestion.substring(codeSuggestion.lastIndexOf("/")+1, codeSuggestion.length()); //.split("\\.")[0].trim();
		DeviceSuggestionHelper.create(device, audit, this, codeSuggestionSearch);
	}

	@Override
	public void applyAction(Device device, Audit audit, DeviceSuggestions item) {
		item.setCount( item.getCount() + 1 );
		item.setLastModification(audit.getCreationDate());
	}
}
