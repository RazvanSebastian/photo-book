package com.restApi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.DTO.PhotoDto;
import com.repository.model.Photo;
import com.repository.repository.PhotoRepository;
import com.service.PhotoService;
import com.service.local.ImageService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	//to delete after test
	@Autowired
	private ImageService imageService;
	@Autowired
	private PhotoRepository repo;
	
	@RequestMapping(value="/api/photo-number/album={id}" , method=RequestMethod.GET)
	public Long sendTotalPhotos(@PathVariable(value="id") long id){
		return this.photoService.numberOfPhotos(id);
	}
	
	@RequestMapping(value="/api/my-album/{idAlbum}/client-photos/page={pageNumb}",method=RequestMethod.GET)
	public List<PhotoDto> sendPhotosFromAlbum(@PathVariable(value="idAlbum") Long idAlbum,
			@PathVariable(value="pageNumb") int pageNumb){
		return this.photoService.receivePhotoAlbum(idAlbum,pageNumb);
	}
	
	@RequestMapping(value="/api/my-album/{idAlbum}/new-photo",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> saveNewPhoto(@PathVariable(value="idAlbum") Long idAlbum,
			@RequestBody PhotoDto newPhoto ){
		System.out.println(newPhoto.getName());
		try {
			this.photoService.savePhoto(idAlbum, newPhoto);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/api/my-album/delete-photo/{idPhoto}",method=RequestMethod.DELETE)
	public ResponseEntity<String> deletePhoto(@PathVariable(value="idPhoto") Long idPhoto){
		this.photoService.deletePhotoById(idPhoto);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/search-photo/category={category}/date={date}/search={search}" , method=RequestMethod.GET)
	public List<PhotoDto> getPhotoBySearch(@PathVariable(value="category") String category,
			@PathVariable(value="date") String date,
			@PathVariable(value="search") String search){

		return this.photoService.getPhotoListByCriterium(category, date, search);
	}
	
	@RequestMapping(value="/api/my-album/download/original={id}" , method=RequestMethod.GET)
	public PhotoDto getOriginalImage(@PathVariable(value="id") Long id){
		return this.photoService.getOriginalPhotoById(id);
	}
}
