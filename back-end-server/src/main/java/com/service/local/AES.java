package com.service.local;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AES
{
    
	    public static String encrypt(String key,  String value) {
	        try {
	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec );

	            byte[] encrypted = cipher.doFinal(value.getBytes());       
	            
	            System.out.println("encrypted string: "
	                    + DatatypeConverter.printBase64Binary(encrypted));

	            return "RG3s2FPBkxgShh9thcF53jdyIuBhqSTFkc2cJ+oWPtQ=";
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }

	    public static String decrypt(String key, String encrypted) {
	        try {
	      
	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(cipher.DECRYPT_MODE, skeySpec);
	  
	            byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted));

	            return new String(original);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }

	    
	    private static  String padding(String text){
	    	String textPadd=text;
	    	int rest=text.length()%16;
	    	int lengthPadd=15-rest;
	    	char charPadd=' ';
	    	for(int i=0;i<=lengthPadd;i++)
	    		textPadd+=charPadd;
	    	return textPadd;
	    }

	    public static void main(String[] args) {
	        String key = "0123456789abcdef"; // 128 bit key =16 bytes
	        String initVector = "0123456789abcdef"; // 16 bytes IV
	        
	        System.out.println(decrypt(key,
	                encrypt(key, padding("Hello, World!"))));
	    }
}