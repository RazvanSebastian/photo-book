package com.service.local;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.repository.model.Photo;
import com.repository.repository.PhotoAlbumRepository;
import com.repository.repository.PhotoRepository;

@Service
public class PaginationService {
	
	@Autowired
	private PhotoRepository photoRepository;
	@Autowired
	private PhotoAlbumRepository albumRepository;
	@Autowired
	private ImageService imageService;
	
	public List<Photo> getPhotosByPage(long album,int pageNumb){
		int size=8;
		PageRequest request=new PageRequest(pageNumb-1, size);
		return this.convertPageToList(this.photoRepository.findByAlbumOrderByDateDesc
				(this.albumRepository.findById(album), request));
	}
	
	private List<Photo> convertPageToList(Page<Photo> photoPage){
		List<Photo> photos=new ArrayList<Photo>();
		Iterator<Photo> iterator= photoPage.iterator();
		while(iterator.hasNext())
			photos.add(iterator.next());
		return photos;
	}
}
