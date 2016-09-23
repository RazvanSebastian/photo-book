package com.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.DTO.PhotoAlbumDto;
import com.repository.model.PhotoAlbum;
import com.repository.repository.PhotoAlbumRepository;
import com.repository.repository.UserRepository;
import com.service.local.BlobString;
import com.service.local.ImageService;

@Service
public class PhotoAlbumService {
	
	@Autowired
	private PhotoAlbumRepository albumRepository;
	@Autowired
	private UserRepository userRepostiroy;
	@Autowired
	 BlobString blobStringConverter;
	@Autowired
	 ImageService imageService;
	
	
	public void saveNewAlbum(Long userId,PhotoAlbumDto photoAlbum) throws Exception{		
		this.checkAlbumDetails(photoAlbum);
		PhotoAlbum album=new PhotoAlbum();
		album.setName(photoAlbum.getName());
		album.setDescription(photoAlbum.getDescription());
		album.setCategory(photoAlbum.getCategory());
		album.setDate(new Date());
		this.imageService.imageVerification(photoAlbum.getCoverImage());
		album.setCoverImage(this.blobStringConverter.convertToBlob(photoAlbum.getCoverImage()));
		album.setUserAlbum(this.userRepostiroy.findById(userId));
		this.albumRepository.save(album);
	}
	
	private void checkAlbumDetails(PhotoAlbumDto album) throws Exception{
		if(album.getName().equals(""))
				throw new Exception("Name field are rquired");
		if(album.getDescription().equals(""))
			throw new Exception("Description field are rquired");
		if(album.getCategory().equals(""))
			throw new Exception("Category field are rquired");
	}

}
