package com.service.local;

import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	public void imageVerification(String image) throws Exception{
		if(!image.equals("")){
		if(!(image.substring(11, 15).equals("jpeg") || 
				image.substring(11, 14).equals("png") || image.substring(11, 14).equals("jpg")))
			throw new Exception("Wrong format of image!");
		if(image.length()>=150000)
			throw new Exception("Image choosed has too big size!");
		}
		else
			return;
	}
}
