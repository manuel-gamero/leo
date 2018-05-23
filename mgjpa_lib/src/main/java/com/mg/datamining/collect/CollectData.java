package com.mg.datamining.collect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.mg.datamining.actions.UserActionFactory;
import com.mg.datamining.helpers.DeviceSessionHelper;
import com.mg.datamining.interfaces.ICreation;
import com.mg.enums.UserType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;
import com.mg.model.DeviceUser;
import com.mg.service.ServiceLocator;
import com.mg.service.device.DeviceService;
import com.mg.service.device.DeviceServiceImpl;
import com.mg.util.text.StringUtils;

public class CollectData {
	
	private static final Logger log = Logger.getLogger(CollectData.class);
	
	private HashMap<String, Device> sessions =  new HashMap<String, Device>();
	private HashMap<String, Device> devices =  new HashMap<String, Device>();
	public static final String UNKNOWN_FINGERPRINT = "UNKNOWN";

	public void collectSessions(List<Audit> auditList) throws NumberFormatException, ServiceException, ServiceLocatorException{
		log.info(">>> Start proces of collecting data.");
		for (Audit audit : auditList) {
			if( isAuditValid(audit) ){
				ICreation action = UserActionFactory.getInstace().getUserAction(audit);
				if( action != null){
					log.debug("+++ +++ +++ Treating session : " + audit.getSessionGuid() + " action: " + action.getClass().getSimpleName());
					try{
						Device device = getSessionDevice(audit);
						action.apply(device, audit);
					}
					catch(Exception e){
						System.out.println(audit.getMessage());
						e.printStackTrace();
					}
				}
				else{
					log.debug("NULL: " + audit.getActionUser());
				}
			}
		}
		log.info("<<< End proces of collecting data.");
	}
	
	public void saveAudtiHistory(List<Audit> auditList) throws NumberFormatException, ServiceException, ServiceLocatorException{
		log.info(">>> Start proces of archiving auidt.");
		DeviceService deviceService = ServiceLocator.getService(DeviceServiceImpl.class);
		for (Audit audit : auditList) {
			deviceService.saveAuditHist(audit);
		}
		log.info("<<< End proces of archiving auidt.");
	}

	public void groupingSessions() throws ServiceException, ServiceLocatorException{
		log.info(">>> Start proces of grouping sessions.");
		boolean add;
		if(sessions.size() > 0){
			for (String keySession : sessions.keySet()) {
				add = true;
				Device device = sessions.get(keySession);
				String keyDevice = device.getFingerprint();
				if(keyDevice == null ){
					keyDevice = UNKNOWN_FINGERPRINT;
					device.setFingerprint(keyDevice);
				}
				DeviceSessionHelper.create(device);
				//Remove admin session from UNKNOWN fingerprint
				if(DeviceSessionHelper.isAdminUser(device) && device.getFingerprint().equals(UNKNOWN_FINGERPRINT)){
					removeAdminSession(device);
				}
				//If the session is from admin user then don't treat it
				else if(DeviceSessionHelper.isAdminUser(device) && !device.getFingerprint().equals(UNKNOWN_FINGERPRINT)){
					add = false;
				}
				log.debug("+++ +++ +++ Device: " + keyDevice + " session: " + keySession + " add: " + add );
				if(add){
					if( devices.containsKey(keyDevice) ){
						devices.get(keyDevice).add(device);
					}
					else{
						devices.put(keyDevice, device);
					}
				}
				
			}
		}
		log.info("<<< End proces of grouping sessions.");
	}
	
	private void removeAdminSession(Device device) {
		if(device.getDeviceUsers() != null){
			for (DeviceUser item : device.getDeviceUsers()) {
				if(item.getUsers().getTypeCode().equals(UserType.ADMIN)){
					device.getDeviceUsers().remove(item);
					break;
				}
			}
		}		
	}

	public void saveDevices() throws ServiceLocatorException, ServiceException{
		log.info(">>> Start proces of saving devices in database.");
		if( devices != null && devices.size() > 0 ){
			DeviceService deviceService = ServiceLocator.getService(DeviceServiceImpl.class);
			for (String keyFingerPrint : devices.keySet()) {
				Device device = devices.get(keyFingerPrint);
				log.debug("+++ +++ +++ keyFingerPrint: " + keyFingerPrint);
				Long start = System.currentTimeMillis(); 
				Device deviceDatabase = deviceService.getDevice(keyFingerPrint);
				if ( deviceDatabase != null ){
					log.debug("+++ +++ +++ Adding device." );
					//device.add(deviceDatabase);
					//deviceService.saveDevice(device);
					deviceDatabase.addDevice(device);
					deviceService.saveDevice(deviceDatabase);
				}
				else{
					log.debug("+++ +++ +++ Saving device." );
					deviceService.saveDevice(device);
				}
				Long finish = System.currentTimeMillis(); 
				Long millis = finish - start;
				log.debug("Saving duraction: "  + 
							String.format("%d sec, %d millisecond",	TimeUnit.MILLISECONDS.toSeconds(millis), TimeUnit.MILLISECONDS.toMillis(millis)));
			}
		}
		else{
			log.warn("There is no any device.");
		}
		log.info("<<< End proces of saving devices in database.");
	}
	
	

	private boolean isAuditValid(Audit audit) {
		if( audit.getActionUser().toLowerCase().contains("bot") ||
			audit.getActionUser().toLowerCase().contains("spider") ){
			return false;
		}
		return true;
	}

	public HashMap<String, Device> getSessions() {
		return sessions;
	}

	public void setSessions(HashMap<String, Device> sessions) {
		this.sessions = sessions;
	}

