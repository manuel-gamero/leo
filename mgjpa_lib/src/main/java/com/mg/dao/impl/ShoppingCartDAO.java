package com.mg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.model.ShoppingCart;

public class ShoppingCartDAO extends GenericDaoImpl<ShoppingCart> {
	
	private static final long serialVersionUID = 1L;

	public ShoppingCartDAO() {
		super(ShoppingCart.class);
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}
	
	public ShoppingCart findEntityById(int id){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(" select distinct sc " +
							  " from ShoppingCart sc " +
						 	  " join fetch sc.shoppingCartItems sci " +
							  " join fetch sci.item scii " +
						 	  " join fetch scii.product p " +
						 	  " join fetch p.translationByNameTransId tn " +
						 	  " join fetch tn.translationEntries tne " +
							  " join fetch sc.users u " +
							  " join fetch sc.methodShipping ms " +
							  " join fetch sc.shippingAddressId sa " +
							  " left join fetch sc.transaction t " +
							  " where sc.id = :id", parameters);
	}
	
	public ShoppingCart findEntityByReference(String reference){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("reference", reference);    

		return  findOneResult(" select distinct sc " +
							  " from ShoppingCart sc " +
						 	  " join fetch sc.shoppingCartItems sci " +
						 	  " join fetch sci.item i " +
						 	  " join fetch i.product p " +
							  " join fetch sc.users u " +
							  " join fetch sc.methodShipping ms " +
							  " join fetch sc.shippingAddressId sa " +
							  " where sc.reference = :reference", parameters);
	}
	
	public List<ShoppingCart> getAll(){
		return  findResults(" select distinct sc " +
							" from ShoppingCart sc " +
						 	" join fetch sc.users u " +
							" join fetch sc.methodShipping ms " +
							" join fetch sc.shippingAddressId sa ");
	}

	public List<ShoppingCart> getAllByUser(Integer userId){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", userId);   
		
		return  findResults(" select distinct sc " +
							" from ShoppingCart sc " +
						 	" join fetch sc.users u " +
							" join fetch sc.methodShipping ms " +
							" join fetch sc.shippingAddressId sa " +
							" where u.id = :userId " +
							" and sc.statusCode <> 'NEW' ", parameters);
	}
	
	public ShoppingCart getShoppingCartByToken(String token){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("token", token);
		
		return findOneResult(" select distinct sc " +
							 " from ShoppingCart sc " +
							 " join fetch sc.shoppingCartItems sci " +
							 " join fetch sci.item i " +
							 " join fetch i.product p " +
							 " join fetch sc.users u " +
							 " join fetch sc.methodShipping ms " +
							 " join fetch sc.shippingAddressId sa " +
							 " join fetch sc.paypal pp " +
							 " where pp.token = :token ", parameters);
	}
}
