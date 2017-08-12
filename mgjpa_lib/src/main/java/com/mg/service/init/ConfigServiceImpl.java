package com.mg.service.init;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.AuditDAO;
import com.mg.dao.impl.TranslationDAO;
import com.mg.exception.CacheException;
import com.mg.exception.DaoException;
import com.mg.exception.InitializationException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Translation;
import com.mg.service.ServiceImpl;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.search.autocompleter.core.AutocompleterConfigurator;
import com.mg.util.io.PropertiesFileWrapper;

/**
 * Main configuration service.
 * The service loads application-wide configuration from properties files and
 * exposes a unique entry point for all other services.
 * 
 * This service should be initialized on application startup since other subsystems 
 * functionalities depend on it.
 * 
 * @author MJGP
 *
 */
public class ConfigServiceImpl extends ServiceImpl implements ConfigService{
	
	private static final Logger log = Logger.getLogger(ConfigServiceImpl.class);
		
	/**
	 * General properties file name.
	 */
	private static final String GENERAL_CONFIG_FILE = "/project-general.properties";
	
	/* Keys in  general properties file */
	private static final String CACHE_STATISTICS_ENABLED = "mg.cache.cache_statistics_enabled";
	
	private static final String SEARCH_NUMBER_OF_RESULTS = "mg.search.search_number_of_results";
	
	private static final String WEB_RESOURCE_URL_IMAGES_KEY 			= "mg.config.web.resource.url.images";	
	private static final String WEB_RESOURCE_URL_MEDIA_KEY 				= "mg.config.web.resource.url.media";
	private static final String WEB_RESOURCE_URL_CSS_KEY 				= "mg.config.web.resource.url.css";
	private static final String WEB_RESOURCE_URL_SCRIPTS_KEY 			= "mg.config.web.resource.url.scripts";
	private static final String WEB_RESOURCE_URL_IMAGE_PRODUCT_KEY 		= "mg.config.web.resource.path.product";
	private static final String WEB_RESOURCE_URL_IMAGE_COLLECTION_KEY 	= "mg.config.web.resource.path.collection";
	private static final String WEB_RESOURCE_URL_IMAGE_TMP_KEY 			= "mg.config.web.resource.path.tmp";
	private static final String WEB_RESOURCE_URL_IMAGE_SOCIAL_KEY 		= "mg.config.web.resource.path.social";
	private static final String WEB_RESOURCE_UTL_ALLOW 					= "mg.config.web.resource.url.allows";
	private static final String WEB_RESOURCE_AUDIR_FILTER				= "mg.config.web.resource.audit.filter";
	private static final String WEB_RESOURCES_SSL						= "mg.config.web.resource.ssl";
	
	private static final String ROOT_PATH = "mg.root.path";
	private static final String URL_SERVER = "mg.url.server";
	private static final String  JAVA_CACERT = "mg.java.cacert";
	
	private static final String TAXES_ENABLE = "mg.taxes.enable";
	private static final String PRICE_EXTRA_TEXT = "mg.extra.text.price.";
	
	// Properties mails
	public static String MAIL_FROMWEBMASTER_KEY   	= "mg.Mail.fromWebmaster";
	public static String MAIL_DEFAULTDEBUGGER_KEY 	= "mg.Mail.defaultDebugger";
	public static String MAIL_SUPPORT_KEY			= "mg.Mail.support";
	public static String MAIL_SMTP  			  	= "mg.Mail.SMTP";
	public static String MAIL_ERRORS					= "mg.Mail.errors";
	
	// Autocompleters
	private static final String AUTOCOMPLETER_NAME = "mg.search.autocompleter.main_search.name";
	private static final String AUTOCOMPLETER_NUMBER_OF_RESULTS = "mg.search.autocompleter.main_search.number_of_results";
	private static final String AUTOCOMPLETER_AMTCH_INFIX = "mg.search.autocompleter.main_search.match_infix";
	private static final String AUTOCOMPLETER_ORDER_BY_SCORE = "mg.search.autocompleter.main_search.order_by_score";
	private static final String AUTOCOMPLETER_CACHING_ENABLED = "mg.search.autocompleter.main_search.caching_enabled";
	private static final String AUTOCOMPLETER_QUERIES_TO_CACHE = "mg.search.autocompleter.main_search.queries_to_cache";
	private static final String AUTOCOMPLETER_QUERY_LENGTH_TO_CACHE = "mg.search.autocompleter.main_search.query_length_to_cache";	
	private static final String AUTOCOMPLETER_REFRESH_INTERVAL = "mg.search.autocompleter.main_search.refresh_interval";

