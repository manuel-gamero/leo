package com.mg.web.struts.action.ecommerce;

import org.apache.log4j.Logger;

import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;


/***************************************************************************************
* Warning !! CMCIC_Config contains the key, you have to protect this file with all     *   
* the mechanism available in your development environment.                             *
* You may for instance put this file in another directory and/or change its name       *
***************************************************************************************/

/*class CMCIC_Config
{
final static String CMCIC_CLE = "EC9B0440125A8F24B1106D858D7A71B84B66B0PA";
final static String CMCIC_TPE = "0002083";
final static String CMCIC_VERSION = "3.0";
final static String CMCIC_SERVEUR = "https://paiement.e-i.com/desjardins/test/" ;//"https://paiement.e-i.com/desjardins/test/paiement.cgi" ; //"https://paiement.creditmutuel.fr/test/";
final static String CMCIC_CODESOCIETE = "testatelie";
final static String CMCIC_URLOK = "http://latelierdeleo.no-ip.org/shoppingCartPaymentOk.action";
final static String CMCIC_URLKO = "http://latelierdeleo.no-ip.org/shoppingCartPaymentReturn.action";
}*/

class CMCIC_Config
{
	private static final Logger log = Logger.getLogger(CMCIC_Config.class);
	private static ConfigService configService;
	static {
		try {
			configService = ServiceLocator.getService(ConfigServiceImpl.class);
		} catch (ServiceLocatorException e) {
			log.error("A problem has happened while looking up the config service.", e);
		}
	}
	
final static String CMCIC_CLE = configService.getCmcicKey();
final static String CMCIC_TPE = configService.getCmcicTpe();
final static String CMCIC_VERSION = configService.getCmcicVersion();
final static String CMCIC_SERVEUR = configService.getCmcicServer(); //"https://p.monetico-services.com/test/" ;//"https://paiement.e-i.com/desjardins/test/paiement.cgi" ; //"https://paiement.creditmutuel.fr/test/";
final static String CMCIC_CODESOCIETE = configService.getCmcicSocieteCode();
final static String CMCIC_URLOK = configService.getCmcicUtlOk();
final static String CMCIC_URLKO = configService.getCmcicUrlKo();
}
