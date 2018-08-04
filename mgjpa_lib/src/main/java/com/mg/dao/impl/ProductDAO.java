package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.mg.enums.ProductType;
import com.mg.model.Product;

public class ProductDAO extends GenericDaoImpl<Product> {

	private static final long serialVersionUID = 1L;
	
	private static final String joinClause =" from Product p " +
										" join fetch p.image ima " +
										" left join fetch ima.customComponentTexts cct " +
										" join fetch p.collection co " + 
										" left join fetch p.price ppr " +
										" left join fetch ppr.priceEntries " + 
										" left join fetch p.productImages pi " +
										" left join fetch pi.image ";
	private static final String sqlFindProduct = " select distinct p from Product p " +
												 " join fetch p.image ima " +
												 " join fetch p.collection co " +
												 " where p.statusCode = 'ACTIVE' ";

	public ProductDAO() {
		super(Product.class);
	}
	
	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	public Product findProductById(int id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult("select p " + joinClause + 
							" where p.id = :id " +
							" and ima.typeCode = 'PRODUCT' ", parameters);
	}
	
	public Product findCustomProductForProduct(int collectionId, ProductType type){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("collectionId", collectionId);
		parameters.put("type", type);

		return  findOneResult("select p " +
							" from Product p " +
							" where p.collection.id = :collectionId " +
							" and p.statusCode = 'ACTIVE' " +
							" and p.customProduct = true " +
							" and p.typeCode = :type", parameters);
	}
	
	//@Fetch (FetchMode.SELECT)
	public List<Product> getAll(){

		return  findResults(" select distinct p " + joinClause + 
							" where ima.typeCode = 'PRODUCT' " +
							" and co.statusCode = 'ACTIVE'" +
							" and p.statusCode = 'ACTIVE' " );
	}
	
	@Fetch (FetchMode.SELECT)
	public List<Product> getAllForAdmin(){

		return  findResults(" select distinct p from Product p " +
							" join fetch p.collection co ");
	}
	
	public List<Product> findProductByType(ProductType type, Boolean customProduct){
		
		if(customProduct == null){
			customProduct = true;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("typeCode", type);  
		parameters.put("customProduct", customProduct);

		return  findResults( sqlFindProduct +
							" and p.typeCode = :typeCode " + 
							" and p.customProduct = :customProduct ", parameters);
	}
	
	public List<Product> findCustomProductByType(ProductType type, Boolean customProduct){
		
		if(customProduct == null){
			customProduct = true;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("typeCode", type);  
		parameters.put("customProduct", customProduct);

		return  findResults( sqlFindProduct +
							" and co.statusCode = 'ACTIVE' " +
							" and p.typeCode = :typeCode " + 
							" and p.customProduct = :customProduct ", parameters);
	}
	
	public List<Product> findProductByCollection(int collectionId, Boolean customProduct){
		
		if(customProduct == null){
			customProduct = true;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("collectionId", collectionId);
		parameters.put("customProduct", customProduct);

		return  findResults(" select distinct p " +
							" from Product p " +
							" join fetch p.image ima " +
							" join fetch p.collection co " +
							" where co.statusCode = 'ACTIVE'" +
							" and p.statusCode = 'ACTIVE' " +
							" and p.collection.id = :collectionId " +
							" and p.customProduct = :customProduct ", parameters);
	}

	public List<Product> getAllDiscount(String currencyCode){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("currencyCode", currencyCode);
		
		return  findResults(" select distinct p " + 
							" from Product p " +
							" left join p.price ppr " +
							" left join ppr.priceEntries pprpe" +
							" left join p.collection c " + 
							" where p.statusCode = 'ACTIVE' " +
							//" and c.statusCode = 'ACTIVE' " +
							" and pprpe.discount is not null " +
							" and pprpe.currency = :currencyCode ", parameters);
	}
}
