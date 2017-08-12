package com.mg.datamining.actions;

import java.util.regex.Pattern;

import com.mg.datamining.interfaces.ICreation;
import com.mg.model.Audit;

public class UserActionFactory {
	
	private static final UserActionFactory INSTANCE = new UserActionFactory();
	
	private UserActionFactory(){
		super();
	}
	
	public static UserActionFactory getInstace(){
		return INSTANCE;
	}
	
	public ICreation getUserAction(Audit audit){
		String userAction = audit.getActionUser();
		ICreation creationObj = null;
		if( userAction.contains("componentImage")){
			creationObj = new UserActionComponentImage();
		}
		else if( userAction.contains("shareImage")){
			creationObj = new UserActionShareProduct();
		}
		else if( userAction.contains("addProduct")){
			creationObj = new UserActionAddProduct();
		}
		else if( userAction.contains("removeItem")){
			creationObj = new UserActionRemoveProduct();
		}
		else if( isLookProductAction( userAction ) ){
			creationObj = new UserActionLookProduct();
		}
		else if( userAction.contains("login") || userAction.contains("registerUser") ){
			creationObj = new UserActionLogin();
		}
		else if( userAction.contains("auditAjax.execute") && audit.getMessage().contains("fpw[")){
			creationObj = new UserActionCreateDevice();
		}
		else if( userAction.contains("auditAjax.execute") && !audit.getMessage().contains("fpw[")){
			creationObj = new UserActionUrl();
		}
		else if( isCollectionOrType( userAction ) ){
			creationObj = new UserActionUrl();
		}
		else if( userAction.contains( "homePage" ) ){
			creationObj = new UserActionUrl();
		}
		else if( userAction.contains("shoppingCartPaymentSend") || userAction.contains("shoppingCartPaypalSend")){
			creationObj = new UserActionSoldProduct();
		}
		return creationObj;
	}

	private boolean isCollectionOrType(String userAction) {
		if(Pattern.compile("/type/products/").matcher(userAction).find()){
			return true;
		}
		else if(Pattern.compile("/type/produits/").matcher(userAction).find()){
			return true;
		}
		else if(Pattern.compile("/collections/produits/").matcher(userAction).find()){
			return true;
		}
		else if(Pattern.compile("/collections/products/").matcher(userAction).find()){
			return true;
		}
		return false;
	}

	private static boolean isLookProductAction(String userAction) {
		if(Pattern.compile("/product/[0-9]*/").matcher(userAction).find()){
			return true;
		}
		else if(Pattern.compile("/produit/[0-9]*/").matcher(userAction).find()){
			return true;
		}
		return false;
	}

}
