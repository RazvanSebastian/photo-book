package com.repository.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.repository.model.IpAdress;

@Transactional()
public interface IpAdressRepository extends JpaRepository<IpAdress, Long> {

	@Modifying
	@Transactional
	@Query("update IpAdress client set client.numb =:numbEmailSend where client.ip=:clientIp")
	int setNumbByIp(@Param("clientIp") String ip,@Param("numbEmailSend") int numb);

	@Modifying
	@Transactional
	@Query("update IpAdress client set client.date =?2, client.numb =?3 where client.ip=?1")
	int setDateAndNumbByIp( String ip, String date,  int numb);

	@Query("select client from IpAdress client where client.ip =:clientIp")
	IpAdress findByIp(@Param("clientIp") String ip);

}
