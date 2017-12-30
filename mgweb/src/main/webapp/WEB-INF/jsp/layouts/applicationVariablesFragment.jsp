<%@ taglib uri="/struts-tags" prefix="s" %>

<s:set var="images"  			value="@com.mg.web.WebConstants@IMAGES_BASE_URL" />
<s:set var="collectionpath"  	value="@com.mg.web.WebConstants@COLLECTION_BASE_URL" />
<s:set var="productpath"  		value="@com.mg.web.WebConstants@PRODUCT_BASE_URL" />
<s:set var="tmppath"  			value="@com.mg.web.WebConstants@TMP_BASE_URL" />
<s:set var="promotionpath"  	value="@com.mg.web.WebConstants@PROMOTION_BASE_URL" />
<s:set var="media"  			value="@com.mg.web.WebConstants@MEDIA_BASE_URL" />
<s:set var="scripts"  			value="@com.mg.web.WebConstants@SCRIPTS_BASE_URL" />
<s:set var="css"  				value="@com.mg.web.WebConstants@CSS_BASE_URL" />
<s:set var="isLogin"  			value="#session[@com.mg.web.WebConstants@USER] != null" />
<s:set var="isCurrencyChoice"	value="@com.mg.web.WebConstants@CURRENCY_CHOICE" />
<s:set var="loginName" 			value="#session[@com.mg.web.WebConstants@USER].login" />
<s:set var="thereIsCoupon"		value="#session[@com.mg.web.WebConstants@SHOPPING_CART_COUPON] != null" />
<s:set var="shoppingCartCount"	value="(#session[@com.mg.web.WebConstants@SHOPPING_CART_ITEMS] != null? (#session[@com.mg.web.WebConstants@SHOPPING_CART_ITEMS]).size():0)" />



