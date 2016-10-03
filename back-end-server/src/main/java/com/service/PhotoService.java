package com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.DTO.PhotoDto;
import com.repository.model.Photo;
import com.repository.repository.PhotoAlbumRepository;
import com.repository.repository.PhotoRepository;
import com.service.local.BlobString;
import com.service.local.ImageService;

@Service
public class PhotoService {
	
	@Autowired 
	private PhotoRepository photoRepository;
	@Autowired
	private PhotoAlbumRepository albumRepository;
	@Autowired
	private BlobString converter;
	@Autowired
	private ImageService imageService;
	
	public List<PhotoDto> receivePhotoAlbum(Long id){
		List<PhotoDto> listToSend=new ArrayList<PhotoDto>();
		for(Photo photo: this.photoRepository.findByAlbumOrderByDateDesc(this.albumRepository.findById(id)))
			listToSend.add(this.convertToDto(photo));
		return listToSend;
	}
	
	private PhotoDto convertToDto(Photo photo){
		return new PhotoDto(photo.getId(),photo.getName(),photo.getDescription(),photo.getCategory(),
				photo.getVisualisations(),photo.getRating(),photo.getDate(),
				this.converter.convertBlobToString(photo.getImage()));
	}
	
	
	//Save new photo for existing album
	public void savePhoto(Long idAlbum,PhotoDto newPhoto) throws Exception{
		Photo photo=new Photo();
		this.checkInfo(newPhoto);
		this.imageService.newPhotoVerification(newPhoto.getImage());
		photo.setName(newPhoto.getName());
		photo.setCategory(this.albumRepository.findById(idAlbum).getCategory());
		photo.setDescription(newPhoto.getDescription());
		photo.setDate(new Date());
		photo.setImage(this.converter.convertStringToBlob(newPhoto.getImage()));
		photo.setRating(5);
		photo.setVisualisations((long) 0);
		photo.setAlbum(this.albumRepository.findById(idAlbum));
		this.photoRepository.save(photo);
	}
	
	private void checkInfo(PhotoDto newPhoto) throws Exception{
		if(newPhoto.getName().equals(""))
			throw new Exception("Name field is required!");
		if(newPhoto.getImage().equals(""))
			throw new Exception("You must chose an image!");
	}
	
	public void deletePhotoById(Long id){
		this.photoRepository.delete(this.photoRepository.findById(id));
	}
}
