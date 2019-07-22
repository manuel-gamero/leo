package com.mg.web.suggestion;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;

import com.mg.exception.CacheException;
import com.mg.exception.CurrencyNoExistException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ItemViewDTO;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.service.suggestion.SuggestionServiceImpl;
import com.mg.web.util.AuditUtil;

public class SuggestionManager {

	private static final Logger log = LogManager.getLogger(SuggestionManager.class);
	
	public static List<ItemViewDTO> getCustomSuggestionList(Integer productId, String session, String lang, String currency,
			int size) throws ServiceLocatorException, CurrencyNoExistException, ServiceException, CacheException{
		
		List<ItemViewDTO> listItem = null;
		List<Object[]> suggestionsList = null;
		
		if( ServiceLocator.getService(ConfigServiceImpl.class).isDataminigEnable() ){
			suggestionsList = ServiceLocator.getService(SuggestionServiceImpl.class).getSuggestionByCustomProduct(productId, session, currency);
			listItem = suggestionsList.stream().map( i -> {
				try {
					return DTOFactory.getItemViewDTOForItem((String)i[0],(Integer)i[1],lang, currency);
				} catch (Exception e) {
					log.error(e);
					return null;
				}
			}).limit(size).collect(Collectors.toList());
			//listItem = DTOFactory.getItemViewDTOForItemList( suggestionsList.stream().map(i -> (Item)i[0]).collect(Collectors.toList()), getCurrentLanguage(), getCurrentCurrencyCode(), 3 );
		}
		if(listItem != null && listItem.size() >= 3){
			generateAuditSuggestion(suggestionsList, size );	
			return listItem;
		}
		else{
			return DTOFactory.getItemViewDTOForItemList( ServiceLocator.getService(ProductServiceImpl.class).getListItemByProduct(productId), lang, currency, size );
		}
	}
	
	private static void generateAuditSuggestion(List<Object[]> listItem, int size) {
		listItem.subList(0, size).stream().forEach( p -> {
			try {
				Audit audit = AuditUtil.createAudit(null, "suggestion = " + ((String)p[0]) + " type = CUSTOM_PRODUCT ");
				ServiceLocator.getService(ConfigServiceImpl.class).saveAudit(audit);
				ServiceLocator.getService(SuggestionServiceImpl.class).saveSuggestion(null, ServletActionContext.getRequest().getSession().getId(), (Integer)p[1], ((String)p[0]), p[2]!=null?(Integer)p[2]:null, p[3]!=null?(Integer)p[3]:null);
			} catch (Exception e) {
				log.error(e);
			}} );
	} 
}
