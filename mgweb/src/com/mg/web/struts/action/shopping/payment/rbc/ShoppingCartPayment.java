package com.mg.web.struts.action.shopping.payment.rbc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.annotation.Action;
import com.mg.enums.PaymentCVX;
import com.mg.enums.PaymentCodeReturn;
import com.mg.enums.PaymentFraudCode;
import com.mg.enums.PaymentMethodType;
import com.mg.enums.ShoppingCartStatus;
import com.mg.model.Coupons;
import com.mg.model.ShoppingCart;
import com.mg.model.Transaction;
import com.mg.service.ServiceLocator;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.mg.web.struts.action.ecommerce.CMCIC_Hmac;
import com.mg.web.struts.action.ecommerce.CMCIC_Tpe;

public class ShoppingCartPayment extends BasicAction {
	
	private static final long serialVersionUID = 8439090570963991085L;
	private static Logger log = LogManager.getLogger(ShoppingCartPayment.class);
	private InputStream inputStream;
	
	private String montant ;
	private String TPE;
	private String reference ;
	private String MAC ;
	private String textelibre ;
	private String coderetour ;
	private String date ;
	private String cvx ;
	private String vld ;
	private String brand ;
	private String status3ds ;
	private String numauto ;
	private String motifrefus ;
	private String originecb ;
	private String bincb ;
	private String hpancb ;
	private String ipclient ;
	private String originetr ;
	private String veres ;
	private String pares ;
	private String montantech;
	private String filtragecause;
	private String filtragevaleur;
	private String cbenregistree;
	private String cbmasquee;
	private String motifdebrayage;
	private String modepaiement;
	private boolean free = false;
	
