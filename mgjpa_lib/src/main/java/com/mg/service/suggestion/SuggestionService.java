package com.mg.service.suggestion;

import java.util.List;

import com.mg.exception.ServiceException;
import com.mg.model.Product;
import com.mg.model.Users;
import com.mg.service.Service;

public interface SuggestionService extends Service {

	Product getProductBySuggestion(String suggestion) throws ServiceException;

	int saveSuggestion(Users users, String sessionGuid, Product product, String suggestion) throws ServiceException;

	List<Object[]> getSuggestionByCustomProduct(final int productId, final String session, final String currency)
			throws ServiceException;

	public int saveSuggestion(final Users users, final String sessionGuid, final Integer productId, final String suggestion, final Integer count, final Integer suggestionId) throws ServiceException;

}