	//Commerce
	private static final String CMCIC_KEY = "mg.commerce.cmcic";
	private static final String CMCIC_TPE = "mg.commerce.cmcic.tpe";
	private static final String CMCIC_VERSION = "mg.commerce.cmcic.version";
	private static final String CMCIC_SERVER = "mg.commerce.cmcic.server";
	private static final String CMCIC_CODE_SOCIETE = "mg.commerce.cmcic.codeSociete";
	private static final String CMCIC_URL_OK = "mg.commerce.cmcic.urlOk";
	private static final String CMCIC_URL_KO = "mg.commerce.cmcic.urlKo";
	
	private static final String PAYPAL_USER = "mg.commerce.paypal.user";
	private static final String PAYPAL_PWD = "mg.commerce.paypal.pwd";
	private static final String PAYPAL_SIGNATURE = "mg.commerce.paypal.signature";
	private static final String PAYPAL_VERSION = "mg.commerce.paypal.version";
	private static final String PAYPAL_URL_OK = "mg.commerce.paypal.urlOk";
	private static final String PAYPAL_URL_ERROR = "mg.commerce.paypal.utlError";
	private static final String PAYPAL_ENDPOINT_CERTIFICATE = "mg.commerce.paypal.url.endpoint.certificate";
	private static final String PAYPAL_ENDPOINT_SIGNATURE = "mg.commerce.paypal.url.endpoint.signature";
	private static final String PAYPAL_URL_CHECKOUT = "mg.commerce.paypal.url.checkout";
	private static final String PAYPAL_METHOD = "mg.commerce.paypal.method";
	private static final String PAYPAL_CERT_PSW = "mg.commerce.paypal.certificate.psw";	
	private static final String PAYMENT_ALLOW = "mg.commerce.payment.allow";
	private static final String PAYMENT_DEFAULT = "mg.commerce.payment.default";
	private static final String CURRENCY_CHOICE = "mg.commerce.allow.currency.choice";
	private static final String ALLOW_MULTYCURRENCY = "mg.commerce.allow.multy.currency";
	
	//Monitoring 
	private static final String MONITORING_URLS 	= "mg.monitoring.urls"; 
	private static final String SITEMAP_URL 		= "mg.sitemap.url";
	private static final String FILTER_REQUES_EXT 	= "mg.filter.request.ext";
	private static final String FILTER_FORWARD 		= "mg.filter.reques.forward";
	
	//Debug
	private static final String DEBUG_SEND_EMAIL = "mg.debug.sendEmail";
	private static final String DEBUG_TRACE_ERROR = "mg.debug.traceError";
	/*private static final ConfigService INSTANCE = new ConfigServiceImpl();
	
	*//**
	 * A singleton config service
	 * @return
	 *//*
	public static ConfigService getInstance(){
		return INSTANCE;
	}
	
	public ConfigServiceImpl(){
		if (INSTANCE != null) {
			throw new IllegalStateException(this.getClass()	+ " instance is already created");
		}
	}*/
	
	private boolean configServiceInitialized = false;
	private PropertiesFileWrapper generalProps;
	
	
	protected boolean isConfigServiceInitialized() {
		return configServiceInitialized;
	}

	protected void setConfigServiceInitialized(boolean configServiceInitialized) {
		this.configServiceInitialized = configServiceInitialized;
	}

	protected PropertiesFileWrapper getGeneralProps() {
		return generalProps;
	}

	protected void setGeneralProps(PropertiesFileWrapper generalProps) {
		this.generalProps = generalProps;
	}

	public synchronized void initializeProperties() throws Exception{
		if(!isConfigServiceInitialized()){
			try{
				PropertiesFileWrapper projectGeneralProps  = new PropertiesFileWrapper(GENERAL_CONFIG_FILE);			
				if(projectGeneralProps.isValidProperties()){
					setGeneralProps(projectGeneralProps);
				}
				if(log.isInfoEnabled()){
					log.info("The config service has been successfully initialized.");
				}
				setConfigServiceInitialized(true);
			}catch (Exception e) {
				setConfigServiceInitialized(false);
				throw new InitializationException("Something went wrong upon trying to initialize properties project.", e);
			}
		}		
	}
	public synchronized void initialize() throws InitializationException{
		try{
			initializeProperties();
			/* Initialize caching service*/
			ServiceLocator.getService(CacheServiceImpl.class).initialize();	
		}catch (Exception e) {
			throw new InitializationException("Something went wrong upon trying to initialize project config service.", e);
		}
	}
	
