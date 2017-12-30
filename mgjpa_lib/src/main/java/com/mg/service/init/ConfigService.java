package com.mg.service.init;

import java.util.List;

import com.mg.exception.InitializationException;
import com.mg.exception.ServiceException;
import com.mg.model.Audit;
import com.mg.model.Translation;
import com.mg.service.Service;
import com.mg.service.search.autocompleter.core.AutocompleterConfigurator;

/**
 * Configuration service interface that is used whenever a client desires
 * accessing configuration properties.
 *  
 * @author MJGP
 *
 */
public interface ConfigService extends Service{
	
	
	/**
	 * This method initialize just the properties. 
	 * Give access to properties in the property file to other classes that run in a different VM.
	 * @throws Exception
	 */
	void initializeProperties() throws Exception;
	
	/**
	 * Initializes the config service. 
	 * @throws InitializationException
	 */
	void initialize() throws InitializationException;
	
	/**
	 * Shuts down the config service
	 */
	void shutdown();
	
	String getRootPathWeb();
	
	/**
	 * Method to get the location where we store all javascript scripts for the project
	 */
	String getWebScriptsLocation();
	
	/**
	 * Method to get the location where we store all css files for the project
	 */
	String getWebCssLocation();
	
	/**
	 * Method to get the location where we store all images files for the project
	 */
	String getWebImagesLocation();

	/**
	 * Method to get the location where we store all products image files for the project
	 */
	String getWebImageProdcutLocation();
	
	/**
	 * Method to get the location where we store all collections image files for the project
	 */
	String getWebImageCollectionLocation();
	
	/**
	 * Method to get the location where I save the png file temporarily
	 */
	String getWebImageTmp();
	
	/**
	 * Method to get the location where we store all media files for the project (covers, screenshots, etc ...)
	 */
	String getWebMediaLocation();
	
	//Mails
	String getMailFromWebmaster();
	String[] getMailDefaultDebugger();
	String[] getMailProjectSupport();
	String getMailSMTP();
	String[] getMailErrors();
	
	/**
	 * Tells whether to collect cache statistics on shut down.
	 * @return
	 */
	boolean isCacheStatisticsEnabled();
	
	/**
	 * Determines the default number of search results.
	 * @return
	 */
	int getSearchNumberOfResults();
	
	/**
	 * Returns the configurator of the search autocompleter.
	 * @return
	 */
	AutocompleterConfigurator getSearchConfigurator();

	/**
	 * @return True if the taxes logic applay, false otherwise
	 */
	boolean isTaxesEnable();
	
	/**
	 * @param currency
	 * @return The price for extra text for the currency as parameter.
	 */
	public String getPriceExtraText(String currencyCode);
	
	public int saveAudit( Audit audit ) throws ServiceException;
	
	public boolean isDebugSendEmail();
	
	public boolean isDebugTraceError();
	
	public String getCmcicKey();
	
	public String getCmcicTpe();
	
	public String getCmcicServer();
	
	public String getCmcicSocieteCode();
	
	public String getCmcicVersion();
	
	public String getCmcicUrlKo();
	
	public String getCmcicUtlOk();
	
	public List<Translation> getAllTranslation() throws ServiceException ;
	
	public Translation getTranslation(final Integer id) throws ServiceException ;
	
	public String getWebImageSocial();
	
	public String[] getUrlAllow();
	
	//Monitoring
	public String[] getMonitoringUrls();
	
	public String[] getAuditFilter();
	
	public String getSitemapUrl();
	
	public String getUrlServer();

	//develop information 
	//https://developer.paypal.com/docs/classic/api/merchant/SetExpressCheckout_API_Operation_NVP/?mark=SetExpressCheckout
	
	String getPaypalPwd();

	String getPaypalUser();

	String getPaypalSignature();

	String getPaypalVersion();

	/**
	 * (Required) URL to which the buyer's browser is returned after choosing to pay with PayPal. For digital goods, you must add JavaScript to this page to close the in-context experience.
	 * Note: PayPal recommends that the value be the final review page on which the buyer confirms the order and payment or billing agreement.
	 * Character length and limitations: 2048 single-byte characters.
	 * @return
	 */
	
	String getPaypalUrlOk();

	/**
	 * (Required) URL to which the buyer is returned if the buyer does not approve the use of PayPal to pay you. For digital goods, you must add JavaScript to this page to close the in-context experience.
	 * Note: PayPal recommends that the value be the original page on which the buyer chose to pay with PayPal or establish a billing agreement.
	 *  Character length and limitations: 2048 single-byte characters.
	 * @return
	 */
	String getPaypalUrlError();

	String getPaypalEndpointCertificate();

	String getPaypalEndpointSignature();

	/**
	 * This is the url that we have to redirect the user.
	 * In this url, we have to appened the token received.
	 * @return
	 */
	String getPaypalUrlCheckout();

	String getPaymentAllow();

	String getPaymentDefault();

	String getPaymentPaypalMethod();

	String getPaypalCertPsw();

	String getWebResourceSSL();

	String getPathJavaCacert();

	boolean isCurrencyChoice();

	boolean isAllowMultyCurrency();

	String getFilterRequestExt();

	String getFilterRequestForward();

	String getInstagramClientID();

	String getInstagramAccessToken();

	String getInstagramClientSecret();

	String getInstagramUserId();

	String getWebImagePromotionLocation();
}
