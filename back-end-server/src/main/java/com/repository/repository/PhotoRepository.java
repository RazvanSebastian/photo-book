package com.repository.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.repository.model.Photo;
import com.repository.model.PhotoAlbum;

public interface PhotoRepository extends JpaRepository<Photo,Long>{
	
	
	Page<Photo> findByAlbumOrderByDateDesc(PhotoAlbum album,Pageable pageable);
	
	Photo findById(Long id);
	
	Long countByAlbum(PhotoAlbum album);
	
	List<Photo> findByNameContaining(String name);
	
	List<Photo> findByCategoryOrderByDateDesc(String category);
	
	List<Photo> findByDateAfter(Date date);
		
	List<Photo> findByCategoryAndDateAfter(String category,Date date);
	
	List<Photo> findByCategoryAndNameContaining(String category,String name);

	List<Photo> findByNameContainingAndDateAfter(String name,Date date);
	
	List<Photo> findByNameContainingAndCategoryAndDateAfter(String name,String category,Date date);
}
