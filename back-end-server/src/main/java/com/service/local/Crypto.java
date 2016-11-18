package com.service.local;
import java.security.*;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Crypto {
	

	private static final byte[] KEY= new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
			'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
	private static final String ALGHORITM="AES";
	
	 public static String decrypt(String encryptedData) throws Exception {
	        Key key = generateKey();
	        Cipher c = Cipher.getInstance(ALGHORITM);
	        c.init(Cipher.DECRYPT_MODE, key);
	        byte[] decordedValue = DatatypeConverter.parseBase64Binary(encryptedData);
	        byte[] decValue = c.doFinal(decordedValue);
	        String decryptedValue = new String(decValue);
	        return decryptedValue;
	    }
	    private static Key generateKey() throws Exception {
	        Key key = new SecretKeySpec(KEY, ALGHORITM);
	        return key;
	}

}
