package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.model.Item;

public class ItemDAO extends GenericDaoImpl<Item> {
	
	private static final long serialVersionUID = 1L;

	public ItemDAO() {
		super(Item.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public List<Item> getListItemByProduct(Integer productId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", productId);    

		return  findResults(" select i.product.id, i.nameImage, count(*) " +
							  " from Item i " +
							  " where i.product.id = :id " +
							  " and i.id not in (select it from Item it " +
							  " join it.itemComponents itic " +
							  " join itic.customComponentCollection iticccc " +
							  " where iticccc.statusCode = 'INACTIVE' " +
							  " and it.product.id = :id) " +
							  " group by i.product.id, i.nameImage ", parameters);
	}

	public List<Item> getSuggestionByCustomProduct(int productId, String session, String currency) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);
		parameters.put("session", session);
		parameters.put("currency", currency);

//		return (List<Item>) findResults(" select distinct i, su " +
//									  	" from ProductData pd1 " +
//									  	" left join pd1.product p " +
//									  	" left join p.items i " +
//									  	" left join i.suggestionsProduct sp " +
//									  	" left join sp.suggestionsUser su with su.sessionGuid = :session " +
//									  	" where pd1.cluster in (select pd2.cluster " +
//									  	" from ProductData pd2 " +
//									  	" where pd2.product.id = :productId )" +
//									  	" order by su.count asc", parameters);
//		return (List<Item>) findResults(" select distinct i, (select count(sp) from SuggestionsProduct sp " +
//															" left join sp.suggestionsUser su " +
//															" where sp.suggestion = i.nameImage " +
//															" and su.sessionGuid = :session ) "+
//															
//									  	" from ProductData pd1 " +
//									  	" left join pd1.product p " +
//									  	" left join p.items i " +
//									  	" where pd1.cluster in (select pd2.cluster " +
//									  	" from ProductData pd2 " +
//									  	" where pd2.product.id = :productId )" , parameters);
		return (List<Item>) findResults(" select i.nameImage, p.id, coalesce(s.count,0), s.id, " +
										" sum(dp.count), sum(dp.shareCount), " +
										" sum(dp.addCount), sum(dp.sellCount), sum(dp.removeCount) " +			
													
										" from ProductData pd1 " +
										" left join pd1.product p " +
										" join p.price pp " +
										" join pp.priceEntries ppp " +
										" join p.items i " +
										" join i.itemComponents ic " +
										"join ic.customComponentCollection icccc " +
										" left join p.deviceProducts dp " +
										" left join Suggestions s on (s.suggestion = i.nameImage and s.sessionGuid = :session)" +
										" where pd1.cluster in (select pd2.cluster " +
																" from ProductData pd2 " +
																" where pd2.product.id = :productId )" +
										" and ppp.currency = :currency " +
										" and p.statusCode = 'ACTIVE' " +
										" and icccc.statusCode = 'ACTIVE' " +
										" group by i.nameImage, p.id, s.count, s.id " + 
										" order by coalesce(s.count,0) asc,sum(dp.sellCount) desc, " +
										" sum(dp.removeCount) asc,sum(dp.addCount) desc, " +
										" sum(dp.shareCount) desc,sum(dp.count) desc ", parameters);
//		return (List<Item>) findResults(" select i, s"+
//										" from ProductData pd1 " +
//										" left join pd1.product p " +
//										" left join p.items i " +
//										" left join i.suggestions s with s.sessionGuid = :session " +
//										" where pd1.cluster in (select pd2.cluster " +
//										" from ProductData pd2 " +
//										" where pd2.product.id = :productId ) " +
//										" order by s.count desc ", parameters);
	}
}
