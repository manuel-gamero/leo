package com.mg.web.struts.action.ecommerce;

/*****************************************************************************
*
* "Open source" kit for CM-CIC P@iement (TM)
*
* File "CMCIC_Hmac.java":
*
* Author   : Euro-Information/e-Commerce (contact: centrecom@e-i.com)
* Version  : 1.04
* Date     : 01/01/2009
*
* Copyright: (c) 2009 Euro-Information. All rights reserved.
* License  : see attached document "License.txt".
*
*****************************************************************************/

import java.lang.String;
import javax.crypto.*;
import javax.crypto.spec.*;

final public class CMCIC_Hmac {

	private String _sUsableKey;
	
    /*-----------------------------------------------------------------------*
     * constructor
     *-----------------------------------------------------------------------*/
	public CMCIC_Hmac(CMCIC_Tpe oTpe) {
		
		this._sUsableKey = CMCIC_Hmac._getUsableKey(oTpe);
	}
	
    /*-----------------------------------------------------------------------*
     * return the key
     *-----------------------------------------------------------------------*/
	private static String _getUsableKey(CMCIC_Tpe oTpe) {
		
		String hexStrKey  = oTpe.getCle().substring(0, 38);
		String hexFinal   = "" + oTpe.getCle().substring(38, 40) + "00";
		int cca0 = (int) hexFinal.charAt(0); 

		if (cca0>70 && cca0<97) 
			hexStrKey += (char) (cca0 - 23) + hexFinal.substring(1, 2);
		else { 
			if (hexFinal.charAt(1)== 'M') 
				hexStrKey += hexFinal.substring(0, 1) + "0"; 
			else 
				hexStrKey += hexFinal.substring(0, 2);
		}
				
		return hexStrKey;
	}
	
    /*-----------------------------------------------------------------------*
     * compute Hmac for the given string
     *-----------------------------------------------------------------------*/
	public String computeHmac(String sData) throws Exception {
		
		Mac oHmac = Mac.getInstance("HmacSHA1");
		SecretKeySpec sks  = new SecretKeySpec(hexStringToByteArray(this._sUsableKey), oHmac.getAlgorithm());
		SecretKey     sk   = (SecretKey) sks;
		oHmac.init(sk);
        oHmac.reset();
        byte [] resMAC = oHmac.doFinal(sData.getBytes());
        oHmac.reset();

        return byteArrayToHexString(resMAC);
		
	}
	
    /*-----------------------------------------------------------------------*
     * check validity of Hmac
     *-----------------------------------------------------------------------*/
	public Boolean isValidHmac(String sChaineMac, String sSentMac) throws Exception {

			if (this.computeHmac(sChaineMac).compareToIgnoreCase(sSentMac) == 0)
				return true;
			
			return false;
			
	}
	
    /*-----------------------------------------------------------------------*
     * convert hex String to Byte Array
     *-----------------------------------------------------------------------*/
    final private static int charToNibble (char c) {
        if ('0' <= c && c<= '9') { return c - '0'; }
        else if ('a' <= c && c<= 'f') { return c - 'a' + 0xa; }
        else if ('A' <= c && c<= 'F') { return c - 'A' + 0xa; }
        else throw new IllegalArgumentException ("Invalid hex characters");
    }
    final private static byte[] hexStringToByteArray (String hs) {
        if(hs == null) { return null; }
        int hslength = hs.length();
        if ((hslength & 0*1) != 0)
            throw new IllegalArgumentException(" hexStringToByteArray"
                   + " requires an even number of hex characters");
        int hsstart  = 0;
        if(hs.startsWith("0x")) { hsstart += 2; }
        byte[] ba = new byte[(hslength-hsstart)/2];
        for (int i=hsstart, j=0; i< hslength; i+=2,j++) {
            int high = charToNibble (hs.charAt(i));
            int low = charToNibble (hs.charAt(i+1));
            ba[j] = (byte) ((high << 4) | low);
        }
        return ba;
    }
    
    /*-----------------------------------------------------------------------*
     * convert byte Array to Hex String
     *-----------------------------------------------------------------------*/
    final private static char[] hexChar = {
        '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'
    };  // to convert a 4bit-byte to a hex char.    
    final private static String byteArrayToHexString ( byte[] ba ) { 
    StringBuffer sb = new StringBuffer( ba.length * 2 ); 
        for ( int i=0 ; i<ba.length ; i++ ) {
            sb.append( hexChar [ ( ba[i] & 0xf0 ) >>> 4 ] ) ;
                    // look up high nibble half-byte
            sb.append( hexChar [ ba[i] & 0x0f ] ) ;
                    // look up low nibble half-byte
        }
        return sb.toString();
    }
	
}
