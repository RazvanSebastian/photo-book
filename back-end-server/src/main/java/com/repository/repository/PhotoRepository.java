package com.repository.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.repository.model.Photo;
import com.repository.model.PhotoAlbum;

public interface PhotoRepository extends JpaRepository<Photo,Long>{
	List<Photo> findByAlbumOrderByDateDesc(PhotoAlbum album);
	
	Photo findById(Long id);
}
