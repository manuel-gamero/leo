package com.mg.util.text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mg.util.exception.ExceptionHandler;
import com.mg.util.io.FileUtils;

/**
 * Utility class to handle Strings.
 * 
 * @author MJGP
 * 
 */
public class StringUtils {

	public static final int RIGHT = 1;
	public static final int LEFT = 2;

	/**
	 * Replaces, in a given string , all the occurrences of string1 by string2
	 * 
	 * @param container
	 * @param theName
	 * @param theValue
	 * @return
	 */
	public static String replace(String container, String theName,
			String theValue) {
		StringBuilder finalString = new StringBuilder();

		if ((container != null) && (theName != null) && (theValue != null)) {
			int last = 0;
			int pos = container.indexOf(theName);
			while (pos != -1) {
				finalString.append(container.substring(last, pos));
				finalString.append(theValue);
				last = pos + theName.length();
				pos = container.indexOf(theName, last);
			}

			if (container.length() > last) {
				finalString.append(container.substring(last));
			}
		}
		return (finalString.toString());
	}

	/**
	 * Determines the number of times a substring matches within a String.
	 */
	public static int numMatches(String string, String subStr) {
		int returnVal = 0;
		int index = 0;
		while ((index = string.indexOf(subStr, index)) != -1) {
			++index;
			++returnVal;
		}

		return returnVal;
	}

	/**
	 * This function allows the user to call replace with a map of name/value
	 * pairs so that they can do multiple replaces on one string.
	 **/
	public static String replaceStringByMap(String container, Map keysValues) {
		String finalString = null;
		// don't change the users string
		finalString = container;
		try {
			if (keysValues != null) {
				Set keys = keysValues.keySet();
				Iterator iterator = keys.iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					finalString = replace(finalString, (String) entry.getKey(),
							(String) entry.getValue());
				}
			}
		} catch (Exception e) {
		}

