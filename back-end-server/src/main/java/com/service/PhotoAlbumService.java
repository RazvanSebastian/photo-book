package com.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.DTO.PhotoAlbumDto;
import com.repository.model.Photo;
import com.repository.model.PhotoAlbum;
import com.repository.repository.PhotoAlbumRepository;
import com.repository.repository.PhotoRepository;
import com.repository.repository.StatsRepository;
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
	 private BlobString blobStringConverter;
	@Autowired
	 private ImageService imageService;
	@Autowired
	private PhotoRepository photoRepository;
	@Autowired
	private StatsRepository statsRepo;
	/*
	 * Save new album
	 * */
	public void saveNewAlbum(Long userId,PhotoAlbumDto photoAlbum) throws Exception{		
		this.checkAlbumDetails(photoAlbum);
		PhotoAlbum album=new PhotoAlbum();
		album.setName(photoAlbum.getName());
		album.setDescription(photoAlbum.getDescription());
		album.setCategory(photoAlbum.getCategory());
		album.setDate(photoAlbum.getDate());
		this.imageService.newPhotoVerification(photoAlbum.getCoverImage());
		album.setCoverImage(this.blobStringConverter.convertStringToBlob(photoAlbum.getCoverImage()));
		album.setUser(this.userRepostiroy.findById(userId));
		Long albumId=this.albumRepository.save(album).getId();
		
		Photo photoCover=new Photo(this.albumRepository.findById(albumId),
				"Cover image",				
				photoAlbum.getDescription(),
				photoAlbum.getCategory(),(long) 0,5,photoAlbum.getDate(),
				this.blobStringConverter.convertStringToBlob(photoAlbum.getCoverImage()));
		this.statsRepo.updateStatsValue(this.statsRepo.getNumbeOfStatsByName("albums")+1, "albums");
		this.photoRepository.save(photoCover);
	}
	
	private void checkAlbumDetails(PhotoAlbumDto album) throws Exception{
		if(album.getName().equals(""))
				throw new Exception("Name field are rquired");
		if(album.getDescription().equals(""))
			throw new Exception("Description field are rquired");
		if(album.getCategory().equals(""))
			throw new Exception("Category field are rquired");
	}
	
	/*
	 * Receive all albums
	 * */
	public List<PhotoAlbumDto> getAllAlbums(long id){
		List<PhotoAlbumDto> albumToSend=new ArrayList<>();
		for(PhotoAlbum album:this.albumRepository.findByUserOrderByDateDesc(this.userRepostiroy.findById(id)))
			albumToSend.add(this.imageService.convertToDto(album));
		return albumToSend;
	}
	
	/*
	 * Receive album by id for details header page
	 * */
	
	public PhotoAlbumDto getAlbumById(long id){
		return this.imageService.convertToDto(this.albumRepository.findById(id));
	}
	
	/*
	 * Delete album (lose all photos)	
	 */
	
	public void deleteAlbumById(Long id){
		this.albumRepository.delete(id);
	}
	
	/*
	 Get all categories distinct
	 * */
	public List<String> receviceCategory(){
		List<String> categories=new ArrayList<>();
		for(String photoCategory:this.albumRepository.findAllCategoryDistinct())
			categories.add(photoCategory);
		return categories;
	}
	
}
