package com.mg.web.struts.action.shopping.payment.paypal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.CurrencyNoExistException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Paypal;
import com.mg.model.ShoppingCart;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.shopping.ShoppingServiceImpl;
import com.mg.web.exception.PaypalException;
import com.mg.web.struts.action.shopping.payment.paypal.beans.PaypalAction;
import com.mg.web.util.ConnectionWebUtils;

public abstract class AbstractPaypalAction implements PaypalAction {
	
	private static Logger log = LogManager.getLogger(AbstractPaypalAction.class);
	private static final String FILE_PAYPAL_P12 = "paypal_cert.p12";
	private static final String CACERTS_PSW = "changeit";
	private static final String SUCCESS_RESPONDE = "Success";
	private ShoppingCartPaypal shoppingCartAction;
	private PaypalAction paypalAction;

	public String actionPaypal() throws PaypalException{
		try{
			Map<String, String> params = new HashMap<String, String>();
			params = requestPaypal(getUrl());
			if (isValideResponde(params)) {
				String retul = execute(params);
				if( paypalAction != null ){
					retul = paypalAction.actionPaypal();
				}
				return retul;
			}
			else{
				createErrorPaypal(getShoppingCart(), params);
				log.error("Error : " + params.toString());
				ServiceLocator.getService(ShoppingServiceImpl.class).updateShoppingCart(getShoppingCart());
				throw new PaypalException(params.toString());
			}
		}catch (Exception e){
			throw new PaypalException(e);
		}
	}

	public abstract String execute(Map<String, String> params) throws UnsupportedEncodingException, ServiceLocatorException, ServiceException, ParseException;

	public abstract String getUrl() throws ServiceLocatorException, UnsupportedEncodingException, CurrencyNoExistException, ServiceException;

	private Map<String, String> requestPaypal(String params) throws PaypalException {
		Map<String, String> paramMap = null;
		try {
			// Connect to the url
			ConfigService configService = ServiceLocator.getService(ConfigServiceImpl.class);
			String urlCall = configService.getPaypalEndpointCertificate() + params;
			String url = getPaypalUrl();
			log.debug("Calling URL: " + urlCall);
			// -Djavax.net.debug=all flag to enable debugging of the SSL connection established.
			//-Dhttps.protocols="TLSv1,TLSv1.1,TLSv1.2" 
			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			
			//URLConnection conn = url.openConnection();
			HttpURLConnection conn = getConnection(url);
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			// Post the data
			wr.write(params);
			wr.flush();
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			paramMap = deformatNVP(rd.readLine());
			wr.close();
			rd.close();
		} catch (Exception e) {
			throw new PaypalException(e);
		}
		return paramMap;
	}

	private String getPaypalUrl() throws ServiceLocatorException {
		if(isPaypalCertificate()){
			return ServiceLocator.getService(ConfigServiceImpl.class).getPaypalEndpointCertificate();
		}
		else{
			return ServiceLocator.getService(ConfigServiceImpl.class).getPaypalEndpointSignature();
		}
	}

