package com.repository.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.repository.model.PhotoAlbum;
import com.repository.model.User;

public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum,Long>{
	List<PhotoAlbum> findByUserOrderByDateDesc(User user);
	
	PhotoAlbum findById(Long id);
	
	PhotoAlbum findByDate(Date date);
	
}
