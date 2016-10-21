package com.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.repository.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
	User findByEmail(String email);
	User findById(Long id);
	User findByUsername(String user);
}