	private HttpURLConnection getConnection(String urlPaypal) throws ServiceLocatorException, KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
		HttpURLConnection connection;
		if( isPaypalCertificate() ){
			String psw = ServiceLocator.getService(ConfigServiceImpl.class).getPaypalCertPsw();
			KeyStore clientStore = KeyStore.getInstance("PKCS12");
			String pathSSL = ServiceLocator.getService(ConfigServiceImpl.class).getRootPathWeb();
			pathSSL = pathSSL + ServiceLocator.getService(ConfigServiceImpl.class).getWebResourceSSL();
			pathSSL = pathSSL + FILE_PAYPAL_P12;
			FileInputStream fileP12 = null;
			try{
				//fileP12 = new FileInputStream("D:/dev/paypal_cert.p12");
				fileP12 = new FileInputStream(pathSSL);
				clientStore.load(fileP12, psw.toCharArray());
				fileP12.close();
			}
			catch(Exception e){
				if(fileP12 != null){
					fileP12.close();
				}
			}
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(clientStore, psw.toCharArray());
			KeyManager[] kms = kmf.getKeyManagers();

			KeyStore trustStore = KeyStore.getInstance("JKS");
			FileInputStream fileKeyStore = null;
			String cacerts = ServiceLocator.getService(ConfigServiceImpl.class).getPathJavaCacert();
			cacerts = cacerts + "cacerts";
			try{
				fileKeyStore = new FileInputStream(cacerts);
				trustStore.load(fileKeyStore, CACERTS_PSW.toCharArray());
				fileKeyStore.close();
			}
			catch(Exception e){
				if(fileKeyStore != null){
					fileKeyStore.close();
				}
			}

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			TrustManager[] tms = tmf.getTrustManagers();

			SSLContext sslContext = null;
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kms, tms, new SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
			URL url = new URL(urlPaypal);

			connection = (HttpsURLConnection) url.openConnection();
		}
		else{
			//URL url = new URL(configService.getPaypalEndpointSignature());
			URL url = new URL(urlPaypal);
			connection = (HttpURLConnection) url.openConnection();
		}
		return connection;
	}

	private boolean isPaypalCertificate() throws ServiceLocatorException {
		String paypalMethod = ServiceLocator.getService(ConfigServiceImpl.class).getPaymentPaypalMethod();
		if("certificate".equals(paypalMethod)){
			return true;
		}
		else{
			return false;
		}
	}

	private Map<String, String> deformatNVP(String request) throws PaypalException {
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			String delims = "[&]";
			String equals = "[=]";
			String[] tokens = request.split(delims);

			for (int i = 0; i < tokens.length; i++) {
				String[] newTokens = tokens[i].split(equals);
				paramMap.put(getValueParam(newTokens[0]), 
							 getValueParam(newTokens[1]));
			}

		} catch (Exception e) {
			throw new PaypalException(e);
		}
		return paramMap;
	}
	
	protected String getValueParam(String value) throws UnsupportedEncodingException{
		if(value != null){
			return URLDecoder.decode(value, "UTF-8").toString();
		}
		else{
			return null;
		}
	}
	
	private boolean isValideResponde(Map<String, String> paramReponde){
		if (paramReponde != null && SUCCESS_RESPONDE.equals(paramReponde.get("ACK"))) {
			return true;
		}
		else{
			return false;
		}
	}
	
	private void createErrorPaypal(ShoppingCart shoppingCart,
			Map<String, String> result) {
		if(shoppingCart.getPaypal() == null ){
			Paypal paypal = new Paypal();
			paypal.setToken(shoppingCartAction.getToken());
			paypal.setShoppingCart(shoppingCart);
			shoppingCart.setPaypal(paypal);
		}
		
		shoppingCart.getPaypal().setAck((String) result.get("ACK"));
		shoppingCart.getPaypal().setErrorcode((String) result.get("L_ERRORCODE0"));
		shoppingCart.getPaypal().setLongmessage((String) result.get("L_LONGMESSAGE0"));
		shoppingCart.getPaypal().setSeveritycode((String) result.get("L_SEVERITYCODE0"));
		shoppingCart.getPaypal().setShormessage((String) result.get("L_SHORTMESSAGE0"));
		shoppingCart.getPaypal().setUpdateDate(new Date());
		shoppingCart.getPaypal().setCreationDate(new Date());
		shoppingCart.getPaypal().setShoppingCart(shoppingCart);
	}
	

	public ShoppingCartPaypal getShoppingCartAction() {
		return shoppingCartAction;
	}

	public void setShoppingCartAction(ShoppingCartPaypal shoppingCartAction) {
		this.shoppingCartAction = shoppingCartAction;
	}
	
	protected String getBasicParamUrl(String method) throws ServiceLocatorException, UnsupportedEncodingException {
		ConfigService configService = ServiceLocator.getService(ConfigServiceImpl.class);
		String url = "USER=" + ConnectionWebUtils.getParameter(configService.getPaypalUser());
		url += "&PWD=" + ConnectionWebUtils.getParameter(configService.getPaypalPwd());
		if(!isPaypalCertificate()){
			url += "&SIGNATURE=" + ConnectionWebUtils.getParameter(configService.getPaypalSignature());
		}
		url += "&VERSION=" + ConnectionWebUtils.getParameter(configService.getPaypalVersion());
		url += "&METHOD=" + ConnectionWebUtils.getParameter(method);
		return url;
	}
	
	public ShoppingCart getShoppingCart() throws ServiceException, ServiceLocatorException{
		return getShoppingCartAction().getShoppingCart();
	}

	public PaypalAction getPaypalAction() {
		return paypalAction;
	}

	public void setPaypalAction(PaypalAction paypalAction) {
		this.paypalAction = paypalAction;
	}
}
