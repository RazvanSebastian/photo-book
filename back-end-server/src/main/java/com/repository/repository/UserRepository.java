package com.repository.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.repository.model.User;

@Transactional()
public interface UserRepository extends JpaRepository<User,Long>{
	User findByEmail(String email);
	User findById(Long id);
	User findByUsername(String user);
	
	@Query("SELECT user.password FROM User user where user.username=:username")
	String getPasswordByUsername(@Param("username") String username);
	
	@Modifying
	@Transactional
	@Query("UPDATE User user SET user.password=:password where user.username=:username")
	void updatePasswordByUserName(@Param("password") String password,@Param("username") String username);
	
	@Modifying
	@Transactional
	@Query("update User user set user.email=?1 , user.firstName=?2 , user.lastName=?3 , user.birthDay=?4 where user.username=?5")
	void updateUserProfileByUsername(String email,String firstname,String lastNme,Date birthDay,String username);
}
