package com.mg.web.jobs;

import java.net.HttpURLConnection;
import org.apache.log4j.Logger;

import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.communication.Mail;
import com.mg.web.util.ConnectionWebUtils;

public class MonitoringSite {

	private static Logger log = Logger.getLogger(MonitoringSite.class);
	private static final String subject = "MonitoringSite Error status - " + ConnectionWebUtils.getHostName();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		log.info( ">>> Start monitoring" );
		try {
			
			ConfigService configService = ServiceLocator.getService(ConfigServiceImpl.class);
			
			log.debug(" +++ +++ initializeProperties " );
			configService.initializeProperties();
			
			log.debug(" +++ +++ getMonitoringUrls " );
			String[] hostList = configService.getMonitoringUrls();
			String message = " ";
			boolean sendErrorMail = false;
			log.debug(" hostList size: " + hostList.length );
			for (int i = 0; i < hostList.length; i++) {
				String url = hostList[i];
				log.debug(" url: " + url );
				String status = MonitoringSite.getStatus(url);
				message = message + url + "\t\t Status: " + status + " \n\r ";
				if( message.contains("ERROR")){
					sendErrorMail = true;
				}
				
			}
			log.debug(" sendErrorMail: " + sendErrorMail );
			boolean sendMail = ServiceLocator.getService(ConfigServiceImpl.class).isDebugSendEmail();
			if(sendErrorMail && sendMail){
				log.debug( "message: " + message);
				Mail.send(ServiceLocator.getService(ConfigServiceImpl.class).getMailErrors(), subject , message);
			}
		} 
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info( "<<< End monitoring" );
	}

	private static String getStatus(String url) {

		String result = "";
		HttpURLConnection connection = null;
		try {
			long start = System.currentTimeMillis();
			connection = ConnectionWebUtils.getHttpConnetion(url);
			int timeConnection = (int) (System.currentTimeMillis() - start);
			
			int code = connection.getResponseCode();
			if (code == 503) {
				result = code + " ERROR: Service Unavailable";
			}
			else if (code == 400) {
				result = code + " ERROR: Bad Request";
			}
			else if (code == 401) {
				result = code + " ERROR: Unauthorized";
			}
			else if (code == 403) {
				result = code + " ERROR: Forbidden";
			}
			else if (code == 502) {
				result = code + " ERROR: Bad Gateway";
			}
			else if (timeConnection >= ConnectionWebUtils.CONNECTIONTIMEMAX) {
				result = code + " ERROR: connection time: " + timeConnection + " miliseg";
			}
			else if (code == 200) {
				result = code + " connection OK!!!";
			}
			else{
				result = code + " ERROR: unexpected error";
			}
		} catch (Exception e) {
			result = " ERROR: exception -> " + e.getClass().toString() + " " + e.getMessage();
		}
		finally {
			if(connection != null){
				connection.disconnect();
			}
			else{
				result = " ERROR: NO connection!";
			}
		}
		return result;
	}
}
