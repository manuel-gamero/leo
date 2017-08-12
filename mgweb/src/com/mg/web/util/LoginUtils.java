package com.mg.web.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;

import com.mg.service.dto.UserSessionDTO;
import com.mg.web.WebConstants;

/**
 * Logic that handle logging in to the system goes here.
 * @author MJGP
 *
 */
public final class LoginUtils {
	
	// Utility class, no instantiation
	private LoginUtils(){};
	
	public static boolean isLogged(HttpServletRequest request){
		return request.getSession().getAttribute(WebConstants.USER) != null;
	}
	
	public static UserSessionDTO getUser(HttpServletRequest request){
		return (UserSessionDTO) request.getSession().getAttribute(WebConstants.USER);
	}
	
	public static String getUserLanguage(HttpServletRequest request){
		String userLanguage = null;
		if(request.getSession().getAttribute(WebConstants.USER) != null){
			// TODO return user language from profile
			return null;
		}
		
		return userLanguage;
	}
	
	public static String md5(String input) {
		
		String md5 = null;
		if(null == input) return null;
		try {
		//Create MessageDigest object for MD5
		MessageDigest digest = MessageDigest.getInstance("MD5");
	
		//Update input string in message digest
		digest.update(input.getBytes(), 0, input.length());

		//Converts message digest value in base 16 (hex) 
		md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return md5;
	}
	
	public static String get_SHA_512_SecurePassword(String input) {
		
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(getSalt().getBytes());
			byte[] bytes = md.digest(input.getBytes());
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	private static String getSalt() throws NoSuchAlgorithmException
	{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt.toString();
	}
	
	public static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		int iterations = 1000;
		char[] chars = password.toCharArray();
		byte[] salt = getSalt().getBytes();
		
		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}
	
	private static String toHex(byte[] array) throws NoSuchAlgorithmException
	{
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if(paddingLength > 0)
		{
			return String.format("%0"  +paddingLength + "d", 0) + hex;
		}else{
			return hex;
		}
	}
}
