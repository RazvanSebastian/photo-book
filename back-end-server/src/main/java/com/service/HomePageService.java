package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.repository.model.HomePageStats;
import com.repository.repository.PhotoAlbumRepository;
import com.repository.repository.PhotoRepository;
import com.repository.repository.StatsRepository;
import com.repository.repository.UserRepository;

@Service
public class HomePageService {
	
	@Autowired
	private StatsRepository statsRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PhotoAlbumRepository albumRepo;
	@Autowired
	private PhotoRepository photoRepository;
	
	private void initiEmptyStatsTable(){
		if(statsRepo.findAll().isEmpty()==true){
			this.statsRepo.save(new HomePageStats("clients",this.userRepo.count()));
			this.statsRepo.save(new HomePageStats("downloads",(long)0));
			this.statsRepo.save(new HomePageStats("albums",this.albumRepo.count()));
			this.statsRepo.save(new HomePageStats("photos",this.photoRepository.count()));
		}
	}
	
	public List<Long> getStatsNumbers(){
		this.initiEmptyStatsTable();
		List<Long> statsNumbers=new ArrayList<>();
		statsNumbers.addAll(this.statsRepo.getAllNumbersOfStats());
		return statsNumbers;
	}
}
