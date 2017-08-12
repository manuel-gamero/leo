package com.mg.web.struts.action.ecommerce;

/*****************************************************************************
*
* "Open source" kit for CM-CIC P@iement (TM)
*
* File "CMCIC_Tpe.java":
*
* Author   : Euro-Information/e-Commerce (contact: centrecom@e-i.com)
* Version  : 1.04
* Date     : 01/01/2009
*
* Copyright: (c) 2009 Euro-Information. All rights reserved.
* License  : see attached document "License.txt".
*
*****************************************************************************/

/*===========================================================================*
 *                              Imported modules
 *===========================================================================*/

import java.lang.String;

final public class CMCIC_Tpe {
	
	final static String CMCIC_URLPAIEMENT = "paiement.cgi";
	public String sVersion;
	public String sNumero;
	public String sUrlPaiement;
	public String sCodeSociete;
	public String sLangue;
	public String sUrlOk;
	public String sUrlKo;
	
	private String _sCle;
	
	public CMCIC_Tpe (String sLang) throws Exception {
		
		this.sVersion 		= CMCIC_Config.CMCIC_VERSION;
		this._sCle 		= CMCIC_Config.CMCIC_CLE;
		this.sNumero 		= CMCIC_Config.CMCIC_TPE;
		this.sUrlPaiement 	= CMCIC_Config.CMCIC_SERVEUR + CMCIC_URLPAIEMENT;
		this.sCodeSociete 	= CMCIC_Config.CMCIC_CODESOCIETE;
		this.sLangue 		= (sLang != null) ? sLang : "FR";
		this.sUrlOk 		= CMCIC_Config.CMCIC_URLOK;
		this.sUrlKo 		= CMCIC_Config.CMCIC_URLKO;
	}
	
	public CMCIC_Tpe () throws Exception {
		
		this.sVersion 		= CMCIC_Config.CMCIC_VERSION;
		this._sCle 		= CMCIC_Config.CMCIC_CLE;
		this.sNumero 		= CMCIC_Config.CMCIC_TPE;
		this.sUrlPaiement 	= CMCIC_Config.CMCIC_SERVEUR + CMCIC_URLPAIEMENT;
		this.sCodeSociete 	= CMCIC_Config.CMCIC_CODESOCIETE;
		this.sLangue 		= "FR";
		this.sUrlOk 		= CMCIC_Config.CMCIC_URLOK;
		this.sUrlKo 		= CMCIC_Config.CMCIC_URLKO;
	}
		
	public String getCle() {
		
		return this._sCle;
	}
}

// End of abstract class


/*===========================================================================*/
