package com.service;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.DTO.PhotoDto;
import com.repository.model.Photo;
import com.repository.repository.PhotoAlbumRepository;
import com.repository.repository.PhotoRepository;
import com.repository.repository.StatsRepository;
import com.service.local.BlobString;
import com.service.local.ImageService;
import com.service.local.PaginationService;

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
	@Autowired
	private PaginationService paginationService;
	@Autowired
	private StatsRepository statsRepo;
	
	public PhotoDto getOriginalPhotoById(Long id){
		PhotoDto photo=new PhotoDto();
		photo.setId(id);
		photo.setImage(this.converter.convertBlobToString(this.photoRepository.findById(id).getImage()));
		this.statsRepo.updateStatsValue(this.statsRepo.getNumbeOfStatsByName("downloads")+1, "downloads");
		return photo;
	}
	
	public Long numberOfPhotos(Long id){
		return this.photoRepository.countByAlbum(this.albumRepository.findById(id));
	}
	
	public List<PhotoDto> receivePhotoAlbum(Long id,Integer pageNumber){
		return this.imageService.convertPhotoToDto(this.paginationService.getPhotosByPage(id, pageNumber));
		
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
		this.statsRepo.updateStatsValue(this.statsRepo.getNumbeOfStatsByName("photos")+1, "photos");
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
	
	private Date setDate(String date){
		Date dateTime = new Date();	
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		
		switch(date){
		case "today": 
			cal.add(Calendar.HOUR_OF_DAY, -24);
			dateTime=cal.getTime();
			break;
		case "week":
			cal.add(Calendar.WEEK_OF_YEAR, -1);
			dateTime=cal.getTime();
			break;
		case "month":
			cal.add(Calendar.MONTH,-1);
			dateTime=cal.getTime();
			break;
		case "year":
			cal.add(Calendar.YEAR, -1);
			dateTime=cal.getTime();
			break;
		}
		return dateTime;
	}
	
	public List<PhotoDto> getPhotoListByCriterium(String category,String date,String search){
		if(category.equals("") && date.equals("") && search.equals(""))
			return new ArrayList<PhotoDto>();
		
		if(!category.equals("") && date.equals("") && search.equals("")){
			return this.imageService.convertPhotoToDto(
					this.photoRepository.findByCategoryOrderByDateDesc(category));
		}
		
		if(category.equals("") && !date.equals("") && search.equals(""))
			return this.imageService.convertPhotoToDto(
					this.photoRepository.findByDateAfter(this.setDate(date)));
		
		if(category.equals("") && date.equals("") && !search.equals(""))
			return this.imageService.convertPhotoToDto(
					this.photoRepository.findByNameContaining(search));
		
		//by category and date
		if(!category.equals("") && !date.equals("") && search.equals(""))
			return this.imageService.convertPhotoToDto(
					this.photoRepository.findByCategoryAndDateAfter(category, this.setDate(date)));
		
		//by category and name
		if(!category.equals("") && date.equals("") && !search.equals(""))
			return this.imageService.convertPhotoToDto(
					this.photoRepository.findByCategoryAndNameContaining(category, search));
		
		//by date and name
		if(category.equals("") && !date.equals("") && !search.equals(""))
			return this.imageService.convertPhotoToDto(
					this.photoRepository.findByNameContainingAndDateAfter(search, this.setDate(date)));
		
		//by all
		if(!category.equals("") && !date.equals("") && !search.equals(""))
			return this.imageService.convertPhotoToDto(
					this.photoRepository.findByNameContainingAndCategoryAndDateAfter(search, category, this.setDate(date)));
		return null;
	}
}
