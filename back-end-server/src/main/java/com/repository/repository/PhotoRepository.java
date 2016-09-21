package com.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.repository.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo,Long>{

}