	@Override
	@Action(value="TPE = #TPE, date = #date, montant = #montant, reference = #reference, texte-libre = #texte-libre, MAC = #MAC, code-retour = #code-retour, cvx = #cvx, vld = #vld, brand = #brand, status3ds = #status3ds, numauto = #numauto, motifrefus = #motifrefus, originecb = #originecb, bincb = #bincb, hpancb = #hpancb, ipclient = #ipclient, originetr = #originetr, veres = #veres, pares = #pares, motifdebrayage = #motifdebrayage, modepaiement = #modepaiement ")
	public String execute() {
		try {
			CMCIC_Tpe oTpe = new CMCIC_Tpe();
			CMCIC_Hmac oMac = new CMCIC_Hmac(oTpe);
			/*if(!oTpe.sNumero.equals(TPE)){
				return ERROR;
			}*/
			/*-----------------------------------------------------------------------*
		     * data string for hmac
		     *-----------------------------------------------------------------------*/
			String sChaineMAC = TPE + "*" + date + "*" + montant + "*" + reference + "*" + textelibre + "*" + oTpe.sVersion + "*" + coderetour + "*";
			sChaineMAC += cvx + "*" + vld + "*" + brand + "*" + status3ds + "*" + numauto + "*" + motifrefus + "*";
			sChaineMAC += originecb + "*" + bincb + "*" + hpancb + "*" + ipclient + "*" + originetr + "*" + veres + "*" + pares + "*";
	        
			/*String sChaineMAC = TPE + "*" + date + "*" + montant + "*" + reference + "*" + textelibre + "*" + oTpe.sVersion + "*" + coderetour + "*" +
								cvx + "*" + vld + "*" + brand + "*" + status3ds + "*" + numauto + "*********";
			*/
			log.debug("sChaineMAC : " + sChaineMAC );
			PaymentCodeReturn returnCode = PaymentCodeReturn.getPaymentCodeReturn(coderetour);
			Coupons coupon = getCoupon();
			if (coupon != null && Float.valueOf(getMontant()) <= 0 && oMac != null){
				free = true;
			}
			
			log.debug("returnCode : " + returnCode.toString() );
	        if (oMac.isValidHmac(sChaineMAC.replace("null", ""), MAC) == true || free ) {
	        	
	        	com.mg.model.ShoppingCart shoppingCart = ServiceLocator.getService(ShoppingServiceImpl.class).getShoppingCartByReference(reference);
        		
	        	//Save transaction
        		Transaction transaction = generateTranscation(shoppingCart);
        		
        		//Modify shoppingCart with the last transaction
        		shoppingCart.setPurchaseDate(getPurchaseDateFromBank(date));
        		
	        	if (returnCode.equals(PaymentCodeReturn.ANNULATION)) {
	        		
	        		shoppingCart.setStatusCode(ShoppingCartStatus.CANCEL);
					/* Payment has been refused
					   put your code here (email sending / Database update)
					   Attention : an authorization may still be delivered later   */
	        	}
	        	else if (returnCode.equals(PaymentCodeReturn.TEST) ||
	        			 returnCode.equals(PaymentCodeReturn.PAIEMENT)) {
	        		
	        		BigDecimal totalToVerify = shoppingCart.getTotal().add(shoppingCart.getShippingFees()).add(shoppingCart.getTaxes());
	        		if(!(String.valueOf(totalToVerify) + shoppingCart.getCurrency()).equals(montant)){
	        			log.error("Reference: " + reference + " total: " + shoppingCart.getTotal() + " montant: " + montant);
	        		}
	        		
	        		shoppingCart.setStatusCode(ShoppingCartStatus.PAIMENT);
	        		//ServiceLocator.getService(ShoppingServiceImpl.class).updateShoppingCart(shoppingCart);
	        		
	        		//Send email to user
	        		log.debug("Sending email for reference: " +  reference);
	        		sendEmailToUser(shoppingCart);
	        		
	        		//Inactive coupon if the user has used one
	        		inactiveCoupon( shoppingCart.getUsers() );
	        	}
	        	
        		shoppingCart.setPaymentMethod(PaymentMethodType.getEnumByCode(brand));
        		shoppingCart.setTransaction(transaction);
        		
        		ServiceLocator.getService(ShoppingServiceImpl.class).updateTransaction(transaction);
        		if(free){
        			return FREE;
        		}
        		else{
        			log.debug("Send infor OK version=2\ncdr=0\n for ref: " + reference );
        			setInputStream(new ByteArrayInputStream("version=2\ncdr=0\n".getBytes()));
        			return SUCCESS;
        		}
	        }
	        
		}
		catch (Exception e) {				
			managerException(e);
		}
		log.warn("ERROR MAC RECEIVED : " + MAC);
		//setInputStream(new ByteArrayInputStream("version=2\r\ncdr=1\r\n".getBytes("UTF-8")));
		log.debug("Send infor ERROR version=2\ncdr=1\n for ref: " + reference );
		if(free){
			return ERRORFREE;
		}
		else{
			setInputStream(new ByteArrayInputStream("version=2\ncdr=1\n".getBytes()));
			return ERROR;
		}
	}

