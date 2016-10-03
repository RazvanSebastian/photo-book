package com.restApi;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.DTO.PhotoDto;
import com.service.PhotoService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	@RequestMapping(value="/api/my-album/{idAlbum}/client-photos",method=RequestMethod.GET)
	public List<PhotoDto> sendPhotosFromAlbum(@PathVariable(value="idAlbum") Long idAlbum){
		return this.photoService.receivePhotoAlbum(idAlbum);
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
}