	public synchronized void shutdown(){
		try{
			//Shutdown caching service
			ServiceLocator.getService(CacheServiceImpl.class).shutdown();
			
			//Shutdown search service 
			//ServiceLocator.getService(SearchService.class).shutdown();
		}catch (CacheException ce) {	
			// TODO
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String getWebCssLocation() {
		return getProperty(WEB_RESOURCE_URL_CSS_KEY);
	}

	@Override
	public String getWebImagesLocation() {
		return getProperty(WEB_RESOURCE_URL_IMAGES_KEY);
	}
	
	@Override
	public String getWebImageProdcutLocation() {
		return getProperty(WEB_RESOURCE_URL_IMAGE_PRODUCT_KEY);
	}
	
	@Override
	public String getWebImageCollectionLocation() {
		return getProperty(WEB_RESOURCE_URL_IMAGE_COLLECTION_KEY);
	}
	
	@Override
	public String getWebImageTmp() {
		return getProperty(WEB_RESOURCE_URL_IMAGE_TMP_KEY);
	}
	
	@Override
	public String getWebImageSocial() {
		return getProperty(WEB_RESOURCE_URL_IMAGE_SOCIAL_KEY);
	}

	@Override
	public String getWebMediaLocation() {
		return getProperty(WEB_RESOURCE_URL_MEDIA_KEY);
	}	
	
	@Override
	public String getWebScriptsLocation() {
		return getProperty(WEB_RESOURCE_URL_SCRIPTS_KEY);
	}

	@Override
	public String getMailFromWebmaster() {
		return getProperty(MAIL_FROMWEBMASTER_KEY);
	}
	
	@Override
	public String[] getMailDefaultDebugger() {
		return getPropertyAsStringArray(MAIL_DEFAULTDEBUGGER_KEY);
	}	

	@Override
	public String getMailSMTP() {
		return getProperty(MAIL_SMTP);
	}	
	
	@Override
	public String[] getMailErrors() {
		return getPropertyAsStringArray(MAIL_ERRORS);
	}	
	
	@Override
	public String[] getMonitoringUrls() {
		return getPropertyAsStringArray(MONITORING_URLS);
	}
	
	private String getProperty(String key) {
		if(isConfigServiceInitialized()){
			return getGeneralProps().getPropertyAsString(key);
		} else {
			log.error("Trying to get the config property key : " + key + " while config service is not initialized");
			return null;
		}
	}
	
	/**
	 * Returns property as String Array
	 * 
	 * @param name
	 * @return a String Array of properties
	 */
	public String[] getPropertyAsStringArray(String name) {
		List<String> list = null;
		String stringToSplit = getProperty(name);
		if (stringToSplit != null) {
			list = Arrays.asList(stringToSplit.split(","));
			for (int i = 0; i < list.size(); i++) {
				list.set(i, list.get(i).trim());
			}
		}
		if (list != null) {
			return list.toArray(new String[list.size()]);
		} else {
			return new String[0];
		}

	}
	
	/**
	 * Returns true if the collecting cache statistics is enabled.
	 */
	@Override
	public boolean isCacheStatisticsEnabled(){		
		return getGeneralProps().getPropertyAsBoolean(CACHE_STATISTICS_ENABLED);
	}
	
	@Override
	public int getSearchNumberOfResults(){
		return getGeneralProps().getPropertyAsInteger(SEARCH_NUMBER_OF_RESULTS);
	}
	
	@Override
	public AutocompleterConfigurator getSearchConfigurator(){
		return AutocompleterConfigurator.valueOf(
				getGeneralProps().getPropertyAsString(AUTOCOMPLETER_NAME),
				getGeneralProps().getPropertyAsInteger(AUTOCOMPLETER_NUMBER_OF_RESULTS),
				getGeneralProps().getPropertyAsBoolean(AUTOCOMPLETER_AMTCH_INFIX), 
				getGeneralProps().getPropertyAsBoolean(AUTOCOMPLETER_ORDER_BY_SCORE), 
				getGeneralProps().getPropertyAsBoolean(AUTOCOMPLETER_CACHING_ENABLED), 
				getGeneralProps().getPropertyAsInteger(AUTOCOMPLETER_QUERIES_TO_CACHE), 
				getGeneralProps().getPropertyAsInteger(AUTOCOMPLETER_QUERY_LENGTH_TO_CACHE), 
				getGeneralProps().getPropertyAsInteger(AUTOCOMPLETER_REFRESH_INTERVAL));
	}

	@Override
	public String[] getMailProjectSupport() {
		return getPropertyAsStringArray(MAIL_SUPPORT_KEY);
	}
	
	@Override
	public String getRootPathWeb() {
		return getProperty(ROOT_PATH);
	}
	
	@Override
	public String getUrlServer() {
		return getProperty(URL_SERVER);
	}
	
	@Override
	public boolean isTaxesEnable(){		
		return getGeneralProps().getPropertyAsBoolean(TAXES_ENABLE);
	}
	
	@Override
	public boolean isDebugSendEmail(){		
		return getGeneralProps().getPropertyAsBoolean(DEBUG_SEND_EMAIL);
	}
	
	@Override
	public boolean isDebugTraceError(){		
		return getGeneralProps().getPropertyAsBoolean(DEBUG_TRACE_ERROR);
	}
	
	@Override
	public String getCmcicKey() {
		return getProperty(CMCIC_KEY);
	}
	
	@Override
	public String getCmcicTpe() {
		return getProperty(CMCIC_TPE);
	}
	
	@Override
	public String getCmcicServer() {
		return getProperty(CMCIC_SERVER);
	}
	
	@Override
	public String getCmcicSocieteCode() {
		return getProperty(CMCIC_CODE_SOCIETE);
	}
	
	@Override
	public String getCmcicVersion() {
		return getProperty(CMCIC_VERSION);
	}
	
	@Override
	public String getCmcicUrlKo() {
		return getProperty(CMCIC_URL_KO);
	}
	
	@Override
	public String getCmcicUtlOk() {
		return getProperty(CMCIC_URL_OK);
	}
	
	/*public String getPriceExtraText(Currency currency){
		String currencyText = currency.getCode().toLowerCase();
		return getGeneralProps().getPropertyAsString(PRICE_EXTRA_TEXT + currencyText);		
	}*/
	
	public String getPriceExtraText(String currencyCode){
		return getGeneralProps().getPropertyAsString(PRICE_EXTRA_TEXT + currencyCode);		
	}

	@Override
	public int saveAudit(final Audit audit) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					DaoFactory.getDAO(AuditDAO.class, em).save(audit);
					return(audit.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public List<Translation> getAllTranslation() throws ServiceException {
		List<Translation> list = null;
		try {
			daoManager.setCommitTransaction(true);
			list = (List<Translation>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(TranslationDAO.class, em)
									.getAll();
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}
	
	public Translation getTranslation(final Integer id) throws ServiceException {
		Translation translation = null;
		try {
			daoManager.setCommitTransaction(true);
			translation = (Translation) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(TranslationDAO.class, em).findEntityById(id);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return translation;
	}

	@Override
	public String[] getUrlAllow() {
		return getPropertyAsStringArray(WEB_RESOURCE_UTL_ALLOW);
	}
	
	@Override
	public String[] getAuditFilter() {
		return getPropertyAsStringArray(WEB_RESOURCE_AUDIR_FILTER);
	}

	@Override
	public String getSitemapUrl() {
		return getProperty(SITEMAP_URL);
	}
	
	@Override
	public String getFilterRequestExt() {
		return getProperty(FILTER_REQUES_EXT);
	}
	
	@Override
	public String getFilterRequestForward() {
		return getProperty(FILTER_FORWARD);
	}
	
	@Override
	public String getPaypalUser() {
		return getProperty(PAYPAL_USER);
	}
	
	@Override
	public String getPaypalPwd() {
		return getProperty(PAYPAL_PWD);
	}
	
	@Override
	public String getPaypalSignature() {
		return getProperty(PAYPAL_SIGNATURE);
	}
	
	@Override
	public String getPaypalVersion() {
		return getProperty(PAYPAL_VERSION);
	}
	
	@Override
	public String getPaypalUrlOk() {
		return getProperty(PAYPAL_URL_OK);
	}
	
	@Override
	public String getPaypalUrlError() {
		return getProperty(PAYPAL_URL_ERROR);
	}
	
	@Override
	public String getPaypalEndpointCertificate() {
		return getProperty(PAYPAL_ENDPOINT_CERTIFICATE);
	}
	
	@Override
	public String getPaypalEndpointSignature() {
		return getProperty(PAYPAL_ENDPOINT_SIGNATURE);
	}
	
	@Override
	public String getPaypalUrlCheckout() {
		return getProperty(PAYPAL_URL_CHECKOUT);
	}
	
	@Override
	public String getPaymentAllow() {
		return getProperty(PAYMENT_ALLOW);
	}
	
	@Override
	public String getPaymentDefault() {
		return getProperty(PAYMENT_DEFAULT);
	}
	
	@Override
	public String getPaymentPaypalMethod() {
		return getProperty(PAYPAL_METHOD);
	}
	
	@Override
	public String getPaypalCertPsw() {
		return getProperty(PAYPAL_CERT_PSW);
	}
	
	@Override
	public String getWebResourceSSL() {
		return getProperty(WEB_RESOURCES_SSL);
	}
	
	@Override
	public String getPathJavaCacert() {
		return getProperty(JAVA_CACERT);
	}
	
	@Override
	public boolean isCurrencyChoice(){		
		return getGeneralProps().getPropertyAsBoolean(CURRENCY_CHOICE);
	}

	@Override
	public boolean isAllowMultyCurrency() {
		return getGeneralProps().getPropertyAsBoolean(ALLOW_MULTYCURRENCY);
	}
	

}
