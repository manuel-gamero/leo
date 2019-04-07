//package com.mg.dao.impl;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.mg.model.Item;
//import com.mg.model.Product;
//import com.mg.model.SuggestionsProduct;
//
//public class SuggestionsProductDAO extends GenericDaoImpl<SuggestionsProduct> {
//	
//	private static final long serialVersionUID = 1L;
//
//	public SuggestionsProductDAO() {
//		super(SuggestionsProduct.class);
//	}
//
//	@Override
//	public void joinTransaction() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public SuggestionsProduct getProductBySuggestion(String suggestion) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("suggestion", suggestion); 
//
//		return findOneResult(" select s " +
//						   " from SuggestionsProduct s " +
//						   " where s.suggestion = :suggestion " , parameters);
//	}
//	
//	public SuggestionsProduct getProductBySuggestionSession(String suggestion, String session) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("suggestion", suggestion); 
//		parameters.put("session", session); 
//
//		return findOneResult(" select s " +
//						   " from SuggestionsProduct s " +
//						   " inner join fetch s.suggestionsUser su " +
//						   " where s.suggestion = :suggestion " +
//						   " and su.sessionGuid = :session ", parameters);
//	}
//}
package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.model.Suggestions;

public class SuggestionsDAO extends GenericDaoImpl<Suggestions> {
	
	private static final long serialVersionUID = 1L;

	public SuggestionsDAO() {
		super(Suggestions.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public Suggestions getProductBySuggestion(String suggestion) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("suggestion", suggestion); 

		return findOneResult(" select s " +
						   " from Suggestions s " +
						   " where s.suggestion = :suggestion " , parameters);
	}
	
	public Suggestions getProductBySuggestionSession(String suggestion, String session) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("suggestion", suggestion); 
		parameters.put("session", session); 

		return findOneResult(" select s " +
						   " from Suggestions s " +
						   " where s.suggestion = :suggestion " +
						   " and s.sessionGuid = :session ", parameters);
	}
	
	public List<Suggestions> getListSuggestions(Integer userId, String session) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", userId); 
		parameters.put("session", session); 

		return  findResults(" select s " +
							  " from Suggestions s " +
							  " where s.users.id = :userId " +
							  " and s.sessionGuid = :session ", parameters);
	}
}
