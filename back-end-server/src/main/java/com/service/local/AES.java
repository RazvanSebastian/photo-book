package com.service.local;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import org.apache.commons.codec.binary.Base64;

@Service
public class AES
{
	private static final String VALUES="qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM"; 
    
//	    public static String encrypt(String key,String ivInit,  String value) {
//	        try {
//	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
//	            IvParameterSpec iv = new IvParameterSpec(ivInit.getBytes("UTF-8"));
//	            
//	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec , iv );
//
//	            byte[] encrypted = cipher.doFinal(value.getBytes());       
//	            
//	            System.out.println("encrypted string: "
//	                    + DatatypeConverter.printBase64Binary(encrypted));
//
//	            return "9kNOsGeNImfT2FEGt4p9UDdyIuBhqSTFkc2cJ+oWPtQ=";
//	        } catch (Exception ex) {
//	            ex.printStackTrace();
//	        }
//
//	        return null;
//	    }
	
		public  String generateRandomIv16Byte(){
			String randomIv="";
			int index;
			for(int i=1;i<=16;i++){
				index=(int) (Math.random()*(62-0))+0;
				randomIv+=VALUES.substring(index, index+1);
			}
			return randomIv;
		}
			
		public String convertToBase64(String planeText){
			byte[]   bytesEncoded = Base64.encodeBase64(planeText .getBytes());
			return new String(bytesEncoded) ;
		}

	    public String decrypt(String key,String ivInit, String encryptedBase64) {
	        try {
	      
	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
	            IvParameterSpec iv = new IvParameterSpec(ivInit.getBytes("UTF-8"));
	            
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	            cipher.init(cipher.DECRYPT_MODE, skeySpec,iv);
	  
	            byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(encryptedBase64));

	            return new String(original);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }

	    
//	    private static  String padding(String text){
//	    	String textPadd=text;
//	    	int rest=text.length()%16;
//	    	int lengthPadd=15-rest;
//	    	char charPadd=' ';
//	    	for(int i=0;i<=lengthPadd;i++)
//	    		textPadd+=charPadd;
//	    	return textPadd;
//	    }

//	    public static void main(String[] args) {
//	        String key = "0123456789abcdef"; // 128 bit key =16 bytes
//	        String initVector = AES.generateRandomIv16Byte(); // 16 bytes IV
//	        System.out.println(initVector);
//	        System.out.println(AES.convertToBase64(initVector));
//	        System.out.println(decrypt(key,"KKRmrDTPsB70yK8j","Bin0URwYXovAbl6nGbixGMPuLjxX061SCrDGf8Zz1/A="));
//	    }
}