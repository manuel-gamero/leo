package com.mg.web.struts.action.shopping.payment.rbc;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mg.annotation.Action;
import com.mg.exception.CurrencyNoExistException;
import com.mg.model.Item;
import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.util.CommonUtils;
import com.mg.util.currency.CurrencyUtils;
import com.mg.web.WebConstants;
import com.mg.web.struts.action.ecommerce.CMCIC_Hmac;
import com.mg.web.struts.action.ecommerce.CMCIC_Tpe;
import com.mg.web.struts.action.shopping.BasicShoppingCart;
import com.mg.web.util.ConnectionWebUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class ShoppingCartPaymentSend extends BasicShoppingCart  implements Preparable{

	private static final long serialVersionUID = 5889902524032581061L;
	private static final Logger log = Logger.getLogger(ShoppingCartPaymentSend.class);
	private String sTexteLibre = "Texte Libre";
	private String bankUrl;
	private String sChaineMAC;
	private String reference;
	private String montant;
	private String tpe;
	private boolean termsConditions;
	
	@Override
	public void validate() {
		if(! termsConditions){
			String error = getText("bolsos.shoppingcart.summary.error.terms");
			ActionContext.getContext().getSession().put(WebConstants.ERRORACTION, error);
			addActionError(error);
		}
	}
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
	}

	private String datePaiement(String string, int i) {
		SimpleDateFormat formatter = new SimpleDateFormat(string);
		Date now = new Date();
		return formatter.format(now);
	}

	@Action(value="sTexteLibre = #sTexteLibre , bankUrl = #bankUrl ")
	@Override
	public String execute() {
		try{
			// Language of the company code
			final String lgue = getCurrentLanguage().toUpperCase();
			String sChaineMAC;
			String date = datePaiement("dd/MM/yyyy:HH:mm:ss", 0);
			CMCIC_Tpe oTpe = new CMCIC_Tpe(lgue);
			CMCIC_Hmac oMac = new CMCIC_Hmac(oTpe);
			ShoppingCart shoppingCart = null;
			/*-----------------------------------------------------------------------*
		     * control string needed by support
		     *-----------------------------------------------------------------------*/
			//sChaineDebug = "V1.04.sha1.java--[CtlHmac" + oTpe.sVersion + oTpe.sNumero + "]-" + oMac.computeHmac("CtlHmac" + oTpe.sVersion + oTpe.sNumero);
			if(!isValidShoppingCart()){
				return ERROR;
			}
			else{
				shoppingCart = getShoppingCart();
				//Apply coupon
				applyCoupon();
				//Save values in shopping cart object
				saveShoppingCart();
				reference = getShoppingCart().getReference();
							
				//Set values for payment
				bankUrl = oTpe.sUrlPaiement + "?";
				String montant = CommonUtils.formatPrice(getTotal()).replace(",", ".");
				String currency = getCurrentCurrencyCode();
				
				/*-----------------------------------------------------------------------*
			     * data string for hmac
			     *-----------------------------------------------------------------------*/ 
				sChaineMAC = oTpe.sNumero + "*" + date + "*" + 
							montant.toString()+ currency + "*" + 
							reference + "*" + sTexteLibre + "*" + oTpe.sVersion + "*" + lgue + "*" + 
							oTpe.sCodeSociete + "*" + getUserSession().getEmail() + "**********";
				
				log.debug("sChaineMAC: " + sChaineMAC);
				
				bankUrl = bankUrl + "lgue=" + ConnectionWebUtils.getParameter(lgue);
				bankUrl = bankUrl + "&mail=" + ConnectionWebUtils.getParameter(getUserSession().getEmail());
				bankUrl = bankUrl + "&reference=" + ConnectionWebUtils.getParameter(reference);
				bankUrl = bankUrl + "&version=" + ConnectionWebUtils.getParameter(oTpe.sVersion);
				bankUrl = bankUrl + "&TPE=" + ConnectionWebUtils.getParameter(oTpe.sNumero);
				bankUrl = bankUrl + "&date=" + ConnectionWebUtils.getParameter(date);
				bankUrl = bankUrl + "&montant=" + ConnectionWebUtils.getParameter(montant + currency);
				bankUrl = bankUrl + "&MAC=" + ConnectionWebUtils.getParameter(oMac.computeHmac(sChaineMAC));
				bankUrl = bankUrl + "&url_retour=" + ConnectionWebUtils.getParameter(oTpe.sUrlKo);
				bankUrl = bankUrl + "&url_retour_ok=" + ConnectionWebUtils.getParameter(oTpe.sUrlOk);
				bankUrl = bankUrl + "&url_retour_err=" + ConnectionWebUtils.getParameter(oTpe.sUrlKo);
				bankUrl = bankUrl + "&societe=" + ConnectionWebUtils.getParameter(oTpe.sCodeSociete);
				bankUrl = bankUrl + "&texte-libre=" + ConnectionWebUtils.getParameter("Texte Libre");
				
				log.debug("Calling url payment: " + bankUrl);
				
				//Register coupon to the user
				registeCouponToUser( shoppingCart.getUsers(), getCoupon() );
				
				if(shoppingCart.getUsers() == null || shoppingCart.getMethodShipping() == null ||
				   shoppingCart.getPaymentMethod() == null || shoppingCart.getShippingAddressId() == null ||
				   shoppingCart.getShippingFees() == null || shoppingCart.getShoppingCartItems() == null ||
				   shoppingCart.getTaxes() == null || shoppingCart.getTotal() == null){
					return ERROR;
				}
				else{
					//ServiceLocator.getService(ShoppingServiceImpl.class).saveShoppingCart(shoppingCart);
					ServiceLocator.getService(ShoppingServiceImpl.class).updateShoppingCart(shoppingCart);
					//Receipt.orderSummaryConfirmation(userSession, shoppingCart, getCurrentLanguage(), getCurrentCountry());
				}
				
				if( getCoupon() != null && getTotal().floatValue() <= 0 ){
					log.info( "User: " + shoppingCart.getUsers() + " coupon: " + getCoupon().getCouponName() + " FREE!!! ");
					this.sChaineMAC = oMac.computeHmac(sChaineMAC);
					this.reference = shoppingCart.getReference();
					this.montant = String.valueOf( shoppingCart.getTotalShopping() );
					this.tpe = ConnectionWebUtils.getParameter(oTpe.sNumero);
					return FREE;
				}
				return SUCCESS;
			}
		}
		catch(Exception e){
			managerException(e);
			return ERROR;
		}
	}

	private String getTotal(List<Item> itemList) throws NumberFormatException, CurrencyNoExistException {
		BigDecimal total = new BigDecimal(0.00);
		for (Item item : itemList) {
			total = total.add(CurrencyUtils.priceToLocale( item.getProduct().getPrice(), getCurrentCurrencyCode()));
		}
		return String.valueOf(total);
	}

	public String getBankUrl() {
		return bankUrl;
	}

	public void setBankUrl(String bankUrl) {
		this.bankUrl = bankUrl;
	}
	
	public static void main(String args[]){
		try {
		final String lgue = "EN"; 
		CMCIC_Tpe oTpe = new CMCIC_Tpe(lgue);
		CMCIC_Hmac oMac = new CMCIC_Hmac(oTpe);
		
		//String sChaineMAC = "0002083*02/10/2015:20:31:13*46.0CAD*ref203113*Texte Libre*3.0*EN*testatelie*test5@ts.es**********";
		String sChaineMAC = "6911630*15/02/2016:13:05:28*107.00CAD*160215130528*Texte Libre*3.0*EN*atelierdel*test15@ts.eses********** ";
		System.out.println("sChaineMAC:  " + sChaineMAC);
		System.out.println("MAC:  " + oMac.computeHmac(sChaineMAC));
		
		System.out.println("sChaineMAC:  " + URLEncoder.encode(oMac.computeHmac(sChaineMAC) , "UTF-8"));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getsChaineMAC() {
		return sChaineMAC;
	}

	public String getReference() {
		return reference;
	}

	public String getMontant() {
		return montant;
	}

	public String getTpe() {
		return tpe;
	}

	public boolean isTermsConditions() {
		return termsConditions;
	}

	public void setTermsConditions(boolean termsConditions) {
		this.termsConditions = termsConditions;
	}
}
