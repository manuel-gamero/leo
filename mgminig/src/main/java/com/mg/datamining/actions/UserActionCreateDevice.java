package com.mg.datamining.actions;

import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.util.text.StringUtils;

public class UserActionCreateDevice extends UserActionBasic{

	private static final Logger log = LogManager.getLogger(UserActionCreateDevice.class);
	
	@Override
	public void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException {
		String message = audit.getMessage();
		String availableResolution = getValueForParam(message, "available_resolution value: [0-9|,]*");
		device.setAvailableResolution(availableResolution);
		
		String colorDepth = getValueForParam(message, "color_depth value: [0-9]*");
		device.setColorDepth(colorDepth);
		
		String cpuClass = getValueForParam(message, "cpu_class value: [a-zA-Z0-9]*");
		device.setCpuClass(cpuClass);
		
		device.setCreationDate(new Date());
		String msg = message.replace("[", "");
		String fingerprint = StringUtils.getListRegexMatches(msg,  "fpw[a-zA-Z0-9]*").get(0).replace("fpw", "").trim();
		device.setFingerprint(fingerprint);
		
		String language = getValueForParam(message, "language value: [a-zA-Z|-]*");
		device.setLanguage(language);
		device.setMovil(isMovil(audit));
		
		String navigatorPlatform = getValueForParam(message, "navigator_platform value: [a-zA-Z0-9]*");
		device.setNavigatorPlatform(navigatorPlatform);
		
		String pixelRatio = getValueForParam(message, "pixel_ratio value: [0-9]*");
		if( pixelRatio.length()>0 ){
			device.setPixelRatio( Integer.valueOf(pixelRatio) );
		}
		else{
			device.setPixelRatio( null );
		}
		
		String resolution = getValueForParam(message, "resolution value: [0-9|,]*");
		device.setResolution(resolution);
		
		String timezoneOffset = getValueForParam(message, "timezone_offset value: [0-9]*");
		device.setTimezoneOffset(timezoneOffset);
		
		String userAgent = getValueForParam(message, "user_agent value: [a-zA-Z0-9|,|.|;|/|:|+|-|'|(|)| ]*");
		device.setUserAgent(audit.getBrowser());
		 
	}
	
	private Boolean isMovil(Audit audit) {
		if(audit.getMessage().contains("movil")){
			return true;
		}
		else if(audit.getMessage().contains("samsung")){
			return true;
		}
		else if(audit.getMessage().contains("iphone")){
			return true;
		}
		else if(audit.getBrowser().contains("iPhone")){
			return true;
		}
		else if(audit.getBrowser().contains("iPhone")){
			return true;
		}
		else if(audit.getBrowser().contains("Android")){
			return true;
		}
		return false;
	}

	private String getValueForParam( String message, String param ){
		if(StringUtils.getListRegexMatches(message, param).size() > 0){
			return StringUtils.getListRegexMatches(message, param).get(0).split(":")[1].trim();
		}
		else{
			log.info("Value doesn't found for param: " + param + " message: " + message);
			return null;
		}
	}
}
