package com.restApi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.HomePageService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HomePageController {
	
	@Autowired
	private HomePageService statsService;
	
	@RequestMapping(value="/api/stats",method=RequestMethod.GET)
	public List<Long> getHomePageStats(){
		return this.statsService.getStatsNumbers();
	}

}
