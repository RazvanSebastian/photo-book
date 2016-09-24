package com.restApi;

import java.util.List;

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

import com.entity.DTO.PhotoAlbumDto;
import com.repository.model.PhotoAlbum;
import com.service.PhotoAlbumService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PhotoAlbumController {
	
	@Autowired
	private PhotoAlbumService albumService;
	
	@RequestMapping(value="/api/account/{userId}/photoAlbum",method=RequestMethod.POST)
	public ResponseEntity<String> receviceNewAlbum(@PathVariable(value="userId") long id ,
			@RequestBody PhotoAlbumDto photoAlbum){
		try {
			this.albumService.saveNewAlbum(id,photoAlbum);
			return new ResponseEntity<String>("succes",HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="api/account/{userId}/clientAlbums",method=RequestMethod.GET)
	public @ResponseBody List<PhotoAlbumDto> sendAllAlbums(@PathVariable(value="userId") long id){
		return this.albumService.getAllAlbums(id);
	}

}
