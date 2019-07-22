package com.mg.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.ServiceException;
import com.mg.util.constant.BackEndConstants;
import com.mg.util.text.StringUtils;


/**
 * Common utilities.
 * 
 *
 */
public final class CommonUtils {
	private static Logger log = LogManager.getLogger(CommonUtils.class);
	protected static final DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
	
	private CommonUtils(){}
	
	/**
	 * Returns enum from Integer Value.
	 * 
	 * @param elements
	 * @param value
	 * @return
	 */
	/*public static BasicEnum enumFromValue(BasicEnum[] elements, Integer value){
		BasicEnum returnValue = null;
		
		for(BasicEnum myEnum:elements){
			if(myEnum.getValue().equals(value)){
				returnValue = myEnum;
				break;
			}
		}
		
		return returnValue;
	}*/
	
	public static Float getFloat(String price){
		Float result = 0f;
		try{
			if(price!= null){
				result = Float.valueOf(price);
			}
			log.warn("Format error for string: " + price);
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	public static String formatPrice(BigDecimal price){
		String result = "0.00";
		try{
			if(price!= null){
				result = df.format(price);
			}
			log.warn("Format error for float: " + price);
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	public static String normalizeImageName(String imageName){
		return  StringUtils.normalizeImageName(imageName.replaceAll(" ", "_").replace("-", "_"), '_').toLowerCase();
	}
	
	public static String getNormalizedProductImage(String imgName){
		
		String[] splitName = imgName.split("\\.");
		String name = StringUtils.cleanQueryFromSpecialChars(splitName[0]);
		
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("_");
		builder.append(new Date().getTime());
		return normalizeImageName(builder.toString() ) + "." + splitName[1].toLowerCase();
	}

	private static String getNormalizedLogo(String gameName,long userID, long gameID,String fileExtension){
		StringBuilder builder = new StringBuilder();
		builder.append(gameID);
		builder.append("_");
		builder.append(userID); 
		builder.append("_");
		//builder.append(new Date().getTime());
		builder.append(gameName.toLowerCase());
		builder.append("_");
		return normalizeImageName(builder.toString() ) + "." +fileExtension.toLowerCase();
	}

	public static String getNormalizedLogo(String realName,long userID, long gameID){
		String[] split = CommonUtils.splitNameFile(realName);
		return getNormalizedLogo(split[0],userID, gameID, split[1]);
	}
	
	/**
	 * Simply checks if a String is null or
	 * empty.
	 * @param s
	 * @return
	 */
	public static boolean isValidString(String s){
		return (s != null && s.trim().length() > 0);
	}
	
	/**
	 * Print the message if the logger is enabled in the application server
	 * @param log
	 * @param message
	 */
	public static void logMessage (Logger log, Object message){
		if(log.isDebugEnabled()){
			log.debug(message);
		}
	}
	
	/**
	 * String[0] = name
	 * String[1] = ext
	 * @param name
	 * @return
	 */
	public static String[] splitNameFile(String name) {
		String[] file = new String[2];
		StringTokenizer tokenizer = new StringTokenizer(name, ".");
		file[0] = tokenizer.nextToken();
		file[1] = tokenizer.nextToken();
		return file;
	}
	
	
	/**
	 * @param <T1>
	 * @param <T2>
	 * @param <T>
	 * @param <T>
	 * method for all Autocomplete
	 * 
	 */

	@SuppressWarnings("unchecked")
	public static <K, V, L, M> Map<K, V> autocompleterFormat(K query, List<L> suggestion, List<M> data) {
		
		Map<K, V> jsonResponse = new HashMap<K, V>(); 
		
		jsonResponse.put((K) "query", (V) query);
		jsonResponse.put((K) "suggestions", (V) suggestion);
		jsonResponse.put((K) "data", (V) data);
		
		return jsonResponse;
	}
	
	public static  Map<String,String> autocompleterFormatString(String query, String suggestion, String data) {
		
		Map<String, String> jsonResponse = new HashMap<String, String>(); 
		
		jsonResponse.put("query", query);
		jsonResponse.put("suggestions", suggestion);
		jsonResponse.put("data", data);
		
		return jsonResponse;
	}
	
	/**
	 * Day list [1..31] 
	 * static method
	 * 
	 */
	public static List<Integer> getDayList() {
		
		List<Integer> days = new ArrayList<Integer>();
				
		for(int i=1; i<=31; i++){
			days.add(i);			
		}
		return days;
	}
	
	/**
	 * year list [ 2010, 2009 ....] 
	 * @param 
	 * @return
	 */
	public static List<Integer> getYearList() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());	
		int now = calendar.get(Calendar.YEAR);
		
		List<Integer> years = new ArrayList<Integer>();
		now += 2; // this years and 2 up
		for(int i=now; i>= BackEndConstants.MIN_YEAR; i--){
			years.add(i);
		}
		
		return years;		
	}
	
	/**
	 * splits the url: the cases www.ign.com or ps3.ign.com will take ign.com as  string 
	 * @param url
	 * @return
	 */
	public static String splitUrl(URL url){
		String[] split = url.getHost().toLowerCase().split("\\.");
		int length=split.length;
		//System.out.print(length+"-");
		if (length>1) {
			return split[length-2]+"."+split[length-1];
		}
		return split[length-1];
	}
	
	
	/**
	 * check the cases www.ign.com and ps3.ign.com to decide if the url exists
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean isSameUrls(URL first, URL second){
		String one=CommonUtils.splitUrl(first);
		String two=CommonUtils.splitUrl(second);
		return one.equals(two);		
	}
	
	/**
	 * True if ALL occurrences of the substrings are in the string otherwise false
	 * 
	 * @param subStrings
	 *            Set <String>
	 * @param string
	 * @return
	 */
	public static boolean indexofSubStrings(Set<String> subStrings, String string) {
		if (subStrings.size() == 0) {
			return false;
		}
		// TODO System.out.println
		for (String entry : subStrings) {
			if (string.toLowerCase().indexOf(entry.toLowerCase()) == -1)
				return false;
		}
		return true;
	}
	public static boolean indexofSubStrings(String [] subStrings, String string) {
		if (subStrings.length == 0) {
			return false;
		}
		//System.out.println("principal----"+string);
		for (String entry : subStrings) {
			//System.out.println("matched----"+entry);
			//System.out.println("toLowerCase----:"+string.toLowerCase().indexOf(entry.toLowerCase()));
			if (string.toLowerCase().indexOf(entry.toLowerCase()) == -1)
				return false;
		}
		return true;
	}
	
	
	/**
	 * Analyze the url
	 * @param link
	 * @return
	 * @throws MalformedURLException
	 */
	public static String urlAnalysis(String link,boolean openConnection) throws MalformedURLException {
		if (!link.toLowerCase().startsWith(BackEndConstants.HTTP)) {
			// link=HTTP+link;
			throw new MalformedURLException("URL MALFORMED (Missing HTTP):"+ link);
		}
		if (!isURL(link,openConnection)) {
			throw new MalformedURLException("URL MALFORMED:" + link);
		}
		return link;
	}	
	
	/**
	 * Validate if the URL is  Malformed 
	 * @throws ServiceException
	 */
	public static boolean isURL(String sourceUrl, boolean openConnection) {
		String urlLink = sourceUrl;
		if (!sourceUrl.toLowerCase().startsWith(BackEndConstants.HTTP)) {
			urlLink = BackEndConstants.HTTP + sourceUrl;
		}
/*		URL url = null;
		try {
			url=new URL(urlLink);
			if (openConnection)
				url.openStream();			
			return true; 
		} 
		catch (MalformedURLException e) { } 
		catch (IOException e) { }*/
		
		return urlLink.matches(BackEndConstants.URLPATTERN);
	}	
	
	
	public static String characterStripper(String string, String pattern){
		return string.replaceAll(pattern, "");
	}

	public static String characterStripper(String string, String pattern,String replace){
		return string.replaceAll(pattern, replace);
	}
	public static String HtmlTagsStripper(String string){
		String resultat = string.replaceAll(BackEndConstants.REMOVE_HTML_TAGS,"");
		return	resultat.replaceAll("\\[[0-9]*\\]", "");
	}
	//best way to trip string from html tag
	public static String HtmlTagsStrippeSpaces(String html) {
		String resultat = html.replaceAll(BackEndConstants.REMOVE_HTML_TAGS,"");
		resultat = resultat.trim().replaceAll("&nbsp;|\\n|\\t", "");
	    return resultat;
	}

	public static Date normalizeUpcommingDate(int year, int month , int day){
		Date date =null;
		if(day != 0){
			date = new GregorianCalendar(year, month-1, day).getTime();
		}else if (month != 0){ 
				int maxDay = getLastDayOfMonth(year,  month);
				date = new GregorianCalendar(year, month, maxDay).getTime();
		}else{			
			date = new GregorianCalendar(year, 12, 31).getTime();
		}
		return date;
	}	
	
	public static int getLastDayOfMonth(int year, int theMonth){
		
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(year, theMonth, 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDay;
	}
	
		/**
	 * True if one of the substring occurs in string
	 * @param subStrings
	 * @param string
	 * @return
	 */
	public static boolean ocurrenceSubString(Set<String> subStrings, String string) {
		if (subStrings.size() == 0) {
			return false;
		}
		// TODO System.out.println
		for (String entry : subStrings) {
			if (string.toLowerCase().indexOf(entry.toLowerCase()) != -1){
				//System.out.println("TRUE TRUE: " + string +"--------" + entry); 
				return true;
			}
		}
		//System.out.println("FALSE FALSE");
		return false;
	}
		
	public static boolean isValidEmail( String email ){
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		boolean valid = m.matches();
		return valid;	
	}
	

	/**
	 * Used in search by name to add like % to find the product
	 * 
	 * 
	 * @param searchString
	 * @param searchField
	 * @return
	 */
	public static String createSearchString(String searchString,
			String searchField) {
		StringBuffer bufferStr = new StringBuffer();
		StringTokenizer st = new StringTokenizer(searchString);

		int count = 0;
		while (st.hasMoreTokens()) {

			if (count > 0) {
				bufferStr.append(" and ");
			}

			bufferStr.append(searchField + " like '%"
					+ st.nextToken().toLowerCase() + "%'");
			++count;
		}
		return (bufferStr.toString());
	}
	
	/**
	 * write url profile error message
	 * 
	 */
	public static String writeHTMLUrlProfileMessage(String globalMsg, Set<String> message){
		
		StringBuilder sb = new StringBuilder( globalMsg );
		sb.append("<ul>");	
		if( message != null){
			for( String msg : message){
				sb.append("<li>");
				sb.append(msg);
				sb.append("</li>");			
			}
		}
		sb.append("</ul>");		
		return sb.toString();
		
	}
	
	/**
	 * write url profile error message
	 * 
	 */
	public static String writeTEXTUrlProfileMessage(String globalMsg, Set<String> message){
		
		StringBuilder sb = new StringBuilder( globalMsg );
		sb.append("\t");	
		for( String msg : message){
			sb.append("- ");
			sb.append(msg);	
			sb.append("\n");	
		}	

		return sb.toString();
		
	}
	
	
	/**
	 * - tout ce qui est lien <a></a>
	  +- [edit] [1] [2] etc tout ce qui est entre crochet (fréquent sur wikipedia)
	 * @param string
	 * @return
	 */
	public static String HtmlHrefTagsStripper(String string){
		//<a title=\"90th Infantry Division (United States)\" href=\"http://en.wikipedia.org/wiki/90th_Infantry_Division_(United_States)\">
//		resultat = resultat.replaceAll("(<(A|a)[^>]*>)+(<[^>]*>)*", ""); 
		String resultat = string.replaceAll("(<(A|a)[^>]*>)+", "");
		//</a>
//		resultat = resultat.replaceAll("(<[^>]*>)*(<\\/(A|a)[^>]*>)+", "");
		resultat = resultat.replaceAll("(<\\/(A|a)[^>]*>)+", "");
		//[edit] =[<a title=\"Edit section: British/French campaign\" href=\"http://en.wikipedia.org/w/index.php?title=Call_of_Duty_3&amp;action=edit&amp;section=3\"><span style=\"color: #0645ad;\">edit</span></a>]
		resultat = resultat.replaceAll("\\[<[^>]*>(E|e)[A-Za-z ]*(T|t)<[^>]*>\\]", "");
		resultat = resultat.replaceAll("\\[<[^>]*>(H|h)[A-Za-z ]*(e|E)<[^>]*>\\]", "");
		resultat = resultat.replaceAll("\\[[A-Za-z ]*\\]", "");
		
		
		//[1]
		resultat = resultat.replaceAll("\\[<[^>]*>*[0-9]*<[^>]*>*\\]", "");
		resultat = resultat.replaceAll("\\[[0-9]*\\]", "");
		return (resultat);
		
		
	}	
	
	/**
	 * get value of list. 
	 * @param <T> must implements equals method
	 * @param list
	 * @param obj
	 * @return
	 */
	
	public static <T> T getValueFromList(List<T> list, Object obj){
		
		int index = list.indexOf(obj);
		T response = index >= 0 ? (T) list.get(index) : null; 
		return response;
	}
	
	/**
	 * search of word in text
	 * @param value
	 * @param text
	 * @return
	 */
	public static boolean wordInText(String value, String text){
		String [] sValue = value.toLowerCase().trim().split("\\s+");
		String [] sText = text.toLowerCase().trim().split("\\s+");
		boolean here = false;
		int i = 0;
		Arrays.sort(sText);
		do {
			if( Arrays.binarySearch(sText, sValue[i]) >= 0 )
				here = true;
			i++;
		}while(i < sValue.length && !here);
		
		
		
		return here;				
	}
	
	
	/**
	 * search of word in of text
	 * he marches if the same word are used rather then the position 	
	 * @param value
	 * @param list
	 * @return
	 */
	public static boolean wordMatchesInText(String value, String text){
		String [] sText = text.split("\\s+");		
		boolean here = false;
		for(String s : sText){			
			if( s.startsWith(value) ){
				here = true;
			    break;
			}
		}		
		return here;	
	}
	
	/**
	 * convert the double to a two decimals digigs 13.3 =13.30
	 * @param d
	 * @return
	 */
   public 	static String roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#########0.00");
		return twoDForm.format(d).replace(',', '.');
   }
   
   /**
    * convert one string y input string
    * @param text
    * @return
    * @throws UnsupportedEncodingException
    */
   public static InputStream  stringToStream(String text) throws UnsupportedEncodingException {
		return new ByteArrayInputStream(text.getBytes("UTF-8"));
	} 
   

 	

	public static String translateStringMap(Map<String, Object> params){

		StringBuilder sb = new StringBuilder();		
		for(Map.Entry<String, Object> entry : params.entrySet()){
			if(! entry.getKey().equals("_")){
				sb.append("-");
				String[] values = (String[])entry.getValue();
				sb.append(values[0]);
			}
		}
		
		return sb.toString();
		
	}
	
	public static String getHttpParamsMap(Map<String, Object> params, String param){

		String value = null;		
		for(Map.Entry<String, Object> entry : params.entrySet()){
			if(entry.getKey().equals(param)){				
				String[] values = (String[])entry.getValue();
				value = values[0];
			}
		}		
		return value;		
	}
	
	public static Date getNewestDate(Date date1, Date date2){
		if(date1.before(date2)){
			return date2;
		}
		else{
			return date1;
		}
	}
	
	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static Long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    if(date1 == null && date2 == null){
	    	
	    	return null;
	    }
	    else if ( date1 == null && date2 != null){
	    	return date2.getTime();
	    }
	    else if(date2 == null){
	    	return null;
	    }
		long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
}
