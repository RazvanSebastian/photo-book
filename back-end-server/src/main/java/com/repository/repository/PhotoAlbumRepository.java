package com.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.repository.model.PhotoAlbum;

public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum,Long>{

}