	private Device getSessionDevice(Audit audit) {
		Device device = sessions.get(audit.getSessionGuid());
		if( device == null ){
			device = new Device();
			log.debug("+++ +++ +++ Creation session : " + audit.getSessionGuid());
			sessions.put(audit.getSessionGuid(), device);
		}
		return device;
	}
	
	public HashMap<String, Device> getDevices() {
		return devices;
	}

	public void setDevices(HashMap<String, Device> devices) {
		this.devices = devices;
	}

	public static void main(String[] args) {
	
		//String message = "customComponentCollection = imag_70_4_664_4,imag_70_3_667_3,imag_70_5_668_5 , text = TEST TEST, color = #add8e6, font = Arial Rounded MT ";
		//String message = "product = 87 , shoppingCartList.size = , customComponentCollection = [imag_87_3_1080_3, null, imag_87_5_1084_5, null, null, imag_87_4_1082_4] ";
		//String message = "username = manuel.gamero@hotmail.com ";
		String message = "audit = fpw[67501f9cc23a769cf68e564d5c88687e-key: touch_support value: 0,false,false key: has_lied_browser value: false key: has_lied_os value: false key: has_lied_resolution value: false key: has_lied_languages value: false key: adblock value: true key: do_not_track value: unspecified key: navigator_platform value: Win32 key: cpu_class value: unknown key: indexed_db value: 1 key: local_storage value: 1 key: session_storage value: 1 key: timezone_offset value: 240 key: available_resolution value: 1280,770 key: resolution value: 1280,800 key: pixel_ratio value: 1 key: color_depth value: 24 key: language value: es-ES key: user_agent value: Mozilla/5.0 (Windows NT 5.1; rv:47.0) Gecko/20100101 Firefox/47.0 ] ";
		
		
		//List<String> listImags = StringUtils.getListRegexMatches(message, "imag*,");
		List<String> allMatches = new ArrayList<String>();
		//Matcher m = Pattern.compile("imag[0-9|_]*").matcher(message);
		//Matcher m = Pattern.compile("font = [a-z|A-Z|0-9]*").matcher(message);
		Matcher m = Pattern.compile("fpw\\[[a-zA-Z0-9]*").matcher(message);
		while (m.find()) {
			allMatches.add(m.group());
		}
		System.out.println(allMatches);
		String msg = message.replace("[", "");
		System.out.println("MESSAGE : " + msg);
		System.out.println("RESULT : " + StringUtils.getListRegexMatches(msg,  "fpw[a-zA-Z0-9]*"));
		
		
		/*try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d = sdf.parse("21/05/2016");
			List<Audit> auditList= ServiceLocator.getService(ApplicationServiceImpl.class).getAuditByDates(d, new Date());
			CollectData collection = new CollectData();
			collection.collect(auditList);
			collection.getDevices();
			//System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
			
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
			System.setProperty(Context.URL_PKG_PREFIXES,"org.apache.naming");
			InitialContext ic = new InitialContext();

			InitialContext cxt = new InitialContext();
			if ( cxt == null ) {
			   throw new Exception("Uh oh -- no context!");
			}

			DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgresDS" );

			if ( ds == null ) {
			   throw new Exception("Data source not found!");
			}
			
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
		    System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");            
		    InitialContext ic = new InitialContext();

		    ic.createSubcontext("java:");
		    ic.createSubcontext("java:/comp");
		    ic.createSubcontext("java:/comp/env");
		    ic.createSubcontext("java:/comp/env/jdbc");
		    
		    ic.createSubcontext("java:comp");
		    ic.createSubcontext("java:comp/env");
		    ic.createSubcontext("java:comp/env/jdbc");
		    
		     * PGPoolingDataSource ds = new PGPoolingDataSource();
    ds.setServerName("localhost:5432/leadmanager");
    ds.setUser("postgres");
    ds.setPassword("xxxxxxxx");
    ic.bind("java:/comp/env/jdbc/leadmanager", ds);
    
    JdbcConnectionPool ds = JdbcConnectionPool.create(
                    "jdbc:h2:file:src/main/resources/test.db;FILE_LOCK=NO;MVCC=TRUE;DB_CLOSE_ON_EXIT=TRUE", "sa", "sasasa");
            // Construct DataSource
            // OracleConnectionPoolDataSource ds = new
            // OracleConnectionPoolDataSource();
            // ds.setURL("jdbc:oracle:thin:@host:port:db");
            // ds.setUser("MY_USER_NAME");
            // ds.setPassword("MY_USER_PASSWORD");

            ic.bind("java:/mydatasourcename", ds);
            
		     
		    PGPoolingDataSource ds = new PGPoolingDataSource();
		    //ds.setServerName("192.168.1.65:5432/bolsosdb?searchpath=bolsos");
		    ds.setUrl("jdbc:postgresql://192.168.1.65:5432/bolsosdb?searchpath=bolsos");
		    ds.setUser("raphaele");
		    ds.setPassword("raphaele");
		    ic.bind("java:/comp/env/jdbc/postgresDS", ds);
		    ic.bind("java:comp/env/jdbc/postgresDS", ds);
		    
		    //DataSource ds1 = (DataSource) ic.lookup( "java:/comp/env/jdbc/postgresDS" );
		    
		    List<Audit> auditList= ServiceLocator.getService(ApplicationServiceImpl.class).getAuditByDates(d, new Date());
			CollectData collection = new CollectData();
			collection.collect(auditList);
			collection.getDevices();
			
			
			File configDir = new File(System.getProperty("catalina.base"), "conf");
			File configFile = new File(configDir, "myconfig.properties");
			InputStream stream = new FileInputStream(configFile);
			Properties props = new Properties();
			props.load(stream);
			
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:jdbc/postgresDS");
			DataSource ds = (DataSource) envContext.lookup("jdbc/postgresDS");
			Connection conn = ds.getConnection();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}*/
	}


}