	private Date getDate(String date2) {
		DateFormat format = new SimpleDateFormat("DD/MM/YYY_a_HH:MM:SS");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			return new Date();
		}
	}

	private Transaction generateTranscation(ShoppingCart shoppingCart) throws ParseException {
		Transaction transaction = new Transaction();
		transaction.setShoppingCart(shoppingCart);
		transaction.setBincb(bincb);
		transaction.setBrand(PaymentMethodType.getEnumByCode(brand));
		transaction.setCbenregistree(cbenregistree);
		transaction.setCbmasquee(cbmasquee);
		transaction.setCodeRetour(PaymentCodeReturn.getPaymentCodeReturn(coderetour));
		transaction.setCreationDate(new Date());
		transaction.setCvx(PaymentCVX.getEnumByCode(cvx));
		transaction.setFiltragecause(PaymentFraudCode.getEnumByCode(filtragecause));
		transaction.setHpancb(hpancb);
		transaction.setIpclient(ipclient);
		transaction.setMac(MAC);
		transaction.setMontant(montant);
		transaction.setOriginecb(originecb);
		transaction.setOriginetr(originetr);
		transaction.setPurchaseDate(getPurchaseDateFromBank(date));
		transaction.setStatus3ds(status3ds);
		transaction.setTexteLibre(textelibre);
		transaction.setTpe(TPE);
		transaction.setVld(vld);
		
		return transaction;
	}

	private Date getPurchaseDateFromBank(String purchaseDate){
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			date = formatter.parse(purchaseDate.replace("_a_"," "));
		} catch (Exception e) {
			date = new Date();
			log.error(e.getMessage(), e);
		}
		
		return date;
	}
	public String getMontant() {
		return montant;
	}
	public void setMontant(String montant) {
		this.montant = montant;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getMAC() {
		return MAC;
	}
	public void setMAC(String mAC) {
		MAC = mAC;
	}

	public String getTPE() {
		return TPE;
	}

	public void setTPE(String tPE) {
		TPE = tPE;
	}

	public String getTextelibre() {
		return textelibre;
	}

	public void setTextelibre(String textelibre) {
		this.textelibre = textelibre;
	}

	public String getCoderetour() {
		return coderetour;
	}

	public void setCoderetour(String coderetour) {
		this.coderetour = coderetour;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCvx() {
		return cvx;
	}

	public void setCvx(String cvx) {
		this.cvx = cvx;
	}

	public String getVld() {
		return vld;
	}

	public void setVld(String vld) {
		this.vld = vld;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getStatus3ds() {
		return status3ds;
	}

	public void setStatus3ds(String status3ds) {
		this.status3ds = status3ds;
	}

	public String getNumauto() {
		return numauto;
	}

	public void setNumauto(String numauto) {
		this.numauto = numauto;
	}

	public String getMotifrefus() {
		return motifrefus;
	}

	public void setMotifrefus(String motifrefus) {
		this.motifrefus = motifrefus;
	}

	public String getOriginecb() {
		return originecb;
	}

	public void setOriginecb(String originecb) {
		this.originecb = originecb;
	}

	public String getBincb() {
		return bincb;
	}

	public void setBincb(String bincb) {
		this.bincb = bincb;
	}

	public String getHpancb() {
		return hpancb;
	}

	public void setHpancb(String hpancb) {
		this.hpancb = hpancb;
	}

	public String getIpclient() {
		return ipclient;
	}

	public void setIpclient(String ipclient) {
		this.ipclient = ipclient;
	}

	public String getOriginetr() {
		return originetr;
	}

	public void setOriginetr(String originetr) {
		this.originetr = originetr;
	}

	public String getVeres() {
		return veres;
	}

	public void setVeres(String veres) {
		this.veres = veres;
	}

	public String getPares() {
		return pares;
	}

	public void setPares(String pares) {
		this.pares = pares;
	}

	public String getMontantech() {
		return montantech;
	}

	public void setMontantech(String montantech) {
		this.montantech = montantech;
	}

	public String getFiltragecause() {
		return filtragecause;
	}

	public void setFiltragecause(String filtragecause) {
		this.filtragecause = filtragecause;
	}

	public String getFiltragevaleur() {
		return filtragevaleur;
	}

	public void setFiltragevaleur(String filtragevaleur) {
		this.filtragevaleur = filtragevaleur;
	}

	public String getCbenregistree() {
		return cbenregistree;
	}

	public void setCbenregistree(String cbenregistree) {
		this.cbenregistree = cbenregistree;
	}

	public String getCbmasquee() {
		return cbmasquee;
	}

	public void setCbmasquee(String cbmasquee) {
		this.cbmasquee = cbmasquee;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getMotifdebrayage() {
		return motifdebrayage;
	}

	public void setMotifdebrayage(String motifdebrayage) {
		this.motifdebrayage = motifdebrayage;
	}

	public String getModepaiement() {
		return modepaiement;
	}

	public void setModepaiement(String modepaiement) {
		this.modepaiement = modepaiement;
	}
	
}