		return finalString;
	}

	/**
	 * Removes all spaces and capitalizes the given string and returns a new one
	 */
	public static String toUpperCaseAndRemoveSpaces(String str) {
		if (str == null) {
			return "";
		} else {
			return replace(str, " ", "").toUpperCase();
		}
	}

	/**
	 * Returns all the characters (a-z, A-Z) and number (0-9) from the passed in
	 * string
	 */
	public static String stripToAlphanumeric(String s) {
		return stripToAlphanumeric(s, null);
	}

	public static String stripToAlphanumericSpaceReplace(String s) {
		String string = s.replace(" ", "_");
		return stripToAlphanumeric(string, '_');
	}

	public static String stripToAlphanumeric(String s, Character escapedChar) {
		String returnString = "";
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) >= '0' && s.charAt(i) <= '9')
					|| (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
					|| (s.charAt(i) >= 'a' && s.charAt(i) <= 'z')
					|| (escapedChar == null ? false
							: s.charAt(i) == escapedChar.charValue())) {

				returnString = returnString.concat(String.valueOf(s.charAt(i)));
			}
		}

		return returnString;
	}

	public static String normalizeImageName(String s, Character escapedChar) {
		String returnString = "";
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) >= '0' && s.charAt(i) <= '9')
					|| (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
					|| (s.charAt(i) >= 'a' && s.charAt(i) <= 'z')
					|| s.charAt(i) == '.'
					|| (escapedChar == null ? false
							: s.charAt(i) == escapedChar.charValue())) {

				returnString = returnString.concat(String.valueOf(s.charAt(i)));
			}
		}

		return returnString;
	}

	public static boolean isValidAscii(String s) {
		boolean returnVal = true;
		for (int x = 0; x < s.length(); x++) {
			if (s.charAt(x) < 32 || s.charAt(x) > 127) {
				returnVal = false;
				break;
			}
		}

		return returnVal;
	}

	/**
	 ** converts from internal Java String Format -> ASCII encoded effectively
	 * this removes the high ascii characters from a string
	 **/
	public static String convertToASCII(String s) {
		String out = null;
		try {
			out = new String(s.getBytes("US-ASCII"));
		} catch (java.io.UnsupportedEncodingException e) {
			return null;
		}
		return out;
	}

	/**
	 ** converts from internal Java String Format -> UTF-8 encoded effectively
	 **/
	public static String convertToUTF8(String s) {
		String out = null;
		try {
			out = new String(s.getBytes("UTF-8"));
		} catch (java.io.UnsupportedEncodingException e) {
			return null;
		}
		return out;
	}

	public static String getDisplayOrderDate(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatter.format(date);
	}

	public static String generateUrl(String partialUrl){
		return convertToUTF8( cleanPartialUrl(partialUrl).replace(" ", "-"));
	}

	public static String cleanUrl(String partialUrl){
		return convertToUTF8( partialUrl.replace("-", " "));
	}

	private static String cleanPartialUrl(String partialUrl) {
		String urlResult = partialUrl.toLowerCase().replaceAll("[àáâä]", "a");
		urlResult = urlResult.replaceAll("[èéêë]", "e");
		urlResult = urlResult.replaceAll("[ìíîï]", "i");
		urlResult = urlResult.replaceAll("[òóôö]", "o");
		urlResult = urlResult.replaceAll("[ùúûü]", "u");
		urlResult = urlResult.replaceAll("!", "");
		return cleanUrlFromSpecialChars(urlResult.trim());
	}

	/**
	 * Returns the longest word in the string (words are separated by spaces) If
	 * more than one word is the longest (same length), return the first of the
	 * longest words found if null is passed in, the empty string will be
	 * returned ("")
	 */
	public static String longestWord(String s) {
		String longestWord = "";
		if (s != null) {
			StringTokenizer tokenizer = new StringTokenizer(s, " ");
			String currentWord;
			while (tokenizer.hasMoreTokens()) {
				currentWord = tokenizer.nextToken();
				if ((currentWord != null)
						&& (currentWord.length() > longestWord.length())) {
					longestWord = currentWord;
				}
			}
		}

		return longestWord;
	}

	/**
	 * This takes a string and strips out all the letters, spaces, and
	 * punctuation to leave just numbers. If there are no numbers in the string,
	 * then the empty string is returned (""). If null is passed in, null is
	 * returned.
	 */
	public static String removeNonNumbers(String number) {
		if (number == null) {
			return null;
		} else {
			StringBuilder num = new StringBuilder();

			for (int i = 0; i < number.length(); i++) {
				if (Character.isDigit(number.charAt(i))) {
					num.append(number.charAt(i));
				}
			}

			return num.toString();
		}
	}

	/**
	 * Returns true if the string parameter is a number, false if it is not a
	 * number.
	 */
	public static boolean isNumber(String value) {
		if (value == null)
			return false;

		for (int i = 0; i < value.length(); i++) {
			if (!(Character.isDigit(value.charAt(i)))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the string that is passed in, shortened to the specified length.
	 **/
	public static String shortenStr(String toShorten, int newLength) {
		if ((toShorten == null) || (toShorten.length() < newLength)) {
			return (toShorten);
		} else {
			return (toShorten.substring(0, newLength) + "...");
		}
	}

	/**
	 * Pad a string with spaces on the right.
	 * 
	 * @param str
	 *            String to add spaces to
	 * @param width
	 *            Width of string after padding
	 */
	public static String pad(String str, int width) {
		return (padRight(str, width));
	}

	/**
	 * Pad a string with spaces on the right.
	 * 
	 * @param str
	 *            the string to add spaces to
	 * @param width
	 *            the string's width after padding
	 */
	public static String padRight(String str, int width) {
		return (pad(str, width, ' ', RIGHT));
	}

	/**
	 * Pad a string with spaces on the left.
	 * 
	 * @param str
	 *            the String to add spaces to
	 * @param width
	 *            the string's width after padding
	 */
	public static String padLeft(String str, int width) {
		return (pad(str, width, ' ', LEFT));
	}

	/**
	 * Pad a string with spaces on the right.
	 * 
	 * @param str
	 *            the String to add spaces to
	 * @param width
	 *            the string's width after padding
	 * @param padChar
	 *            the character to pad with
	 */
	public static String pad(String str, int width, char padChar) {
		return (pad(str, width, padChar, RIGHT));
	}

	/**
	 * Pad a string with spaces on the right.
	 * 
	 * @param str
	 *            the String to add spaces
	 * @param width
	 *            the string's wisth after padding
	 * @param padChar
	 *            the character to pad with
	 * @param rightORLeft
	 *            should we pad on the left or right side (use the two specified
	 *            statics)
	 */
	public static String pad(String str, int width, char padChar,
			int rightORLeft) {
		if ((str != null) && (width > str.length())) {
			StringBuilder buf = new StringBuilder(str);
			int space = width - buf.length();
			while (space-- > 0) {
				if (rightORLeft == RIGHT) {
					buf.append(padChar);
				} else { // pad left
					buf.insert(0, padChar);
				}
			}
			return buf.toString();
		} else {
			return (str);
		}
	}

	/**
	 * Remove all words (including the delimiter and space) leading up to the
	 * space.
	 * 
	 * <pre>
	 * Delimites '#' (notice the space after the second delimiter string) 
	 * 
	 * Example String: everything #in here# will be #removed#.
	 * Result String:  everything will be.
	 * </pre>
	 */
	public static String removeAllStartingWith(String str, String substr) {
		return removeAllBetween(str, substr, " ");
	}

	/**
	 * Remove all words (including the delimiters) contained between a set of
	 * delimiters.
	 * 
	 * <pre>
	 * Delimiters '#', '# ' (notice the space after the second delimiter string)
	 * 
	 * Example String: everything #in here# will be #removed#.
	 * Result String:  everything will be.
	 * </pre>
	 */

	public static String removeAllBetween(String str, String beginStr,
			String endStr) {

		int index = 0;

		StringBuilder sb = new StringBuilder(str);

		while ((index = sb.toString().indexOf(beginStr)) != -1) {

			int endIndex = sb.toString().indexOf(endStr, index);
			sb.replace(index, endIndex + endStr.length(), "");
			++index;
		}

		return sb.toString();
	}

	public static String intArrayToCommaSeperatedString(int[] theArray) {
		StringBuilder returnStr = new StringBuilder();
		if (theArray != null) {
			for (int i = 0; i < theArray.length; i++) {
				returnStr.append(theArray[i]);
				if ((i + 1) != theArray.length) {
					returnStr.append(",");
				}
			}
		}

		return (returnStr.toString());
	}

	public static String stringArrayToCommaSeperatedString(String[] theArray) {
		StringBuilder returnStr = new StringBuilder();
		if (theArray != null) {
			for (int i = 0; i < theArray.length; i++) {
				returnStr.append(theArray[i]);
				if ((i + 1) != theArray.length) {
					returnStr.append(",");
				}
			}
		}

		return (returnStr.toString());
	}

	public static String cleanStringFromSpecialChars(String value) {

		value = value.replaceAll(" ", "").replaceAll(",", "");
		value = value.replaceAll("\\!", "").replaceAll("\\.", "");
		value = value.replaceAll(":", "");
		value = value.replaceAll("'", "");
		value = value.replaceAll("/", " ");

		return value;
	}

	public static String cleanString(StringBuffer sf) {
		String str;
		str = sf.toString().replaceAll("&rsaquo;", "");
		str = str.trim().replaceAll("[\r\n\t]", "");

		return str;
	}

	public static String cleanString(String sf) {
		String str;
		str = sf.trim().replaceAll("[\r\n\t]", "");
		return str;
	}

	public static String cleanStringDoublePoint(StringBuffer sf) {
		String str;
		str = sf.toString().trim().replaceAll("[:\r\n\t]", "");
		return str;
	}

	public static String cleanStringPlatfrom(StringBuffer sf) {
		String str;
		str = sf.toString().trim().replaceAll("[\r\n\t()]", "");
		return str;
	}

	public static String cleanUrlFromSpecialChars(String s) {

		/*
		 * value = value.replaceAll(" ", "_").replaceAll(",", "_"); value =
		 * value.replaceAll("\\!", "_").replaceAll("\\.", "_"); value =
		 * value.replaceAll("\\/", "_"); value = value.replaceAll(":", "_");
		 * value = value.replaceAll("'", "_"); value = value.replaceAll("\"",
		 * "_");
		 */

		String returnString = "";
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) >= '0' && s.charAt(i) <= '9')
					|| (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
					|| (s.charAt(i) >= 'a' && s.charAt(i) <= 'z')
					|| (s.charAt(i) == ' ')) {

				returnString = returnString.concat(String.valueOf(s.charAt(i)));
			}
		}

		return returnString.replaceAll(" ", "-");
	}

	public static String cleanQueryFromSpecialChars(String s) {

		String returnString = "";
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) >= '0' && s.charAt(i) <= '9')
					|| (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
					|| (s.charAt(i) >= 'a' && s.charAt(i) <= 'z')
					|| (s.charAt(i) == '#') || (s.charAt(i) == '@')
					|| (s.charAt(i) == '&') || (s.charAt(i) == '-')
					|| (s.charAt(i) == '_') || (s.charAt(i) == ' ')) {

				returnString = returnString.concat(String.valueOf(s.charAt(i)));
			}
			/*
			 * else { returnString = returnString.concat(String.valueOf(" ")); }
			 */
		}

		return returnString;
	}

	/* Pretty standard, Return the file passed in as a string with all occurances of
    name/values replaced.
	 */
	public static String replaceFileWithHashTable(File userFile, Hashtable<String,String> nameValues){
		String fileStr = null;
		try
		{
			String name = null;
			if((userFile != null) && (nameValues != null))
			{
				try
				{
					fileStr = FileUtils.fileContentsToString(userFile);
				}
				catch(Exception e)
				{
					ExceptionHandler.handleException(e,"StringUtils.replaceFileWithHashTable() Exception:");
					System.out.println("Exception! = " + e);
				}

				//new Enumeration to iterate through the hash table.
				Enumeration<String> enum1 = nameValues.keys();
				while(enum1.hasMoreElements())
				{
					//get the key and the value and do the replace for THIS name/value pair
					name = (String)enum1.nextElement();
					fileStr = replace(fileStr, name, (String)nameValues.get(name));
				}
			}
			return(fileStr);
		}
		catch(Exception e)
		{
			ExceptionHandler.handleException(e,"StringUtils  fileStr :"+fileStr+"\n"+ nameValues);
		}
		return(fileStr);
	}

	public static boolean isNoNullNoEmpty(String string){
		if(string != null && string.trim().length() > 0 ){
			return true;
		}
		return false;
	}


	/*
	 *//**
	 * true
	 * 
	 * @param list
	 * @param string
	 * @return 
	 *         http://staffwww.dcs.shef.ac.uk/people/S.Chapman/stringmetrics.html
	 *         http
	 *         ://www.isi.edu/info-agents/workshops/ijcai03/papers/Cohen-p.pdf
	 */
	/*
	 * public static float stringMatchingPorcentage (String strToCheck, String
	 * strCheker){
	 * 
	 * AbstractStringMetric metric = new JaroWinkler(); float result =
	 * metric.getSimilarity(strToCheck, strCheker); return result;
	 * 
	 * 
	 * }
	 */

	public static String removeHtmlTag(String html){
		String result = html.replaceAll("<[^>]*>", "");
		//String result = html.replaceAll("<[^>]+>",""); 
		return result;
	}

	public static List<String> getListRegexMatches(String message, String regex) {
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile(regex).matcher(message);
		while (m.find()) {
			allMatches.add(m.group());
		}
		return allMatches;
	}
}
