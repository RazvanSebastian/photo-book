package com.service.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.DTO.PhotoAlbumDto;
import com.repository.model.PhotoAlbum;

@Service
public class ImageService {
	
	@Autowired
	private BlobString converte;
	
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
	
	public PhotoAlbumDto convertToDto(PhotoAlbum album){
		PhotoAlbumDto dtoAlbum=new PhotoAlbumDto();
		dtoAlbum.setName(album.getName());
		dtoAlbum.setDescription(album.getDescription());
		dtoAlbum.setDate(album.getDate());
		dtoAlbum.setCategory(album.getCategory());
		dtoAlbum.setCoverImage(this.converte.convertBlobToString(album.getCoverImage()));
		return dtoAlbum;
	}
}
