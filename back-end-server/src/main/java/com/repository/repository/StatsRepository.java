package com.repository.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.repository.model.HomePageStats;

@Transactional()
public interface StatsRepository extends JpaRepository<HomePageStats,Long>{
	
	@Modifying
	@Transactional
	@Query("UPDATE HomePageStats stats SET stats.number=:updateValue WHERE stats.name=:name")
	int updateStatsValue(@Param("updateValue") Long value,@Param("name") String name);
	
	@Query("SELECT stats FROM HomePageStats stats WHERE stats.name=:name")
	HomePageStats findByName(@Param("name") String name);
	
	@Query("SELECT stats.number FROM HomePageStats stats WHERE stats.name=:name")
	Long getNumbeOfStatsByName(@Param("name") String name);

	@Query("SELECT stats.number FROM HomePageStats stats")
	List<Long> getAllNumbersOfStats();
}
