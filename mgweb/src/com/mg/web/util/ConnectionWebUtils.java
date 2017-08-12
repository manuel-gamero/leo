package com.mg.web.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class ConnectionWebUtils {

	private static Logger log = Logger.getLogger(ConnectionWebUtils.class);
	private static final String USERAGENT = "atelierBot";
	public static final int CONNECTIONTIMEMAX = 20000; //20 seg
	
	public static HttpURLConnection getHttpConnetion(String url) throws IOException{
		URL siteURL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(CONNECTIONTIMEMAX);
		connection.setReadTimeout(CONNECTIONTIMEMAX);
		connection.setRequestProperty("User-Agent", USERAGENT );
		connection.connect();
		return connection;
	}
	
	public static String getHostName(){
		String hostname = "Unknown";
		try
		{
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
			log.error(ex.getMessage());
		}
		return hostname;
	}
	
	public static String  getParameter(String param) throws UnsupportedEncodingException {
		if(param != null){ 
			return (URLEncoder.encode(param , "UTF-8"));
		}
		else{
			return "";
		}
	}
}
